package com.chardskarth.itunestrack.track.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chardskarth.itunestrack.databinding.TrackListItemBinding
import com.chardskarth.itunestrack.track.MusicTrackPagedListAdapter
import com.chardskarth.itunestrack.track.model.MusicTrack

class MusicTrackAdapterForRecyclerView(
    private val context: Context
    , recyclerView: RecyclerView
) : MusicTrackPagedListAdapter(
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


}
