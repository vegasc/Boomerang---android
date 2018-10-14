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
import com.kotlin.alekseyrobul.boomerang.views.VideoPlayer
import com.kotlin.alekseyrobul.boomerang.views.boomButton
import com.kotlin.alekseyrobul.boomerang.views.videoSurfaceView
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.button
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.dip
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
    private lateinit var mVideoPlayer: VideoPlayer
    private var mVideoUri:Uri? = null

    /**
     * Override funcs
     */
    override fun updateUI(): View {
        return UI {
            val context = context
            constraintLayout {
                val layout = constraintLayout()
                layout.backgroundColor = resources.getColor(R.color.colorPrimaryDark, context!!.theme)
                mVideoPlayer = videoSurfaceView(context = context!!) {
                    isLoop = true
                }.lparams(height = 800) {
                    topToTop = layout.top
                    leftToLeft = layout.left
                    rightToRight = layout.right
                }

                boomButton(context) {
                    id = R.id.button_save_video_file
                    text = resources.getString(R.string.button_save_video_file)
                    setOnClickListener { saveVideo() }
                }.lparams {
                    bottomToBottom = layout.bottom
                    leftToLeft = layout.left
                    rightToRight = layout.right
                    bottomMargin = dip(16)
                    rightMargin = dip(200)
                }

                boomButton(context) {
                    text = resources.getString(R.string.button_choose_video_file)
                    setOnClickListener { getVideoFromLibrary() }
                }.lparams {
                    bottomToBottom = layout.bottom
                    leftToLeft = layout.left
                    rightToRight = layout.right
                    bottomMargin = dip(16)
                    leftMargin = dip(200)
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
                mVideoPlayer.playVideo(context!!, uri)
            }
        })
    }
}
















































