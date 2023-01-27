package com.th3pl4gu3.mes.ui.screens.precall

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.th3pl4gu3.mes.data.AppContainer
import com.th3pl4gu3.mes.models.Service
import com.th3pl4gu3.mes.ui.utils.TIMEOUT_MILLIS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class PreCallViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val container: AppContainer
) : ViewModel() {

    /**
     * Private properties
     **/

    private val mTick = MutableLiveData("")

    private val mStartCall = MutableLiveData(false)

    private val mCountDown = object: CountDownTimer(4000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            mTick.value = TimeUnit.SECONDS.convert(millisUntilFinished, TimeUnit.MILLISECONDS).toString()
        }
        override fun onFinish() {
            mStartCall.value = true
        }
    }

    /**
     * Public properties
     **/
    val tick: LiveData<String> = Transformations.map(mTick) {
        "${it}s"
    }

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
     * Init clause
     **/
    init {
        startCountDown()
    }

    /**
     * Private functions
     **/
    private fun startCountDown(){
        mCountDown.start()
    }

    private fun getService(): Flow<List<Service>> {

        val serviceIdentifier: String? = savedStateHandle["serviceIdentifier"]

        return if (serviceIdentifier.isNullOrEmpty()) {
            container.offlineServiceRepository.getAllServices()
        } else {
            container.offlineServiceRepository.getService(identifier = serviceIdentifier)
        }
    }
}
