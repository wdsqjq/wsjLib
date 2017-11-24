package per.wsj.kotlinapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView

class ArticleActivity : AppCompatActivity() {

    private var webView: WebView?=null
    private var position:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        setTitle(intent.getStringExtra("title"))
        position=intent.getIntExtra("position",0)

        initView()
    }

    private fun initView() {
        webView= findViewById(R.id.webView) as WebView?
        webView?.loadUrl("file:///android_asset/${position}.html")

        /*webView?.setWebViewClient(object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                super.onPageStarted(view, url, favicon)
//                Log.d("setWebViewClient", url)
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
//                Log.d("Article","onPageFinished")
//                Log.d("setWebViewClient", "onPageFinished"+url)
            }

            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                super.onReceivedError(view, request, error)
//                Log.d("setWebViewClient", "onReceivedError")
            }
        })*/
    }
}
