package com.th3pl4gu3.mauritius_emergency_services.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import kotlinx.coroutines.flow.Flow

/**
 * CRUD for the Service Object
 **/
@Dao
interface ServiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(services: List<Service>)

    @Query("SELECT COUNT(identifier) from service_table")
    suspend fun count(): Int

    @Query("DELETE FROM service_table")
    suspend fun wipe()

    @Query("SELECT * FROM service_table")
    fun getAllServices(): Flow<List<Service>>

    @Query("SELECT * FROM service_table WHERE identifier = :identifier")
    fun getService(identifier: String): Flow<List<Service>>

    @Query("SELECT * FROM service_table WHERE type = 'E'")
    fun getEmergencyServices(): Flow<List<Service>>

    @Query(
        "SELECT * FROM service_table WHERE " +
                "name LIKE :search " +
                "OR type LIKE :search " +
                "OR main_contact LIKE :search"
    )
    fun search(search: String): Flow<List<Service>>

}