package com.technologies.ghusers.core.utils

import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.technologies.ghusers.R

@BindingAdapter("android:visibility")
fun setVisibility(view: View, visible: Boolean?) {
    view.visibility = if (visible == true) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("imageUrl", "invertImageColor")
fun setImageFromUrl(imageView: ImageView, source: String?, invertImageColor: Boolean = false) {
    source?.let {
        val colorFilterNegative = floatArrayOf(
            -1.0f, 0f, 0f, 0f, 255f,
            0f, -1.0f, 0f, 0f, 255f,
            0f, 0f, -1.0f, 0f, 255f,
            0f, 0f, 0f, 1.0f, 0f
        )

        if (it.isNotBlank()) {
            Glide.with(imageView.context)
                .load(it).run {
                    if (!invertImageColor) {
                        this.circleCrop()
                            .placeholder(R.drawable.img_placeholder)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .into(imageView)
                    } else {
                        this.circleCrop()
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .apply(
                                RequestOptions().placeholder(
                                    ContextCompat.getDrawable(
                                        imageView.context,
                                        R.drawable.img_placeholder
                                    )
                                )
                            )
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .into(object : CustomTarget<Drawable>() {
                                override fun onLoadCleared(placeholder: Drawable?) {}

                                override fun onResourceReady(
                                    resource: Drawable,
                                    transition: Transition<in Drawable>?
                                ) {
                                    resource.colorFilter =
                                        ColorMatrixColorFilter(colorFilterNegative)
                                    imageView.setImageDrawable(resource)
                                }
                            })
                    }
                }
        }
    }
}