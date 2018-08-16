package com.kotlin.alekseyrobul.englishquiz.views

import android.content.Context
import android.media.MediaPlayer
import android.view.SurfaceView
import android.view.View
import android.view.ViewManager
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.videoPlayer(theme: Int = 0, context: Context) = videoPlayer(theme, context) {}
//inline fun ViewManager.videoPlayer(theme: Int = 0, context: Context, init: View.(demoView: VideoPlayer) -> Unit): View {
//    val demoView = VideoPlayer(context)
//    return ankoView({ demoView.createView(AnkoContext.create(it)) }, theme, { init(demoView) })
//}
inline fun ViewManager.videoPlayer(theme: Int = 0, context: Context, init: VideoPlayer.(demoView: VideoPlayer) -> Unit): VideoPlayer {
    val demoView = VideoPlayer(context)
    ankoView({ demoView.createView(AnkoContext.create(it)) }, theme, { init(demoView,demoView) })
    return demoView
}

class VideoPlayer(context: Context): SurfaceView(context), AnkoComponent<Context> {
    /**
     * Private fields
     */
    private val mPlayer = MediaPlayer()

    /**
     * Listeners
     */
    private val mPlayerComplitionListener = MediaPlayer.OnCompletionListener {
        print("completed")
    }

    private val mPlayerErrorListener = MediaPlayer.OnErrorListener { mp, what, extra ->
        print(mp)
        print(what)
        print(extra)
        return@OnErrorListener true
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mPlayer.setDisplay(holder)
        mPlayer.setOnCompletionListener(mPlayerComplitionListener)
        mPlayer.setOnErrorListener(mPlayerErrorListener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mPlayer.stop()
        mPlayer.release()
    }

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) { constraintLayout() }
    }

    public fun playVideo(path:String) {
        mPlayer.setDataSource(path)
    }
}