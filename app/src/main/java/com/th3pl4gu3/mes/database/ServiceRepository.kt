package com.th3pl4gu3.mes.database

import android.app.Application
import com.th3pl4gu3.mes.api.ApiRepository
import com.th3pl4gu3.mes.api.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*
* Repository pattern for Service CRUD
*/
class ServiceRepository private constructor(application: Application) {

    private val mDatabase =
        MesDatabase.getDatabase(
            application
        )
    private val serviceDao = mDatabase.serviceDao()

    companion object {
        @Volatile
        private var instance: ServiceRepository? = null

        fun getInstance(application: Application) =
            instance ?: synchronized(this) {
                instance ?: ServiceRepository(application).also { instance = it }
            }
    }

    fun getAll() = serviceDao.getAll()

    fun getEmergencies() = serviceDao.getEmergencies()

    fun search(search: String) = serviceDao.search(search)

    suspend fun hasCache() = serviceDao.count() > 0

    suspend fun refresh(): String? {
        var message: String? = null

        withContext(Dispatchers.IO) {
            val response = ApiRepository.getInstance().getServices()
            if (response.success) {
                serviceDao.refresh(response.services)
            } else {
                message = response.message
            }
        }

        return message
    }

}