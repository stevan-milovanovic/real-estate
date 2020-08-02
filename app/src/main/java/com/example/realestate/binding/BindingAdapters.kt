package com.example.realestate.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.realestate.R

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("propertyImageUrl")
    fun bindImage(imageView: ImageView, url: String?) {
        Glide.with(imageView)
            .load(url)
            .placeholder(R.drawable.ic_property_placeholder_foreground)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("showIf")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

}