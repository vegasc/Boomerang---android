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
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.dip
import org.jetbrains.anko.imageView
import org.jetbrains.anko.matchParent
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

                mVisibleImageView = imageView {
                    id = R.id.fragment_image_visible_image_view
                    backgroundColor = R.color.colorGray
                }.lparams(width = 800, height = 800) {
                    topToTop = layout.top
                    leftToLeft = layout.left
                    rightToRight = layout.right
                    topMargin = dip(50)
                }

                boomButton(context) {
                    text = context.getText(R.string.button_save_image)
                }.lparams {
                    topToBottom = R.id.fragment_image_visible_image_view
                    leftToLeft = layout.left
                    rightToRight = layout.right
                    topMargin = dip(12)
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