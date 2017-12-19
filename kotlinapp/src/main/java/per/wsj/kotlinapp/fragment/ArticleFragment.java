package per.wsj.kotlinapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import per.wsj.kotlinapp.Constants;
import per.wsj.kotlinapp.R;
import per.wsj.kotlinapp.adapter.ArticleAdapter;

public class ArticleFragment extends Fragment {

    private static final String FRAGMENT_POSITION = "fragment_position";

    private List<String> mData=new ArrayList<>();
    private Context mContext;

    public ArticleFragment() {
    }

    public static ArticleFragment newInstance(int columnCount) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_POSITION, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        initData();
    }

    private void initData() {
        mData.add("Kotlin_简介");
        mData.add("IntelliJ IDEA环境搭建");
        mData.add("Eclipse环境搭建");
        mData.add("使用命令行编译");
        mData.add("Android环境搭建");
        mData.add("基础语法");
        mData.add("基本数据类型");
        mData.add("条件控制");
        mData.add("循环控制");
        mData.add("类和对象");
        mData.add("继承");
        mData.add("接口");
        mData.add("扩展");
        mData.add("数据类与密封类");
        mData.add("泛型");
        mData.add("枚举类");
        mData.add("对象表达式与声明");
        mData.add("委托");

        String url = Constants.baseUrl+"GetArticleInfo";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = okHttpClient.newCall(request);

        Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                        Response response = call.execute();
                        observableEmitter.onNext(response.body().string());
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                Log.d("ArticleFragment", s);
                            }
                        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(new ArticleAdapter(mContext,mData));
        }
        return view;
    }

}
