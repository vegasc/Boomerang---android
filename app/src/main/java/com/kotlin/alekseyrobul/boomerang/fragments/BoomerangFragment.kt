package com.kotlin.alekseyrobul.boomerang.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Environment
import android.view.View
import com.kotlin.alekseyrobul.boomerang.helpers.BaseFragment
import com.kotlin.alekseyrobul.boomerang.helpers.PermissionHelper
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.support.v4.UI
import java.io.File

class BoomerangFragment: BaseFragment() {

    companion object {
        @JvmStatic
        val GET_VIDEO_REQUEST = 101
    }

    /**
     * Private fields
     */
    private lateinit var mView:BoomerangFragmentView

    private var mViewListener = object : BoomerangFragmentViewListener {
        override fun buttonTappedChooseVideoFile() {
            getVideoFromLibrary()
        }

    }

    /**
     * Override funcs
     */
    override fun updateUI(): View {
        return UI {
            constraintLayout {
                val layout = constraintLayout()
                mView = boomerangView(context = context) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GET_VIDEO_REQUEST) {
                displayVideoFrom(data)
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
        startActivityForResult(intent, GET_VIDEO_REQUEST)
    }

    private fun displayVideoFrom(intent: Intent?) {
        if (intent == null) { return }
        if (context == null) { return }
        mView.displayVideo(intent!!.data)
    }
}
















































