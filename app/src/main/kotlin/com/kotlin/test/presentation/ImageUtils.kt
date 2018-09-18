package com.kotlin.test.presentation

import android.widget.ImageView

import com.bumptech.glide.Glide
import com.kotlin.test.R

object ImageUtils {

    fun getImage(imageUrl: String, size: String?, mLeavingAirlineImageView: ImageView) {
        val url: String = if (size != null && !size.equals("", ignoreCase = true)) {
            mLeavingAirlineImageView.context.getString(R.string.imageURL) + size + imageUrl
        } else {
            mLeavingAirlineImageView.context.getString(R.string.imageURL) + POSTER_SIZE_W_800 + imageUrl
        }

        Glide.with(mLeavingAirlineImageView.context)
                .load(url)
                .into(mLeavingAirlineImageView)
    }


    const val POSTER_SIZE_W_185 = "w185"
    const val POSTER_SIZE_W_800 = "w500"

}
