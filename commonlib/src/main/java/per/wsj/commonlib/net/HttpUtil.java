package per.wsj.commonlib.net;

import android.content.Context;

import com.google.gson.Gson;
import com.wtk.corelib.BaseApplication;
import com.wtk.corelib.R;
import com.wtk.corelib.utils.ValueUtil;

import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 请继承该类，重写参数
 * Created by shiju.wang on 2018/1/29.
 */

public class HttpUtil {
    protected static final int DEFAULT_TIMEOUT = 20;

    protected ApiService apiService;

    protected OkHttpClient okHttpClient;

    protected Context mContext;

//    protected String baseUrl = "http://wangsj.cn:8000/";

    /*private static final HttpUtil ourInstance = new HttpUtil();

    public static HttpUtil getInstance() {
        return ourInstance;
    }*/

    protected HttpUtil(String baseUrl,String cer) {
        mContext = BaseApplication.getContext();

        OkHttpClient.Builder builder=new OkHttpClient.Builder()
                //                .addNetworkInterceptor(
                //                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                //                .cookieJar(new NovateCookieManger(context))
                //                .addInterceptor(new BaseInterceptor(mContext))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier());
        // 证书不为空则使用证书，否则忽略证书
        if(ValueUtil.isStrNotEmpty(cer)){
            builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory(mContext,"")) ;        // https证书
        }else{
            builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactoryIgnore());
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
     * GET请求
     *
     * @param callBack
     * @param <T>
     */
    public <T> void get(Map<String, Object> param,Class clazz, CallBack<T> callBack) {
        get("req.action", param, clazz, callBack);
    }

    /**
     * GET请求
     *
     * @param url
     * @param callBack
     * @param <T>
     */
    public <T> void get(String url, Class clazz, CallBack<T> callBack) {
        get(url, null, clazz, callBack);
    }

    /**
     * GET请求
     *
     * @param url
     * @param param
     * @param callBack
     * @param <T>
     */
    public <T> void get(String url, Map<String, Object> param, final Class clazz, final CallBack<T> callBack) {
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
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callBack.onStart(d);
                    }

                    @Override
                    public void onNext(ResponseBody value) {
                        try {
                            String responseBody = value.string();
                            JSONObject jsonObject = new JSONObject(responseBody);
                            String code = jsonObject.getString("code");
                            String msg = jsonObject.getString("msg");
                            callBack.onSuccess((T) new Gson().fromJson(responseBody, clazz), code, msg);
                        } catch (Exception e) {
                            callBack.onError(e,e.toString());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(e instanceof SocketTimeoutException){
                            callBack.onError(e,mContext.getString(R.string.net_error_timeout));
                        }else if(e instanceof HttpException){
                            callBack.onError(e,mContext.getString(R.string.net_error_nonet));
                        }else {
                            callBack.onError(e,e.toString());
                        }
                        callBack.onComplete();
                    }

                    @Override
                    public void onComplete() {
                        callBack.onComplete();
                    }
                });
    }

    /**
     * POST请求
     *
     * @param param
     * @param callBack
     * @param <T>
     */
    public <T> void post(Object param, final Class clazz, final CallBack<T> callBack) {
        post("req.action", param, clazz, callBack);
    }

    /**
     * POST请求
     *
     * @param url
     * @param param
     * @param callBack
     * @param <T>
     */
    public <T> void post(String url, Object param, final Class clazz, final CallBack<T> callBack) {
        if (callBack == null) {
            return;
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(param));
        apiService.executePost(url, requestBody)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callBack.onStart(d);
                    }

                    @Override
                    public void onNext(ResponseBody value) {
                        try {
                            String responseBody = value.string();
                            JSONObject jsonObject = new JSONObject(responseBody);
                            String code = jsonObject.getString("code");
                            // 两个baseurl返回的msg参数不一样，先不解析
//                            String msg = jsonObject.getString("msg");
                            callBack.onSuccess((T) new Gson().fromJson(responseBody, clazz), code, "");
                        } catch (Exception e) {
                            callBack.onError(e,e.toString());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(e instanceof SocketTimeoutException){
                            callBack.onError(e,mContext.getString(R.string.net_error_timeout));
                        }else if(e instanceof HttpException || e instanceof ConnectException){
                            callBack.onError(e,mContext.getString(R.string.net_error_nonet));
                        }else {
                            callBack.onError(e,e.toString());
                        }
                        callBack.onComplete();
                    }

                    @Override
                    public void onComplete() {
                        callBack.onComplete();
                    }

                });
    }

    public interface CallBack<T> {

        /**
         * 开始请求
         *
         * @param disposable
         */
        public void onStart(Disposable disposable);

        /**
         * 请求成功
         *
         * @param result 泛型
         * @param code
         * @param msg
         */
        public void onSuccess(T result, String code, String msg);

        /**
         * 请求失败
         *
         * @param throwable
         */
        public void onError(Throwable throwable, String msg);

        /**
         * 请求结束,不管是成功或失败都会走这里
         */
        public void onComplete();
    }
}
