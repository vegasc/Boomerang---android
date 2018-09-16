package com.kotlin.alekseyrobul.boomerang.fragments.gif

import android.content.Context
import android.view.View
import android.view.ViewManager
import com.kotlin.alekseyrobul.boomerang.R
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.custom.ankoView

/**
 * Extensions
 */
inline fun ViewManager.gifView(theme: Int = 0, context: Context, init: GifFragmentView.(view: GifFragmentView) -> Unit): GifFragmentView {
    val bview = GifFragmentView(context)
    ankoView({ bview.createView(AnkoContext.create(it)) }, theme, { init(bview, bview) })
    return bview
}

interface GifFragmentViewListener {
    fun chooseVideo()
    fun saveGif()
}

class GifFragmentView(context: Context): View(context), AnkoComponent<Context> {

    var viewListener:GifFragmentViewListener? = null

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            constraintLayout {
                val layout = constraintLayout()

                imageView {
                    id = R.id.gif_fragment_image_view
                    backgroundColor = context!!.getColor(R.color.colorGray)
                }.lparams (width = 800, height = 800) {
                    topToTop = layout.top
                    leftToLeft = layout.left
                    rightToRight = layout.right
                    topMargin = dip(50)
                }

                button(text = R.string.button_save_gif_file) {
                    id = R.id.button_save_gif_file
                    setOnClickListener { viewListener?.saveGif() }
                }.lparams {
                    bottomToBottom = layout.bottom
                    leftToLeft = layout.left
                    rightToRight = layout.right
                    bottomMargin = dip(16)
                }

                button(text = R.string.button_choose_video_file) {
                    setOnClickListener { viewListener?.chooseVideo() }
                }.lparams {
                    bottomToTop = R.id.button_save_gif_file
                    leftToLeft = layout.left
                    rightToRight = layout.right
                }
            }
        }
    }
}