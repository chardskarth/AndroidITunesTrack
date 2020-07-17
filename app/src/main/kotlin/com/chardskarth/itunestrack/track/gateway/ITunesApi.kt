package com.chardskarth.itunestrack.track.gateway

import com.chardskarth.itunestrack.common.gateway.HttpTimeoutConfigBlock
import com.chardskarth.itunestrack.common.gateway.JsonFeatureConfigBlock
import com.chardskarth.itunestrack.common.gateway.HttpBuilderFeatureConfig
import com.chardskarth.itunestrack.common.gateway.IApi
import com.chardskarth.itunestrack.common.gateway.IApiResultCallback
import com.chardskarth.itunestrack.logd
import com.chardskarth.itunestrack.track.gateway.model.MusicTrackResponse
import com.chardskarth.itunestrack.track.gateway.model.MusicTrackSearchResponse
import com.chardskarth.itunestrack.track.model.MusicTrack
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.util.KtorExperimentalAPI

class ITunesApi : IApi {
    override val baseUrl = "https://itunes.apple.com/search"
    override var apiResultCallback: IApiResultCallback? = null

    @KtorExperimentalAPI
    private val client by lazy {
        createClient(object : HttpBuilderFeatureConfig {
            override val jsonFeatureConfigBlock: JsonFeatureConfigBlock = {
                acceptContentTypes = acceptContentTypes + ContentType("text", "javascript")
            }
            override val httpTimeoutConfigBlock: HttpTimeoutConfigBlock = {
                requestTimeoutMillis = 30000
            }
        })
    }

    companion object {
        const val ITEMS_PER_PAGE = 50
    }

    @KtorExperimentalAPI
    suspend fun search(
        searchTerm: String = "",
        page: Int = 0
    ): List<MusicTrack> {
        val encodedSearchTerm = "&term=%22${encodeUrl(searchTerm)}%22"
        val offset = page * ITEMS_PER_PAGE
        val urlString = "$baseUrl?media=music${encodedSearchTerm}&offset=$offset"
        apiResultCallback?.onIsLoading()
        return try {
            val result = client.get<MusicTrackSearchResponse>(urlString).results
            apiResultCallback?.onIsSuccess()
            result.map(MusicTrackResponse::toMusicTrack)
        } catch (err: Exception) {
            logd(err.localizedMessage ?: err.message.toString())
            apiResultCallback?.onIsError(err)
            emptyList()
        }
    }

}

private fun MusicTrackResponse.toMusicTrack() = MusicTrack(
    title, price, id, priceCurrency, genreName, trackImageUrl30, trackImageUrl60, trackImageUrl100
)