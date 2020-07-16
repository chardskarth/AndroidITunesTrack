package com.chardskarth.itunestrack.track.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chardskarth.itunestrack.R
import com.chardskarth.itunestrack.databinding.TrackListItemBinding
import com.chardskarth.itunestrack.track.model.MusicTrack

class MusicTrackAdapterForRecyclerView(
    private val context: Context
    , recyclerView: RecyclerView
) : PagedListAdapter<MusicTrack, MusicTrackAdapterForRecyclerView.MusicTrackViewHolder>(
    DiffUtilItemCallback
) {

    private companion object {
        object DiffUtilItemCallback : DiffUtil.ItemCallback<MusicTrack>() {
            override fun areItemsTheSame(oldItem: MusicTrack, newItem: MusicTrack) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: MusicTrack,
                newItem: MusicTrack
            ) =
                oldItem == newItem
        }

    }

    init {
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicTrackViewHolder {
        val trackListView = TrackListItemBinding.inflate(LayoutInflater.from(context))
        return MusicTrackViewHolder(trackListView)
    }

    override fun onBindViewHolder(holder: MusicTrackViewHolder, position: Int) {
        getItem(position)?.let { musicTrack ->
            holder.bind(musicTrack)
        }
    }

    class MusicTrackViewHolder(private val trackListItemBinding: TrackListItemBinding) :
        RecyclerView.ViewHolder(trackListItemBinding.trackList) {

        fun bind(musicTrack: MusicTrack) {
            trackListItemBinding.musicTrack = musicTrack
        }
    }

}
