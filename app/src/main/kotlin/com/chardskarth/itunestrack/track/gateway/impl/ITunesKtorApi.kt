package com.chardskarth.itunestrack.track.gateway.impl

import com.chardskarth.itunestrack.common.extensions.logd
import com.chardskarth.itunestrack.common.gateway.*
import com.chardskarth.itunestrack.track.gateway.ITunesApi
import com.chardskarth.itunestrack.track.gateway.model.MusicTrackResponse
import com.chardskarth.itunestrack.track.gateway.model.MusicTrackSearchResponse
import com.chardskarth.itunestrack.track.mapper.toMusicTrack
import com.chardskarth.itunestrack.track.model.MusicTrack
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.util.KtorExperimentalAPI


class ITunesKtorApi : KtorApi, ITunesApi {
    override val baseUrl = "https://itunes.apple.com/search"
    override var apiResultCallbackHandler: ApiResultCallbackHandler? = null

    companion object {
        const val ITEMS_PER_PAGE = 50
    }

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

    @KtorExperimentalAPI
    override suspend fun search(
        searchTerm: String,
        page: Int
    ): List<MusicTrack> {
        val encodedSearchTerm = "&term=%22${encodeUrl(searchTerm)}%22"
        val offset = page * ITEMS_PER_PAGE
        val urlString = "$baseUrl?media=music${encodedSearchTerm}&offset=$offset"
        apiResultCallbackHandler?.onIsLoading()
        return try {
            val result = client.get<MusicTrackSearchResponse>(urlString).results
            apiResultCallbackHandler?.onIsSuccess()
            result.map(MusicTrackResponse::toMusicTrack)
        } catch (err: Exception) {
            logd(err.localizedMessage ?: err.message.toString())
            apiResultCallbackHandler?.onIsError(err)
            emptyList()
        }
    }

}

