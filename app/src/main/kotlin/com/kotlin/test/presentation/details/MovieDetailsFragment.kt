
package com.kotlin.test.presentation.details

import android.os.Bundle
import android.view.View
import com.kotlin.test.R
import com.kotlin.test.core.extension.loadUrlAndPostponeEnterTransition
import com.kotlin.test.core.platform.BaseFragment
import com.kotlin.test.presentation.movies.MovieView
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class MovieDetailsFragment : BaseFragment() {

    @Inject
    lateinit var movieDetailsAnimator: MovieDetailsAnimator

    override fun layoutId() = R.layout.fragment_movie_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)

        activity?.let {
            movieDetailsAnimator.postponeEnterTransition(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val movieView = it.get(PARAM_MOVIE) as MovieView
            renderMovieDetails(movieView)
        }
    }

    override fun onBackPressed() {
        movieDetailsAnimator.fadeInvisible(scrollView, movieDetails)
    }

    private fun renderMovieDetails(movie: MovieView) {
        with(movie) {
            activity?.let {
                moviePoster.loadUrlAndPostponeEnterTransition(backdropPath, it)
                it.toolbar.title = title
            }
            movieTitle.text = movie.title
            movieSummary.text = movie.overview
        }

        movieDetailsAnimator.fadeVisible(scrollView, movieDetails)
    }

    companion object {
        private const val PARAM_MOVIE = "param_movie"

        fun forMovie(movie: MovieView?): MovieDetailsFragment {
            val movieDetailsFragment = MovieDetailsFragment()
            val arguments = Bundle()
            arguments.putParcelable(PARAM_MOVIE, movie)
            movieDetailsFragment.arguments = arguments

            return movieDetailsFragment
        }
    }
}
