package com.kotlin.alekseyrobul.boomerang.fragments.boomerang

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.kotlin.alekseyrobul.boomerang.R
import com.kotlin.alekseyrobul.boomerang.classes.BoomerangEffect
import com.kotlin.alekseyrobul.boomerang.helpers.BaseFragment
import com.kotlin.alekseyrobul.boomerang.helpers.PermissionHelper
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.support.v4.UI
import java.io.File
import java.net.URI

class BoomerangFragment: BaseFragment() {

    companion object {
        @JvmStatic
        val GET_VIDEO_REQUEST = 101
    }

    /**
     * Private fields
     */
    private lateinit var mView: BoomerangFragmentView

    private var mViewListener = object : BoomerangFragmentViewListener {
        override fun buttonTappedChooseVideoFile() { getVideoFromLibrary() }

        override fun buttonTappedSaveVideo() { saveVideo() }
    }

    private var mVideoUri:Uri? = null

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

    private fun saveVideo() {
        if (context == null) { return }

        // get video from hidden folder and save to media storage
        if (mVideoUri != null) {
            val videoFile = File(URI.create(mVideoUri.toString()))
            if (videoFile.exists()) {
                val videoDirectory = File(context!!.getExternalFilesDir(Environment.DIRECTORY_MOVIES).absolutePath + "/boomerang.mp4")
                videoFile.copyTo(videoDirectory, true, DEFAULT_BUFFER_SIZE)
                context!!.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(videoFile)))
                Toast.makeText(context!!, context!!.getText(R.string.boomerang_has_been_saved), LENGTH_SHORT).show()
            }
        }
    }

    private fun displayVideoFrom(intent: Intent?) {
        if (intent == null) { return }
        if (context == null) { return }
        BoomerangEffect.getBoomerangFrom(context!!, intent!!.data, { uri ->
            if (uri != null) {
                mVideoUri = uri
                mView.displayVideo(uri)
            }
        })
    }
}
















































