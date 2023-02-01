package com.th3pl4gu3.mauritius_emergency_services.models

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.th3pl4gu3.mauritius_emergency_services.MesApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * This is the ServiceWorker class that will be
 * scheduled periodically
 * It will wipe all existing services from the Room Database and
 * fetch the services again from the MES API to get any updates
 **/
class ServiceWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {

        return withContext(Dispatchers.IO) {

            return@withContext try {

                // Get the container from the application
                val container = (applicationContext as MesApplication).container

                // Get the services from the MES API
                val mesResponse = container.onlineServiceRepository.getMesServices()

                // Verify if the request is successful
                if (!mesResponse.success) throw Exception("Error getting the services from the MES API. Message: ${mesResponse.message}")

                // Force refresh the services
                container.offlineServiceRepository.forceRefresh(
                    services = mesResponse.services
                )

                // Mark result as success
                Result.success()

            } catch (exception: Exception) {
                Log.e(
                    "service_worker_test", exception.message, exception
                )
                Result.failure()
            }
        }
    }
}