package com.th3pl4gu3.mes.ui.screens.home

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

class HomeViewModel(private val serviceRepository: MesServiceRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
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
            homeUiState = HomeUiState.Loading
            homeUiState = try {
                HomeUiState.Success(serviceRepository.getMesServices())
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    companion object {
        fun provideFactory(
            appContainer: AppContainer
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                HomeViewModel(serviceRepository = appContainer.mesServiceRepository)
            }
        }
    }
}