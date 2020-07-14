package com.chardskarth.itunestrack

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.chardskarth.itunestrack.databinding.TrackListBinding
import com.chardskarth.itunestrack.track.MusicTrackAdapter
import com.chardskarth.itunestrack.track.MusicTrackViewModel

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
        recyclerView.setHasFixedSize(true)


        val musicTrackAdapter = MusicTrackAdapter(this)

        musicTrackViewModel.livePagedList.observe(this, Observer {
            musicTrackAdapter.submitList(it)
        })

        recyclerView.adapter = musicTrackAdapter
    }

    private fun bindViewModel(musicTrackViewModel: MusicTrackViewModel) {
        val mainActivityBinding: TrackListBinding = DataBindingUtil.setContentView(this, R.layout.track_list)
        mainActivityBinding.viewModel = musicTrackViewModel

    }
}