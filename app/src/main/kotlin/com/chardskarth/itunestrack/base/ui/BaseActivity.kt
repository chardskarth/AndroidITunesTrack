package com.chardskarth.itunestrack.base.ui

import androidx.appcompat.app.AppCompatActivity
import com.chardskarth.itunestrack.R

abstract class BaseActivity: AppCompatActivity() {
    val appName: String
        get() = resources.getString(R.string.app_name)

    val appNameInitials: String
        get() = resources.getString(R.string.app_name_initials)


    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }
}