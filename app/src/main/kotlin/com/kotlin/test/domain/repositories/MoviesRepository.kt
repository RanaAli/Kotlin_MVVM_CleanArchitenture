package com.kotlin.test.domain.repositories

import com.kotlin.test.core.exception.Failure
import com.kotlin.test.core.functional.Either
import com.kotlin.test.domain.model.Discover

interface MoviesRepository {
    fun discover(page: Int): Either<Failure, Discover>
    fun discoverByReleaseDate(page: Int, primaryReleaseYear: String): Either<Failure, Discover>
}
