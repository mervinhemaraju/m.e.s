package com.th3pl4gu3.mes.ui

import android.app.Application
import android.os.Build
import android.os.Build.VERSION_CODES.*
import androidx.work.*
import com.th3pl4gu3.mes.ui.utils.extensions.updateAppTheme
import com.th3pl4gu3.mes.ui.utils.work.ServiceWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/*
* This is the Application class for Mes
* The class is a singleton so that we can access it anywhere
* E.g access it from Binding Adapters
*/
class MesApplication : Application() {

    private val mApplicationScope = CoroutineScope(Dispatchers.Default)

    companion object {
        @Volatile
        private var INSTANCE: MesApplication? = null

        fun getInstance() =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: MesApplication().also { INSTANCE = it }
            }
    }

    override fun onCreate() {
        super.onCreate()

        // Assigns 'this' to the singleton object
        INSTANCE = this

        // Updates the application's theme
        updateAppTheme()

        // Runs in a queue to prevent delaying app launch
        runDelayedConfigs()
    }

    private fun runDelayedConfigs() {
        mApplicationScope.launch {

            /**
             * This function will setup a work manager to
             * send a periodic request to fetch updated services
             * from the service API
             */
            setupServiceRefreshWork()
        }
    }

    private fun setupServiceRefreshWork() {
        // If all defined constraints are matched, worker will do the work
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true) // Battery must not be low
            .setRequiredNetworkType(NetworkType.CONNECTED) // Must be connected to the network
            .setRequiredNetworkType(NetworkType.UNMETERED) // Connection type must be unmetered
            .apply {
                if (Build.VERSION.SDK_INT >= N) {
                    setRequiresDeviceIdle(true) // If SDK is After M, it requires device to be idle
                }
            }.build()

        // Requests will be repeated every 4 days
        val repeatingRequest = PeriodicWorkRequestBuilder<ServiceWork>(
            4, TimeUnit.DAYS
        ).setConstraints(constraints).build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            ServiceWork.WORKER_NAME,
            ExistingPeriodicWorkPolicy.KEEP, // If duplicate requests are found, it will keep previous one and delete new one
            repeatingRequest
        )
    }
}