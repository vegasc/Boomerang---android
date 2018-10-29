package com.alekseyrobul.boomerang.classes

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Looper
import com.alekseyrobul.boomerang.helpers.FileUtilitty
import org.jcodec.api.SequenceEncoder
import org.jcodec.scale.BitmapUtil
import java.io.File

class BoomerangEffect {

    companion object {
        @JvmStatic
        fun getBoomerangFrom(context: Context, uri: Uri, result:(Uri?) -> Unit) {
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

                // reverse images
                imgs.addAll(imgs.reversed())
                val folderFile = FileUtilitty.externalMediaFolder(context)

                val movieFile = File(folderFile.absolutePath + File.separator + "boom_movie.mp4")
                // convert into video
                val encoder = SequenceEncoder.createSequenceEncoder(movieFile, 10)
                for (img in imgs) {
                    // create movie from 'Picture'
                    encoder.encodeNativeFrame(BitmapUtil.fromBitmap(img))
                }
                encoder.finish()

                if (movieFile.exists()) {
                    // return to a main thread
                    val handler = android.os.Handler(Looper.getMainLooper())
                    val runnable = Runnable {
                        result(Uri.parse(movieFile.toURI().toString()))
                    }
                    handler.post(runnable)
                } else {
                    result(null)
                }
            }).start()
        }
    }
}