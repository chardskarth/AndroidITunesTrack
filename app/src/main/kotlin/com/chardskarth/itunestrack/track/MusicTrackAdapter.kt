package com.chardskarth.itunestrack.track

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

class MusicTrackAdapter(
    private val context: Context
) :
    PagedListAdapter<MusicTrack, MusicTrackAdapter.ItemViewHolder>(DiffUtilItemCallback) {

    companion object {
        object DiffUtilItemCallback : DiffUtil.ItemCallback<MusicTrack>() {
            override fun areItemsTheSame(oldItem: MusicTrack, newItem: MusicTrack) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MusicTrack, newItem: MusicTrack) =
                oldItem == newItem
        }

        private const val USD_CURR = "USD"
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgViewTrackArtwork: ImageView =
            itemView.findViewById(R.id.trackListItemImgViewTrackArtwork)
        val txtViewTrackName: TextView = itemView.findViewById(R.id.trackListItemTxtViewTrackName)
        val txtViewTrackPrice: TextView = itemView.findViewById(R.id.trackListItemTxtViewTrackPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.track_list_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let { musicTrack ->
            Glide.with(context)
                .load(musicTrack.trackImageUrl60)
                .placeholder(R.drawable.ic_image_placeholder)
                .into(holder.imgViewTrackArtwork)

            holder.txtViewTrackName.text = musicTrack.title
            holder.txtViewTrackPrice.text = getTrackPriceText(musicTrack)
        }

    }

    private fun getTrackPriceText(musicTrack: MusicTrack): String {
        val trackPriceCurrency = when (musicTrack.priceCurrency?.trim()?.toUpperCase()) {
            USD_CURR -> "$"
            else -> musicTrack.priceCurrency
        }

        return musicTrack.price?.let {
            "${musicTrack.price} $trackPriceCurrency"
        } ?: context.resources.getString(R.string.track_list_price_no_price)
    }
}
