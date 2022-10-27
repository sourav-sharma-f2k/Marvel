package com.sourav1.marvel.Presentation.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.sourav1.marvel.R
import com.sourav1.marvel.Util.Constants.Companion.convertHttpToHttps

class ComicPurchaseScreen() : Fragment(R.layout.fragment_comic_purchse_screen) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar: ProgressBar = view.findViewById(R.id.webViewProgressBar)
        progressBar.visibility = View.VISIBLE
        val url = requireArguments().getString("PURCHASE_URL")

        val webView: WebView = view.findViewById(R.id.webView)
        webView.progress
        val correctUrl = convertHttpToHttps(url!!)

        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(correctUrl)
            progressBar.visibility = View.GONE
        }
    }
}