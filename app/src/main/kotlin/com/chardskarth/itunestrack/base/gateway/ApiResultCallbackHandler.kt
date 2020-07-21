package com.chardskarth.itunestrack.base.gateway

interface ApiResultCallbackHandler {
    fun onIsLoading()
    fun onIsError(err: Exception)
    fun onIsSuccess()
}

