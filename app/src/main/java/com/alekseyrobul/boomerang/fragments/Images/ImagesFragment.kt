package com.alekseyrobul.boomerang.fragments.Images

import android.graphics.Color
import android.media.Image
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

                val g= guideline {
                    id = R.id.fragment_images_g
                }.lparams (width = wrapContent, height = wrapContent) {
                    orientation     = ConstraintLayout.LayoutParams.HORIZONTAL
                    guidePercent    = 0.20f
                }

                val lg = guideline {

                }.lparams (width = wrapContent, height = wrapContent) {
                    orientation     = ConstraintLayout.LayoutParams.VERTICAL
                    guidePercent    = 0.20f
                }

                videoCard(context){
                    backgroundColor = Color.RED
                }.lparams(width = g.width, height = dip(100)) {
                    topToBottom     = R.id.fragment_images_g
                    leftToLeft      = layout.left
                    rightToRight    = layout.right
                }

                val linearLayout = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                mRecyclerView = recyclerView {
                    backgroundColor = resources.getColor(R.color.colorPrimaryDark, context.theme)
                    layoutManager = linearLayout
                }.lparams(width = 400, height = 200) {
                    bottomToBottom = layout.bottom
                    leftToLeft = layout.left
                    rightToRight = layout.right
                }
            }
            populateAdapter()
        }.view
    }

    private fun populateAdapter() {
        mImagesList = arrayOf(ImagePick()).toList()
        mIMagesAdapter = ImagesAdapter(context!!,mImagesList)
        mRecyclerView.adapter = mIMagesAdapter
        mIMagesAdapter.notifyDataSetChanged()
    }
}