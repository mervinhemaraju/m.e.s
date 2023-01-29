package com.th3pl4gu3.mes.ui.screens.precall

import androidx.lifecycle.*
import com.th3pl4gu3.mes.data.AppContainer
import com.th3pl4gu3.mes.models.Service
import com.th3pl4gu3.mes.ui.utils.KEYWORD_SERVICE_IDENTIFIER_ARGUMENT
import com.th3pl4gu3.mes.ui.utils.PRE_CALL_COUNTDOWN_RANGE
import com.th3pl4gu3.mes.ui.utils.TIMEOUT_MILLIS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PreCallViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val container: AppContainer
) : ViewModel() {

    /**
     * Private properties
     **/
    private val mStartCall = MutableLiveData(false)

    val seconds: Flow<Int> = (PRE_CALL_COUNTDOWN_RANGE)
        .asSequence()
        .asFlow()
        .map {
            it
        }
        .onEach {
            if(it != PRE_CALL_COUNTDOWN_RANGE.first){
                delay(500)
            }
        }

    /**
     * Public properties
     **/

    val startCall: LiveData<Boolean>
        get() = mStartCall

    val service: StateFlow<PreCallUiState> = getService().map {

        if(it.isNotEmpty()){
            PreCallUiState.Success(it[0])
        }else{
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
            container.offlineServiceRepository.getAllServices()
        } else {
            container.offlineServiceRepository.getService(identifier = serviceIdentifier)
        }
    }
}
