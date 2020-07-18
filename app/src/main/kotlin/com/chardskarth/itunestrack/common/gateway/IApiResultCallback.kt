package com.chardskarth.itunestrack.common.gateway

interface ApiResultCallbackHandler {
    fun onIsLoading()
    fun onIsError(err: Exception)
    fun onIsSuccess()
}

interface ApiResultCallback {
    var apiResultCallbackHandler: ApiResultCallbackHandler?
}