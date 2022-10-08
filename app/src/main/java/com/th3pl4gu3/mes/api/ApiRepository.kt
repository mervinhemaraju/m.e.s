package com.th3pl4gu3.mes.api

import java.util.*

/*
* Repository pattern for API requests
*/
class ApiRepository private constructor() {

    private val apiService =
        RetrofitManager.retrofit

    companion object {
        @Volatile
        private var INSTANCE: ApiRepository? = null

        fun getInstance(): ApiRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: ApiRepository()
                        .also { INSTANCE = it }
            }
    }

    suspend fun getServices() = when (Locale.getDefault()) {
        Locale.ENGLISH -> apiService.getServicesEn(API_ORDER_TYPE)
        Locale.FRENCH, Locale.FRANCE, Locale.CANADA_FRENCH -> apiService.getServicesFr(
            API_ORDER_TYPE
        )
        else -> apiService.getServicesEn(API_ORDER_TYPE)
    }
}