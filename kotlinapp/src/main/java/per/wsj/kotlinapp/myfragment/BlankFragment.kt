package per.wsj.kotlinapp.myfragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_video.*
import per.wsj.kotlinapp.R
import per.wsj.kotlinapp.adapter.Article2Adapter
import java.util.*


/**
 */
class BlankFragment : Fragment() {

    private var mParam1: String? = null
    private val mData = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =inflater?.inflate(R.layout.fragment_video,container,false)
        initData()
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        var mAdapter: Article2Adapter = Article2Adapter(context, mData)
        rvVideo?.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rvVideo.adapter=mAdapter
    }

    private fun initData() {
        mData.add("kotlin变量与输出")
        mData.add("kotlin二进制基础")
        mData.add("kotlin变量和常亮及类型推断")
        mData.add("kotlin变量取值范围")
        mData.add("kotlin函数入门")
        mData.add("kotlin语言boolean")
        mData.add("kotlin命令行交互式终端")
        mData.add("kotlin函数加强")
        mData.add("kotlin函数作业详解")
        mData.add("kotlin字符串模板")
        mData.add("kotlin条件控制if和else")
        mData.add("kotlin字符串比较")
        mData.add("kotlin空值处理")
        mData.add("kotlin的when表达式")
        mData.add("kotlin的loop和Range")
        mData.add("kotlin的list和map入门")
        mData.add("kotlin的函数和函数表达式")
        mData.add("kotlin默认参数和具名参数")
        mData.add("kotlin字符串和数字之间的转换")
        mData.add("kotlin人机交互")
        mData.add("kotlin异常处理")
        mData.add("kotlin递归")
        mData.add("kotlin尾递归优化")
        mData.add("kotlin idea使用入门")
        mData.add("kotlin面向对象入门")
        mData.add("kotlin静态属性和动态行为")
        mData.add("kotlin面向对象")
        mData.add("kotlin面向对象实战-洗衣机")
        mData.add("kotlin面向对象实战-洗衣机升级")
        mData.add("kotlin面向对象实战-封装")
        mData.add("kotlin面向对象-继承(open和override)")
        mData.add("kotlin抽象类和继承")
        mData.add("kotlin面向对象-多态")
        mData.add("kotlin面向对象-抽象类和接口")
        mData.add("kotlin面向对象-代理和委托")
        mData.add("kotlin面向对象-单利模式")
        mData.add("kotlin面向对象-枚举")
        mData.add("kotlin面向对象-印章类")


    }

    companion object {
        private val ARG_PARAM1 = "param1"

        fun newInstance(param1: String): BlankFragment {
            val fragment = BlankFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            fragment.arguments = args
            return fragment
        }
    }

}
