package com.juanfe.project.catsbreedsapplication.core.ex

import android.widget.ImageView
import com.bumptech.glide.Glide


fun ImageView.loadCatBreadImg(imgId: String) {
    Glide.with(this).load("https://cdn2.thecatapi.com/images/$imgId.jpg")
        .thumbnail(0.25f)
        .into(this)
}