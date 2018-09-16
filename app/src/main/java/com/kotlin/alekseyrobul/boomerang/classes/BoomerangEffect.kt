package com.kotlin.alekseyrobul.boomerang.classes

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Environment
import com.kotlin.alekseyrobul.boomerang.App
import org.jcodec.api.SequenceEncoder
import org.jcodec.scale.BitmapUtil
import java.io.File

class BoomerangEffect {

    companion object {
        @JvmStatic
        fun getBoomerangFrom(context: Context, uri: Uri, result:(Uri?) -> Unit) {
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

            // reverse images
            imgs.addAll(imgs.reversed())

            // create file
            val folderName = Environment.getExternalStorageDirectory().absolutePath  + File.separator +
                    "." + App.getAppName(context) + File.separator + "boomfile"
            val folderFile = File(folderName)
            folderFile.mkdirs()

            val movieFile = File(folderFile.absolutePath + File.separator + "boom_movie.mp4")
            // convert into video
            val encoder = SequenceEncoder.createSequenceEncoder(movieFile, 10)
            for (img in imgs) {
                // create movie from 'Picture'
                encoder.encodeNativeFrame(BitmapUtil.fromBitmap(img))
            }
            encoder.finish()

            if (movieFile.exists()) {
                result(Uri.parse(movieFile.toURI().toString()))
            } else {
                result(null)
            }
        }
    }
}