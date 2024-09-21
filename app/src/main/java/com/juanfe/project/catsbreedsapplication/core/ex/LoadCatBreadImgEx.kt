package com.juanfe.project.catsbreedsapplication.core.ex

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.juanfe.project.catsbreedsapplication.R
import com.juanfe.project.catsbreedsapplication.core.CenterCropBlurTransformation


fun ImageView.loadCatBreadImg(imgId: String) {
    Glide.with(this).load("https://cdn2.thecatapi.com/images/$imgId.jpg")
        .thumbnail(0.25f)
        .placeholder(R.mipmap.ic_launcher_round)
        .apply(RequestOptions().transform(CenterCropBlurTransformation(this.context, 25)))
        .sizeMultiplier(0.5f)
        .into(this)
}