package com.th3pl4gu3.mes

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.datastore.dataStore
import androidx.work.*
import com.th3pl4gu3.mes.data.AppContainer
import com.th3pl4gu3.mes.models.MesAppSettingsSerializer
import com.th3pl4gu3.mes.models.ServiceWorker
import com.th3pl4gu3.mes.ui.extensions.MesWorkManager
import com.th3pl4gu3.mes.ui.extensions.NotificationBuilderServiceUpdating
import com.th3pl4gu3.mes.ui.extensions.createNotificationChannels
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltAndroidApp
class MesApplication : Application() {

    /** [AppContainer] instance used by the rest of classes to obtain dependencies **/
    @Inject lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        /** Create all Notification Channels **/
        createNotificationChannels()

        /**
         * This function will create a worker to
         * send a periodic request to fetch updated services
         * from the service API
         */
        with(createServiceRefreshWork()) {

            /** Instantiate observers **/
            runServiceWorkerObserver(
                serviceWorkerId = this.id
            )

            /** Runs in a queue to prevent delaying app launch **/
            runDelayedConfigs(
                serviceWorkerRequest = this
            )
        }
    }

    private fun runServiceWorkerObserver(
        serviceWorkerId: UUID
    ) {

        /**
         * Observe the work manager for state changes
         **/
        MesWorkManager.getWorkInfoByIdLiveData(serviceWorkerId).observeForever {
            if (it != null) {
                when (it.state) {
                    WorkInfo.State.ENQUEUED -> {
                        Log.i("service_worker_test", "Work has been enqueued")

                        with(NotificationManagerCompat.from(this@MesApplication)) {
                            cancel(100)
                        }
                    }
                    WorkInfo.State.RUNNING -> {
                        Log.i("service_worker_test", "Work is now running")

                        with(NotificationManagerCompat.from(this@MesApplication)) {
                            notify(100, NotificationBuilderServiceUpdating)
                        }
                    }
                    else -> {
                        Log.i("service_worker_test", "Work is ${it.state}")

                        with(NotificationManagerCompat.from(this@MesApplication)) {
                            cancel(100)
                        }
                    }
                }
            }
        }

    }

    private fun runDelayedConfigs(
        serviceWorkerRequest: PeriodicWorkRequest
    ) {
        CoroutineScope(Dispatchers.Default).launch {

            /**
             * Create the periodic work
             **/
            MesWorkManager.enqueueUniquePeriodicWork(
                "service_worker_update",
                ExistingPeriodicWorkPolicy.KEEP, // If duplicate requests are found, it will keep previous one and delete new one
                serviceWorkerRequest
            )
        }
    }

    private fun createServiceRefreshWork(): PeriodicWorkRequest {
        // If all defined constraints are matched, worker will do the work
        val constraints = Constraints
            .Builder()
            .setRequiresDeviceIdle(true)
            .setRequiresBatteryNotLow(true) // Battery must not be low
            .setRequiredNetworkType(NetworkType.CONNECTED) // Must be connected to the network
            .setRequiredNetworkType(NetworkType.UNMETERED) // Connection type must be un-metered
            .build()

        // Requests will be repeated every 4 days
        return PeriodicWorkRequestBuilder<ServiceWorker>(
            4, TimeUnit.DAYS
        )
            .setConstraints(constraints)
            .build()
    }
}