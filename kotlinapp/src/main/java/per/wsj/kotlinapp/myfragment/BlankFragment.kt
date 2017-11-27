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
        mData.add("Kotlin_简介")
        mData.add("委托")
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
