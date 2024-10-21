package com.example.denemedrac.music.youtubeMusic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.denemedrac.R


class YoutubeMusicFragment : Fragment() {

    private lateinit var ytMViewModel: YtMViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ytMViewModel =
            ViewModelProvider(this).get(YtMViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_youtube_music, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        ytMViewModel.channel.observe(viewLifecycleOwner, Observer {
            if (it != null && it.items.isNotEmpty()){
                it.items.forEach {  channel ->
                    textView.text = channel.snippet.title
                }
            }
        })
        return root
    }
}