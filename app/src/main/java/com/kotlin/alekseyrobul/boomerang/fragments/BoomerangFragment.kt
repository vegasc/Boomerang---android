package com.kotlin.alekseyrobul.boomerang.fragments

import android.view.View
import com.kotlin.alekseyrobul.boomerang.helpers.BaseFragment
import com.kotlin.alekseyrobul.boomerang.helpers.PermissionHelper
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.support.v4.UI

class BoomerangFragment: BaseFragment() {

    /**
     * Private fields
     */
    private var mViewListener = object : BoomerangFragmentViewListener {
        override fun buttonTappedChooseVideoFile() {
            getVideoFromLibarry()
        }

    }

    /**
     * Override funcs
     */
    override fun updateUI(): View {
        return UI {
            constraintLayout {
                val layout = constraintLayout()
                boomerangView(context = context) {
                    viewListener = mViewListener
                }.lparams {
                    topToTop = layout.top
                    bottomToBottom = layout.bottom
                    leftToLeft = layout.left
                    rightToRight = layout.right
                }
            }
        }.view
    }

    /**
     * Private funcs
     */
    private fun getVideoFromLibarry() {
        if (context == null) { return }
        val r = PermissionHelper.isMediaGalleryAvailable(context!!)
    }
}
















































