package com.chardskarth.itunestrack.track.gateway

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.chardskarth.itunestrack.track.MusicTrackPageKeyedDataSource
import com.chardskarth.itunestrack.track.model.MusicTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject


class MusicTrackDataSourceFactory
    : DataSource.Factory<Int, MusicTrack>() {
    private val liveDataSource = MutableLiveData<MusicTrackPageKeyedDataSource>()
    private lateinit var dataSource: MusicTrackDataSource

    override fun create(): DataSource<Int, MusicTrack> {
        dataSource = MusicTrackDataSource()
        liveDataSource.postValue(dataSource)
        return dataSource
    }

    fun invalidate() {
        dataSource.invalidate()
    }

    class MusicTrackDataSource : PageKeyedDataSource<Int, MusicTrack>(), KoinComponent {
        private val iTunesApi: ITunesApi by inject()

        companion object {
            const val FIRST_PAGE = 0
            const val PAGE_SIZE = 50
            const val SEARCH_TERM = ""
        }

        override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, MusicTrack>
        ) {
            CoroutineScope(Dispatchers.IO).launch {
                val musicTrackResult = iTunesApi.search(
                    SEARCH_TERM,
                    FIRST_PAGE
                )
                callback.onResult(musicTrackResult, null, FIRST_PAGE + 1)
            }
        }

        override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MusicTrack>) {
            CoroutineScope(Dispatchers.IO).launch {
                val pageKey = params.key + 1
                val musicTrackResult = iTunesApi.search(SEARCH_TERM, pageKey)
                callback.onResult(musicTrackResult, pageKey)
            }
        }

        override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MusicTrack>) {
            CoroutineScope(Dispatchers.IO).launch {
                val pageKey = if (params.key > FIRST_PAGE) params.key - 1 else 0
                val musicTrackResult = iTunesApi.search(SEARCH_TERM, pageKey)
                callback.onResult(musicTrackResult, pageKey)
            }
        }

    }
}