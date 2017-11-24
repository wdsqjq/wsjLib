package per.wsj.kotlinapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {

    private var rvArticle:RecyclerView?=null
    private val mData = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initData()

        var mAdapter:ArticleAdapter= ArticleAdapter(this,mData)
        rvArticle?.setLayoutManager(LinearLayoutManager(this))
        rvArticle?.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        rvArticle?.setAdapter(mAdapter)
    }

    private fun initView() {
        rvArticle = findViewById(R.id.rvArticle) as RecyclerView?
    }

    private fun initData() {
        mData.add("Kotlin_简介")
        mData.add("IntelliJ IDEA环境搭建")
        mData.add("Eclipse环境搭建")
        mData.add("使用命令行编译")
        mData.add("Android环境搭建")
        mData.add("基础语法")
        mData.add("基本数据类型")
        mData.add("条件控制")
        mData.add("循环控制")
        mData.add("类和对象")
        mData.add("继承")
        mData.add("接口")
        mData.add("扩展")
        mData.add("数据类与密封类")
        mData.add("泛型")
        mData.add("枚举类")
        mData.add("对象表达式与声明")
        mData.add("委托")
    }
}
