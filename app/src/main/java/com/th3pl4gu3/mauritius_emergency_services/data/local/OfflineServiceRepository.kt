package com.th3pl4gu3.mauritius_emergency_services.data.local

import android.util.Log
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import kotlinx.coroutines.flow.Flow

class OfflineServiceRepository(private val serviceDao: ServiceDao) : ServiceRepository {

    companion object {
        const val TAG = "OFFLINE_SERVICE_REPOSITORY"
    }

    override suspend fun forceRefresh(services: List<Service>){
        // Log info
        Log.i(TAG, "Force refreshing services...")

        /**
         * Force refresh the services by
         * 1. Deleting the existing ones
         * 2. Inserting the updated ones
         **/
        serviceDao.wipe()
        serviceDao.insertAll(services)
    }

    override suspend fun count(): Int = serviceDao.count()

    override fun getAllServices(): Flow<List<Service>> = serviceDao.getAllServices()

    override fun getService(identifier: String): Flow<List<Service>> = serviceDao.getService(identifier = identifier)

    override fun getEmergencyServices(): Flow<List<Service>> = serviceDao.getEmergencyServices()

    override fun search(search: String): Flow<List<Service>> = serviceDao.search("%$search%")
}