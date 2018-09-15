package com.kotlin.alekseyrobul.boomerang.classes

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.media.MediaMetadataRetriever.OPTION_NEXT_SYNC
import android.net.Uri
import android.os.Environment
import com.kotlin.alekseyrobul.boomerang.App
import org.jcodec.api.SequenceEncoder
import org.jcodec.common.model.ColorSpace
import org.jcodec.scale.BitmapUtil
import java.io.ByteArrayOutputStream
import java.io.File

class BoomerangEffect {
    companion object {
        @JvmStatic
        fun getBoomerangFrom(context: Context, uri: Uri) {
            // get images
            val media = MediaMetadataRetriever()
            media.setDataSource(context, uri)

            var imgs = arrayListOf<Bitmap>()
            var i:Long = 0
            while (i < 100) {
                val img = media.getFrameAtTime(i, OPTION_NEXT_SYNC)
                imgs.add(img)
                i++
            }
            println(imgs)

            // create file
            val folderName = Environment.getExternalStorageDirectory().absolutePath  + File.separator +
                    "." + App.getAppName(context) + File.separator + "boomfile"
            val folderFile = File(folderName)
            folderFile.mkdirs()

            val movieFile = File(folderFile.absolutePath + File.separator + "boom_movie.mp4")
            // convert into video
            val encoder = SequenceEncoder.createSequenceEncoder(movieFile, 20)
            for (img in imgs) {
                // create movie from 'Picture'
                encoder.encodeNativeFrame(BitmapUtil.fromBitmap(img))
            }
            encoder.finish()
        }

        @JvmStatic
        private  fun getImagesArray(uri: Uri) {

        }
    }
}