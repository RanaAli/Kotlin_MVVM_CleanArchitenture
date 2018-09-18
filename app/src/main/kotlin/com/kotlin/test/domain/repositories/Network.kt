package com.kotlin.test.domain.repositories

import com.kotlin.test.core.exception.Failure
import com.kotlin.test.core.functional.Either
import com.kotlin.test.core.platform.NetworkHandler
import com.kotlin.test.data.EntityDataMapper
import com.kotlin.test.data.entity.DiscoverEntity
import com.kotlin.test.data.service.MoviesService
import com.kotlin.test.domain.model.Discover
import retrofit2.Call
import javax.inject.Inject

class Network
@Inject
constructor(private val networkHandler: NetworkHandler,
            private val service: MoviesService)
    : MoviesRepository {

    private val apiKey = "4a703e0d0c5d3fea538ba225463200d3"

    override fun discover(page: Int): Either<Failure, Discover> {
        return when (networkHandler.isConnected) {

            true -> request(
                    service.discover(page, apiKey),
                    { EntityDataMapper.map(it) },
                    DiscoverEntity()
            )

            false, null -> {
                Either.Left(Failure.NetworkConnection())
            }
        }
    }

    override fun discoverByReleaseDate(
            page: Int,
            primaryReleaseYear: String
    ): Either<Failure, Discover> {
        return when (networkHandler.isConnected) {

            true -> request(
                    service.discoverByReleaseDate(page, apiKey, primaryReleaseYear),
                    { EntityDataMapper.map(it) },
                    DiscoverEntity()
            )

            false, null -> {
                Either.Left(Failure.NetworkConnection())
            }
        }
    }

    private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body() ?: default)))
                false -> Either.Left(Failure.ServerError())
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerError())
        }
    }
}