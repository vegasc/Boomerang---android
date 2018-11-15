package com.alekseyrobul.boomerang.fragments.boomerang

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.alekseyrobul.boomerang.BuildConfig
import com.alekseyrobul.boomerang.R
import com.alekseyrobul.boomerang.classes.BoomerangEffect
import com.alekseyrobul.boomerang.helpers.BaseFragment
import com.alekseyrobul.boomerang.helpers.DateUtils
import com.alekseyrobul.boomerang.helpers.FileUtilitty
import com.alekseyrobul.boomerang.helpers.PermissionHelper
import com.alekseyrobul.boomerang.views.VideoPlayer
import com.alekseyrobul.boomerang.views.boomButton
import com.alekseyrobul.boomerang.views.progressView
import com.alekseyrobul.boomerang.views.videoSurfaceView
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.dip
import org.jetbrains.anko.support.v4.UI
import java.io.File
import java.net.URI

class BoomerangFragment: BaseFragment() {

    companion object {
        @JvmStatic
        val GET_VIDEO_REQUEST = 101
        @JvmStatic
        var mVideoUri:Uri? = null
    }

    /**
     * Private fields
     */
    private lateinit var mVideoPlayer: VideoPlayer
//    private var mVideoUri:Uri? = null
    private lateinit var mProgressView: View

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

                mProgressView = progressView(context = context) {
                    elevation = 16.0f
                }.lparams {
                    topToTop = layout.top
                    bottomToBottom = layout.bottom
                    leftToLeft = layout.left
                    rightToRight = layout.right
                }
                mProgressView.visibility = View.INVISIBLE
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

    override fun onResume() {
        super.onResume()
        if (mVideoUri != null && context != null) {
            mVideoPlayer.playVideo(context!!, mVideoUri!!)
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
        val videoFile = File(URI.create(mVideoUri.toString()))
        println(Uri.parse(mVideoUri.toString()))
        if (context == null) { return }
        if (mVideoUri == null) { return }
        if (!videoFile.exists()) { return }

        // move cached file to hidden directory
        val fileName = DateUtils.getCurrentDate() + "_" + "boom_movie.mp4"
        val copy = videoFile.copyTo(File(FileUtilitty.externalMediaFolder(context!!).absolutePath + "/" + fileName))

        // prepare meta data
        val values = ContentValues(3)
        values.put(MediaStore.Video.Media.TITLE, "Boomerang")
        values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
        values.put(MediaStore.Video.Media.DATA, copy.absolutePath)

        // insert file uri to files db
        val uri = FileProvider.getUriForFile(context!!, BuildConfig.APPLICATION_ID, copy)
        if (uri == null) {
            Toast.makeText(context!!, context!!.getText(R.string.error_saving_file), LENGTH_SHORT).show()
            return
        }
        val insertUri = context!!.contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)
        if (insertUri == null) {
            Toast.makeText(context!!, context!!.getText(R.string.error_saving_file), LENGTH_SHORT).show()
            return
        }
        context!!.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, insertUri))
        Toast.makeText(context!!, context!!.getText(R.string.boomerang_has_been_saved), LENGTH_SHORT).show()
    }

    private fun displayVideoFrom(intent: Intent?) {
        if (intent == null) { return }
        if (context == null) { return }
        mProgressView.visibility = View.VISIBLE
        BoomerangEffect.getBoomerangFrom(context!!, intent!!.data, { uri ->
            mProgressView.visibility = View.INVISIBLE
            if (uri != null) {
                mVideoUri = uri
                mVideoPlayer.playVideo(context!!, uri)
            }
        })
    }
}
















































