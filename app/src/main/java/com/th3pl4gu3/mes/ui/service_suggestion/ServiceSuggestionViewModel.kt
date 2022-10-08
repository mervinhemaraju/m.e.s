package com.th3pl4gu3.mes.ui.service_suggestion

import android.app.Application
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.th3pl4gu3.mes.BR
import com.th3pl4gu3.mes.ui.utils.helpers.ObservableViewModel

class ServiceSuggestionViewModel(application: Application) : ObservableViewModel(application) {

    // Private Variables
    private var mServiceName: String = ""
    private var mServiceNumber: String = ""
    private var mServiceProof: String = ""

    private var mFormSubmitted = MutableLiveData(false)
    private var mFormValid = MutableLiveData(false)

    // Bind-able two-way binding
    var serviceName: String
        @Bindable get() {
            return mServiceName
        }
        set(value) {
            mServiceName = value
            notifyPropertyChanged(BR.serviceName)
        }

    var serviceNumber: String
        @Bindable get() {
            return mServiceNumber
        }
        set(value) {
            mServiceNumber = value
            notifyPropertyChanged(BR.serviceNumber)
        }

    var serviceProof: String
        @Bindable get() {
            return mServiceProof
        }
        set(value) {
            mServiceProof = value
            notifyPropertyChanged(BR.serviceProof)
        }

    // Live Data
    val formSubmitted: LiveData<Boolean>
        get() = mFormSubmitted

    val formValid: LiveData<Boolean>
        get() = mFormValid


    // Functions
    fun submit() {
        // Flag for error message check in each field
        mFormSubmitted.value = true

        // Check if data submitted is valid
        mFormValid.value = !hasEmptyFields()
    }

    private fun hasEmptyFields() = mServiceName.isEmpty() || mServiceNumber.isEmpty()
}