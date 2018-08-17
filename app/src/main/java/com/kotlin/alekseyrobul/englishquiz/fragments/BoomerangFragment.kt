package com.kotlin.alekseyrobul.englishquiz.fragments

import android.view.View
import com.kotlin.alekseyrobul.englishquiz.helpers.BaseFragment
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.support.v4.UI

class BoomerangFragment: BaseFragment() {

    override fun updateUI(): View {
        return UI {
            constraintLayout {
                val layout = constraintLayout()
                boomerangView {}.lparams {
                    topToTop = layout.top
                    bottomToBottom = layout.bottom
                    leftToLeft = layout.left
                    rightToRight = layout.right
                }
            }
        }.view
    }
}