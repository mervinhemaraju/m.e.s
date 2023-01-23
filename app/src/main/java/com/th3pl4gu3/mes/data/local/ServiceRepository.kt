package com.th3pl4gu3.mes.data.local

import com.th3pl4gu3.mes.models.Service
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {

    suspend fun forceRefresh(services: List<Service>)

    suspend fun count(): Int

    fun getAllServices(): Flow<List<Service>>

    fun getService(identifier: String): Flow<List<Service>>

    fun getEmergencyServices(): Flow<List<Service>>

    fun search(search: String): Flow<List<Service>>
}