package com.chardskarth.itunestrack.track.gateway

import com.chardskarth.itunestrack.common.gateway.ApiResultCallback
import com.chardskarth.itunestrack.track.model.MusicTrack

interface ITunesApi : ApiResultCallback {
    suspend fun search(
        searchTerm: String = "",
        page: Int = 0
    ): List<MusicTrack>
}