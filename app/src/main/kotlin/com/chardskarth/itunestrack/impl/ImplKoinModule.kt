package com.chardskarth.itunestrack.impl

import com.chardskarth.itunestrack.impl.track.gateway.ITunesKtorApi
import com.chardskarth.itunestrack.track.gateway.ITunesApi
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun loadImplKoinModule() {
    loadKoinModules(module {
        single<ITunesApi> { ITunesKtorApi() }
    })
}