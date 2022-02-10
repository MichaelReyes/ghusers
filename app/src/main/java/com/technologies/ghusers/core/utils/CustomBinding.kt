package com.technologies.ghusers.core.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.technologies.ghusers.R

@BindingAdapter("android:visibility")
fun setVisibility(view: View, visible: Boolean?) {
    view.visibility = if (visible == true) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("imageUrl")
fun setImageFromUrl(imageView: ImageView, source: String?) {
    source?.let {
        if (it.isNotBlank()) {
            Glide.with(imageView.context)
                .load(it)
                .circleCrop()
                .placeholder(R.drawable.img_placeholder)
                .into(imageView)
        }
    }
}