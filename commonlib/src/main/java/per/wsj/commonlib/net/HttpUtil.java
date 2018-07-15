package per.wsj.commonlib.net;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import per.wsj.commonlib.R;
import per.wsj.commonlib.utils.ValueUtil;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 请继承该类，重写参数
 * Created by shiju.wang on 2018/1/29.
 */

public class HttpUtil {
    protected static final int DEFAULT_TIMEOUT = 15;

    protected ApiService apiService;

    protected OkHttpClient okHttpClient;

    protected Context mContext;

    protected HttpUtil(Context context, String baseUrl, String cer,Interceptor interceptor) {
        mContext = context;

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                                .addNetworkInterceptor(
//                                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
//                                .cookieJar(new NovateCookieManger(context))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier());
        if(interceptor!=null){
            builder.addInterceptor(interceptor);
        }
        // 证书不为空则使用证书，否则忽略证书
        if (ValueUtil.isStrNotEmpty(cer)) {
            builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory(mContext, ""));        // https证书
        } else {
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

    public <T> void get(String url, Class clazz, CallBack<T> callBack) {
        get(url, null, clazz, callBack);
    }

    /**
     * GET请求（对象）
     *
     * @param url
     * @param param
     * @param callBack
     * @param <T>
     */
    public <T> void get(String url, Map<String, Object> param, final Class clazz, final CallBack<T> callBack) {
        getRequest(url,param,clazz,callBack);
    }

    public <T> void get(String url, Class clazz, ListCallBack<T> callBack) {
        get(url, null, clazz, callBack);
    }

    /**
     * GET请求 (List)
     *
     * @param url
     * @param param
     * @param callBack
     * @param <T>
     */
    public <T> void get(String url, Map<String, Object> param, final Class clazz, final ListCallBack<T> callBack) {
        getRequest(url,param,clazz,callBack);
    }

    public <T> void getRequest(String url, Map<String, Object> param, final Class clazz, final BaseCallBack<T> callBack) {
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
                .subscribe(new MyObserver<T>(clazz, callBack));
    }

    public <T> void post(String url, Class clazz, ListCallBack<T> callBack) {
        post(url,null,clazz,callBack);
    }

    /**
     * POST请求(对象)
     *
     * @param url
     * @param param
     * @param callBack
     * @param <T>
     */
    public <T> void post(String url, Object param, final Class clazz, final ListCallBack<T> callBack) {
        postRequest(url,param,clazz,callBack);
    }

    public <T> void post(String url, Class clazz, CallBack<T> callBack) {
        post(url,null,clazz,callBack);
    }

    /**
     * POST请求（List）
     *
     * @param url
     * @param param
     * @param callBack
     * @param <T>
     */
    public <T> void post(String url, Object param, final Class clazz, final CallBack<T> callBack) {
        postRequest(url,param,clazz,callBack);
    }

    public <T> void postRequest(String url, Object param, final Class clazz, final BaseCallBack<T> callBack) {
        if (callBack == null) {
            return;
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(param));
        apiService.executePost(url, requestBody)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<T>(clazz, callBack));
    }

    /**
     * 公用Observer
     *
     * @param <T>
     */
    class MyObserver<T> implements Observer<ResponseBody> {
        BaseCallBack<T> callBack;
        Class clazz;

        public MyObserver(Class clazz, BaseCallBack<T> callBack) {
            this.clazz = clazz;
            this.callBack = callBack;
        }

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
                String detail = jsonObject.getString("detail");
                if (callBack instanceof ListCallBack) {
                    ArrayList<T> result = (ArrayList<T>) JSON.parseArray(detail, clazz);
                    ((ListCallBack) callBack).onSuccess(result, code, msg);
                } else {
                    ((CallBack) callBack).onSuccess((T) JSON.parseObject(detail, clazz), code, msg);
                }

            } catch (Exception e) {
                onError(e);
            }
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof SocketTimeoutException) {
                callBack.onError(e, mContext.getString(R.string.net_error_timeout));
            } else if (e instanceof HttpException
                    || e instanceof ConnectException
                    || e instanceof SSLHandshakeException
                    || e instanceof UnknownHostException) {
                callBack.onError(e, mContext.getString(R.string.net_error_nonet));
            } else {
                callBack.onError(e, e.toString());
            }
            callBack.onComplete();
        }

        @Override
        public void onComplete() {
            callBack.onComplete();
        }
    }

    /****************************************************************************/

    public interface CallBack<T> extends BaseCallBack<T> {

        /**
         * 请求成功
         *
         * @param result 泛型
         * @param code
         * @param msg
         */
        void onSuccess(T result, String code, String msg);
    }

    public interface ListCallBack<T> extends BaseCallBack<T> {

        /**
         * 请求成功
         *
         * @param result 泛型
         * @param code
         * @param msg
         */
        void onSuccess(ArrayList<T> result, String code, String msg);
    }
}
