package com.th3pl4gu3.mes.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.th3pl4gu3.mes.data.AppContainer
import com.th3pl4gu3.mes.models.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel(
    private val onlineServiceRepository: com.th3pl4gu3.mes.data.network.ServiceRepository,
    private val offlineServiceRepository: com.th3pl4gu3.mes.data.local.ServiceRepository
) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> = offlineServiceRepository.getEmergencyServices().map { HomeUiState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState.Loading
        )

    companion object {

        private const val TIMEOUT_MILLIS = 5_000L

        fun provideFactory(
            appContainer: AppContainer
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                HomeViewModel(
                    onlineServiceRepository = appContainer.onlineServiceRepository,
                    offlineServiceRepository = appContainer.offlineServiceRepository
                )
            }
        }
    }


    init {
        loadOnlineServices()
    }


    fun loadOnlineServices() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                if(!doesServicesExists()) {

                    // Logging for information
                    Log.i("api_services", "No services found. Fetching data from the API")

                    // First we wipe the existing data
                    wipeServices()

                    // Then we save the services locally in Room
                    saveServicesLocally(onlineServiceRepository.getMesServices().services)
                }

            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    private suspend fun doesServicesExists(): Boolean{
        return offlineServiceRepository.count() > 0
    }

    private suspend fun wipeServices(){
        offlineServiceRepository.wipe()
    }

    private suspend fun saveServicesLocally(services: List<Service>){
        if(services.isNotEmpty()){
            offlineServiceRepository.insertAll(services = services)
        }
    }
}