package com.chardskarth.itunestrack.track

import com.chardskarth.itunestrack.track.gateway.ITunesApi
import com.chardskarth.itunestrack.track.viewmodel.MusicTrackViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val trackKoinModule = module {
    single { ITunesApi() }
    viewModel { MusicTrackViewModel(get()) }
}