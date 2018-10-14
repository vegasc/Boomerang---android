package com.kotlin.alekseyrobul.boomerang.views

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.view.View
import android.view.ViewManager
import android.widget.ProgressBar
import kotlinx.coroutines.experimental.android.UI
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.progressView(context: Context): ProgressView = progressView(context) {}
inline fun ViewManager.progressView(context: Context, init: (@AnkoViewDslMarker ProgressView).() -> Unit): ProgressView {
    val progressView = ProgressView(context)
    progressView.createView(AnkoContext.create(context, progressView))
    progressView.setStyle()
    return ankoView({progressView}, theme = 0, init = { init() })
}

class ProgressView(context: Context): View(context), AnkoComponent<View> {
    override fun createView(ui: AnkoContext<View>): View {
        return with(ui){
            return constraintLayout {  }
        }
    }

    fun setStyle() {
//        val layout = constraintLayout()
//        backgroundColor = Color.RED
//        progressBar {
//        }.lparams {
//            topToTop = layout.top
//            bottomToBottom = layout.bottom
//            leftToLeft = layout.left
//            rightToRight = layout.right
//        }
    }
}