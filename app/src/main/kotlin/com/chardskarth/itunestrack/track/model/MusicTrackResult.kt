package com.chardskarth.itunestrack.track.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.nullable

@Serializable
data class MusicTrack(
    @SerialName("trackName") val title: String
    , @SerialName("trackPrice") val price: Double? = null
    , @SerialName("trackId") val id: String
    , @SerialName("currency") val priceCurrency: String?
    , @SerialName("primaryGenreName") val genreName: String
    , @SerialName("artworkUrl30") val trackImageUrl30: String
    , @SerialName("artworkUrl60") val trackImageUrl60: String
    , @SerialName("artworkUrl100") val trackImageUrl100: String
)

@Serializable
data class MusicTrackResult(
    val resultCount: Int
    , val results: List<MusicTrack>
)

