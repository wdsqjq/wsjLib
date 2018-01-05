package per.wsj.kotlinapp.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_video.*
import per.wsj.kotlinapp.R
import per.wsj.kotlinapp.adapter.VideoAdapter
import per.wsj.kotlinapp.net.ApiService
import per.wsj.kotlinapp.net.ArticleRequest
import per.wsj.kotlinapp.net.BaseVideoResponse
import per.wsj.kotlinapp.net.NetManager
import java.util.*


/**
 */
class VideoFragment : Fragment() {

    private var mParam1: String? = null
    private var mData = ArrayList<BaseVideoResponse.BaseVideo>()
    private var mAdapter:VideoAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =inflater?.inflate(R.layout.fragment_video,container,false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
//        var mAdapter = Article2Adapter(context, mData)
        mAdapter = VideoAdapter(context, mData)
//        rvVideo?.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rvVideo?.layoutManager=GridLayoutManager(context,2)
        rvVideo?.adapter=mAdapter
    }

    private fun initData() {

        NetManager(context).create(ApiService::class.java)
                .getBaseVideo(ArticleRequest(""))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BaseVideoResponse>{
                    override fun onNext(value: BaseVideoResponse?) {
                        if (value?.code == "200") {
                            val videos = value.detail
                            if (videos != null) {
                                mData.clear()
                                mData.addAll(videos)
                                mAdapter?.notifyDataSetChanged()
                            }
                        }
                    }

                    override fun onError(e: Throwable?) {
                        Log.d("VideoFragment",e.toString())
                    }

                    override fun onComplete() {
                        Log.d("VideoFragment","onComplete")
                    }

                    override fun onSubscribe(d: Disposable?) {
                        Log.d("VideoFragment","onSubscribe")
                    }

                })
    }

    companion object {
        private val ARG_PARAM1 = "param1"

        fun newInstance(param1: String): VideoFragment {
            val fragment = VideoFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            fragment.arguments = args
            return fragment
        }
    }

}
