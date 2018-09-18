
package com.kotlin.test.presentation.movies

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.kotlin.test.R
import com.kotlin.test.core.extension.inflate
import com.kotlin.test.core.navigation.Navigator
import javax.inject.Inject
import kotlin.properties.Delegates.observable

class MoviesAdapter
@Inject constructor() : RecyclerView.Adapter<MovieListItem>() {

    internal var collection: MutableList<MovieView>
            by observable<MutableList<MovieView>>(arrayListOf())
            { _, _, _ ->
                notifyDataSetChanged()
            }

    internal var clickListener:
            (MovieView, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            MovieListItem(parent.inflate(R.layout.list_item_view))

    override fun onBindViewHolder(viewHolder: MovieListItem, position: Int) =
            viewHolder.populate(collection[position], clickListener)

    override fun getItemCount() = collection.size

}
