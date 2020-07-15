package com.chardskarth.itunestrack.track.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.chardskarth.itunestrack.R
import com.chardskarth.itunestrack.databinding.TrackListBinding
import com.chardskarth.itunestrack.track.viewmodel.MusicTrackViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val musicTrackViewModel: MusicTrackViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainActivityBinding: TrackListBinding = DataBindingUtil.setContentView(this,
            R.layout.track_list
        )
        mainActivityBinding.lifecycleOwner = this
        mainActivityBinding.viewModel = musicTrackViewModel

        initializeRecyclerView(musicTrackViewModel, mainActivityBinding)
    }

    private fun initializeRecyclerView(
        musicTrackViewModel: MusicTrackViewModel
        , trackListBinding: TrackListBinding
    ) {
        recyclerView = trackListBinding.trackListRecyclerView

        val musicTrackAdapter =
            MusicTrackAdapterForRecyclerView(
                this,
                recyclerView
            )

        musicTrackViewModel.livePagedList.observe(this, Observer {
            musicTrackAdapter.submitList(it)
        })

        musicTrackViewModel.generalViewTypeMediator.observe(this, Observer {  })
    }

}