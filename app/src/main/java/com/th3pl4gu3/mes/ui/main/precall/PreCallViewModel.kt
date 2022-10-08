package com.th3pl4gu3.mes.ui.main.precall

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.api.Service
import com.th3pl4gu3.mes.ui.utils.extensions.requireStringRes
import com.th3pl4gu3.mes.ui.utils.extensions.join

class PreCallViewModel(application: Application) : AndroidViewModel(application) {

    private val mTick = MutableLiveData<String>()
    private val mService = MutableLiveData<Service>()

    var retrievedService: Service? = null
        private set

    val tick: LiveData<String> = Transformations.map(mTick) {
        requireStringRes(R.string.styling_seconds_s).join(it)
    }

    val number: LiveData<String> = Transformations.map(mService) {
        requireStringRes(R.string.title_precall_subtitle).join(it.number.toString())
    }

    val name: LiveData<String> = Transformations.map(mService) {
        requireStringRes(R.string.title_precall_title).join(it.name)
    }

    fun tick(second: Long) {
        mTick.value = second.toString()
    }

    fun updateService(service: Service) {
        mService.value = service
        retrievedService = service
    }
}