package com.kotlin.test.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kotlin.test.core.platform.BaseViewModel
import com.kotlin.test.domain.model.Discover
import com.kotlin.test.domain.usecase.GetMovies
import com.kotlin.test.domain.usecase.GetMoviesByDate
import com.kotlin.test.presentation.movies.MovieView
import javax.inject.Inject

class MoviesViewModel
@Inject constructor(
        private val getMovies: GetMovies,
        private val getMoviesByDate: GetMoviesByDate
) : BaseViewModel() {

    var movies: MutableLiveData<List<MovieView>> = MutableLiveData()

    fun loadMovies(page: Int) = getMovies(GetMovies.Params(page)) {
        it.either(::handleFailure, ::handleMovieList)
    }

    fun loadMoviesByDate(page: Int, date: String) = getMoviesByDate(GetMoviesByDate.Params(page, date)) {
        it.either(::handleFailure, ::handleMovieList)
    }

    private fun handleMovieList(discover: Discover) {
        val results = discover.results
        this.movies.value = results.map {
            MovieView(
                    it.id,
                    it.posterPath,
                    it.releaseDate,
                    it.title,
                    it.overview,
                    it.backdropPath
            )
        }
    }
}