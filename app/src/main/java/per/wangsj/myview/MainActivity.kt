package per.wangsj.myview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
//import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import per.wsj.commonlib.net.HttpUtil
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        /**
         * get List请求
         */
//        btn1.setOnClickListener {
//            val map = HashMap<String, Any>()
//            map["type"] = "3"
//            NetManager.getInstance().get("getBaseArticle", map, ArticleList::class.java, object : HttpUtil.ListCallBack<ArticleList> {
//                override fun onStart(disposable: Disposable) {
//                    btn1.isClickable = false
//                    tvResult.text = "正在请求..."
//                }
//
//                override fun onSuccess(result: ArrayList<ArticleList>, code: String, msg: String) {
//                    tvResult.text = result.toString()
//                }
//
//                override fun onError(throwable: Throwable, string: String) {
//                    tvResult.text = string
//                }
//
//                override fun onComplete() {
//                    btn1.isClickable = true
//                }
//            })
//        }
//
//        btn2.setOnClickListener {
//            val map = HashMap<String, Any>()
//            map["id"] = "10"
//            NetManager.getInstance().get("readArticle/", map, String::class.java, object : HttpUtil.CallBack<String> {
//                override fun onSuccess(result: String?, code: String?, msg: String?) {
//                    Log.d("MainActivity", "result:$result")
//                    tvResult.text = result
//                }
//
//                override fun onStart(disposable: Disposable) {
//                    btn1.isClickable = false
//                    tvResult.text = "正在请求..."
//                }
//
//                override fun onError(throwable: Throwable, string: String) {
//                    tvResult.text = string
//                }
//
//                override fun onComplete() {
//                    btn1.isClickable = true
//                }
//            })
//        }

    }
}
