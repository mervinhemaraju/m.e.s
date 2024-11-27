package com.th3pl4gu3.mauritius_emergency_services.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.th3pl4gu3.mauritius_emergency_services.data.local.LocalServiceRepository
import com.th3pl4gu3.mauritius_emergency_services.data.store.StoreRepository
import com.th3pl4gu3.mauritius_emergency_services.models.MesAppSettings
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.GetAppLocale
import com.th3pl4gu3.mauritius_emergency_services.ui.wrappers.NetworkRequests
import com.th3pl4gu3.mauritius_emergency_services.utils.MES_EMERGENCY_TYPE
import com.th3pl4gu3.mauritius_emergency_services.utils.MES_KEYWORD_DEFAULT_EB_ACTION
import com.th3pl4gu3.mauritius_emergency_services.utils.NetworkRequestException
import com.th3pl4gu3.mauritius_emergency_services.utils.TIMEOUT_MILLIS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val offlineServiceRepository: LocalServiceRepository,
    private val dataStoreRepository: StoreRepository,
    private val onlineServiceRequests: NetworkRequests
) : ViewModel() {

    companion object {
        const val TAG = "HOME_VIEW_MODEL_TAG"
    }

    val homeUiState: StateFlow<HomeUiState> =
        offlineServiceRepository.getEmergencyServices().map {
            if (it.isEmpty()) {
                reloadServices(currentEmergencyButtonIdentifier = null)
            } else {
                HomeUiState.Success(it)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState.Loading
        )


    val mesAppSettings: StateFlow<MesAppSettings> =
        dataStoreRepository.fetch().map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = MesAppSettings()
            )

    fun refresh(currentEmergencyButtonIdentifier: String) = viewModelScope.launch {
        reloadServices(
            currentEmergencyButtonIdentifier = currentEmergencyButtonIdentifier
        )
    }

    private suspend fun reloadServices(currentEmergencyButtonIdentifier: String?): HomeUiState {
        return try{
            // Load service online
            with(loadOnlineServices()) {

                // Force refresh local repository
                forceRefresh(this)

                // Verify if emergency button action is still valid
                // If not, update it with the default
                if (
                    !this.any {
                        it.identifier == currentEmergencyButtonIdentifier
                    }
                ) {
                    updateEmergencyButtonAction(filterDefaultService(this).identifier)
                }

                HomeUiState.Success(this)
            }

        }catch (e: NetworkRequestException){
            HomeUiState.NoNetwork
        }catch (e: java.lang.Exception){
            HomeUiState.Error
        }
    }

    private fun filterDefaultService(
        services: List<Service>
    ): Service {
        return services.first {
            it.type == MES_EMERGENCY_TYPE && it.name.lowercase()
                .contains(MES_KEYWORD_DEFAULT_EB_ACTION)
        }
    }

    private suspend fun forceRefresh(
        services: List<Service>
    ) {
        // Recreate services
        offlineServiceRepository.forceRefresh(
            services = services
        )
    }

    private suspend fun updateEmergencyButtonAction(
        identifier: String
    ) {
        dataStoreRepository.updateEmergencyButtonActionIdentifier(
            identifier
        )
    }

    private suspend fun loadOnlineServices(): List<Service> {
        return onlineServiceRequests.getMesServices(language = GetAppLocale).services
    }
}