package com.chardskarth.itunestrack

import android.app.Application
import android.util.Log
import com.chardskarth.itunestrack.track.trackKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import com.github.s0nerik.glide_bindingadapter.GlideBindingConfig

class ITunesTrackApplication : Application() {

    private val appModule = module {
    }

    companion object {
        fun registerGlideConfigs() {
            GlideBindingConfig.registerProvider("config0") { _, request ->
                request.centerCrop()
                    .placeholder(R.drawable.ic_baseline_photo_album_24)
                    .error(R.drawable.ic_baseline_error_outline_24)
            }
            GlideBindingConfig.setDefault("config0")
        }
    }


    override fun onCreate() {
        super.onCreate()
        registerGlideConfigs()

        startKoin {
            androidLogger()
            androidContext(this@ITunesTrackApplication)
            modules(appModule, trackKoinModule)
        }

    }
}

fun Any.logd(message: String) {
    Log.d(Any::class.java.simpleName, message)
}