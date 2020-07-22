package com.chardskarth.itunestrack.track.gateway

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.chardskarth.itunestrack.track.MusicTrackPageKeyedDataSource
import com.chardskarth.itunestrack.track.model.MusicTrack


class MusicTrackDataSourceFactory(private val searchText: String) :
    DataSource.Factory<Int, MusicTrack>() {

    private val liveDataSource = MutableLiveData<MusicTrackPageKeyedDataSource>()
    private lateinit var dataSource: MusicTrackDataSource

    override fun create(): DataSource<Int, MusicTrack> {
        dataSource = MusicTrackDataSource(searchText)
        liveDataSource.postValue(dataSource)
        return dataSource
    }

    fun invalidate() {
        dataSource.invalidate()
    }

}