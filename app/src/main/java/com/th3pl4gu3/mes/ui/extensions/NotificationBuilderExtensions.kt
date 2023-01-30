package com.th3pl4gu3.mes.ui.extensions

import androidx.compose.ui.res.stringResource
import androidx.core.app.NotificationCompat
import com.th3pl4gu3.mes.MesApplication
import com.th3pl4gu3.mes.R

val MesApplication.NotificationBuilderServiceUpdating
    get() = NotificationCompat.Builder(this, "channel_network")
        .setSmallIcon(R.drawable.ic_mes)
        .setContentTitle(applicationContext.resources.getText(R.string.app_name_long))
        .setContentText(applicationContext.resources.getText(R.string.message_updating_services))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setOngoing(true)
        .build()