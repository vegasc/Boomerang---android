package com.kotlin.alekseyrobul.boomerang.fragments

import android.content.Context
import android.view.View
import android.view.ViewManager
import com.kotlin.alekseyrobul.boomerang.views.VideoPlayer
import com.kotlin.alekseyrobul.boomerang.views.videoSurfaceView
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.boomerangView(theme: Int = 0, init: View.(demoView: BoomerangFragmentView) -> Unit): View {
    val demoView = BoomerangFragmentView()
    return ankoView({ demoView.createView(AnkoContext.create(it)) }, theme, { init(demoView) })
}

class BoomerangFragmentView: AnkoComponent<Context> {
    private lateinit var mVideoPlayer: VideoPlayer

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            constraintLayout {
                val layout = constraintLayout()
                mVideoPlayer = videoSurfaceView(context = context) {

                }.lparams(height = 800) {
                    topToTop = layout.top
                    leftToLeft = layout.left
                    rightToRight = layout.right
                }

            }
        }
    }
}
