package com.alekseyrobul.boomerang.views

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewManager
import android.widget.LinearLayout
import com.alekseyrobul.boomerang.R
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.videoCard(context: Context): VideoCard = videoCard(context) {}
inline fun ViewManager.videoCard(context: Context, init: (@AnkoViewDslMarker VideoCard).() -> Unit): VideoCard {
    val videoCard = VideoCard(context)
    videoCard.createView(AnkoContext.create(context))
    return ankoView({videoCard}, theme = 0, init = { init() })
}

class VideoCard(context: Context): LinearLayout(context), AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>): View {
        return linearLayout {
//            val layout = constraintLayout()
//            layout.backgroundColor = resources.getColor(R.color.colorPrimary, context!!.theme)
//
//            linearLayout {
//                orientation = LinearLayout.HORIZONTAL
//                imageView{
//                    backgroundColor = Color.BLACK
//                }
//            }.lparams(width = matchParent, height = wrapContent) {
//                topToTop        = layout.top
//                bottomToBottom  = layout.bottom
//                leftToLeft      = layout.left
//                rightToRight    = layout.right
//            }
            backgroundColor = resources.getColor(R.color.colorPrimary, context!!.theme)
            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                imageView{
                    backgroundColor = Color.BLACK
                    minimumWidth = 250
                    minimumHeight = 250
                }
            }.lparams(width = matchParent, height = wrapContent)
        }
    }
}