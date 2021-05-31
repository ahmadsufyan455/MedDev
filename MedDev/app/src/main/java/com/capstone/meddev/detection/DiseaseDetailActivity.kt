package com.capstone.meddev.detection

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.capstone.meddev.R
import com.capstone.meddev.detection.DiseaseDetectionActivity.Companion.urlCataract
import com.capstone.meddev.detection.DiseaseDetectionActivity.Companion.urlGlaucoma


class DiseaseDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CATARACT = "extra_cataract"
        const val EXTRA_GLAUCOMA = "extra_glaucoma"
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disease_detail)

        supportActionBar?.title = getString(R.string.informasi_penyakit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val webView = findViewById<WebView>(R.id.myWebView)

        val cataract = intent.getStringExtra(EXTRA_CATARACT)
        val glaucoma = intent.getStringExtra(EXTRA_GLAUCOMA)

        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.webViewClient = WebViewClient()

        if (cataract == urlCataract) {
            webView.loadUrl(cataract)
        } else if (glaucoma == urlGlaucoma) {
            webView.loadUrl(glaucoma)
        }

        // enable responsive layout
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}