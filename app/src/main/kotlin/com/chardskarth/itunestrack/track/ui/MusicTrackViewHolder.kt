package com.chardskarth.itunestrack.track.ui

import androidx.recyclerview.widget.RecyclerView
import com.chardskarth.itunestrack.databinding.TrackListItemBinding
import com.chardskarth.itunestrack.track.model.MusicTrack

class MusicTrackViewHolder(private val trackListItemBinding: TrackListItemBinding) :
    RecyclerView.ViewHolder(trackListItemBinding.trackList) {

    fun bind(musicTrack: MusicTrack) {
        trackListItemBinding.musicTrack = musicTrack
    }
}
