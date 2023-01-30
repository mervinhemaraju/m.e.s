package com.th3pl4gu3.mes.ui.screens.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.th3pl4gu3.mes.data.AppContainer
import com.th3pl4gu3.mes.ui.screens.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val _servicesUiState: MutableStateFlow<ServicesUiState> =
        MutableStateFlow(ServicesUiState.Loading)

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

    /**
     * Public Functions
     **/
    fun loadOnlineServices() =
        viewModelScope.launch(Dispatchers.IO) {
            try {

                // Force refresh the services
                container.offlineServiceRepository.forceRefresh(
                    services = container.onlineServiceRepository.getMesServices().services
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
                _servicesUiState.value = ServicesUiState.Success(services.sortedBy { it.name })
            }
        }


    /**
     * Private Functions
     **/
    private fun loadOfflineServices() = viewModelScope.launch {
        container.offlineServiceRepository.getAllServices().collect {
            _servicesUiState.value = if (it.isNotEmpty()) {
                ServicesUiState.Success(it)
            } else {
                ServicesUiState.Error
            }
        }
    }

}