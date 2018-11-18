package com.alekseyrobul.boomerang.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewManager
import android.widget.Button
import com.alekseyrobul.boomerang.R
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.boomButton(context: Context): com.alekseyrobul.boomerang.views.BoomButton = boomButton(context) {}
inline fun ViewManager.boomButton(context: Context, init: (@AnkoViewDslMarker BoomButton).() -> Unit): BoomButton {
    val boomButton = BoomButton(context)
    boomButton.setStyle()
    boomButton.createView(AnkoContext.create(context, boomButton))
    return ankoView({boomButton}, theme = 0, init = { init() })
}

class BoomButton(context: Context): Button(context), AnkoComponent<Button> {
    override fun createView(ui: AnkoContext<Button>): View {
        return with(ui) {
            constraintLayout {}
        }
    }

    fun setStyle() {
        layout.apply {
            leftPadding = dip(16)
            rightPadding = dip(16)
        }
        textColor = Color.WHITE

        val gradient = GradientDrawable()
        gradient.setColor(resources.getColor(R.color.colorAccent, context.theme))
        gradient.cornerRadius = 20.0f
        background = gradient
    }

    fun setStyle(color: Int) {
        layout.apply {
            leftPadding = dip(16)
            rightPadding = dip(16)
        }
        textColor = Color.WHITE

        val gradient = GradientDrawable()
        gradient.setColor(color)
        gradient.cornerRadius = 20.0f
        background = gradient
    }
}