package com.chardskarth.itunestrack.track.model

data class MusicTrack(
    val title: String
    , val price: Double? = null
    , val id: String
    , val priceCurrency: String?
    , val genreName: String
    , val trackImageUrl30: String
    , val trackImageUrl60: String
    , val trackImageUrl100: String
) {

    constructor() : this("", null, "", null, "", "", "", "")

    private val USD_CURR = "USD"

    val trackPriceText: String?
        get() {
            val trackPriceCurrency = when (priceCurrency?.trim()?.toUpperCase()) {
                USD_CURR -> "$"
                else -> priceCurrency
            }

            return price?.let {
                "$price $trackPriceCurrency"
            }
        }
}

