package com.chardskarth.itunestrack.track.gateway.model

import kotlinx.serialization.Serializable


@Serializable
data class MusicTrackSearchResponse(
    val resultCount: Int = 0
    , val results: List<MusicTrackResponse> = emptyList()
)

