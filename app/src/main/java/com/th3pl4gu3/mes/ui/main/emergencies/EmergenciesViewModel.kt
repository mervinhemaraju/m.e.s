package com.th3pl4gu3.mes.ui.main.emergencies

import android.app.Application
import androidx.lifecycle.*
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.api.Service
import com.th3pl4gu3.mes.database.ServiceRepository
import com.th3pl4gu3.mes.ui.utils.extensions.requireEmergencyButton
import com.th3pl4gu3.mes.ui.utils.helpers.Global
import com.th3pl4gu3.mes.ui.utils.extensions.requireStringRes
import com.th3pl4gu3.mes.ui.utils.extensions.withoutEmergencyButtonService
import kotlinx.coroutines.launch
import java.lang.Exception

class EmergenciesViewModel(application: Application) : AndroidViewModel(application) {

    // Private Variables
    private val mMessage = MutableLiveData<String>()
    private val mLoading = MutableLiveData(true)
    private val mUnsuccessfulClicks = MutableLiveData(0)
    private var mEmergencyButtonHolder: Service? = null

    // Properties
    val emergencyButtonHolder: Service?
        get() = mEmergencyButtonHolder

    val unsuccessfulClicks: LiveData<Int>
        get() = mUnsuccessfulClicks

    val message: LiveData<String>
        get() = mMessage

    val loading: LiveData<Boolean>
        get() = mLoading

    val emergencies: LiveData<List<Service>> = Transformations.map(
        ServiceRepository.getInstance(getApplication()).getEmergencies()
    ) { services ->

        if (!services.isNullOrEmpty()) {
            // Assign the police direct line 1 service as emergency button
            mEmergencyButtonHolder = services.requireEmergencyButton

            // Remove the emergency button service from other emergencies list
            return@map services.withoutEmergencyButtonService
        }

        return@map null
    }

    init {
        startLoading()

        refreshServices()
    }

    internal fun refreshServices() {

        viewModelScope.launch {

            // Reset previous Message value
            mMessage.value = null
            val serviceRepo = ServiceRepository.getInstance(getApplication())
            val hasCache = serviceRepo.hasCache()

            try {
                if (Global.isNetworkConnected) {
                    with(serviceRepo.refresh()) {
                        if (!hasCache) {
                            mMessage.value = this
                        }
                    }
                } else {
                    // If not connected to internet, we check if cache present already
                    // If not, display error message
                    if (!hasCache) {
                        mMessage.value = requireStringRes(R.string.message_info_no_internet)
                    }
                }
            } catch (e: Exception) {
                mMessage.value = requireStringRes(R.string.message_error_bug_report)
            }

        }
    }

    fun emergencyButtonClick() {
        mUnsuccessfulClicks.value.let {
            mUnsuccessfulClicks.value = it?.inc()
        }
    }

    internal fun resetEmergencyButtonClicks() {
        mUnsuccessfulClicks.value = 0
    }

    internal fun stopLoading() {

        // Set loading to false to
        // notify the fragment that loading
        // has completed and to hide loading animation
        mLoading.value = false

    }

    private fun startLoading() {

        // Set loading to true to
        // notify the fragment that loading
        // has started and to show loading animation
        mLoading.value = true

    }
}