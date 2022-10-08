package com.th3pl4gu3.mes.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.th3pl4gu3.mes.api.Service

/*
 * CRUD for the Service Object
*/
@Dao
interface ServiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(services: List<Service>)

    @Query("DELETE FROM service_table")
    fun wipe()

    @Query("SELECT * FROM service_table")
    fun getAll(): DataSource.Factory<Int, Service>

    @Query("SELECT * FROM service_table WHERE type = 'E'")
    fun getEmergencies(): LiveData<List<Service>>

    @Query("SELECT * FROM service_table WHERE name LIKE :search OR type LIKE :search OR number LIKE :search")
    fun search(search: String): DataSource.Factory<Int, Service>

    @Query("SELECT COUNT(identifier) from service_table")
    suspend fun count(): Int

    @Transaction
    suspend fun refresh(services: List<Service>) {
        wipe()
        insertAll(services)
    }
}