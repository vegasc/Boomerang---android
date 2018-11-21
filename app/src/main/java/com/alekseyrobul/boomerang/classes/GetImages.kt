package com.alekseyrobul.boomerang.classes

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Looper
import android.provider.OpenableColumns
import com.alekseyrobul.boomerang.helpers.FileUtilitty
import com.alekseyrobul.boomerang.views.VideoCard
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class GetImages(val context: Context, private val uri: Uri) {
    fun getImages (completion:(images:ArrayList<Uri>, data: VideoCard.VideoCardData) -> Unit) {
        Thread(Runnable {
            // clear cache
            val folderFile = FileUtilitty.cacheFolder(context)
            val screensFolder = File(folderFile.absolutePath + File.separator + "clip_screens")
            if (screensFolder.exists()) {
                // clear folder
                for (file in folderFile.listFiles()) {
                    file.delete()
                }
            } else {
                screensFolder.mkdirs()
            }

            // get images
            val media = MediaMetadataRetriever()
            media.setDataSource(context, uri)

            var imgs = arrayListOf<Bitmap>()
            var i:Long = 1000000 // frame time in milliseconds
            var seconds = 0
            while (seconds < 6) {
                val img = media.getFrameAtTime(i)
                imgs.add(img)
                i += 1000000
                seconds++
            }

            // get uris
            var uris = ArrayList<Uri>()
            var i2 = 0
            for (img in imgs) {
                // write screen to cache
                val movieFile = File(screensFolder.absolutePath + File.separator + "clip_screen$i2.jpg")
                val bos = ByteArrayOutputStream()
                img.compress(Bitmap.CompressFormat.JPEG, 100, bos)
                val bytes = bos.toByteArray()
                val fos = FileOutputStream(movieFile)
                fos.write(bytes)
                img.recycle()

                uris.add(Uri.parse(movieFile.toURI().toString()))
                i2 += 1
            }

            // get video data
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
            cursor.moveToFirst()
            val fileName = cursor.getString(nameIndex)
            val fileSize = cursor.getString(sizeIndex)
            cursor.close()

            val videoCardData = VideoCard.VideoCardData(fileName, fileSize, uris[0], uris.size)

            // return data
            val handler = android.os.Handler(Looper.getMainLooper())
            val runnable = Runnable {
                completion(uris, videoCardData)
            }
            handler.post(runnable)
        }).start()
    }
}