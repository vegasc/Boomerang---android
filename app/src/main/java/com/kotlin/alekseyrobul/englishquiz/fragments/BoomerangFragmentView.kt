package com.kotlin.alekseyrobul.englishquiz.fragments

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewManager
import com.kotlin.alekseyrobul.englishquiz.views.VideoPlayer
import com.kotlin.alekseyrobul.englishquiz.views.videoPlayer
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.boomerangView(theme: Int = 0) = boomerangView(theme) {}
inline fun ViewManager.boomerangView(theme: Int = 0, init: View.(demoView: BoomerangFragmentView) -> Unit): View {
    val demoView = BoomerangFragmentView()
    return ankoView({ demoView.createView(AnkoContext.create(it)) }, theme, { init(demoView) })
}

class BoomerangFragmentView: AnkoComponent<Context> {
    private lateinit var mVideoPlayer: VideoPlayer

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            constraintLayout {
                mVideoPlayer = videoPlayer(context = ui.ctx) {
                    backgroundColor = Color.GREEN
                }.lparams(width = 300, height = 300){
                    x = constraintLayout().x
                    y = constraintLayout().y
                }
            }
        }
    }
}
