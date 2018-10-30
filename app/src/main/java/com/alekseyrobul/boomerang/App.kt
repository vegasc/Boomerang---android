package com.alekseyrobul.boomerang

import android.app.Application
import android.content.Context
import android.content.Intent
import com.alekseyrobul.boomerang.services.ExitService

class App: Application() {

    companion object {
        @JvmStatic
        fun getAppName(context: Context):String {
            val info = context.applicationInfo
            val stringId = info.labelRes
            if (stringId == 0) {
                return info.nonLocalizedLabel.toString()
            } else {
                return context.getString(stringId)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        val intent = Intent(baseContext, ExitService::class.java)
        startService(intent)
    }
}