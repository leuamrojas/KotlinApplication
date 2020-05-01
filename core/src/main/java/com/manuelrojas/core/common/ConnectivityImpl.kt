package com.manuelrojas.core.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import javax.inject.Inject

class ConnectivityImpl @Inject constructor(private val context: Context) : Connectivity {
  
  @Suppress("DEPRECATION")
  override fun hasNetworkAccess(): Boolean {
    var result = false
    val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

      connectivityManager?.let {
        it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
          result = when {
            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
          }
        }
      }

    } else {

      val info = connectivityManager?.activeNetworkInfo
      result = info != null && info.isConnected

    }

    return result
  }
}