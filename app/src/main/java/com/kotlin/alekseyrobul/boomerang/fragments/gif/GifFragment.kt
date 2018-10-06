package com.kotlin.alekseyrobul.boomerang.fragments.gif

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import com.kotlin.alekseyrobul.boomerang.R
import com.kotlin.alekseyrobul.boomerang.classes.GifEffect
import com.kotlin.alekseyrobul.boomerang.fragments.boomerang.BoomerangFragment
import com.kotlin.alekseyrobul.boomerang.helpers.BaseFragment
import com.kotlin.alekseyrobul.boomerang.helpers.PermissionHelper
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.button
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.dip
import org.jetbrains.anko.imageView
import org.jetbrains.anko.support.v4.UI

class GifFragment: BaseFragment() {

    companion object {
        @JvmStatic
        val GET_VIDEO_REQUEST = 101
    }

    override fun updateUI(): View {
        return UI {
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
                    setOnClickListener {  }
                }.lparams {
                    bottomToBottom = layout.bottom
                    leftToLeft = layout.left
                    rightToRight = layout.right
                    bottomMargin = dip(16)
                }

                button(text = R.string.button_choose_video_file) {
                    setOnClickListener { getVideoFromLibrary() }
                }.lparams {
                    bottomToTop = R.id.button_save_gif_file
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

    private fun displayGif(uri:Uri?) {
        if (uri == null) { return }

    }

    private fun convertVideoToGif(intent: Intent?) {
        if (intent == null) { return }
        if (context == null) { return }
        GifEffect.gifFromVideo(context!!, intent!!.data, result = {uri -> displayGif(uri) })
    }
}