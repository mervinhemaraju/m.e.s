package com.th3pl4gu3.mauritius_emergency_services.ui.screens.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.th3pl4gu3.mauritius_emergency_services.data.local.LocalServiceRepository
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import com.th3pl4gu3.mauritius_emergency_services.ui.wrappers.NetworkRequests
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.GetAppLocale
import com.th3pl4gu3.mauritius_emergency_services.utils.NetworkRequestException
import com.th3pl4gu3.mauritius_emergency_services.utils.TIMEOUT_MILLIS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ServicesViewModel @Inject constructor(
    private val offlineServiceRepository: LocalServiceRepository,
    private val onlineServiceRequests: NetworkRequests
) : ViewModel() {

    /**
     * Create a private ServicesUiState to hold the MutableStateFlow of the default items
     * It starts as Loading
     **/
    private val mServicesUiState: MutableStateFlow<ServicesUiState> =
        MutableStateFlow(ServicesUiState.Loading)

    /**
     * Create accessible ServicesUiState for the Composable to collect
     **/
    val servicesUiState: StateFlow<ServicesUiState> = mServicesUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = ServicesUiState.Loading
    )

    /**
     * On Init, load the offline services
     **/
    init {
        viewModelScope.launch {
            loadOfflineServices()
        }
    }

    /**
     * Public Functions
     **/
    fun reload() =
        viewModelScope.launch(Dispatchers.IO) {
            mServicesUiState.value = try {

                // Get services online
                val services = loadOnlineServices()

                // Force refresh the services
                offlineServiceRepository.forceRefresh(
                    services = services
                )

                // Return the UI State
                ServicesUiState.Success(services.sortedBy { it.name })
            } catch (e: NetworkRequestException) {
                ServicesUiState.NoNetwork
            } catch (e: IOException) {
                ServicesUiState.Error
            } catch (e: HttpException) {
                ServicesUiState.Error
            }
        }

    /**
     * Private Functions
     **/
    private suspend fun loadOnlineServices() =
        onlineServiceRequests.getMesServices(language = GetAppLocale).services

    private suspend fun loadOfflineServices() =
        offlineServiceRepository.getAllServices().collect { services ->
            mServicesUiState.value = servicesDisplayDecision(services.sortedBy { it.name })
        }

    private fun servicesDisplayDecision(services: List<Service> = listOf()) =
        if (services.isNotEmpty()) {
            ServicesUiState.Success(services)
        } else {
            ServicesUiState.NoContent
        }
}