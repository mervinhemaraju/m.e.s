package com.th3pl4gu3.mauritius_emergency_services.models.api

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CycloneReport(
    val level: Int,
    val next_bulletin: String,
    val news: List<String>
){
    fun getNextBulletinHour(): String {
        val timeSlices = this.next_bulletin.split(":")

        return if (timeSlices.isNotEmpty()){
            timeSlices[0]
        }else {
            "00"
        }
    }

    fun getNextBulletinMinute(): String {
        val timeSlices = this.next_bulletin.split(":")

        return if (timeSlices.size > 1){
            timeSlices[1]
        }else {
            "00"
        }
    }

    fun getNextBulletinSecond(): String {
        val timeSlices = this.next_bulletin.split(":")

        return if (timeSlices.size > 2){
            timeSlices[2]
        }else {
            "00"
        }
    }
}
