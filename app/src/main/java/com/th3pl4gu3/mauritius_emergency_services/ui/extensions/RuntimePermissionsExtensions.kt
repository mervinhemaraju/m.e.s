package com.th3pl4gu3.mauritius_emergency_services.ui.extensions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.th3pl4gu3.mauritius_emergency_services.MesActivity

val Context.HasNotificationPermission: Boolean
    get() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.POST_NOTIFICATIONS
    ) == PackageManager.PERMISSION_GRANTED

val Context.HasNecessaryPermissions: Boolean
    get() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.CALL_PHONE
    ) == PackageManager.PERMISSION_GRANTED

val GetRuntimePermissions: Array<String>
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.CALL_PHONE,
            Manifest.permission.POST_NOTIFICATIONS
        )
    } else {
        arrayOf(
            Manifest.permission.CALL_PHONE
        )
    }

val MesActivity.ShouldShowRationale: Boolean
    get() = ActivityCompat.shouldShowRequestPermissionRationale(
        this,
        Manifest.permission.CALL_PHONE
    )