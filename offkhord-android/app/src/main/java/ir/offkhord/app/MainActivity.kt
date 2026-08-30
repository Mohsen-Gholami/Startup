package ir.offkhord.app

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var web: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(8,13,18)
        window.navigationBarColor = Color.rgb(8,13,18)
        web = WebView(this)
        web.setBackgroundColor(Color.rgb(8,13,18))
        setContentView(web)
        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true
        web.settings.loadsImagesAutomatically = true
        web.settings.mediaPlaybackRequiresUserGesture = true
        web.settings.setSupportZoom(false)
        web.webChromeClient = WebChromeClient()
        web.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val u = request.url
                return if (u.host == "offkhord.ir" || u.host == "www.offkhord.ir") false else {
                    startActivity(Intent(Intent.ACTION_VIEW, u)); true
                }
            }
        }
        val start = intent?.data?.toString()?.takeIf { it.startsWith("https://offkhord.ir") } ?: "https://offkhord.ir/"
        web.loadUrl(start)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() { if (web.canGoBack()) web.goBack() else finish() }
        })
    }
}
