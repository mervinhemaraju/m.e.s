package com.th3pl4gu3.mes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.th3pl4gu3.mes.api.Service

/*
* The main Mes database that caches all services retrieved
* from the MES API.
* Extends the parent class RoomDatabase() of Android
*/
@Database(
    entities = [Service::class],
    version = 1,
    exportSchema = false
)
abstract class MesDatabase : RoomDatabase() {

    /*
    * Objects Dao defined here
    */
    abstract fun serviceDao(): ServiceDao

    companion object {
        /*
        * Singleton prevents multiple instances of database opening at the
        * same time.
        */
        @Volatile
        private var INSTANCE: MesDatabase? = null

        fun getDatabase(context: Context): MesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MesDatabase::class.java,
                    "mes_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}