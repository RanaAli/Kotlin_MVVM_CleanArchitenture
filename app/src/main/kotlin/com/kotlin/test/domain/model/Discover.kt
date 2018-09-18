package com.kotlin.test.domain.model

import java.util.*

data class Discover(
        var page: Int = 0,
        var results: List<Result> = ArrayList(),
        var totalResults: Int = 0,
        var totalPages: Int = 0
)