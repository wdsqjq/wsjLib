package per.wsj.kotlinapp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import per.wsj.kotlinapp.R

/**
 */
class SeniorFragment2 : Fragment() {

    private var mParam1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_senior, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(e: ObservableEmitter<String>?) {
                e?.onNext("aaa")
            }

        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String> {
                    override fun onNext(value: String?) {
                        Log.d("VideoFragment",value)
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
                });
    }

    companion object {
        private val ARG_PARAM1 = "param1"

        fun newInstance(param1: String): SeniorFragment2 {
            val fragment = SeniorFragment2()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            fragment.arguments = args
            return fragment
        }
    }
}
