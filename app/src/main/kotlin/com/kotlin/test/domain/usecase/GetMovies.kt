
package com.kotlin.test.domain.usecase

import com.kotlin.test.core.exception.Failure
import com.kotlin.test.core.functional.Either
import com.kotlin.test.core.interactor.UseCase
import com.kotlin.test.domain.model.Discover
import com.kotlin.test.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetMovies
@Inject
constructor(private val moviesRepository: MoviesRepository)
    : UseCase<Discover, GetMovies.Params>() {

    override suspend fun run(params: Params):
            Either<Failure, Discover> = moviesRepository.discover(params.page)

    data class Params(val page: Int)
}
