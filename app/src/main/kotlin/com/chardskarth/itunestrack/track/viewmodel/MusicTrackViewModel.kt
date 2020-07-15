package com.chardskarth.itunestrack.track.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.chardskarth.itunestrack.common.GeneralViewType
import com.chardskarth.itunestrack.common.IApiResultCallback
import com.chardskarth.itunestrack.logd
import com.chardskarth.itunestrack.track.gateway.ITunesApi
import com.chardskarth.itunestrack.track.gateway.MusicTrackDataSourceFactory
import com.chardskarth.itunestrack.track.model.MusicTrack
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess


typealias PagedListMusicTrack = PagedList<MusicTrack>
typealias PageKeyedMusicTrackDataSource = PageKeyedDataSource<Int, MusicTrack>

class MusicTrackViewModel(
    private val iTunesApi: ITunesApi
) : ViewModel() {
    val generalViewTypeMediator = MediatorLiveData<GeneralViewType>()
    val generalViewType = MutableLiveData(GeneralViewType.Loading)

    private val _textSearchTrack: MutableLiveData<String>
    val textSearchTrack: LiveData<String>
        get() = _textSearchTrack

    val livePagedList: LiveData<PagedListMusicTrack>

    companion object {
        val resultStatus = MutableLiveData(HttpStatusCode.MultiStatus)

        object MusicTrackApiResultCallback : IApiResultCallback {
            override fun onIsLoading() {
                logd("loading here")
                resultStatus.postValue(HttpStatusCode.MultiStatus)
            }

            override fun onIsError() {
                logd("Error here")
                resultStatus.postValue(HttpStatusCode.InternalServerError)
            }

            override fun onIsSuccess() {
                logd("Success here")
                resultStatus.postValue(HttpStatusCode.OK)
            }
        }

    }

    init {
        iTunesApi.apiResultCallback = MusicTrackApiResultCallback
        val musicTrackItemDataSourceFactory = MusicTrackDataSourceFactory()

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(MusicTrackDataSourceFactory.MusicTrackDataSource.PAGE_SIZE)
            .build()

        livePagedList = LivePagedListBuilder(musicTrackItemDataSourceFactory, pagedListConfig)
            .build()

        generalViewTypeMediator.addSource(livePagedList) { mutateGeneralViewType() }
        generalViewTypeMediator.addSource(resultStatus) { mutateGeneralViewType() }

        _textSearchTrack = MutableLiveData("")
    }

    private fun mutateGeneralViewType() {
        val derivedGeneralViewType = when {
            isEmptyListAndIsLoading() -> GeneralViewType.Loading
            isEmptyListAndHasError() -> GeneralViewType.Error
            isEmptyListAndHasNoError() -> GeneralViewType.Empty
            !livePagedList.value.isEmpty() -> GeneralViewType.Normal
            else -> {
                logd("Cant derive GeneralViewType. Defaulting.")
                logd("list size: ${livePagedList.value?.size}. result status: $resultStatus")
                GeneralViewType.Normal

            }
        }
        logd("Derived generalviewtype: $derivedGeneralViewType")
        generalViewType.postValue(derivedGeneralViewType)
    }

    private fun isEmptyListAndIsLoading() =
        (livePagedList.value.isEmpty()
                && resultStatus.value.equals(HttpStatusCode.MultiStatus))

    private fun isEmptyListAndHasError() =
        (livePagedList.value.isEmpty()
                && resultStatus.value.equals(HttpStatusCode.InternalServerError))


    private fun isEmptyListAndHasNoError() =
        (livePagedList.value.isEmpty()
                && resultStatus.value.isSuccess())

    override fun onCleared() {
        iTunesApi.apiResultCallback = null
    }

}

fun <T> List<T>?.isEmpty() = this?.isEmpty() ?: true
fun Any?.equals(other: Any) = this?.equals(other) ?: false
fun HttpStatusCode?.isSuccess() = this?.isSuccess() ?: false
