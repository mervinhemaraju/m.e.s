package com.th3pl4gu3.mes.ui.screens.services

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.th3pl4gu3.mes.data.network.AppContainer
import com.th3pl4gu3.mes.data.network.MesServiceRepository
import com.th3pl4gu3.mes.models.Service
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ServicesViewModel(private val serviceRepository: MesServiceRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var servicesUiState: ServicesUiState by mutableStateOf(ServicesUiState.Loading)
        private set

    /**
     * Call [getServices] on init so we can display status immediately.
     */
    init {
        getServices()
    }

    /**
     * Gets services information from the Mes API Retrofit service and updates the
     * [Service] [List] [MutableList].
     */
    fun getServices() {
        viewModelScope.launch {
            servicesUiState = ServicesUiState.Loading
            servicesUiState = try {
                ServicesUiState.Success(serviceRepository.getMesServices())
            } catch (e: IOException) {
                ServicesUiState.Error
            } catch (e: HttpException) {
                ServicesUiState.Error
            }
        }
    }

    /**
     * Factory for [ServicesViewModel] that takes [MesServiceRepository] as a dependency
     */
//    companion object {
//        fun provideFactory(
//            mesServiceRepository: MesServiceRepository,
//        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//            @Suppress("UNCHECKED_CAST")
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return ServicesViewModel(mesServiceRepository) as T
//            }
//        }
//    }

    companion object {
        fun provideFactory(
            appContainer: AppContainer
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ServicesViewModel(serviceRepository = appContainer.mesServiceRepository)
            }
        }
    }
}