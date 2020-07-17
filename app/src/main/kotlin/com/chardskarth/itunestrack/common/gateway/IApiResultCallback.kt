package com.chardskarth.itunestrack.common.gateway

interface IApiResultCallback {
    fun onIsLoading()
    fun onIsError(err: Exception)
    fun onIsSuccess()
}