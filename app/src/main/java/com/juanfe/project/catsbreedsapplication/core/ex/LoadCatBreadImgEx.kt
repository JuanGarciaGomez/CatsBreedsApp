package com.juanfe.project.catsbreedsapplication.core.ex

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.juanfe.project.catsbreedsapplication.R
import com.juanfe.project.catsbreedsapplication.core.CenterCropBlurTransformation

fun ImageView.loadCatBreadImg(imgId: String) {
    val url = "https://cdn2.thecatapi.com/images/$imgId.jpg"
    pretty(url)
        .error(
            pretty(url.replace(".jpg", ".png"))
        )
        .into(this)
}

fun View.pretty(url: String): RequestBuilder<Drawable> {
    return Glide
        .with(this)
        .load(url)
        .thumbnail(0.25f)
        .placeholder(R.mipmap.ic_launcher_round)
        .apply(RequestOptions().transform(CenterCropBlurTransformation(this.context, 25)))
        .sizeMultiplier(0.5f)
}