package com.chardskarth.itunestrack.common

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter

enum class MainViewType {
    Error // state to call for an action of reload
    , Loading    // state to expect the user to wait
    , Normal     // state to expect the user to mainly interact in
}

object MainViewTypeBindingAdapters {
    @JvmStatic
    @BindingAdapter("app:visibleOnMainViewTypeLoading")
    fun setVisibleWhenMainViewTypeLoading(view: View, mainViewType: MainViewType) {
        Log.i("MainViewType", mainViewType.toString())
        if (mainViewType == MainViewType.Loading)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.GONE
    }

    @JvmStatic
    @BindingAdapter("app:visibleOnMainViewTypeError")
    fun setVisibleWhenMainViewTypeError(view: View, mainViewType: MainViewType) {
        if (mainViewType == MainViewType.Error)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.GONE
    }

    @JvmStatic
    @BindingAdapter("app:visibleOnMainViewTypeNormal")
    fun setVisibleWhenMainViewTypeNormal(view: View, mainViewType: MainViewType) {
        if (mainViewType == MainViewType.Normal)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.GONE
    }
}