package com.kotlin.alekseyrobul.boomerang.views

import android.content.Context
import android.content.IntentSender
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.progressView(context: Context, init: (@AnkoViewDslMarker ProgressView).() -> Unit): ProgressView {
    val progressView = ProgressView(context)
    progressView.createView(AnkoContext.create(context, progressView))
    constraintLayout {
        val layout = constraintLayout()
        view {
            backgroundColor = Color.BLACK
            alpha = 0.9f
        }.lparams {
            topToTop = layout.top
            bottomToBottom = layout.bottom
            leftToLeft = layout.left
            rightToRight = layout.right
        }

        progressBar {

        }.lparams {
            topToTop = layout.top
            bottomToBottom = layout.bottom
            leftToLeft = layout.left
            rightToRight = layout.right
        }
    }
    return ankoView({progressView}, theme = 0, init = { init()})
}

class ProgressView(context: Context): ViewGroup(context), AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        constraintLayout {}
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }
}