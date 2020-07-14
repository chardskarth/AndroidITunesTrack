package com.chardskarth.itunestrack.track.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.chardskarth.itunestrack.common.GeneralViewType
import com.chardskarth.itunestrack.track.gateway.MusicTrackDataSourceFactory
import com.chardskarth.itunestrack.track.model.MusicTrack


typealias PagedListMusicTrack = PagedList<MusicTrack>
typealias PageKeyedMusicTrackDataSource = PageKeyedDataSource<Int, MusicTrack>

class MusicTrackViewModel : ViewModel() {
    private val _generalViewType: MutableLiveData<GeneralViewType>
    val generalViewType: LiveData<GeneralViewType>
        get() = _generalViewType

    private val _textSearchTrack: MutableLiveData<String>
    val textSearchTrack: LiveData<String>
        get() = _textSearchTrack

    val livePagedList: LiveData<PagedListMusicTrack>

    init {
        val musicTrackItemDataSourceFactory =
            MusicTrackDataSourceFactory()

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(MusicTrackDataSourceFactory.MusicTrackDataSource.PAGE_SIZE)
            .build()

        livePagedList = LivePagedListBuilder(musicTrackItemDataSourceFactory, pagedListConfig)
            .build()
        _generalViewType = MutableLiveData(GeneralViewType.Normal)
        _textSearchTrack = MutableLiveData("")
    }


    fun setMainViewTypeToLoading() {
        _generalViewType.value = GeneralViewType.Loading
    }

    fun setMainViewTypeToLoaded() {
        _generalViewType.value = GeneralViewType.Normal
    }

    fun setMainViewTypeToError() {
        _generalViewType.value = GeneralViewType.Error
    }

}

