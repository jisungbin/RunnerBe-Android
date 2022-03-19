/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [NetworkUtil.kt] created by Ji Sungbin on 22. 1. 31. 오후 4:50
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.android.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object NetworkUtil {
    @Suppress("DEPRECATION", "MissingPermission")
    fun isNetworkAvailable(context: Context) =
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getNetworkCapabilities(activeNetwork)?.run {
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                } ?: false
            } else {
                val activeNetworkInfo = activeNetworkInfo
                if (activeNetworkInfo != null) {
                    val type = activeNetworkInfo.type
                    if (type == ConnectivityManager.TYPE_MOBILE || type == ConnectivityManager.TYPE_WIFI) {
                        return true
                    }
                }
                return false
            }
        }
}
