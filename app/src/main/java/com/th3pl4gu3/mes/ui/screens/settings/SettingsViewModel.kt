package com.th3pl4gu3.mes.ui.screens.settings

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.th3pl4gu3.mes.data.AppContainer
import com.th3pl4gu3.mes.models.MesAppSettings
import com.th3pl4gu3.mes.models.Service
import com.th3pl4gu3.mes.ui.screens.home.HomeUiState
import com.th3pl4gu3.mes.ui.screens.services.ServicesUiState
import com.th3pl4gu3.mes.ui.utils.TIMEOUT_MILLIS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val container: AppContainer
) : ViewModel() {

    private val mMessageQueue: MutableStateFlow<String?> = MutableStateFlow(null)

    val services: StateFlow<List<Service>> =
        container.offlineServiceRepository.getEmergencyServices().map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = listOf()
            )

    val messageQueue: StateFlow<String?> = mMessageQueue

    /**
     * Public Functions
     **/
    fun forceRefreshServices() =
        viewModelScope.launch(Dispatchers.IO) {
            try {

                // Force refresh the services
                container.offlineServiceRepository.forceRefresh(
                    services = container.onlineServiceRepository.getMesServices().services
                )

                mMessageQueue.value = "Cache reset successfully."

            } catch (e: IOException) {
                mMessageQueue.value = e.message
                HomeUiState.Error
            } catch (e: HttpException) {
                mMessageQueue.value = e.message
                HomeUiState.Error
            }
        }

    fun clearMessageQueue(){
        mMessageQueue.value = null
    }

    fun updateEmergencyButtonAction(service: Service){
        viewModelScope.launch {
            container.dataStoreServiceRepository.updateEmergencyButtonActionIdentifier(identifier = service.identifier)
            mMessageQueue.value = "${service.name} has been set as the emergency button action"
        }
    }
}