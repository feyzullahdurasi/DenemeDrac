package com.example.denemedrac.music.spotify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.denemedrac.R

class SpotifyFragment : Fragment() {
    private lateinit var viewModel: SpotifyViewModel
    private lateinit var trackTitle: TextView
    private lateinit var artistName: TextView
    private lateinit var albumArt: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_spotify, container, false)

        // Initialize views
        trackTitle = view.findViewById(R.id.track_title)
        artistName = view.findViewById(R.id.artist_name)
        albumArt = view.findViewById(R.id.album_art)

        // Setup ViewModel
        viewModel = ViewModelProvider(this).get(SpotifyViewModel::class.java)

        // Observe ViewModel
        observeViewModel()

        return view
    }

    private fun observeViewModel() {
        viewModel.recentTracks.observe(viewLifecycleOwner) { tracks ->
            if (tracks.isNotEmpty()) {
                val track = tracks[0].track
                trackTitle.text = track.name
                artistName.text = track.artists.firstOrNull()?.name ?: "Unknown Artist"

                // Load album art
                track.album.images.firstOrNull()?.let { image ->
                    Glide.with(requireContext())
                        .load(image.url)
                        .placeholder(R.drawable.arrow_back_white)
                        .into(albumArt)
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // Handle loading state if needed
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            // Handle error state
            trackTitle.text = "Error: $error"
        }
    }
}