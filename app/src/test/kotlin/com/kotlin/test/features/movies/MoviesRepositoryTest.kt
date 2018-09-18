package com.kotlin.test.features.movies

import com.kotlin.test.UnitTest
import com.kotlin.test.core.exception.Failure
import com.kotlin.test.core.functional.Either
import com.kotlin.test.core.functional.Either.Right
import com.kotlin.test.core.platform.NetworkHandler
import com.kotlin.test.data.entity.DiscoverEntity
import com.kotlin.test.data.service.MoviesService
import com.kotlin.test.domain.model.Discover
import com.kotlin.test.domain.repositories.Network
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Response

class MoviesRepositoryTest : UnitTest() {

    private val apiKey = "4a703e0d0c5d3fea538ba225463200d3"

    private lateinit var networkRepository: Network

    @Mock
    private lateinit var networkHandler: NetworkHandler
    @Mock
    private lateinit var service: MoviesService

    @Mock
    private lateinit var moviesCall: Call<DiscoverEntity>
    @Mock
    private lateinit var moviesResponse: Response<DiscoverEntity>
    @Mock
    private lateinit var movieDetailsCall: Call<DiscoverEntity>
    @Mock
    private lateinit var movieDetailsResponse: Response<DiscoverEntity>

    @Before
    fun setUp() {
        networkRepository = Network(networkHandler, service)
    }

    @Test
    fun `should return empty list by default`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { moviesResponse.body() }.willReturn(null)
        given { moviesResponse.isSuccessful }.willReturn(true)
        given { moviesCall.execute() }.willReturn(moviesResponse)
        given { service.discover(PAGE_NUMBER, apiKey) }.willReturn(moviesCall)

        val movies = networkRepository.discover(PAGE_NUMBER)

        movies shouldEqual Right(Discover())
        verify(service).discover(PAGE_NUMBER, apiKey)
    }

    @Test
    fun `should get movie list from service`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { moviesResponse.body() }.willReturn((DiscoverEntity()))
        given { moviesResponse.isSuccessful }.willReturn(true)
        given { moviesCall.execute() }.willReturn(moviesResponse)
        given { service.discover(PAGE_NUMBER, apiKey) }.willReturn(moviesCall)

        val movies = networkRepository.discover(PAGE_NUMBER)

        movies shouldEqual Right(Discover())
        verify(service).discover(PAGE_NUMBER, apiKey)
    }

    @Test
    fun `movies service should return network failure when no connection`() {
        given { networkHandler.isConnected }.willReturn(false)

        val movies = networkRepository.discover(PAGE_NUMBER)

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.either({ failure
            ->
            failure shouldBeInstanceOf Failure.NetworkConnection::class.java
        }, {})
        verifyZeroInteractions(service)
    }

    @Test
    fun `movies service should return network failure when undefined connection`() {
        given { networkHandler.isConnected }.willReturn(null)

        val movies = networkRepository.discover(PAGE_NUMBER)

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.either({ failure
            ->
            failure shouldBeInstanceOf Failure.NetworkConnection::class.java
        }, {})
        verifyZeroInteractions(service)
    }

    @Test
    fun `movies service should return server error if no successful response`() {
        given { networkHandler.isConnected }.willReturn(true)

        val movies = networkRepository.discover(PAGE_NUMBER)

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.either({ failure
            ->
            failure shouldBeInstanceOf Failure.ServerError::class.java
        }, {})
    }

    @Test
    fun `movies request should catch exceptions`() {
        given { networkHandler.isConnected }.willReturn(true)

        val movies = networkRepository.discover(PAGE_NUMBER)

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.either({ failure -> failure shouldBeInstanceOf Failure.ServerError::class.java }, {})
    }

    @Test
    fun `should return empty movie details by default`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { movieDetailsResponse.body() }.willReturn(null)
        given { movieDetailsResponse.isSuccessful }.willReturn(true)
        given { movieDetailsCall.execute() }.willReturn(movieDetailsResponse)
        given {
            service.discoverByReleaseDate(PAGE_NUMBER, apiKey, RELEASE_DATE)
        }.willReturn(movieDetailsCall)

        val movieDetails = networkRepository
                .discoverByReleaseDate(PAGE_NUMBER, RELEASE_DATE)

        movieDetails shouldEqual Right(Discover())
        verify(service).discoverByReleaseDate(PAGE_NUMBER, apiKey, RELEASE_DATE)
    }

    @Test
    fun `should get movie details from service`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { movieDetailsResponse.body() }.willReturn(DiscoverEntity())
        given { movieDetailsResponse.isSuccessful }.willReturn(true)
        given { movieDetailsCall.execute() }.willReturn(movieDetailsResponse)
        given {
            service.discoverByReleaseDate(PAGE_NUMBER, apiKey, RELEASE_DATE)
        }.willReturn(movieDetailsCall)

        val movieDetails = networkRepository
                .discoverByReleaseDate(PAGE_NUMBER, RELEASE_DATE)

        movieDetails shouldEqual Right(Discover())
        verify(service).discoverByReleaseDate(PAGE_NUMBER, apiKey, RELEASE_DATE)
    }

    @Test
    fun `movie details service should return network failure when no connection`() {
        given { networkHandler.isConnected }.willReturn(false)

        val movieDetails = networkRepository
                .discoverByReleaseDate(PAGE_NUMBER, RELEASE_DATE)

        movieDetails shouldBeInstanceOf Either::class.java
        movieDetails.isLeft shouldEqual true
        movieDetails.either({ failure
            ->
            failure shouldBeInstanceOf Failure.NetworkConnection::class.java
        }, {})
        verifyZeroInteractions(service)
    }

    @Test
    fun `movie details service should return network failure when undefined connection`() {
        given { networkHandler.isConnected }.willReturn(null)

        val movieDetails = networkRepository
                .discoverByReleaseDate(PAGE_NUMBER, RELEASE_DATE)

        movieDetails shouldBeInstanceOf Either::class.java
        movieDetails.isLeft shouldEqual true
        movieDetails.either({ failure -> failure shouldBeInstanceOf Failure.NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test
    fun `movie details service should return server error if no successful response`() {
        given { networkHandler.isConnected }.willReturn(true)

        val movieDetails = networkRepository
                .discoverByReleaseDate(PAGE_NUMBER, RELEASE_DATE)

        movieDetails shouldBeInstanceOf Either::class.java
        movieDetails.isLeft shouldEqual true
        movieDetails.either({ failure -> failure shouldBeInstanceOf Failure.ServerError::class.java }, {})
    }

    @Test
    fun `movie details request should catch exceptions`() {
        given { networkHandler.isConnected }.willReturn(true)

        val movieDetails = networkRepository
                .discoverByReleaseDate(PAGE_NUMBER, RELEASE_DATE)

        movieDetails shouldBeInstanceOf Either::class.java
        movieDetails.isLeft shouldEqual true
        movieDetails.either({ failure -> failure shouldBeInstanceOf Failure.ServerError::class.java }, {})
    }

    companion object {
        private const val PAGE_NUMBER = 1
        private const val RELEASE_DATE = "2018-01-01"
    }
}