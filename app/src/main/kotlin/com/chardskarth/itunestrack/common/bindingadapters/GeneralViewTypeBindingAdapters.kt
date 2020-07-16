package com.chardskarth.itunestrack.common.bindingadapters

import android.view.View
import androidx.databinding.BindingAdapter
import com.chardskarth.itunestrack.common.GeneralViewType

object GeneralViewTypeBindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleOnMainViewTypeLoading")
    fun setVisibleWhenMainViewTypeLoading(view: View, generalViewType: GeneralViewType) {
        if (generalViewType == GeneralViewType.Loading)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.GONE
    }

    @JvmStatic
    @BindingAdapter("visibleOnMainViewTypeError")
    fun setVisibleWhenMainViewTypeError(view: View, generalViewType: GeneralViewType) {
        if (generalViewType == GeneralViewType.Error)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.GONE
    }

    @JvmStatic
    @BindingAdapter("invisibleOnMainViewTypeUnlessNormal")
    fun setVisibleWhenMainViewTypeNormal(view: View, generalViewType: GeneralViewType) {
        if (generalViewType == GeneralViewType.Normal)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.INVISIBLE
    }

    @JvmStatic
    @BindingAdapter("visibleOnMainViewTypeEmpty")
    fun setVisibleWhenMainViewTypeEmpty(view: View, generalViewType: GeneralViewType) {
        if (generalViewType == GeneralViewType.Empty)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.GONE
    }
}
