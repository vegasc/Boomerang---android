package com.alekseyrobul.boomerang.fragments.Images.recycler_view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class ImagesAdapter(private val context: Context, private val list:List<ImagePick>): RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {

    class ImagesViewHolder(view: View): RecyclerView.ViewHolder(view) {
        public lateinit var imageView: ImageView

        public fun ImagesViewHolder() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        return ImagesViewHolder(ImagePickItem(context = context))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {

    }
}