package com.chardskarth.itunestrack.track

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource


class MusicTrackDataSourceFactory: DataSource.Factory<Int, MusicTrack>() {
    private val liveDataSource = MutableLiveData<PageKeyedMusicTrackDataSource>()

    override fun create(): DataSource<Int, MusicTrack> {
        val dataSource = MusicTrackDataSource()
        liveDataSource.postValue(dataSource)
        return dataSource
    }
}