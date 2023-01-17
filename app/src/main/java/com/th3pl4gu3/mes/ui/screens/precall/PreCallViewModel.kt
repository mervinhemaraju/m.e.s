package com.th3pl4gu3.mes.ui.screens.precall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.th3pl4gu3.mes.data.AppContainer
import com.th3pl4gu3.mes.models.Service
import kotlinx.coroutines.flow.*

class PreCallViewModel(
    private val serviceIdentifier: String?,
    private val offlineServiceRepository: com.th3pl4gu3.mes.data.local.ServiceRepository
) : ViewModel() {

    val service: StateFlow<PreCallUiState> = getService().map {

        if(it.isNotEmpty()){
            PreCallUiState.Success(it[0])
        }else{
            PreCallUiState.Error
        }

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = PreCallUiState.Loading
    )

    companion object {

        private const val TIMEOUT_MILLIS = 5_000L

        fun provideFactory(
            serviceIdentifier: String?,
            appContainer: AppContainer
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PreCallViewModel(
                    serviceIdentifier = serviceIdentifier,
                    offlineServiceRepository = appContainer.offlineServiceRepository
                )
            }
        }
    }

    private fun getService(): Flow<List<Service>> {
        return if (serviceIdentifier.isNullOrEmpty()) {
            offlineServiceRepository.getAllServices()
        } else {
            offlineServiceRepository.getService(identifier = serviceIdentifier)
        }

    }
}
