package com.alekseyrobul.boomerang.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.view.ViewManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.alekseyrobul.boomerang.R
import com.alekseyrobul.boomerang.helpers.SizeUtils
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import java.text.DecimalFormat

inline fun ViewManager.videoCard(context: Context): VideoCard = videoCard(context) {}
inline fun ViewManager.videoCard(context: Context, init: (@AnkoViewDslMarker VideoCard).() -> Unit): VideoCard {
    val videoCard = VideoCard(context)
    videoCard.createView(AnkoContext.create(context))
    return ankoView({videoCard}, theme = 0, init = { init() })
}

class VideoCard(context: Context): LinearLayout(context), AnkoComponent<Context> {

    class VideoCardData(var name: String, var size: String, var coverUri: Uri, var imgCount: Int) {}

    private lateinit var mVideoCover:ImageView
    private lateinit var mVideoName:TextView
    private lateinit var mSizeText:TextView

    override fun createView(ui: AnkoContext<Context>): View {
        return linearLayout {
            orientation = LinearLayout.HORIZONTAL
            linearLayout {
                mVideoCover = imageView {
                    backgroundColor     = Color.BLACK
                    scaleType           = ImageView.ScaleType.CENTER
                    minimumWidth        = 250
                    minimumHeight       = 250
                }.lparams(width = 250, height = 250) {
                    topMargin       = dip(20)
                    bottomMargin    = dip(20)
                    leftMargin      = dip(20)
                    rightMargin     = dip(20)
                }
            }.lparams(width = matchParent, height = wrapContent)

            linearLayout {
                orientation = LinearLayout.VERTICAL
                mVideoName = textView {
                    text = context.resources.getText(R.string.video_name)
                    setTextColor(Color.WHITE)
                }
                mSizeText = textView {
                    text = context.resources.getText(R.string.video_file_size)
                    setTextColor(Color.WHITE)
                }
            }.lparams {
                topMargin       = dip(20)
                bottomMargin    = dip(20)
                leftMargin      = dip(20)
                rightMargin     = dip(20)
            }
        }
    }

    fun displayData(data: VideoCardData) {
        mVideoCover.setImageURI(data.coverUri)
        mVideoName.text = data.name
        val size = SizeUtils.getSize(data.size.toLong()) ?: return
        mSizeText.text  = "${context.resources.getText(R.string.video_file_size)}: ${size.size} ${size.type}"
    }
}