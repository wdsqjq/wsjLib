package per.wsj.kotlinapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import per.wsj.commonlib.pulltorefreshlib.PullToRefreshLayout;
import per.wsj.commonlib.pulltorefreshlib.PullToRefreshRecyclerView;
import per.wsj.kotlinapp.R;
import per.wsj.kotlinapp.adapter.ArticleAdapter;
import per.wsj.kotlinapp.net.ApiService;
import per.wsj.kotlinapp.net.ArticleRequest;
import per.wsj.kotlinapp.net.ArticleResponse;
import per.wsj.kotlinapp.net.NetManager;

public class ArticleFragment extends Fragment implements PullToRefreshLayout.OnPullListener {

    private static final String FRAGMENT_POSITION = "fragment_position";

    private List<ArticleResponse.DetailBean> mData = new ArrayList<>();
    private Context mContext;
    private ArticleAdapter mAdapter;
    private PullToRefreshLayout refreshLayout;

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
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initView(View view) {
        refreshLayout=view.findViewById(R.id.refreshLayout);
        refreshLayout.setPullUpEnable(false);
        refreshLayout.setOnPullListener(this);
        PullToRefreshRecyclerView recyclerView=view.findViewById(R.id.list);
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mAdapter=new ArticleAdapter(mContext, mData);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        initData();
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

    }

    private void initData() {
        new NetManager(mContext).create(ApiService.class).getArticle(new ArticleRequest("a"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleResponse>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(ArticleResponse response) {
                        if(response.getCode().equals("200")){
                            refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                            List<ArticleResponse.DetailBean> articles=response.getDetail();
                            mData.clear();
                            mData.addAll(articles);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
