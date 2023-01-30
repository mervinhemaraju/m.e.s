package com.th3pl4gu3.mes.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.th3pl4gu3.mes.MesApplication
import com.th3pl4gu3.mes.ui.navigation.MesDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val application: MesApplication
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String?> = mutableStateOf(null)
    val startDestination: State<String?> = _startDestination

    init {
        viewModelScope.launch {
            application.container.dataStoreServiceRepository.fetch().collect {
                _startDestination.value = if (it.isFirstTimeLogging) {
                    MesDestinations.SCREEN_WELCOME
                } else {
                    MesDestinations.SCREEN_HOME
                }
            }
            _isLoading.value = false
        }
    }

}