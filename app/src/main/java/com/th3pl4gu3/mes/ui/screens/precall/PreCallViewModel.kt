package com.th3pl4gu3.mes.ui.screens.precall

import android.os.CountDownTimer
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.th3pl4gu3.mes.data.AppContainer
import com.th3pl4gu3.mes.models.Service
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeUnit

class PreCallViewModel(
    private val serviceIdentifier: String?,
    private val offlineServiceRepository: com.th3pl4gu3.mes.data.local.ServiceRepository
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
     * Companion constant objects
     **/
    companion object {

        private const val TIMEOUT_MILLIS = 5_000L

        fun provideFactory(
            serviceIdentifier: String?,
            appContainer: AppContainer
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PreCallViewModel(
                    serviceIdentifier = serviceIdentifier,
                    offlineServiceRepository = appContainer.offlineServiceRepository
                )
            }
        }
    }

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
        return if (serviceIdentifier.isNullOrEmpty()) {
            offlineServiceRepository.getAllServices()
        } else {
            offlineServiceRepository.getService(identifier = serviceIdentifier)
        }
    }
}
