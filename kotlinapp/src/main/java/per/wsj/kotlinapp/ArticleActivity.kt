package per.wsj.kotlinapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : AppCompatActivity() {

    private var position:Int=0
//    private var url="file:///android_asset/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        setTitle(intent.getStringExtra("title"))
        position=intent.getIntExtra("position",0)

        initView()
    }

    private fun initView() {

        webView.loadUrl("${Constants.baseUrl}${position}.html")

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
