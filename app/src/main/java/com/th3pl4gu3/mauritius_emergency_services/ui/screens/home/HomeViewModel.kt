package com.th3pl4gu3.mauritius_emergency_services.ui.screens.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.th3pl4gu3.mauritius_emergency_services.MesApplication
import com.th3pl4gu3.mauritius_emergency_services.data.AppContainer
import com.th3pl4gu3.mauritius_emergency_services.models.MesAppSettings
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.GetAppLocale
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.IsConnectedToNetwork
import com.th3pl4gu3.mauritius_emergency_services.utils.MES_EMERGENCY_TYPE
import com.th3pl4gu3.mauritius_emergency_services.utils.MES_KEYWORD_DEFAULT_EB_ACTION
import com.th3pl4gu3.mauritius_emergency_services.utils.TIMEOUT_MILLIS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mesApplication: MesApplication,
    private val container: AppContainer
) : ViewModel() {

    companion object {
        const val TAG = "HOME_VIEW_MODEL_TAG"
    }

    val homeUiState: StateFlow<HomeUiState> =
        container.offlineServiceRepository.getEmergencyServices().map {
            // Verify if offline repo has services
            if (it.isEmpty()) {
                // If services not found,
                // load the services
                loadServices()
            } else {
                contentAvailable(it)
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState.Loading
            )


    val mesAppSettings: StateFlow<MesAppSettings> =
        container.dataStoreServiceRepository.fetch().map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = MesAppSettings()
            )

//    init {
//        // Log info
//        Log.i(TAG, "Starting HomeViewModel Init")
//
//        loadOnlineServices()
//    }

    //    fun loadOnlineServices() =
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                // Log info
//                Log.i(TAG, "Loading online services. Services exists -> ${doesServicesExists()}")
//                Log.i(TAG, "Connected to internet-> ${mesApplication.applicationContext.IsConnectedToNetwork}")
//
//                if (!doesServicesExists()) {
//
//                    // Verify if network connection is available
//                    if (!mesApplication.applicationContext.IsConnectedToNetwork) {
//                        HomeUiState.NoNetwork
//                    } else {
//                        // Logging for information
//                        Log.i(TAG, "No services found. Fetching data from the API")
//
//                        // Get services from API
//                        with(container.onlineServiceRepository.getAllServices(language = GetAppLocale).services) {
//                            // Force refresh the data
//                            container.offlineServiceRepository.forceRefresh(
//                                services = this
//                            )
//
//                            // Get a default service for the action button
//                            val defaultService = this.first {
//                                it.type == MES_EMERGENCY_TYPE && it.name.lowercase()
//                                    .contains(MES_KEYWORD_DEFAULT_EB_ACTION)
//                            }.identifier
//
//                            // Update emergency button action
//                            container.dataStoreServiceRepository.updateEmergencyButtonActionIdentifier(
//                                defaultService
//                            )
//                        }
//                    }
//                }
//
//            } catch (e: IOException) {
//                HomeUiState.Error
//            } catch (e: HttpException) {
//                HomeUiState.Error
//            }
//        }
    fun refresh() = viewModelScope.launch {
        if (mesApplication.applicationContext.IsConnectedToNetwork) {
            loadServices()
        }
    }

    private suspend fun contentAvailable(services: List<Service>): HomeUiState.Success{
        updateEmergencyButtonAction(filterDefaultService(services))
        return HomeUiState.Success(services)
    }

    private suspend fun loadServices(): HomeUiState {
        return if (mesApplication.applicationContext.IsConnectedToNetwork) {
            val services = loadOnlineServices()
            forceRefresh(services)
            contentAvailable(services)
        }else{
            HomeUiState.NoNetwork
        }
    }

    private fun filterDefaultService(
        services: List<Service>
    ): Service{
        return services.first {
            it.type == MES_EMERGENCY_TYPE && it.name.lowercase()
                .contains(MES_KEYWORD_DEFAULT_EB_ACTION)
        }
    }

    private suspend fun forceRefresh(
        services: List<Service>
    ) {
        container.offlineServiceRepository.forceRefresh(
            services = services
        )
    }
    private suspend fun updateEmergencyButtonAction(
        service: Service
    ){
        container.dataStoreServiceRepository.updateEmergencyButtonActionIdentifier(
            service.identifier
        )
    }

    private suspend fun loadOnlineServices(): List<Service> {
        return container.onlineServiceRepository.getAllServices(language = GetAppLocale).services
    }
}