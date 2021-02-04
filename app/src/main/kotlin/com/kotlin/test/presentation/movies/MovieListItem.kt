package com.kotlin.test.presentation.movies

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.test.core.navigation.Navigator
import com.kotlin.test.presentation.ImageUtils
import com.kotlin.test.presentation.ImageUtils.POSTER_SIZE_W_185
import kotlinx.android.synthetic.main.list_item_view.view.*

class MovieListItem(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun populate(result: MovieView, clickListener: (MovieView, Navigator.Extras) -> Unit) {

        itemView.listItemViewTitleTextView.text = result.title
        itemView.listItemViewDateTextView.text = result.releaseDate
        ImageUtils.getImage(result.poster, POSTER_SIZE_W_185, itemView.listItemViewImageView)

        itemView.setOnClickListener {
            clickListener(result, Navigator.Extras(itemView.listItemViewImageView))
        }
    }


}
