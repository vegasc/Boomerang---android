package com.alekseyrobul.boomerang.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.alekseyrobul.boomerang.helpers.FileUtilitty

/**
 * Runs code when application goes closed
 */
class ExitService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        FileUtilitty.clearExternalMediaFolder(context = baseContext, completion = {
            stopSelf()
        })
    }
}