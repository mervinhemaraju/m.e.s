package com.th3pl4gu3.mes.data

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.th3pl4gu3.mes.MesApplication
import com.th3pl4gu3.mes.api.MesApiService
import com.th3pl4gu3.mes.data.local.MesDatabase
import com.th3pl4gu3.mes.data.local.OfflineServiceRepository
import com.th3pl4gu3.mes.data.network.MesApiServiceRepository
import com.th3pl4gu3.mes.data.store.DataStoreRepository
import com.th3pl4gu3.mes.data.store.StoreRepository
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import java.util.*
import com.th3pl4gu3.mes.data.local.ServiceRepository as LocalServiceRepository
import com.th3pl4gu3.mes.data.network.ServiceRepository as NetworkServiceRepository

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val onlineServiceRepository: NetworkServiceRepository
    val offlineServiceRepository: LocalServiceRepository
    val dataStoreServiceRepository: StoreRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer(private val context: Context): AppContainer {

    companion object {
        private const val TAG = "DEFAULT_APP_CONTAINER"
        private const val BASE_URL = "https://mes.mervinhemaraju.com/api/v1/%s/"
    }
    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    @OptIn(ExperimentalSerializationApi::class)
    private fun requireRetrofit(language: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(String.format(BASE_URL, language))
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: MesApiService by lazy {
        return@lazy if(!AppCompatDelegate.getApplicationLocales().isEmpty){
            requireRetrofit(
                AppCompatDelegate.getApplicationLocales()[0].toString()
            ).create(MesApiService::class.java)
        }else{
            requireRetrofit(
                "en"
            ).create(MesApiService::class.java)
        }

    }

    override val onlineServiceRepository: NetworkServiceRepository by lazy {
        MesApiServiceRepository(retrofitService)
    }

    override val offlineServiceRepository: LocalServiceRepository by lazy {
        OfflineServiceRepository(MesDatabase.getDatabase(context).serviceDao())
    }

    override val dataStoreServiceRepository: StoreRepository by lazy {
        DataStoreRepository(context as MesApplication)
    }
}