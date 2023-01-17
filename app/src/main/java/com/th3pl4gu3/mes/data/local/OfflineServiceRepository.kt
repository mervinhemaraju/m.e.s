package com.th3pl4gu3.mes.data.local

import com.th3pl4gu3.mes.models.Service
import kotlinx.coroutines.flow.Flow

class OfflineServiceRepository(private val serviceDao: ServiceDao) : ServiceRepository {

    override suspend fun insertAll(services: List<Service>) = serviceDao.insertAll(services)

    override suspend fun count(): Int = serviceDao.count()

    override suspend fun wipe() = serviceDao.wipe()

    override fun getAllServices(): Flow<List<Service>> = serviceDao.getAllServices()

    override fun getService(identifier: String): Flow<List<Service>> = serviceDao.getService(identifier = identifier)

    override fun getEmergencyServices(): Flow<List<Service>> = serviceDao.getEmergencyServices()

    override fun search(search: String): Flow<List<Service>> = serviceDao.search(search)
}