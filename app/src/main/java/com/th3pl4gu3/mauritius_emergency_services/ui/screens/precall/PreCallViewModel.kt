package com.th3pl4gu3.mauritius_emergency_services.ui.screens.precall

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.th3pl4gu3.mauritius_emergency_services.data.local.LocalServiceRepository
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import com.th3pl4gu3.mauritius_emergency_services.utils.KEYWORD_SERVICE_IDENTIFIER_ARGUMENT
import com.th3pl4gu3.mauritius_emergency_services.utils.KEYWORD_SERVICE_NUMBER_ARGUMENT
import com.th3pl4gu3.mauritius_emergency_services.utils.PRE_CALL_COUNTDOWN_RANGE
import com.th3pl4gu3.mauritius_emergency_services.utils.TIMEOUT_MILLIS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PreCallViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val offlineServiceRepository: LocalServiceRepository
) : ViewModel() {

    companion object {
        private const val TAG = "PRE_CALL_VIEW_MODEL"
    }

    /**
     * Private properties
     **/
    private val mStartCall = MutableStateFlow(false)

    /**
     * Public properties
     **/

    val seconds: Flow<Int> = (PRE_CALL_COUNTDOWN_RANGE)
        .asSequence()
        .asFlow()
        .map {
            it
        }
        .onEach {
            if (it != PRE_CALL_COUNTDOWN_RANGE.first) {
                delay(1000)
            }
            if (it == PRE_CALL_COUNTDOWN_RANGE.last) {
                mStartCall.value = true
            }
        }

    val startCall: StateFlow<Boolean>
        get() = mStartCall

    val service: StateFlow<PreCallUiState> = getService().map {

        if (it.isNotEmpty()) {
            // Get the first service
            val service = it.first()

            // Get the called number from saved state handle
            val serviceNumber: String =
                savedStateHandle[KEYWORD_SERVICE_NUMBER_ARGUMENT] ?: service.main_contact.toString()

            // Replace main contact number to called number
            PreCallUiState.Success(
                service.copy(main_contact = serviceNumber.toInt())
            )
        } else {
            PreCallUiState.Error
        }

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = PreCallUiState.Loading
    )

    /**
     * Private functions
     **/
    private fun getService(): Flow<List<Service>> {

        val serviceIdentifier: String? = savedStateHandle[KEYWORD_SERVICE_IDENTIFIER_ARGUMENT]

        return if (serviceIdentifier.isNullOrEmpty()) {
            offlineServiceRepository.getAllServices()
        } else {
            offlineServiceRepository.getService(identifier = serviceIdentifier)
        }
    }
}
