package com.technorapper.root.ui.list.fragment

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels

import com.technorapper.root.R
import com.technorapper.root.base.BaseFragment
import com.technorapper.root.databinding.FragmentBlankThreeBinding
import com.technorapper.root.ui.list.ListActivityViewModel

class HelpFragment : BaseFragment() {


    private val viewModel by viewModels<ListActivityViewModel>()
    lateinit var binding: FragmentBlankThreeBinding


    val MAX_PROGRESS = 100
    private lateinit var pageUrl: String
    override fun attachViewModel() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_blank_three,
            container,
            false
        )

        val pageUrl = "https://github.com/HaneetGH/WeatherWithMvi/blob/master/README.md"
        initWebView()
        setWebClient()
        handlePullToRefresh()
        loadUrl(pageUrl)



        return binding.root
    }


    private fun handlePullToRefresh() {
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.settings.loadWithOverviewMode = true
        binding.webview.settings.useWideViewPort = true
        binding.webview.settings.domStorageEnabled = true
        binding.webview.webViewClient = object : WebViewClient() {
            override
            fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed()
            }
        }
    }

    private fun setWebClient() {
        binding.webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(
                view: WebView,
                newProgress: Int
            ) {
                super.onProgressChanged(view, newProgress)
                binding.progressCircular.progress = newProgress
                if (newProgress < MAX_PROGRESS && binding.progressCircular.visibility == View.GONE) {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                if (newProgress == MAX_PROGRESS) {
                    binding.progressCircular.visibility = View.GONE
                }
            }
        }
    }


    private fun loadUrl(pageUrl: String) {
        binding.webview.loadUrl(pageUrl)
    }
}





