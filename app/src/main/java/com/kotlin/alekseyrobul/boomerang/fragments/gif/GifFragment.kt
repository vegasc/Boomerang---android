package com.kotlin.alekseyrobul.boomerang.fragments.gif

import android.view.View
import com.kotlin.alekseyrobul.boomerang.helpers.BaseFragment
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.support.v4.UI

class GifFragment: BaseFragment() {

    private lateinit var mView:View

    override fun updateUI(): View {
        return UI {
            constraintLayout {
                val layout = constraintLayout()
                mView = gifView(context = context) {

                }.lparams {
                    topToTop = layout.top
                    bottomToBottom = layout.bottom
                    leftToLeft = layout.left
                    rightToRight = layout.right
                }
            }
        }.view
    }
}