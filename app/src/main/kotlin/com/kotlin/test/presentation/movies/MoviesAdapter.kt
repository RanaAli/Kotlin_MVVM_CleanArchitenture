package com.kotlin.test.presentation.movies

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.test.R
import com.kotlin.test.core.extension.inflate
import com.kotlin.test.core.navigation.Navigator
import javax.inject.Inject

class MoviesAdapter
@Inject constructor() : RecyclerView.Adapter<MovieListItem>() {

    internal var collection: MutableList<MovieView> = arrayListOf()

    internal var clickListener:
                (MovieView, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieListItem(parent.inflate(R.layout.list_item_view))

    override fun onBindViewHolder(viewHolder: MovieListItem, position: Int) =
        viewHolder.populate(collection[position], clickListener)

    override fun getItemCount() = collection.size

    fun addItems(movies: List<MovieView>) {
        collection.addAll(movies)
        notifyDataSetChanged()
    }

}
