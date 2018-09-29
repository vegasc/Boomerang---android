package com.kotlin.alekseyrobul.boomerang.classes

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri

class GifEffect {
    companion object {
        @JvmStatic
        fun gifFromVideo(context: Context, uri: Uri) {
            // get images
            val media = MediaMetadataRetriever()
            media.setDataSource(context, uri)

            var imgs = arrayListOf<Bitmap>()
            var i:Long = 1000000 // frame time in milliseconds
            var seconds = media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toInt() / 1000

            while (seconds > 1) {
                val img = media.getFrameAtTime(i)
                imgs.add(img)
                i += 1000000
                seconds -= 1
            }

            println(imgs)
        }
    }
}