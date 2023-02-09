package com.th3pl4gu3.mauritius_emergency_services.activity

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.th3pl4gu3.mauritius_emergency_services.data.store.StoreRepository
import com.th3pl4gu3.mauritius_emergency_services.models.MesAppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreRepository: StoreRepository
) : ViewModel() {

    private val mIsLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = mIsLoading

    private val mAppSettings: MutableState<MesAppSettings?> = mutableStateOf(null)
    val appSettings: State<MesAppSettings?> = mAppSettings

    init {
        viewModelScope.launch {
            dataStoreRepository.fetch().collect {
                mAppSettings.value = it
            }
            mIsLoading.value = false
        }
    }

}