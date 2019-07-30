package per.wsj.commonlib.net;

import android.content.Context;
import com.google.gson.Gson;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.*;
import per.wsj.commonlib.utils.ValueUtil;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 请继承该类，重写参数
 * Created by shiju.wang on 2018/1/29.
 */

public class HttpUtil {
    private static final int DEFAULT_TIMEOUT = 15;

    protected ApiService apiService;

    protected String mBaseUrl;

    protected OkHttpClient okHttpClient;

    protected Context mContext;

    protected OkHttpClient.Builder builder;

    protected HttpUtil(Context context, String baseUrl, String cer, Interceptor interceptor) {
        mContext = context;
        mBaseUrl = baseUrl;

        builder = new OkHttpClient.Builder()
//                                .addNetworkInterceptor(
//                                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
//                                .cookieJar(new NovateCookieManger(context))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier());

        if (interceptor != null) {
            builder.addInterceptor(interceptor);
        }
        // 证书不为空则使用证书，否则忽略证书
        if (ValueUtil.isStrNotEmpty(cer)) {
            builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory(mContext, cer), SSLSocketClient.getX509TrustManager(context, cer)); // https证书
        } else {
            builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactoryIgnore(), SSLSocketClient.getX509TrustManager());
        }

        okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public <T> void get(String url,  final HttpCallback<T> callBack) {
        get(url,null,callBack);
    }

    public <T> void get(String url, Map<String, Object> param, final HttpCallback<T> callBack) {
        if (callBack == null) {
            return;
        }
        Observable<ResponseBody> observable;
        if (param == null) {
            observable = apiService.executeGet(url);
        } else {
            observable = apiService.executeGet(url, param);
        }
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }


    public <T> void post(String url,  final HttpCallback<T> callBack) {
        post(url,null,callBack);
    }

    public <T> void post(String url, Object param, final HttpCallback<T> callBack) {
        if (callBack == null) {
            return;
        }
        // Map<String, String> headers = new HashMap<>();
        // headers.put("token","123456");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(param));
        apiService.executePost(url, requestBody)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }
}
