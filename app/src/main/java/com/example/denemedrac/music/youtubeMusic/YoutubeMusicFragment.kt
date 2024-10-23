package com.example.denemedrac.music.youtubeMusic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.denemedrac.network.Video
import com.example.denemedrac.network.VideoAdapter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.example.denemedrac.databinding.FragmentYoutubeMusicBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback

class YoutubeMusicFragment : Fragment() {
    private var _binding: FragmentYoutubeMusicBinding? = null
    private val binding get() = _binding!!

    private val viewModel: YtMViewModel by viewModels()
    private lateinit var videoAdapter: VideoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentYoutubeMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupYouTubePlayer()
        setupObservers()
        setupClickListeners()

        viewModel.fetchMusicVideos()
    }

    private fun setupRecyclerView() {
        videoAdapter = VideoAdapter { video ->
            // Video seçildiğinde DetailFragment'a git
            navigateToVideoDetail(video)
        }

        binding.recyclerViewVideos.apply {
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupYouTubePlayer() {
        lifecycle.addObserver(binding.youtubePlayerView)

        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                viewModel.currentVideo.value?.let { video ->
                    youTubePlayer.loadVideo(video.id, 0f)
                }
            }

            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                viewModel.updateCurrentTime(second.toInt())
            }

            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
                viewModel.updateDuration(duration.toInt())
            }
        })
    }

    private fun setupObservers() {
        viewModel.videos.observe(viewLifecycleOwner) { videos ->
            videoAdapter.submitList(videos)
        }

        viewModel.currentVideo.observe(viewLifecycleOwner) { video ->
            binding.youtubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                    video?.let { youTubePlayer.loadVideo(it.id, 0f) }
                }
            })

        }

        viewModel.duration.observe(viewLifecycleOwner) { duration ->
            binding.textDuration.text = "Duration: $duration seconds"
        }

        viewModel.currentTime.observe(viewLifecycleOwner) { currentTime ->
            binding.textCurrentTime.text = "Current Time: $currentTime seconds"
        }
    }

    private fun setupClickListeners() {
        binding.youtubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                video?.let { youTubePlayer.loadVideo(it.id, 0f) }
            }
        })
    }

    private fun navigateToVideoDetail(video: Video) {
        val action = YoutubeMusicFragmentDirections
            .actionYoutubeMusicFragmentToVideoDetailFragment(video)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}