package com.th3pl4gu3.mes

import android.app.Application
import com.th3pl4gu3.mes.data.network.AppContainer
import com.th3pl4gu3.mes.data.network.DefaultAppContainer

class MesApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}