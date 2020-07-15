package com.chardskarth.itunestrack.common

interface IApiResultCallback {
    fun onIsLoading()
    fun onIsError()
    fun onIsSuccess()
}