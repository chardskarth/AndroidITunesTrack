package com.chardskarth.itunestrack

import android.app.Application
import com.chardskarth.itunestrack.track.ITunesApi
import com.chardskarth.itunestrack.track.MusicTrackViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ITunesTrackApplication : Application() {

    private val appModule = module {
        single { ITunesApi() }
        viewModel { MusicTrackViewModel() }

        single { getKoin()._logger }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ITunesTrackApplication)
            modules(appModule)
        }

    }
}