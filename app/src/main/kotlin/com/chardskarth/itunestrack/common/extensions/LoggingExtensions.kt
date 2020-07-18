package com.chardskarth.itunestrack.common.extensions

import android.util.Log

fun Any.logd(message: String) {
    Log.d(Any::class.java.simpleName, message)
}
fun Any.logi(message: String) {
    Log.i(Any::class.java.simpleName, message)
}
