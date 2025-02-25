package com.homanad.android.githubusers.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil3.load


object BindAdapter {

    @JvmStatic
    @BindingAdapter("app:imageUrl")
    fun bindImage(view: ImageView, url: String?) {
        view.load(url)
    }
}
