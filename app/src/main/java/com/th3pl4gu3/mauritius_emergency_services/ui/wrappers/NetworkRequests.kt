package com.th3pl4gu3.mauritius_emergency_services.ui.wrappers

import android.content.Context
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.data.network.NetworkServiceRepository
import com.th3pl4gu3.mauritius_emergency_services.models.responses.MesServicesResponse
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.IsConnectedToNetwork
import com.th3pl4gu3.mauritius_emergency_services.utils.NetworkRequestException
import javax.inject.Inject

class NetworkRequests @Inject constructor(
    private val context: Context,
    private val networkRepository: NetworkServiceRepository
) {
    suspend fun getMesServices(language: String): MesServicesResponse {
        if(!context.IsConnectedToNetwork) throw NetworkRequestException(context.resources.getString(
            R.string.message_internet_connection_needed))

        return networkRepository.getAllServices(language = language)
    }
}