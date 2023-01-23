package com.th3pl4gu3.mes.ui.extensions

import androidx.core.app.NotificationCompat
import com.th3pl4gu3.mes.MesApplication
import com.th3pl4gu3.mes.R

val MesApplication.NotificationBuilderServiceUpdating
    get() = NotificationCompat.Builder(this, "channel_network")
        .setSmallIcon(R.drawable.ic_mes)
        .setContentTitle("Mauritius Emergency Services")
        .setContentText("Updating services list...")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setOngoing(true)
        .build()