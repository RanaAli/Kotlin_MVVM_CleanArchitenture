package com.kotlin.test.features.movies

import com.kotlin.test.AndroidTest
import com.kotlin.test.core.functional.Either.Right
import com.kotlin.test.domain.model.Discover
import com.kotlin.test.domain.model.Result
import com.kotlin.test.domain.usecase.GetMovies
import com.kotlin.test.domain.usecase.GetMoviesByDate
import com.kotlin.test.presentation.viewmodel.MoviesViewModel
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.given
import kotlinx.coroutines.experimental.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class MoviesViewModelTest : AndroidTest() {

    private lateinit var moviesViewModel: MoviesViewModel

    @Mock
    private lateinit var getMovies: GetMovies

    @Mock
    private lateinit var getMoviesByDate: GetMoviesByDate

    @Before
    fun setUp() {
        moviesViewModel = MoviesViewModel(getMovies, getMoviesByDate)
    }

    @Test
    fun `loading movies should update live data`() {
        val discover = Discover();

        val moviesList = listOf(
                Result(id = 0, originalTitle = "IronMan"),
                Result(id = 1, originalTitle = "Batman"))

        discover.results = moviesList;
        given { runBlocking { getMovies.run(eq(any())) } }.willReturn(Right(discover))

        moviesViewModel.movies.observeForever {
            it!!.size shouldEqualTo 2
            it[0].id shouldEqualTo 0
            it[0].poster shouldEqualTo "IronMan"
            it[1].id shouldEqualTo 1
            it[1].poster shouldEqualTo "Batman"
        }

        runBlocking { moviesViewModel.loadMovies(PAGE_NUMBER) }
    }

    companion object {
        private const val PAGE_NUMBER = 1
    }
}