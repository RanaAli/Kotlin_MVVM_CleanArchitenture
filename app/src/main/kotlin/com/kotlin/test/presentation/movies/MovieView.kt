
package com.kotlin.test.presentation.movies

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieView(
        val id: Int,
        val poster: String,
        val releaseDate: String,
        val title: String,
        val overview: String,
        val backdropPath: String
): Parcelable
