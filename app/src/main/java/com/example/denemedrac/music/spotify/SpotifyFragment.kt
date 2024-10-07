package com.example.denemedrac.music.spotify

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.denemedrac.R

class SpotifyFragment : Fragment() {

    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_spotify, container, false)
        webView = view.findViewById(R.id.spotify_webview)
        setupWebView()
        return view
    }

    private fun setupWebView() {
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://open.spotify.com") // Load the Spotify web page
    }
}
