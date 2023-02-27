package com.th3pl4gu3.mauritius_emergency_services.ui.extensions

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri

val Context.IsConnectedToNetwork: Boolean
    get() {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

fun Context.launchEmailIntent(recipient: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(
            Intent.EXTRA_EMAIL,
            arrayOf(recipient)
        )
    }
    startActivity(intent)
}