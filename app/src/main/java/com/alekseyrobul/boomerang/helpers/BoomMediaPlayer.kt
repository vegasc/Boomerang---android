package com.alekseyrobul.boomerang.helpers

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class BoomMediaPlayer: MediaPlayer() {

    var dataPath:Uri? = null

    val getDatapath get() = dataPath

    fun setPath(context: Context, uri: Uri) {
        super.setDataSource(context, uri)
        dataPath = uri
        prepareAsync()
    }
}























