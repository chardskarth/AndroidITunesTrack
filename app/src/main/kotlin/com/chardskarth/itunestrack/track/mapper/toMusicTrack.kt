package com.chardskarth.itunestrack.track.mapper

import com.chardskarth.itunestrack.track.gateway.model.MusicTrackResponse
import com.chardskarth.itunestrack.track.model.MusicTrack

fun MusicTrackResponse.toMusicTrack() = MusicTrack(
    title, price, id, priceCurrency, genreName, trackImageUrl30, trackImageUrl60, trackImageUrl100
)