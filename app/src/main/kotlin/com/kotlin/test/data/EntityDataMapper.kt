package com.kotlin.test.data

import com.kotlin.test.core.extension.orEmpty
import com.kotlin.test.data.entity.DiscoverEntity
import com.kotlin.test.data.entity.ResultEntity
import com.kotlin.test.domain.model.Discover
import com.kotlin.test.domain.model.Result

class EntityDataMapper {

    companion object {
        fun map(discoverEntity: DiscoverEntity): Discover {
            return Discover().apply {

                discoverEntity.let {
                    this.results = getResultsList(it.results)
                }
            }
        }

        private fun getResultsList(list: List<ResultEntity>): MutableList<Result> {
            val resultList: MutableList<Result> = ArrayList()

            list.forEach {
                resultList.add(getResult(it))
            }
            return resultList
        }

        private fun getResult(it: ResultEntity): Result {
            return Result().apply {
                id = it.id ?: -1
                posterPath = it.posterPath.orEmpty()
                releaseDate = it.releaseDate.orEmpty()
                title = it.title.orEmpty()
                overview = it.overview.orEmpty()
                backdropPath = it.backdropPath.orEmpty()
            }
        }
    }
}