package per.wsj.commonlib.net;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import per.wsj.commonlib.BuildConfig;
import per.wsj.commonlib.net.interceptor.LogInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
//                .proxy(Proxy.NO_PROXY)
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier());

        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new LogInterceptor());
        }
        if (interceptor != null) {
            builder.addInterceptor(interceptor);
        }
        // 证书不为空则使用证书，否则忽略证书
        if (cer != null && !cer.isEmpty()) {
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

    /**
     * get请求
     *
     * @param url
     * @param callBack
     * @param <T>
     */
    public <T> void get(String url, final HttpCallback<T> callBack) {
        get(url, null, callBack);
    }

    public <T> void get(String url, Map<String, Object> param, final HttpCallback<T> callBack) {
        get(url, null, param, callBack);
    }

    public <T> void get(String url, Map<String, String> headers, Map<String, Object> param, final HttpCallback<T> callBack) {
        if (callBack == null) {
            return;
        }
        if (headers == null) {
            headers = new HashMap<>();
        }
        if (param == null) {
            param = new HashMap<>();
        }
        Observable<ResponseBody> observable = apiService.executeGet(url, headers, param);
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    public void get(String url, final Observer<Response<Void>> callBack) {
        if (callBack == null) {
            return;
        }
        Observable<Response<Void>> observable = apiService.executeGet2(url);

        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * Post请求
     *
     * @param url
     * @param callBack
     * @param <T>
     */
    public <T> void post(String url, final HttpCallback<T> callBack) {
        post(url, null, callBack);
    }


    public <T> void post(String url, Object param, final HttpCallback<T> callBack) {
        post(url, null, param, callBack);
    }

    public <T> void post(String url, Map<String, String> headers, Object param, final HttpCallback<T> callBack) {
        if (callBack == null) {
            return;
        }
        if (headers == null) {
            headers = new HashMap<>();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(param));
        apiService.executePost(url, headers, requestBody)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    public void post(String url, final Observer<Response<Void>> callBack) {
        if (callBack == null) {
            return;
        }
        Object param = new Object();
        Map<String, String> headers = new HashMap<>();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(param));
        apiService.executePost2(url, headers, requestBody)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * Put请求
     *
     * @param url
     * @param callBack
     */
    public void put(String url, final Observer<Response<Void>> callBack) {
        put(url, null, callBack);
    }

    public void put(String url, Map<String, String> header, final Observer<Response<Void>> callBack) {
        if (callBack == null) {
            return;
        }
        if (header == null) {
            header = new HashMap<>();
        }

        Observable<Response<Void>> observable = apiService.executePut(url, header);
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    public void delete(String url, final Observer<Response<Void>> callBack) {
        if (callBack == null) {
            return;
        }
        Observable<Response<Void>> observable = apiService.executeDelete(url);

        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }
}
