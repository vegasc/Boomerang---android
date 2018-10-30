package com.alekseyrobul.boomerang.fragments.Images.recycler_view

import android.content.Context
import android.view.View
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.constraint.layout.constraintLayout

class ImagePickItem(context: Context): View(context), AnkoComponent<View> {
    override fun createView(ui: AnkoContext<View>): View = with(ui) {
        constraintLayout {  }
    }

}