package com.appringer.common.extensions

import android.widget.ImageView
import com.appringer.wedeliver.R
import com.bumptech.glide.Glide

fun ImageView.setImage(url: String) {
    Glide.with(context)
            .load(url)
            .placeholder(R.mipmap.ic_launcher)
            .into(this)
}

fun ImageView.setImage(url: Int) {
    Glide.with(context)
            .load(url)
            .placeholder(R.mipmap.ic_launcher)
            .into(this)
}