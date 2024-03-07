package com.verianggoro.news.activity

import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.verianggoro.news.R
import com.verianggoro.news.databinding.ActivityWebViewClientBinding


open class WebViewClientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewClientBinding
    private var urlLoadWeb : String? = null

    companion object {
        const val EVENT_URL = "event_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view_client)

        if (intent.extras != null) {
            urlLoadWeb = intent.getStringExtra(EVENT_URL)
            binding.webviewClientShow.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return false
                }
            }
            binding.webviewClientShow.settings.javaScriptEnabled = true
            binding.webviewClientShow.loadUrl(urlLoadWeb!!)
        }
    }
}