package com.kotlin.alekseyrobul.boomerang.helpers

import android.content.Context
import android.content.pm.PackageManager
import java.util.jar.Manifest

class PermissionHelper {
    companion object {
        @JvmStatic
        fun isMediaGalleryAvailable(context: Context): Boolean {
            return context.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        }
    }
}