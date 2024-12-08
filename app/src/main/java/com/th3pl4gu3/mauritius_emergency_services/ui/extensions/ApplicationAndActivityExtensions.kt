package com.th3pl4gu3.mauritius_emergency_services.ui.extensions

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.work.WorkManager
import com.th3pl4gu3.mauritius_emergency_services.MesApplication
import com.th3pl4gu3.mauritius_emergency_services.models.items.NotificationChannels

fun MesApplication.createNotificationChannels() {

    // Load the channels
    val notificationChannels = NotificationChannels.load()

    // For each channel, register it
    notificationChannels.forEach {

        // Create the channel
        val channel = NotificationChannel(it.id, it.name, it.importance).apply {
            description = it.description
        }

        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

val MesApplication.MesWorkManager
    get() = WorkManager.getInstance(this)