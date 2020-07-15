package com.chardskarth.itunestrack.track.gateway

import android.util.Log
import com.chardskarth.itunestrack.common.IApi
import com.chardskarth.itunestrack.common.IApiResultCallback
import com.chardskarth.itunestrack.logd
import com.chardskarth.itunestrack.track.model.MusicTrackResult
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.util.KtorExperimentalAPI

class ITunesApi : IApi {
    override val baseUrl = "https://itunes.apple.com/search"
    override var apiResultCallback: IApiResultCallback? = null

    @KtorExperimentalAPI
    private val client by lazy {
        createClient {
            acceptContentTypes = acceptContentTypes + ContentType("text", "javascript")
        }
    }

    companion object {
        const val ITEMS_PER_PAGE = 50
    }

    @KtorExperimentalAPI
    suspend fun search(
        searchTerm: String = "",
        page: Int = 0
    ): MusicTrackResult {
        val encodedSearchTerm = "&term=%22${encodeUrl(searchTerm)}%22"
        val offset = page * ITEMS_PER_PAGE
        val urlString = "$baseUrl?media=music${encodedSearchTerm}&offset=$offset"
        apiResultCallback?.onIsLoading()
        return try {
            val result = client.get<MusicTrackResult>(urlString)
            apiResultCallback?.onIsSuccess()
            result
        } catch(err: Exception) {
            logd(err.localizedMessage ?: err.message.toString())
            apiResultCallback?.onIsError()
            MusicTrackResult()
        }
    }

}