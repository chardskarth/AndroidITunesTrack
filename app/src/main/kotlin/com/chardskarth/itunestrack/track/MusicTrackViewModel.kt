package com.chardskarth.itunestrack.track

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.chardskarth.itunestrack.common.MainViewType


typealias PagedListMusicTrack = PagedList<MusicTrack>
typealias PageKeyedMusicTrackDataSource = PageKeyedDataSource<Int, MusicTrack>

class MusicTrackViewModel : ViewModel() {
    private val _mainViewType: MutableLiveData<MainViewType>
    val mainViewType: LiveData<MainViewType>
        get() = _mainViewType

    private val _textSearchTrack: MutableLiveData<String>
    val textSearchTrack: LiveData<String>
        get() = _textSearchTrack

    val livePagedList: LiveData<PagedListMusicTrack>

    init {
        val musicTrackItemDataSourceFactory = MusicTrackDataSourceFactory()

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(MusicTrackDataSource.PAGE_SIZE)
            .build()

        livePagedList = LivePagedListBuilder(musicTrackItemDataSourceFactory, pagedListConfig)
            .build()
        _mainViewType = MutableLiveData(MainViewType.Loading)
        _textSearchTrack = MutableLiveData("")
    }


    fun setMainViewTypeToLoading() {
        _mainViewType.value = MainViewType.Loading
    }

    fun setMainViewTypeToLoaded() {
        _mainViewType.value = MainViewType.Normal
    }

    fun setMainViewTypeToError() {
        _mainViewType.value = MainViewType.Error
    }

}

