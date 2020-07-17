package com.chardskarth.itunestrack.common.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.chardskarth.itunestrack.track.model.MusicTrack

object MusicTrackPriceTextBindingAdapters {
    private const val USD_CURR = "USD"


    @JvmStatic
    @BindingAdapter(value = ["musicTrackPriceText", "musicTrackPriceEmptyText"], requireAll = false)
    fun setMusicTrackTextAndEmptyText(
        view: TextView,
        musicTrack: MusicTrack?,
        stringDefault: String?
    ) {
        val trackPriceText = with(musicTrack ?: MusicTrack()) {
            val trackPriceCurrency = when (priceCurrency?.trim()?.toUpperCase()) {
                USD_CURR -> "$"
                else -> priceCurrency
            }

            price?.let {
                "$price $trackPriceCurrency"
            } ?: stringDefault
        }
        view.text = trackPriceText
    }
}