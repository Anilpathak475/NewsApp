package com.anil.newapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.anil.newapp.R
import kotlinx.android.synthetic.main.fragment_news_detail.*


/**
 * A simple [Fragment] subclass.
 */
class NewsDetailFragment : Fragment() {

    private val articleUrl by lazy {
        arguments?.let { NewsDetailFragmentArgs.fromBundle(it).articleId }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_news_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webViewNewsDetails.apply {
            settings.javaScriptEnabled = true
        }
        webViewNewsDetails.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        articleUrl?.let { webViewNewsDetails.loadUrl(it) }
    }
}
