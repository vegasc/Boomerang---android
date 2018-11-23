package com.alekseyrobul.boomerang.fragments.Images

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.provider.MediaStore
import android.support.constraint.ConstraintLayout
import android.support.v4.content.FileProvider
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.alekseyrobul.boomerang.BuildConfig
import com.alekseyrobul.boomerang.R
import com.alekseyrobul.boomerang.classes.GetImages
import com.alekseyrobul.boomerang.fragments.Images.recycler_view.ImagePick
import com.alekseyrobul.boomerang.fragments.Images.recycler_view.ImagesAdapter
import com.alekseyrobul.boomerang.fragments.Images.recycler_view.ImagesAdapterListener
import com.alekseyrobul.boomerang.fragments.boomerang.BoomerangFragment
import com.alekseyrobul.boomerang.helpers.BaseFragment
import com.alekseyrobul.boomerang.helpers.DateUtils
import com.alekseyrobul.boomerang.helpers.FileUtilitty
import com.alekseyrobul.boomerang.helpers.PermissionHelper
import com.alekseyrobul.boomerang.views.VideoCard
import com.alekseyrobul.boomerang.views.boomButton
import com.alekseyrobul.boomerang.views.progressView
import com.alekseyrobul.boomerang.views.videoCard
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.guideline
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import java.io.File
import java.net.URI

class ImagesFragment: BaseFragment() {

    companion object {
        @JvmStatic
        val GET_VIDEO_REQUEST = 1000;
    }

    private lateinit var mVideoCard:VideoCard
    private lateinit var mRecyclerView:RecyclerView
    private lateinit var mIMagesAdapter: ImagesAdapter
    private lateinit var mProgressView: View

    override fun updateUI(): View {
        return UI {
            constraintLayout {
                val layout = constraintLayout()
                layout.backgroundColor = resources.getColor(R.color.colorPrimaryDark, context.theme)

                guideline {
                    id = R.id.fragment_images_g
                }.lparams (width = wrapContent, height = wrapContent) {
                    orientation     = ConstraintLayout.LayoutParams.VERTICAL
                    guideBegin      = dip(20)
                }

                guideline {
                    id = R.id.fragment_images_lg
                }.lparams (width = wrapContent, height = wrapContent) {
                    orientation     = ConstraintLayout.LayoutParams.VERTICAL
                    guideEnd        = dip(20)
                }

                mVideoCard = videoCard(context){
                    id = R.id.fragment_images_video_card
                    val gradient = GradientDrawable()
                    gradient.setColor(resources.getColor(R.color.colorPrimaryDark2, context.theme))
                    gradient.cornerRadius = 20.0f
                    this.background = gradient
                    setOnTouchListener { v, event ->
                        pickVideo()
                        false
                    }
                }.lparams(width = dip(0)) {
                    topToTop          = layout.top
                    bottomToBottom    = layout.bottom
                    startToStart      = R.id.fragment_images_g
                    endToStart        = R.id.fragment_images_lg
                }

                boomButton(context = context) {
                    text = context.resources.getText(R.string.button_choose_video_file)
                    setOnClickListener { pickVideo() }
                }.lparams {
                    topToBottom     = R.id.fragment_images_video_card
                    leftToLeft      = R.id.fragment_images_video_card
                    rightToRight    = R.id.fragment_images_video_card
                    topMargin       = dip(10)
                }

                mRecyclerView = recyclerView {
                    backgroundColor = resources.getColor(R.color.colorPrimaryDark, context.theme)
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                }.lparams(width = dip(0), height = 400) {
                    bottomToBottom  = layout.bottom
                    startToStart    = R.id.fragment_images_g
                    endToStart      = R.id.fragment_images_lg
                    bottomMargin    = dip(20)
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
            if (requestCode == ImagesFragment.GET_VIDEO_REQUEST) {
                if (data == null) { return }
                parseVideo(data)
            }
        }
    }

    private fun pickVideo() {
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
        startActivityForResult(intent, ImagesFragment.GET_VIDEO_REQUEST)
    }

    private fun parseVideo(intent: Intent) {
        if (context == null) { return }
        mProgressView.visibility = View.VISIBLE
        GetImages(context!!, intent.data).getImages { uris, data ->
            mVideoCard.displayData(data)
            populateAdapter(uris)
            mProgressView.visibility = View.INVISIBLE
        }
    }

    private fun populateAdapter(uris:ArrayList<Uri>) {
        var imgPics = ArrayList<ImagePick>()
        for (uri in uris) {
            imgPics.add(ImagePick(uri))
        }
        mIMagesAdapter = ImagesAdapter(context!!, imgPics)
        mRecyclerView.adapter = mIMagesAdapter
        mIMagesAdapter.notifyDataSetChanged()
        val self = this
        mIMagesAdapter.listener = object : ImagesAdapterListener {
            override fun saveImage(uri: Uri) {
                self.saveImage(uri)
            }
        }
    }

    private fun saveImage(uri: Uri) {
        val imageFile = File(URI.create(uri.toString()))
        if (!imageFile.exists()) { return }
        
        // move cached file to hidden directory
        val fileName = DateUtils.getCurrentDate() + "_" + "boom_movie_screen.jpg"
        val copy = imageFile.copyTo(File(FileUtilitty.externalMediaFolder(context!!).absolutePath + "/" + fileName))

        // prepare meta data
        val values = ContentValues(3)
        values.put(MediaStore.Images.Media.TITLE, "Video screen ${fileName}")
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        values.put(MediaStore.Images.Media.DATA, copy.absolutePath)

        // insert file uri to files db
        val uri = FileProvider.getUriForFile(context!!, BuildConfig.APPLICATION_ID, copy)
        if (uri == null) {
            Toast.makeText(context!!, context!!.getText(R.string.error_saving_file), Toast.LENGTH_SHORT).show()
            return
        }
        val insertUri = context!!.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        if (insertUri == null) {
            Toast.makeText(context!!, context!!.getText(R.string.error_saving_file), Toast.LENGTH_SHORT).show()
            return
        }
        context!!.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, insertUri))
        Toast.makeText(context!!, context!!.getText(R.string.image_has_been_saved), Toast.LENGTH_SHORT).show()
    }
}