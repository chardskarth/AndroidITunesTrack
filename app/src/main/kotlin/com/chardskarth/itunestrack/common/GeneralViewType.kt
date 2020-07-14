package com.chardskarth.itunestrack.common

enum class GeneralViewType {
    Error // state to call for an action of reload
    , Loading    // state to expect the user to wait
    , Normal     // state to expect the user to mainly interact in
}

