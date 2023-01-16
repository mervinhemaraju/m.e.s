package com.th3pl4gu3.mes.data.local

import com.th3pl4gu3.mes.models.Service
import kotlinx.coroutines.flow.Flow

class OfflineServiceRepository(private val serviceDao: ServiceDao) : ServiceRepository {

    override suspend fun insertAll(services: List<Service>) = serviceDao.insertAll(services)

    override suspend fun count(): Int = serviceDao.count()

    override fun wipe() = serviceDao.wipe()

    override fun getAll(): Flow<List<Service>> = serviceDao.getAll()

    override fun getEmergencyServices(): Flow<List<Service>> = serviceDao.getEmergencyServices()

    override fun search(search: String): Flow<List<Service>> = serviceDao.search(search)
}