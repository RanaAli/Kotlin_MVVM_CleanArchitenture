package com.kotlin.test.data.service

import com.kotlin.test.data.entity.DiscoverEntity
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesService @Inject constructor(retrofit: Retrofit) : MoviesApi {

    private val moviesApi by lazy { retrofit.create(MoviesApi::class.java) }

    override fun discover(page: Int, apiKey: String)
            : Call<DiscoverEntity> = moviesApi.discover(page, apiKey)


    override fun discoverByReleaseDate(
            page: Int,
            apiKey: String,
            primaryReleaseYear: String
    ): Call<DiscoverEntity> = moviesApi.discoverByReleaseDate(page, apiKey, primaryReleaseYear)

}
