package com.alekseyrobul.boomerang.fragments.Images.recycler_view

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import com.alekseyrobul.boomerang.R
import org.jetbrains.anko.*

class ImagePickItem(context: Context): LinearLayout(context), AnkoComponent<Context> {
    lateinit var imageView: ImageView
    override fun createView(ui: AnkoContext<Context>): ImagePickItem = with(ui) {
        applyUI()
    }

    operator fun invoke(function: () -> LinearLayout): ImagePickItem {
        function()
        return this
    }
}

inline fun ImagePickItem.applyUI(): ImagePickItem {
    return this {
        val min = 400
        linearLayout {
            minimumHeight = min
            minimumWidth = min
            imageView = imageView {
                minimumHeight = min
                minimumWidth = min
                id = R.id.image_pick_item_image_view
                backgroundColor = resources.getColor(R.color.colorPrimaryDark2, context.theme)
            }
        }
    }
}