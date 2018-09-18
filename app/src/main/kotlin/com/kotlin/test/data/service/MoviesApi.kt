package com.kotlin.test.data.service

import com.kotlin.test.data.entity.DiscoverEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MoviesApi {

    @GET("discover/movie/")
    fun discover(@Query("page") page: Int,
                 @Query("api_key") apiKey: String)
            : Call<DiscoverEntity>

    @GET("discover/movie/")
    fun discoverByReleaseDate(@Query("page") page: Int,
                              @Query("api_key") apiKey: String,
                              @Query("release_date.lte") primaryReleaseYear: String)
            : Call<DiscoverEntity>
}
