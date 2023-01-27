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
        loadOnlineServices()
    }

    fun loadOnlineServices() =
        viewModelScope.launch(Dispatchers.IO) {
            try {

                if (!doesServicesExists()) {

                    // Logging for information
                    Log.i("api_services", "No services found. Fetching data from the API")

                    // Force refresh the data
                    container.offlineServiceRepository.forceRefresh(
                        services = container.onlineServiceRepository.getMesServices().services
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