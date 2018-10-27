package com.kotlin.alekseyrobul.boomerang.views

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewManager
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.progressView(context: Context, theme: Int = 0) = progressView(context, theme) {}
inline fun ViewManager.progressView(context: Context, theme: Int = 0,
                                init: View.(pview: ProgressView) -> Unit): View {
    val pview = ProgressView(context = context)
    return ankoView({ pview.createView(AnkoContext.create(it)) }, theme, { init(pview) })
}


class ProgressView(context: Context): View(context), AnkoComponent<Context> {

    private lateinit var ankoContext: AnkoContext<Context>

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        ankoContext = ui
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
    }
}