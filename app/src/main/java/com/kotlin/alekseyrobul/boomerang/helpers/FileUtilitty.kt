package com.kotlin.alekseyrobul.boomerang.helpers

import android.content.Context
import android.os.Environment
import com.kotlin.alekseyrobul.boomerang.App
import java.io.File

class FileUtilitty {
    companion object {
        /**
         * Creates hidden app folder in external storage
         */
        @JvmStatic
        fun externalMediaFolder(context: Context) : File {
            // create file
            val folderName = Environment.getExternalStorageDirectory().absolutePath  + File.separator +
                    "." + App.getAppName(context) + File.separator + "media"
            val folderFile = File(folderName)
            folderFile.mkdirs()
            return folderFile
        }
    }
}