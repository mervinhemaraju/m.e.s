package com.th3pl4gu3.mes.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.th3pl4gu3.mes.data.AppContainer
import com.th3pl4gu3.mes.models.MesAppSettings
import com.th3pl4gu3.mes.ui.utils.TIMEOUT_MILLIS
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
    private val container: AppContainer
) : ViewModel() {

    companion object {
        const val TAG = "HOME_VIEW_MODEL_TAG"
    }

    val homeUiState: StateFlow<HomeUiState> =
        container.offlineServiceRepository.getEmergencyServices().map { HomeUiState.Success(it) }
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

    init {
        // Log info
        Log.i(TAG, "Starting HomeViewModel Init")

        loadOnlineServices()
    }

    fun loadOnlineServices() =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Log info
                Log.i(TAG, "Loading online services. Services exists -> ${doesServicesExists()}")

                if (!doesServicesExists()) {

                    // Logging for information
                    Log.i(TAG, "No services found. Fetching data from the API")

                    // Get services from API
                    val services = container.onlineServiceRepository.getMesServices().services

                    // Force refresh the data
                    container.offlineServiceRepository.forceRefresh(
                        services = services
                    )

                    // Get a default service for the action button
                    val defaultService = services.first { it.type == "E" && it.name.lowercase().contains("police") }.identifier

                    // Update emergency button action
                    container.dataStoreServiceRepository.updateEmergencyButtonActionIdentifier(
                        defaultService
                    )
                }

            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }

    private suspend fun doesServicesExists(): Boolean = container.offlineServiceRepository.count() > 0
}