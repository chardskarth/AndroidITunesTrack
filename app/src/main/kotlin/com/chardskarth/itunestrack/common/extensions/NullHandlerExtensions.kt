package com.chardskarth.itunestrack.common.extensions

import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess

fun <T> List<T>?.isEmpty() = this?.isEmpty() ?: true
fun Any?.equals(other: Any) = this?.equals(other) ?: false
fun HttpStatusCode?.isSuccess() = this?.isSuccess() ?: false
