package com.example.denemedrac.music.youtubeMusic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.denemedrac.R

class YoutubeMusicFragment : Fragment() {

    private lateinit var viewModel: YtMViewModel
    private lateinit var artistImage: ImageView
    private lateinit var songTitle: TextView
    private lateinit var artistName: TextView
    private lateinit var songProgress: SeekBar

    // Buttons
    private lateinit var btnPrevious: ImageButton
    private lateinit var btnPlayPause: ImageButton
    private lateinit var btnNext: ImageButton
    private lateinit var btnShuffle: ImageButton
    private lateinit var btnRepeat: ImageButton

    private var isPlaying = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_youtube_music, container, false)

        // Initialize views
        initializeViews(view)

        // Setup ViewModel
        viewModel = ViewModelProvider(this).get(YtMViewModel::class.java)

        // Setup Listeners
        setupListeners()

        // Observe ViewModel data
        observeViewModel()

        return view
    }

    private fun initializeViews(view: View) {
        artistImage = view.findViewById(R.id.artist_image)
        songTitle = view.findViewById(R.id.song_title)
        artistName = view.findViewById(R.id.artist_name)
        songProgress = view.findViewById(R.id.song_progress)

        btnPrevious = view.findViewById(R.id.btn_previous)
        btnPlayPause = view.findViewById(R.id.btn_play_pause)
        btnNext = view.findViewById(R.id.btn_next)
        btnShuffle = view.findViewById(R.id.btn_shuffle)
        btnRepeat = view.findViewById(R.id.btn_repeat)
    }

    private fun setupListeners() {
        btnPlayPause.setOnClickListener {
            togglePlayPause()
        }

        btnNext.setOnClickListener {
            // Next track logic
        }

        btnPrevious.setOnClickListener {
            // Previous track logic
        }

        btnShuffle.setOnClickListener {
            // Shuffle logic
        }

        btnRepeat.setOnClickListener {
            // Repeat mode logic
        }
    }

    private fun togglePlayPause() {
        isPlaying = !isPlaying
        btnPlayPause.setImageResource(
            if (isPlaying) R.drawable.arrow_up_white
            else R.drawable.arrow_back_white
        )
        // Add actual play/pause implementation
    }

    private fun observeViewModel() {
        viewModel.channel.observe(viewLifecycleOwner) { channelModel ->
            if (channelModel != null && channelModel.items.isNotEmpty()) {
                val channel = channelModel.items[0]
                songTitle.text = channel.snippet.title
                artistName.text = channel.snippet.channelTitle

                // Load image using a library like Glide or Picasso
                // Glide.with(this).load(channel.snippet.thumbnails.high.url).into(artistImage)
            }
        }
    }
}