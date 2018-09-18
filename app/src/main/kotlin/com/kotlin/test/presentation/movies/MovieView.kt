
package com.kotlin.test.presentation.movies

import android.os.Parcel
import com.kotlin.test.core.platform.KParcelable
import com.kotlin.test.core.platform.parcelableCreator

data class MovieView(
        val id: Int,
        val poster: String,
        val releaseDate: String,
        val title: String,
        val overview: String,
        val backdropPath: String
) : KParcelable {

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::MovieView)
    }

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(id)
            writeString(poster)
            writeString(releaseDate)
            writeString(title)
            writeString(overview)
            writeString(backdropPath)
        }
    }
}
