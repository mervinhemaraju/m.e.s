package com.th3pl4gu3.mes.ui.bug_report

import android.app.Application
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.th3pl4gu3.mes.BR
import com.th3pl4gu3.mes.ui.utils.helpers.ObservableViewModel

class BugReportViewModel(application: Application) : ObservableViewModel(application) {

    // Private Variables
    private var mBugIdentified: String = ""
    private var mBugSteps: String = ""

    private var mFormSubmitted = MutableLiveData(false)
    private var mFormValid = MutableLiveData(false)


    // Bind-able two-way binding
    var bugIdentified: String
        @Bindable get() {
            return mBugIdentified
        }
        set(value) {
            mBugIdentified = value
            notifyPropertyChanged(BR.bugIdentified)
        }

    var bugSteps: String
        @Bindable get() {
            return mBugSteps
        }
        set(value) {
            mBugSteps = value
            notifyPropertyChanged(BR.bugSteps)
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

    private fun hasEmptyFields() = mBugIdentified.isEmpty() || mBugSteps.isEmpty()
}