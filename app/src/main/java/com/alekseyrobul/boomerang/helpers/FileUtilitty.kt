package com.alekseyrobul.boomerang.helpers

import android.content.Context
import android.os.Environment
import com.alekseyrobul.boomerang.App
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

        @JvmStatic
        fun clearExternalMediaFolder(context: Context, completion:() -> Unit) {
            val folderName = Environment.getExternalStorageDirectory().absolutePath  + File.separator +
                    "." + App.getAppName(context) + File.separator + "media"
            val folderFile = File(folderName)
            if (folderFile.exists()) {
                // clear folder
                for (file in folderFile.listFiles()) {
                    file.delete()
                }
                // delete folder
                folderFile.delete()
                completion()
            }
        }
    }
}