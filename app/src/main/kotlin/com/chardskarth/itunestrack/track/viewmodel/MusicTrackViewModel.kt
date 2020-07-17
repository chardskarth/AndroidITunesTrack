package com.chardskarth.itunestrack.track.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.chardskarth.itunestrack.common.GeneralViewType
import com.chardskarth.itunestrack.common.extensions.equals
import com.chardskarth.itunestrack.common.extensions.isEmpty
import com.chardskarth.itunestrack.common.extensions.isSuccess
import com.chardskarth.itunestrack.common.gateway.IApiResultCallback
import com.chardskarth.itunestrack.logi
import com.chardskarth.itunestrack.track.gateway.ITunesApi
import com.chardskarth.itunestrack.track.gateway.MusicTrackDataSourceFactory
import com.chardskarth.itunestrack.track.model.MusicTrack
import io.ktor.http.HttpStatusCode


typealias PagedListMusicTrack = PagedList<MusicTrack>
typealias PageKeyedMusicTrackDataSource = PageKeyedDataSource<Int, MusicTrack>

class MusicTrackViewModel(
    private val iTunesApi: ITunesApi
) : ViewModel() {
    val generalViewTypeMediator = MediatorLiveData<GeneralViewType>()
    val generalViewType = MutableLiveData(GeneralViewType.Loading)
    val livePagedList: LiveData<PagedListMusicTrack>
    private val musicTrackItemDataSourceFactory = MusicTrackDataSourceFactory()
    val resultStatus = MutableLiveData(HttpStatusCode.MultiStatus)

    init {
        iTunesApi.apiResultCallback = object : IApiResultCallback {
            override fun onIsLoading() {
                resultStatus.postValue(HttpStatusCode.MultiStatus)
            }

            override fun onIsError(err: Exception) {
                resultStatus.postValue(HttpStatusCode.InternalServerError)
            }

            override fun onIsSuccess() {
                resultStatus.postValue(HttpStatusCode.OK)
            }
        }

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(MusicTrackDataSourceFactory.MusicTrackDataSource.PAGE_SIZE)
            .build()

        livePagedList = LivePagedListBuilder(musicTrackItemDataSourceFactory, pagedListConfig)
            .build()

        generalViewTypeMediator.addSource(livePagedList) { mutateGeneralViewType() }
        generalViewTypeMediator.addSource(resultStatus) { mutateGeneralViewType() }

    }

    private fun mutateGeneralViewType() {
        val derivedGeneralViewType = when {
            isEmptyListAndIsLoading() -> GeneralViewType.Loading
            isEmptyListAndHasError() -> GeneralViewType.Error
            isEmptyListAndHasNoError() -> GeneralViewType.Empty
            !livePagedList.value.isEmpty() -> GeneralViewType.Normal
            else -> {
                logi("Cant derive GeneralViewType. Defaulting.")
                logi("list size: ${livePagedList.value?.size}. result status: $resultStatus")
                GeneralViewType.Normal

            }
        }
        logi("Derived generalviewtype: $derivedGeneralViewType")
        generalViewType.postValue(derivedGeneralViewType)
    }

    private fun isEmptyListAndIsLoading() =
        (livePagedList.value.isEmpty()
                && resultStatus.value.equals(HttpStatusCode.MultiStatus))

    private fun isEmptyListAndHasError() =
        (livePagedList.value.isEmpty()
                && !resultStatus.value.isSuccess())


    private fun isEmptyListAndHasNoError() =
        (livePagedList.value.isEmpty()
                && resultStatus.value.isSuccess())

    override fun onCleared() {
        iTunesApi.apiResultCallback = null
    }

    fun refreshIfError() {
        if (generalViewType.value == GeneralViewType.Error) {
            musicTrackItemDataSourceFactory.invalidate()
        }
    }

}

