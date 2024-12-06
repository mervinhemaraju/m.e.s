package com.th3pl4gu3.mauritius_emergency_services.models.api

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CycloneReport(
    val level: Int,
    val next_bulletin: String?,
    val news: List<String>
){
    fun getTimeSlices(): Triple<String, String, String> {

        if(!next_bulletin.isNullOrEmpty()) {

            val timeSlices = this.next_bulletin.split(":")

            if (timeSlices.isNotEmpty() && timeSlices.size > 2){
                return Triple(timeSlices[0], timeSlices[1], timeSlices[2])
            }
        }

        return Triple("00", "00", "00")
    }
}
