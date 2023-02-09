package com.th3pl4gu3.mauritius_emergency_services.ui.screens.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.th3pl4gu3.mauritius_emergency_services.data.local.LocalServiceRepository
import com.th3pl4gu3.mauritius_emergency_services.ui.wrappers.NetworkRequests
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.GetAppLocale
import com.th3pl4gu3.mauritius_emergency_services.utils.NetworkRequestException
import com.th3pl4gu3.mauritius_emergency_services.utils.TIMEOUT_MILLIS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
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
     * Create a private ServicesUiState to hold the MutableStateFlow of the items
     * It starts as Loading
     **/
    private val mServicesUiState: MutableStateFlow<ServicesUiState> =
        MutableStateFlow(ServicesUiState.Loading)

    /**
     * Create an accessible ServicesUiState for the Composable to collect
     **/
    val servicesUiState: StateFlow<ServicesUiState> = mServicesUiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ServicesUiState.Loading
        )

    /**
     * On Init, load the offline services
     **/
    init {
        loadOfflineServices()
    }

    /**
     * Public Functions
     **/
    fun loadOnlineServices() =
        viewModelScope.launch(Dispatchers.IO) {
            mServicesUiState.value = try {
                val services = onlineServiceRequests.getMesServices(language = GetAppLocale).services

                // Force refresh the services
                offlineServiceRepository.forceRefresh(
                    services = services
                )
                ServicesUiState.Success(services.sortedBy { it.name })
            } catch (e: NetworkRequestException) {
                ServicesUiState.NoNetwork
            }
            catch (e: IOException) {
                ServicesUiState.Error
            } catch (e: HttpException) {
                ServicesUiState.Error
            }
        }

    fun search(query: String) =
        viewModelScope.launch {
            offlineServiceRepository.search(query).collect { services ->
                mServicesUiState.value = if(services.isEmpty()){
                    ServicesUiState.NoContent
                }else{
                    ServicesUiState.Success(services.sortedBy { it.name })
                }
            }
        }


    /**
     * Private Functions
     **/
    private fun loadOfflineServices() = viewModelScope.launch {
        offlineServiceRepository.getAllServices().collect {
            mServicesUiState.value = if (it.isNotEmpty()) {
                ServicesUiState.Success(it)
            } else {
                ServicesUiState.Error
            }
        }
    }

}