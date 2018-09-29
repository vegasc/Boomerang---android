package com.kotlin.alekseyrobul.boomerang.fragments.gif

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.view.View
import com.kotlin.alekseyrobul.boomerang.classes.GifEffect
import com.kotlin.alekseyrobul.boomerang.fragments.boomerang.BoomerangFragment
import com.kotlin.alekseyrobul.boomerang.helpers.BaseFragment
import com.kotlin.alekseyrobul.boomerang.helpers.PermissionHelper
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.support.v4.UI

class GifFragment: BaseFragment() {

    companion object {
        @JvmStatic
        val GET_VIDEO_REQUEST = 101
    }

    private lateinit var mView:View

    private val gifFragmentViewListener = object : GifFragmentViewListener {
        override fun chooseVideo() {
            getVideoFromLibrary()
        }

        override fun saveGif() {

        }
    }

    override fun updateUI(): View {
        return UI {
            constraintLayout {
                val layout = constraintLayout()
                mView = gifView(context = context) {
                    viewListener = gifFragmentViewListener
                }.lparams {
                    topToTop = layout.top
                    bottomToBottom = layout.bottom
                    leftToLeft = layout.left
                    rightToRight = layout.right
                }
            }
        }.view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == BoomerangFragment.GET_VIDEO_REQUEST) {
                convertVideoToGif(data)
            }
        }
    }

    /**
     * Private funcs
     */
    private fun getVideoFromLibrary() {
        if (context == null) { return }
        val r = PermissionHelper.isPermissionReadStorageAvailable(context!!)
        val w = PermissionHelper.isPermissionWriteStorageAvailable(context!!)
        if (!r || !w) {
            // one of permissions is denied
            requestPermissions(arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
                    PermissionHelper.PERMISSION_READ_EXTERNAL_STORAGE)
            return
        }

        // show library activity
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, GifFragment.GET_VIDEO_REQUEST)
    }

    private fun convertVideoToGif(intent: Intent?) {
        if (intent == null) { return }
        if (context == null) { return }
        GifEffect.gifFromVideo(context!!, intent!!.data)
    }
}