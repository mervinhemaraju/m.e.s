package com.th3pl4gu3.mauritius_emergency_services.ui.screens.settings

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.data.local.LocalServiceRepository
import com.th3pl4gu3.mauritius_emergency_services.data.store.StoreRepository
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import com.th3pl4gu3.mauritius_emergency_services.ui.wrappers.NetworkRequests
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.GetAppLocale
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.home.HomeUiState
import com.th3pl4gu3.mauritius_emergency_services.utils.TIMEOUT_MILLIS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val offlineServiceRepository: LocalServiceRepository,
    private val dataStoreRepository: StoreRepository,
    private val onlineServiceRequests: NetworkRequests
) : ViewModel() {

    private val mMessageQueue: MutableStateFlow<Pair<String?, Int>?> = MutableStateFlow(null)

    val services: StateFlow<List<Service>> = offlineServiceRepository.getEmergencyServices().map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = listOf()
            )

    val messageQueue: StateFlow<Pair<String?, Int>?>
        get() = mMessageQueue

    /**
     * Public Functions
     **/
    fun forceRefreshServices(silent: Boolean = false) =
        viewModelScope.launch(Dispatchers.IO) {
            try {

                // Force refresh the services
                offlineServiceRepository.forceRefresh(
                    services = onlineServiceRequests.getMesServices(language = GetAppLocale).services
                )

                if(!silent) mMessageQueue.value = Pair(null, R.string.message_cache_reset_successfully)

            } catch (e: Exception) {
                Log.e(TAG, "Error while resetting cache: ${e.message}")
                mMessageQueue.value = Pair(e.message, R.string.message_error_cache_reset)
                HomeUiState.Error
            }
        }

    fun clearMessageQueue() {
        mMessageQueue.value = null
    }

    fun sendMessageInQueue(@StringRes message: Int){
        mMessageQueue.value = Pair(null, message)
    }

    fun updateEmergencyButtonAction(service: Service) {
        viewModelScope.launch {
            dataStoreRepository.updateEmergencyButtonActionIdentifier(identifier = service.identifier)
            mMessageQueue.value = Pair(service.name, R.string.message_emergency_button_action_updated)
        }
    }
}