package com.th3pl4gu3.mauritius_emergency_services.models

import android.app.NotificationManager

data class NotificationChannel(
    val id: String,
    val name: String,
    val description: String,
    val importance: Int
)

class NotificationChannels {
    companion object{
        fun load() = mutableListOf<NotificationChannel>().apply{
            add(
                NotificationChannel(
                    id = "channel_network",
                    name = "Network",
                    description = "Network interactions",
                    importance = NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }
    }
}