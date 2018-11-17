package com.alekseyrobul.boomerang.classes

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Looper

class GetImages(val context: Context, private val uri: Uri) {
    fun getImages (completion:(images:ArrayList<Bitmap>) -> Unit) {
        Thread(Runnable {
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
            val handler = android.os.Handler(Looper.getMainLooper())
            val runnable = Runnable {
                completion(imgs)
            }
            handler.post(runnable)
        }).start()
    }
}