package com.andreromano.foodmix.ui.utils

import android.net.ConnectivityManager
import android.net.NetworkCapabilities


class NetworkUtils(
    private val connectivityManager: ConnectivityManager
) {

    fun isNetworkAvailable(): Boolean {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }

}