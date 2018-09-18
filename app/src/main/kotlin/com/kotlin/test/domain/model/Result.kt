package com.kotlin.test.domain.model

import com.kotlin.test.core.extension.empty

class Result(var posterPath: String = String.empty(),
             var adult: Boolean = false,
             var overview: String = String.empty(),
             var releaseDate: String = String.empty(),
             var genreIds: List<Int> = ArrayList(),
             var id: Int = -1,
             var originalTitle: String = String.empty(),
             var originalLanguage: String = String.empty(),
             var title: String = String.empty(),
             var backdropPath: String = String.empty(),
             var popularity: Double = 0.0,
             var voteCount: Int = 0,
             var video: Boolean = false,
             var voteAverage: Double = 0.0)