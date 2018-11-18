package com.alekseyrobul.boomerang.fragments.Images.recycler_view

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.provider.MediaStore
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.view

class ImagesAdapter(private val context: Context, private val list:List<ImagePick>): RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {

    class ImagesViewHolder(view: ImagePickItem): RecyclerView.ViewHolder(view) {
        var imagePickItem: ImagePickItem = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val view = ImagePickItem(context).createView(AnkoContext.create(context))
        return ImagesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        // bind data
        val imgPick = list[position]
        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imgPick.uri)
        val drawable = BitmapDrawable(context.resources, bitmap)
        holder.imagePickItem.imageView.background = drawable
    }
}