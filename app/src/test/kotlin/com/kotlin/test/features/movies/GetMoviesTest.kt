package com.kotlin.test.features.movies

import com.kotlin.test.UnitTest
import com.kotlin.test.core.functional.Either.Right
import com.kotlin.test.domain.model.Discover
import com.kotlin.test.domain.repositories.MoviesRepository
import com.kotlin.test.domain.usecase.GetMovies
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetMoviesTest : UnitTest() {

    private lateinit var getMovies: GetMovies

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setUp() {
        getMovies = GetMovies(moviesRepository)
        given {
            moviesRepository.discover(PAGE_NUMBER)
        }.willReturn(
                Right(Discover()))
    }

    @Test
    fun `should get data from repository`() {
        runBlocking { getMovies.run(GetMovies.Params(PAGE_NUMBER)) }

        verify(moviesRepository).discover(PAGE_NUMBER)
        verifyNoMoreInteractions(moviesRepository)
    }

    companion object {
        private const val PAGE_NUMBER = 1
    }
}
