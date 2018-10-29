package com.alekseyrobul.boomerang.helpers

import android.content.Context
import android.content.pm.PackageManager

class PermissionHelper {
    companion object {
        /**
         * Request codes
         */
        @JvmStatic
        val PERMISSION_READ_EXTERNAL_STORAGE = 100

        /**
         * Funcs
         */
        @JvmStatic
        fun isPermissionReadStorageAvailable(context: Context): Boolean {
            return context.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        }

        @JvmStatic
        fun isPermissionWriteStorageAvailable(context: Context): Boolean {
            return context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        }
    }
}