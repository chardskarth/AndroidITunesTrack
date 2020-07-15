package com.chardskarth.itunestrack.common

enum class GeneralViewType {
    Error // state to call for an action of reload
    , Loading    // state to expect the user to wait
    , Empty      // the main data to display is empty
    , Normal     // state to expect the user to mainly interact in
}

