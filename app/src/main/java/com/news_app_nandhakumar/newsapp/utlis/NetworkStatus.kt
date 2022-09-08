package com.news_app_nandhakumar.newsapp.utlis

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class NetworkStatus @Inject constructor(@ActivityContext private val context: Context) {
    private var isConnected: Boolean = false

    fun isConnectedInternet(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null)
                isConnected = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            else
                return isConnected
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
        return isConnected
    }
}