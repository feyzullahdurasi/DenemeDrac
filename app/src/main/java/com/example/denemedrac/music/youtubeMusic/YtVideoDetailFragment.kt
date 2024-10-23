package com.example.denemedrac.music.youtubeMusic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.example.denemedrac.databinding.FragmentYtVideoDetailBinding

class YtVideoDetailFragment : Fragment() {
    private var _binding: FragmentYtVideoDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<YtVideoDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentYtVideoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupYouTubePlayer()
        setupViews()
    }

    private fun setupYouTubePlayer() {
        lifecycle.addObserver(binding.youtubePlayerView)

        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(args.video.id, 0f)
            }
        })
    }

    private fun setupViews() {
        binding.textTitle.text = args.video.title
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}