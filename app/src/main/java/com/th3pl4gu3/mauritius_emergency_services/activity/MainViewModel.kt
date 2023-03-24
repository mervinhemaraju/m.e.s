package com.th3pl4gu3.mauritius_emergency_services.activity

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.th3pl4gu3.mauritius_emergency_services.data.local.LocalServiceRepository
import com.th3pl4gu3.mauritius_emergency_services.data.store.StoreRepository
import com.th3pl4gu3.mauritius_emergency_services.models.MesAppSettings
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.services.ServicesUiState
import com.th3pl4gu3.mauritius_emergency_services.utils.TIMEOUT_MILLIS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreRepository: StoreRepository,
    private val offlineServiceRepository: LocalServiceRepository,
) : ViewModel() {

    private val mIsLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = mIsLoading

    private val mAppSettings: MutableState<MesAppSettings?> = mutableStateOf(null)
    val appSettings: State<MesAppSettings?> = mAppSettings

    private val mServices: MutableStateFlow<List<Service>> = MutableStateFlow(listOf())

    /**
     * Create accessible ServicesUiState for the Composable to collect
     **/
    val services: StateFlow<List<Service>> = mServices.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = mutableListOf()
    )

    init {
        viewModelScope.launch {
            dataStoreRepository.fetch().collect {
                mAppSettings.value = it
            }
            mIsLoading.value = false
        }
    }

    /**
     * Public Functions
     **/
    fun searchOfflineServices(query: String) = viewModelScope.launch {
        if (query.isEmpty() || query.isBlank()) {
            mServices.value = listOf()
        } else {
            offlineServiceRepository.search(query).collect { services ->
                mServices.value = services
            }
        }
    }


}