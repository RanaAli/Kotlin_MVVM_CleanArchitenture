
package com.kotlin.test.core.extension

fun String.Companion.empty() = ""

/** Returns the string if it is not `null`, or the empty string otherwise. */
fun String?.orEmpty(): String = this ?: ""