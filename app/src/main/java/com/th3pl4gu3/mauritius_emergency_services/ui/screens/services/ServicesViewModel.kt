package com.th3pl4gu3.mauritius_emergency_services.ui.screens.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.th3pl4gu3.mauritius_emergency_services.data.AppContainer
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.GetAppLocale
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.home.HomeUiState
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
    private val container: AppContainer
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
            try {
                // Force refresh the services
                container.offlineServiceRepository.forceRefresh(
                    services = container.onlineServiceRepository.getAllServices(language = GetAppLocale).services
                )
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }

    fun search(query: String) =
        viewModelScope.launch {
            container.offlineServiceRepository.search(query).collect { services ->
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
        container.offlineServiceRepository.getAllServices().collect {
            mServicesUiState.value = if (it.isNotEmpty()) {
                ServicesUiState.Success(it)
            } else {
                ServicesUiState.Error
            }
        }
    }

}