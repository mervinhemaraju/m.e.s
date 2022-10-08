package com.th3pl4gu3.mes.ui.utils.extensions

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel

/*
* Get string resources directly from within
* a view model
*/
fun AndroidViewModel.requireStringRes(@StringRes res: Int) =
    getApplication<Application>().getString(res)