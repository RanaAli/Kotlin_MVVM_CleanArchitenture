package com.kotlin.test.domain.usecase

import com.kotlin.test.core.exception.Failure
import com.kotlin.test.core.functional.Either
import com.kotlin.test.core.interactor.UseCase
import com.kotlin.test.domain.model.Discover
import com.kotlin.test.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetMoviesByDate
@Inject
constructor(private val moviesRepository: MoviesRepository)
    : UseCase<Discover, GetMoviesByDate.Params>() {

    override suspend fun run(params: Params):
            Either<Failure, Discover> = moviesRepository.discoverByReleaseDate(
            params.page,
            params.primaryReleaseYear
    )

    data class Params(val page: Int, val primaryReleaseYear: String)
}
