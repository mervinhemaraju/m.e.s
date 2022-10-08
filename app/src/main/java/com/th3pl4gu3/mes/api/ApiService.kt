package com.th3pl4gu3.mes.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/*
* Retrofit API service to fetch service details from API
* Current service API configured: [MES by Th3pl4gu3]
*/
private const val API_BASE_URL = "https://mes.th3pl4gu3.com/"
private const val API_VERSION = "v0.2"
private const val API_QUERY_ORDER = "order"

private const val API_PATH_SERVICES_EN = "/api/${API_VERSION}/en/services?"
private const val API_PATH_SERVICES_FR = "/api/${API_VERSION}/fr/services?"

internal const val API_ORDER_TYPE = "asc"


private val mMoshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofitService = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(mMoshi))
    .baseUrl(API_BASE_URL)
    .build()

interface ApiService {
    @GET(API_PATH_SERVICES_EN)
    suspend fun getServicesEn(@Query(API_QUERY_ORDER) order: String): Response

    @GET(API_PATH_SERVICES_FR)
    suspend fun getServicesFr(@Query(API_QUERY_ORDER) order: String): Response
}

object RetrofitManager {

    val retrofit: ApiService by lazy {
        retrofitService
            .create(ApiService::class.java)
    }

}


