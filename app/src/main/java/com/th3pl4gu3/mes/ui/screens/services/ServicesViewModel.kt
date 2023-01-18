package com.th3pl4gu3.mes.ui.screens.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.th3pl4gu3.mes.data.AppContainer
import com.th3pl4gu3.mes.models.Service
import com.th3pl4gu3.mes.ui.screens.home.HomeUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ServicesViewModel(
    private val onlineServiceRepository: com.th3pl4gu3.mes.data.network.ServiceRepository,
    private val offlineServiceRepository: com.th3pl4gu3.mes.data.local.ServiceRepository
) : ViewModel() {

    /**
     * Create a private ServicesUiState to hold the MutableStateFlow of the items
     * It starts as Loading
     **/
    private val _servicesUiState: MutableStateFlow<ServicesUiState> = MutableStateFlow(ServicesUiState.Loading)

    /**
     * Create an accessible ServicesUiState for the Composable to collect
     **/
    val servicesUiState: StateFlow<ServicesUiState> = _servicesUiState

    /**
     * On Init, load the offline services
     **/
    init {
        loadOfflineServices()
    }

    companion object {
        fun provideFactory(
            appContainer: AppContainer
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ServicesViewModel(
                    onlineServiceRepository = appContainer.onlineServiceRepository,
                    offlineServiceRepository = appContainer.offlineServiceRepository
                )
            }
        }
    }

    /**
     * Public Functions
     **/
    fun loadOnlineServices() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                // First we wipe the existing data
                wipeServices()

                // Then we save the services locally in Room
                saveServicesLocally(onlineServiceRepository.getMesServices().services)

            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun search(query: String){
        viewModelScope.launch {
            offlineServiceRepository.search(query).collect{
                // TODO(Add new state for items that are not found)
                _servicesUiState.value = ServicesUiState.Success(it)
            }
        }
    }


    /**
     * Private Functions
     **/
    private fun loadOfflineServices() {
        viewModelScope.launch {
            offlineServiceRepository.getAllServices().collect{
                _servicesUiState.value = if(it.isNotEmpty()){
                    ServicesUiState.Success(it)
                }else{
                    ServicesUiState.Error
                }
            }
        }
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