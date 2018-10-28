package com.kotlin.alekseyrobul.boomerang.fragments.gif

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.webkit.WebView
import com.kotlin.alekseyrobul.boomerang.R
import com.kotlin.alekseyrobul.boomerang.classes.GifEffect
import com.kotlin.alekseyrobul.boomerang.fragments.boomerang.BoomerangFragment
import com.kotlin.alekseyrobul.boomerang.helpers.BaseFragment
import com.kotlin.alekseyrobul.boomerang.helpers.PermissionHelper
import com.kotlin.alekseyrobul.boomerang.views.ProgressView
import com.kotlin.alekseyrobul.boomerang.views.boomButton
import com.kotlin.alekseyrobul.boomerang.views.progressView
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.support.v4.UI

class GifFragment: BaseFragment() {

    companion object {
        @JvmStatic
        val GET_VIDEO_REQUEST = 101
    }

    private lateinit var mWebView: WebView
    private lateinit var mProgressView: View

    override fun updateUI(): View {
        return UI {
            constraintLayout {
                val layout = constraintLayout()
                layout.backgroundColor = resources.getColor(R.color.colorPrimaryDark, context.theme)

                mWebView = webView {
                    id = R.id.gif_fragment_image_view
                    setBackgroundColor(0x00000000)
                    setBackgroundResource(R.drawable.web_view_rounded)
                    elevation = 1.0f
                }.lparams (width = 800, height = 800) {
                    topToTop = layout.top
                    leftToLeft = layout.left
                    rightToRight = layout.right
                    topMargin = dip(50)
                }

                boomButton(context = context) {
                    id = R.id.button_save_gif_file
                    text = resources.getString(R.string.button_save_gif_file)
                    setOnClickListener {  }
                    elevation = 1.0f
                }.lparams {
                    bottomToBottom = layout.bottom
                    leftToLeft = layout.left
                    rightToRight = layout.right
                    bottomMargin = dip(16)
                    rightMargin = dip(200)
                }

                boomButton(context = context) {
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
            if (requestCode == BoomerangFragment.GET_VIDEO_REQUEST) {
                convertVideoToGif(data)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        mWebView.clearCache(true)
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
        if (context == null) { return }
        if (uri == null) { return }

        val display = (context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val width = display.width
        var v = width.toDouble() / mWebView.width.toDouble()
        v *= 100

        mWebView.loadUrl(uri.toString())
        mWebView.setPadding(0,0,0,0)
        mWebView.setInitialScale(v.toInt())
    }

    private fun convertVideoToGif(intent: Intent?) {
        if (intent == null) { return }
        if (context == null) { return }

        mProgressView.visibility = View.VISIBLE
        GifEffect.gifFromVideo(context!!, intent!!.data, result = {uri ->
            mProgressView.visibility = View.INVISIBLE
            displayGif(uri)
        })
    }
}