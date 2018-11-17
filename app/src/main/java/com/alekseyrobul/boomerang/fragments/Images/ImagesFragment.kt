package com.alekseyrobul.boomerang.fragments.Images

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.media.Image
import android.support.constraint.ConstraintLayout
import android.support.constraint.Guideline
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import com.alekseyrobul.boomerang.R
import com.alekseyrobul.boomerang.fragments.Images.recycler_view.ImagePick
import com.alekseyrobul.boomerang.fragments.Images.recycler_view.ImagesAdapter
import com.alekseyrobul.boomerang.helpers.BaseFragment
import com.alekseyrobul.boomerang.views.boomButton
import com.alekseyrobul.boomerang.views.videoCard
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.guideline
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI

class ImagesFragment: BaseFragment() {

    private lateinit var mRecyclerView:RecyclerView
    private lateinit var mVisibleImageView:ImageView
    private lateinit var mIMagesAdapter: ImagesAdapter
    private lateinit var mImagesList:List<ImagePick>

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

                videoCard(context){
                    val gradient = GradientDrawable()
                    gradient.setColor(resources.getColor(R.color.colorPrimaryDark2, context.theme))
                    gradient.cornerRadius = 20.0f
                    this.background = gradient
                    setOnTouchListener { v, event ->
                        pickVideo()
                        true
                    }
                }.lparams(width = dip(0)) {
                    topToTop          = layout.top
                    bottomToBottom    = layout.bottom
                    startToStart      = R.id.fragment_images_g
                    endToStart        = R.id.fragment_images_lg
                }

                mRecyclerView = recyclerView {
                    backgroundColor = resources.getColor(R.color.colorPrimaryDark, context.theme)
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }.lparams(width = dip(0), height = 200) {
                    bottomToBottom = layout.bottom
                    startToStart      = R.id.fragment_images_g
                    endToStart        = R.id.fragment_images_lg
                }
            }
        }.view
    }

    private fun pickVideo() {

    }

    private fun populateAdapter() {
        mImagesList = arrayOf(ImagePick()).toList()
        mIMagesAdapter = ImagesAdapter(context!!,mImagesList)
        mRecyclerView.adapter = mIMagesAdapter
        mIMagesAdapter.notifyDataSetChanged()
    }
}