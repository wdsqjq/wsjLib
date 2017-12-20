package per.wsj.commonlib.net;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shiju.wang on 2017/12/20.
 */

public abstract class NetLib {

    private Context mContext;

    private Retrofit retrofit;

    public NetLib(Context context,String base_url) {
        mContext=context;
        retrofit = new Retrofit.Builder().baseUrl(base_url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 动态代理创建 api
     *
     * @param api api 接口
     * @param <T> T
     * @return api 实体类
     */
    public <T> T create(Class<T> api) {
        return retrofit.create(api);
    }

    /*String url = Constants.baseUrl+"GetArticleInfo";
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
                        });*/

        /*Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);*/
}
