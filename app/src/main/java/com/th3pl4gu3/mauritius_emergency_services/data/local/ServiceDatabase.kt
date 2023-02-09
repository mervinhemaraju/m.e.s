package com.th3pl4gu3.mauritius_emergency_services.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.th3pl4gu3.mauritius_emergency_services.data.Converters
import com.th3pl4gu3.mauritius_emergency_services.models.Service

/**
* The main Mes database that caches all services retrieved
* from the MES API.
* Extends the parent class RoomDatabase() of Android
**/
@Database(
    entities = [Service::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MesDatabase : RoomDatabase() {

    /*
    * Objects Dao defined here
    */
    abstract fun serviceDao(): ServiceDao

    companion object {
        @Volatile
        private var Instance: MesDatabase? = null

        fun getDatabase(context: Context): MesDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MesDatabase::class.java, "mes_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}