package com.alekseyrobul.boomerang.classes

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Looper
import com.alekseyrobul.boomerang.helpers.DateUtils
import com.alekseyrobul.boomerang.helpers.FileUtilitty
import org.jcodec.api.SequenceEncoder
import org.jcodec.scale.BitmapUtil
import java.io.File

class BoomerangEffect {

    companion object {
        @JvmStatic
        private val loops = 2 // -1 for max loops needed

        @JvmStatic
        fun getBoomerangFrom(context: Context, uri: Uri, result:(Uri?) -> Unit) {
            Thread(Runnable {
                // get images
                val media = MediaMetadataRetriever()
                media.setDataSource(context, uri)

                val imgs = arrayListOf<Bitmap>()
                var i:Long = 1000000 // frame time in milliseconds
                var seconds = 0
                while (seconds < (media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toInt() / 1000)) {
                    val img = media.getFrameAtTime(i)
                    imgs.add(img)
                    i += 1000000
                    seconds++
                }

                for (i in 0..loops) {
                    // reverse images
                    imgs.addAll(imgs.reversed())
                }

                val folderFile = FileUtilitty.cacheFolder(context)
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