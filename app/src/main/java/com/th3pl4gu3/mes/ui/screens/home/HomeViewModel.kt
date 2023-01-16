package com.th3pl4gu3.mes.ui.screens.home

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

    /** The mutable State that stores the status of the most recent request */
//    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
//        private set


    /**
     * Holds home ui state. The list of items are retrieved from [ItemsRepository] and mapped to
     * [HomeUiState]
     */
    val homeUiState: StateFlow<HomeUiState> = offlineServiceRepository.getAll().map { HomeUiState.Success(it) }
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

    /**
     * Call [getServices] on init so we can display status immediately.
     */
    init {
        loadOnlineServices()
    }

    /**
     * Gets services information from the Mes API Retrofit service and updates the
     * [Service] [List] [MutableList].
     */
    fun loadOnlineServices() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                // First we wipe the existing data
                wipeServices()

                // Then we save the services locally in Room
                saveServicesLocally(onlineServiceRepository.getMesServices().services)

                // Load the services on the UI
//                HomeUiState.Success(onlineServiceRepository.getMesServices())

            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    private fun wipeServices(){
        offlineServiceRepository.wipe()
    }

    private suspend fun saveServicesLocally(services: List<Service>){
        if(services.isNotEmpty()){
            offlineServiceRepository.insertAll(services = services)
        }
    }
}