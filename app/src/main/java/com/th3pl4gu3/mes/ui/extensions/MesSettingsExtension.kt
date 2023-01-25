package com.th3pl4gu3.mes.ui.extensions

import com.th3pl4gu3.mes.MesApplication
import com.th3pl4gu3.mes.datastore
import com.th3pl4gu3.mes.models.AppTheme

suspend fun MesApplication.unsetFirstTimeLogging(){
    datastore.updateData {
        it.copy(
            isFirstTimeLogging = false
        )
    }
}

suspend fun MesApplication.updateMesTheme(theme: AppTheme) {
    datastore.updateData {
        it.copy(
            appTheme = theme
        )
    }
}