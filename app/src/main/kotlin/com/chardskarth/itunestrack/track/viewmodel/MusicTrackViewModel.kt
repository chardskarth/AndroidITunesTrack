package com.chardskarth.itunestrack.track.viewmodel

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.chardskarth.itunestrack.base.gateway.ApiResultCallbackHandler
import com.chardskarth.itunestrack.common.GeneralViewType
import com.chardskarth.itunestrack.common.extensions.equals
import com.chardskarth.itunestrack.common.extensions.isEmpty
import com.chardskarth.itunestrack.common.extensions.isSuccess
import com.chardskarth.itunestrack.common.extensions.logi
import com.chardskarth.itunestrack.track.MusicTrackPagedList
import com.chardskarth.itunestrack.track.gateway.ITunesApi
import com.chardskarth.itunestrack.track.gateway.MusicTrackDataSource
import com.chardskarth.itunestrack.track.gateway.MusicTrackDataSourceFactory
import io.ktor.http.HttpStatusCode


class MusicTrackViewModel(
    private val iTunesApi: ITunesApi
) : ViewModel() {
    val generalViewTypeMediator = MediatorLiveData<GeneralViewType>()
    val generalViewType = MutableLiveData(GeneralViewType.Loading)
    private val searchTextLiveData = MutableLiveData("")
    val livePagedList: LiveData<MusicTrackPagedList>
    private lateinit var musicTrackItemDataSourceFactory: MusicTrackDataSourceFactory
    val resultStatus = MutableLiveData(HttpStatusCode.MultiStatus)

    init {
        iTunesApi.apiResultCallbackHandler = object : ApiResultCallbackHandler {
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
            .setPageSize(MusicTrackDataSource.PAGE_SIZE)
            .build()

        livePagedList = Transformations.switchMap(searchTextLiveData) {searchText ->
            musicTrackItemDataSourceFactory = MusicTrackDataSourceFactory(searchText)
            LivePagedListBuilder(musicTrackItemDataSourceFactory, pagedListConfig)
                .build()
        }

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
        iTunesApi.apiResultCallbackHandler = null
    }

    fun refreshIfError() {
        if (generalViewType.value == GeneralViewType.Error) {
            musicTrackItemDataSourceFactory.invalidate()
        }
    }

    fun setSearchText(searchText: String){
        searchTextLiveData.postValue(searchText)
    }
}

