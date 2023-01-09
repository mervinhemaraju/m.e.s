package com.th3pl4gu3.mes.ui.screens.theme_selector

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.th3pl4gu3.mes.MesApplication
import com.th3pl4gu3.mes.models.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ThemeViewModel(private val application: MesApplication) : ViewModel() {

    private val _themeSelected: MutableStateFlow<AppTheme> = MutableStateFlow(value = AppTheme.FOLLOW_SYSTEM)
    val themeSelected = _themeSelected.asStateFlow()

    fun updateTheme(){
        viewModelScope.launch {
            application.updateTheme(themeSelected.value)
        }
    }

    companion object {
        fun provideFactory(
            application: MesApplication
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ThemeViewModel(application = application)
            }
        }
    }
}