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

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val musicTrackViewModel = ViewModelProviders.of(this)
            .get(MusicTrackViewModel::class.java)

        bindViewModel(musicTrackViewModel)
        initializeRecyclerView(musicTrackViewModel)
    }

    private fun initializeRecyclerView(musicTrackViewModel: MusicTrackViewModel) {
        recyclerView = findViewById(R.id.trackListRecyclerView)

        val musicTrackAdapter =
            MusicTrackAdapterForRecyclerView(
                this,
                recyclerView
            )

        musicTrackViewModel.livePagedList.observe(this, Observer {
            musicTrackAdapter.submitList(it)
        })
    }

    private fun bindViewModel(musicTrackViewModel: MusicTrackViewModel) {
        val mainActivityBinding: TrackListBinding = DataBindingUtil.setContentView(this,
            R.layout.track_list
        )
        mainActivityBinding.viewModel = musicTrackViewModel

    }
}