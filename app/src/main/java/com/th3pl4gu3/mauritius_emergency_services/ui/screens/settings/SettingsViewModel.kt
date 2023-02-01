package com.th3pl4gu3.mauritius_emergency_services.ui.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.data.AppContainer
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.home.HomeUiState
import com.th3pl4gu3.mauritius_emergency_services.ui.utils.TIMEOUT_MILLIS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val container: AppContainer
) : ViewModel() {

    private val mMessageQueue: MutableStateFlow<Pair<String?, Int>?> = MutableStateFlow(null)

    val services: StateFlow<List<Service>> =
        container.offlineServiceRepository.getEmergencyServices().map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = listOf()
            )

    val messageQueue: StateFlow<Pair<String?, Int>?> = mMessageQueue

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

                mMessageQueue.value = Pair(null, R.string.message_cache_reset_successfully)

            } catch (e: Exception) {
                Log.e(TAG, "Error while resetting cache: ${e.message}")
                mMessageQueue.value = Pair(e.message, R.string.message_error_cache_reset)
                HomeUiState.Error
            }
        }

    fun clearMessageQueue(){
        mMessageQueue.value = null
    }

    fun updateEmergencyButtonAction(service: Service){
        viewModelScope.launch {
            container.dataStoreServiceRepository.updateEmergencyButtonActionIdentifier(identifier = service.identifier)
            mMessageQueue.value = Pair(service.name, R.string.message_cache_reset_successfully)
        }
    }
}