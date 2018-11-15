package com.alekseyrobul.boomerang.views

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.ViewManager
import com.alekseyrobul.boomerang.helpers.BoomMediaPlayer
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.videoSurfaceView(context: Context): android.view.SurfaceView = videoSurfaceView(context) {}
inline fun ViewManager.videoSurfaceView(context: Context, init: (@AnkoViewDslMarker VideoPlayer).() -> Unit): VideoPlayer {
    val videoPlayer = VideoPlayer(context)
    videoPlayer.createView(AnkoContext.create(context))
    return ankoView({videoPlayer}, theme = 0, init = { init() })
}

class VideoPlayer(context: Context): SurfaceView(context), AnkoComponent<Context> {
    /**
     * Public fields
     */
    var isLoop = false
    var isPrepeared = false
    var isCreated = false

    /**
     * Private fields
     */
    private val mPlayer = BoomMediaPlayer()
    private var mHolder = holder
    private var mUri:Uri? = null

    /**
     * Listeners
     */
    private val mPlayerPrepareListener = MediaPlayer.OnPreparedListener { mp ->
        mp.start()
        isPrepeared = true
    }

    private val mPlayerCompletionListener = MediaPlayer.OnCompletionListener {
        if (isLoop) {
            mPlayer.start()
        }
    }

    private val mPlayerErrorListener = MediaPlayer.OnErrorListener { mp, what, extra ->
        println(mp)
        println(what)
        println(extra)
        isPrepeared = false
        return@OnErrorListener true
    }

    private val mSurfaceListener = object : SurfaceHolder.Callback {
        override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
            mPlayer.setDisplay(holder)
            if (mUri != null && isPrepeared) {
                mPlayer.setPath(context, mUri!!)
            }
        }

        override fun surfaceDestroyed(holder: SurfaceHolder?) {
            if (mPlayer.isPlaying) {
                mPlayer.stop()
            }
            mPlayer.setDisplay(null)
            isPrepeared = false
            isCreated = false
        }

        override fun surfaceCreated(holder: SurfaceHolder?) {
            mHolder = holder
            mPlayer.setDisplay(null)
            mPlayer.setOnPreparedListener(null)
            mPlayer.setOnCompletionListener(null)
            mPlayer.setOnErrorListener(null)
            mPlayer.reset()

            mPlayer.setDisplay(mHolder)
            mPlayer.setOnPreparedListener(mPlayerPrepareListener)
            mPlayer.setOnCompletionListener(mPlayerCompletionListener)
            mPlayer.setOnErrorListener(mPlayerErrorListener)
            if (mUri != null && isPrepeared) {
                mPlayer.setPath(context, mUri!!)
            }
            isCreated = true
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        holder.addCallback(mSurfaceListener)
//        mPlayer.setOnPreparedListener(mPlayerPrepareListener)
//        mPlayer.setOnCompletionListener(mPlayerCompletionListener)
//        mPlayer.setOnErrorListener(mPlayerErrorListener)
    }

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) { constraintLayout() }
    }

    fun playVideo(context: Context, uri: Uri) {
        mUri = uri
        if (isCreated) {
            mPlayer.setPath(context, uri)
            return
        }
    }
}