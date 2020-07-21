package com.chardskarth.itunestrack.track

import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import com.chardskarth.itunestrack.track.model.MusicTrack
import com.chardskarth.itunestrack.track.ui.MusicTrackViewHolder
import com.chardskarth.itunestrack.track.viewmodel.MusicTrackViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


typealias MusicTrackPagedListAdapter = PagedListAdapter<MusicTrack, MusicTrackViewHolder>
typealias MusicTrackPagedList = PagedList<MusicTrack>
typealias MusicTrackPageKeyedDataSource = PageKeyedDataSource<Int, MusicTrack>


fun loadTrackKoinModule() {
    loadKoinModules(module {
        viewModel { MusicTrackViewModel(get()) }
    })
}
