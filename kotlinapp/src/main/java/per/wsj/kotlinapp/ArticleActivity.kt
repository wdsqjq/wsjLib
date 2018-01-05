package per.wsj.kotlinapp

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : AppCompatActivity() {

    private var url:String=""
//    private var url="file:///android_asset/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        setTitle(intent.getStringExtra("title"))
        url=intent.getStringExtra("url")

        initView()
    }

    private fun initView() {

        webView.loadUrl("${Constants.BASE_URL}article/${url}")

        webView?.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Log.d("",request?.url.toString())
                    Log.d("",request?.method.toString())
                    Log.d("",request?.requestHeaders.toString())
                }
                return super.shouldOverrideUrlLoading(view, request)
            }

        })
    }
}
