package com.kotlin.alekseyrobul.boomerang.fragments

import android.content.Context
import android.net.Uri
import android.view.View
import android.view.ViewManager
import com.kotlin.alekseyrobul.boomerang.R
import com.kotlin.alekseyrobul.boomerang.views.VideoPlayer
import com.kotlin.alekseyrobul.boomerang.views.videoSurfaceView
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.custom.ankoView
import java.net.URI

/**
 * Extensions
 */
inline fun ViewManager.boomerangView(theme: Int = 0, context: Context, init: BoomerangFragmentView.(view: BoomerangFragmentView) -> Unit): BoomerangFragmentView {
    val bview = BoomerangFragmentView(context)
    ankoView({ bview.createView(AnkoContext.create(it)) }, theme, { init(bview, bview) })
    return bview
}

/**
 * Listeners
 */
interface BoomerangFragmentViewListener {
    fun buttonTappedChooseVideoFile()
    fun buttonTappedSaveVideo()
}

class BoomerangFragmentView(context: Context): View(context), AnkoComponent<Context> {
    /**
     * Public fields
     */
    var viewListener:BoomerangFragmentViewListener? = null

    /**
     * Private fields
     */
    private lateinit var mVideoPlayer: VideoPlayer

    /**
     * Override funcs
     */
    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            constraintLayout {
                val layout = constraintLayout()

                mVideoPlayer = videoSurfaceView(context = context) {
                    isLoop = true
                }.lparams(height = 800) {
                    topToTop = layout.top
                    leftToLeft = layout.left
                    rightToRight = layout.right
                }

                button(text = R.string.button_save_video_file) {
                    id = R.id.button_save_video_file
                    setOnClickListener { viewListener?.buttonTappedSaveVideo() }
                }.lparams {
                    bottomToBottom = layout.bottom
                    leftToLeft = layout.left
                    rightToRight = layout.right
                    bottomMargin = dip(16)
                }

                button(text = R.string.button_choose_video_file) {
                    setOnClickListener { viewListener?.buttonTappedChooseVideoFile() }
                }.lparams {
                    bottomToTop = R.id.button_save_video_file
                    leftToLeft = layout.left
                    rightToRight = layout.right
                }
            }
        }
    }

    /**
     * Public funcs
     */
    fun displayVideo(uri: Uri) {
        mVideoPlayer.playVideo(context, uri)
    }
}























