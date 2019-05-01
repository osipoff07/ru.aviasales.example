package ru.aviasales.common.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import ru.aviasales.common.domain.ConnectionChecker

class SystemConnectionChecker(
    private val context: Context
): ConnectionChecker {

    override fun isConnected(): Boolean {
        val connectionManager: ConnectivityManager? = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val netInfo: NetworkInfo? = connectionManager?.activeNetworkInfo

        return netInfo?.isConnected == true
    }
}