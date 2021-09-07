package per.wsj.commonlib.net.deprecate;

//import android.content.Context;
//
//import com.alibaba.fastjson.JSON;
//import com.google.gson.Gson;
//
//import org.json.JSONObject;
//
//import java.net.ConnectException;
//import java.net.SocketTimeoutException;
//import java.net.UnknownHostException;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//import javax.net.ssl.SSLHandshakeException;
//
//import io.reactivex.Observable;
//import io.reactivex.Observer;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.schedulers.Schedulers;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.RequestBody;
//import okhttp3.ResponseBody;
//import per.wsj.commonlib.R;
//import per.wsj.commonlib.utils.ValueUtil;
//import retrofit2.HttpException;
//import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;
//
///**
// * 请继承该类，重写参数
// * Created by shiju.wang on 2018/1/29.
// */
//
//public class HttpUtil0 {
//    protected static final int DEFAULT_TIMEOUT = 15;
//
//    protected ApiService apiService;
//
//    protected OkHttpClient okHttpClient;
//
//    protected Context mContext;
//
//    /*private static final HttpUtil ourInstance = new HttpUtil();
//
//    public static HttpUtil getInstance() {
//        return ourInstance;
//    }*/
//
//    protected HttpUtil0(Context context, String baseUrl, String cer) {
//        mContext = context;
//
//        OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                //                .addNetworkInterceptor(
//                //                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
//                //                .cookieJar(new NovateCookieManger(context))
//                //                .addInterceptor(new BaseInterceptor(mContext))
//                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .retryOnConnectionFailure(true)
//                .hostnameVerifier(SSLSocketClient.getHostnameVerifier());
//        // 证书不为空则使用证书，否则忽略证书
//        if (ValueUtil.isStrNotEmpty(cer)) {
//            builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory(mContext, ""));        // https证书
//        } else {
//            builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactoryIgnore());
//        }
//
//        okHttpClient = builder.build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .baseUrl(baseUrl)
//                .build();
//        apiService = retrofit.create(ApiService.class);
//    }
//
//    /**
//     * GET请求（弃用了）
//     *
//     * @param url
//     * @param param
//     * @param callBack
//     * @param <T>
//     */
//    public <T> void get(String url, Map<String, Object> param, final Class clazz, final CallBack<T> callBack) {
//        if (callBack == null) {
//            return;
//        }
//        Observable<ResponseBody> observable;
//        if (param == null) {
//            observable = apiService.executeGet(url);
//        } else {
//            observable = apiService.executeGet(url, param);
//        }
//        observable.subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<ResponseBody>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        callBack.onStart(d);
//                    }
//
//                    @Override
//                    public void onNext(ResponseBody value) {
//                        try {
//                            String responseBody = value.string();
//                            JSONObject jsonObject = new JSONObject(responseBody);
//                            String code = jsonObject.getString("code");
//                            String msg = jsonObject.getString("msg");
//                            callBack.onSuccess((T) new Gson().fromJson(responseBody, clazz), code, msg);
//                        } catch (Exception e) {
//                            onError(e);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        if (e instanceof SocketTimeoutException) {
//                            callBack.onError(e, mContext.getString(R.string.net_error_timeout));
//                        } else if (e instanceof HttpException
//                                || e instanceof ConnectException
//                                || e instanceof SSLHandshakeException
//                                || e instanceof UnknownHostException) {
//                            callBack.onError(e, mContext.getString(R.string.net_error_nonet));
//                        } else {
//                            callBack.onError(e, e.toString());
//                        }
//                        callBack.onComplete();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        callBack.onComplete();
//                    }
//                });
//    }
//
//    /**
//     * POST请求（弃用了）
//     *
//     * @param url
//     * @param param
//     * @param callBack
//     * @param <T>
//     */
//    public <T> void post(String url, Object param, final Class clazz, final CallBack<T> callBack) {
//        if (callBack == null) {
//            return;
//        }
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(param));
//        apiService.executePost(url, requestBody)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<ResponseBody>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        callBack.onStart(d);
//                    }
//
//                    @Override
//                    public void onNext(ResponseBody value) {
//                        try {
//                            String responseBody = value.string();
//                            JSONObject jsonObject = new JSONObject(responseBody);
//                            String code = jsonObject.getString("code");
//                            // 两个baseurl返回的msg参数不一样，先不解析
////                            String msg = jsonObject.getString("msg");
//                            callBack.onSuccess((T) new Gson().fromJson(responseBody, clazz), code, "");
//                        } catch (Exception e) {
//                            onError(e);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        if (e instanceof SocketTimeoutException) {
//                            callBack.onError(e, mContext.getString(R.string.net_error_timeout));
//                        } else if (e instanceof HttpException
//                                || e instanceof ConnectException
//                                || e instanceof SSLHandshakeException
//                                || e instanceof UnknownHostException) {
//                            callBack.onError(e, mContext.getString(R.string.net_error_nonet));
//                        } else {
//                            callBack.onError(e, e.toString());
//                        }
//                        callBack.onComplete();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        callBack.onComplete();
//                    }
//
//                });
//    }
//
//
//    /***********************************新的*****************************************/
//
//    /**
//     * GET请求 (List)
//     *
//     * @param url
//     * @param param
//     * @param callBack
//     * @param <T>
//     */
//    public <T> void get(String url, Map<String, Object> param, final Class clazz, final ListCallBack<T> callBack) {
//        if (callBack == null) {
//            return;
//        }
//        Observable<ResponseBody> observable;
//        if (param == null) {
//            observable = apiService.executeGet(url);
//        } else {
//            observable = apiService.executeGet(url, param);
//        }
//        observable.subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new MyObserver<T>(clazz, callBack));
////                .subscribe(new Observer<ResponseBody>() {
////                    @Override
////                    public void onSubscribe(Disposable d) {
////                        callBack.onStart(d);
////                    }
////
////                    @Override
////                    public void onNext(ResponseBody value) {
////                        try {
////                            String responseBody = value.string();
////                            JSONObject jsonObject = new JSONObject(responseBody);
////                            String code = jsonObject.getString("code");
////                            String msg = jsonObject.getString("msg");
////                            String detail =jsonObject.getString("detail");
////                            List<T> result=JSON.parseArray(detail,clazz);
////                            callBack.onSuccess(result, code, msg);
////                        } catch (Exception e) {
////                            onError(e);
////                        }
////                    }
////
////                    @Override
////                    public void onError(Throwable e) {
////                        callBack.onError(e, mContext.getString(R.string.net_error_timeout));
////                        callBack.onComplete();
////                    }
////
////                    @Override
////                    public void onComplete() {
////                        callBack.onComplete();
////                    }
////                });
//    }
//
//    /**
//     * POST请求
//     *
//     * @param url
//     * @param param
//     * @param callBack
//     * @param <T>
//     */
//    public <T> void post(String url, Object param, final Class clazz, final ListCallBack<T> callBack) {
//        if (callBack == null) {
//            return;
//        }
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(param));
//        apiService.executePost(url, requestBody)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(new MyObserver<T>(callBack));
//                .subscribe(new Observer<ResponseBody>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        callBack.onStart(d);
//                    }
//
//                    @Override
//                    public void onNext(ResponseBody value) {
//                        try {
//                            String responseBody = value.string();
//                            JSONObject jsonObject = new JSONObject(responseBody);
//                            String code = jsonObject.getString("code");
//                            String msg = jsonObject.getString("msg");
//                            String detail = jsonObject.getString("detail");
//                            List<T> result = JSON.parseArray(detail, clazz);
//                            callBack.onSuccess(result, code, msg);
//                        } catch (Exception e) {
//                            onError(e);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        if (e instanceof SocketTimeoutException) {
//                            callBack.onError(e, mContext.getString(R.string.net_error_timeout));
//                        } else if (e instanceof HttpException
//                                || e instanceof ConnectException
//                                || e instanceof SSLHandshakeException
//                                || e instanceof UnknownHostException) {
//                            callBack.onError(e, mContext.getString(R.string.net_error_nonet));
//                        } else {
//                            callBack.onError(e, e.toString());
//                        }
//                        callBack.onComplete();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        callBack.onComplete();
//                    }
//
//                });
//    }
//
//    /**
//     * 公用Observer
//     *
//     * @param <T>
//     */
//    class MyObserver<T> implements Observer<ResponseBody> {
//        ListCallBack<T> callBack;
//
//        CallBack<T> callBack2;
//        Class clazz;
//
//        public MyObserver(Class clazz, ListCallBack<T> callBack) {
//            this.clazz = clazz;
//            this.callBack = callBack;
//        }
//
//        public MyObserver(Class clazz, CallBack<T> callBack) {
//            this.clazz = clazz;
//            this.callBack2 = callBack;
//        }
//
//        @Override
//        public void onSubscribe(Disposable d) {
//            callBack.onStart(d);
//        }
//
//        @Override
//        public void onNext(ResponseBody value) {
//            try {
//                String responseBody = value.string();
//                JSONObject jsonObject = new JSONObject(responseBody);
//                String code = jsonObject.getString("code");
//                String msg = jsonObject.getString("msg");
//                String detail =jsonObject.getString("detail");
//                if(callBack!=null) {
//                    List<T> result = JSON.parseArray(detail, clazz);
//                    callBack.onSuccess(result, code, msg);
//                }else{
//                    callBack2.onSuccess((T) JSON.parseObject(detail,clazz), code, msg);
//                }
//            } catch (Exception e) {
//                onError(e);
//            }
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            if (e instanceof SocketTimeoutException) {
//                callBack.onError(e, mContext.getString(R.string.net_error_timeout));
//            } else if (e instanceof HttpException
//                    || e instanceof ConnectException
//                    || e instanceof SSLHandshakeException
//                    || e instanceof UnknownHostException) {
//                callBack.onError(e, mContext.getString(R.string.net_error_nonet));
//            } else {
//                callBack.onError(e, e.toString());
//            }
//            callBack.onComplete();
//        }
//
//        @Override
//        public void onComplete() {
//            callBack.onComplete();
//        }
//    }
//
//    /****************************************************************************/
//
//    public interface CallBack<T> {
//
//        /**
//         * 开始请求
//         *
//         * @param disposable
//         */
//        public void onStart(Disposable disposable);
//
//        /**
//         * 请求成功
//         *
//         * @param result 泛型
//         * @param code
//         * @param msg
//         */
//        public void onSuccess(T result, String code, String msg);
//
//        /**
//         * 请求失败
//         *
//         * @param throwable
//         */
//        public void onError(Throwable throwable, String msg);
//
//        /**
//         * 请求结束,不管是成功或失败都会走这里
//         */
//        public void onComplete();
//    }
//
//    public interface ListCallBack<T> {
//
//        /**
//         * 开始请求
//         *
//         * @param disposable
//         */
//        public void onStart(Disposable disposable);
//
//
//        /**
//         * 请求成功
//         *
//         * @param result 泛型
//         * @param code
//         * @param msg
//         */
//        public void onSuccess(List<T> result, String code, String msg);
//
//        /**
//         * 请求失败
//         *
//         * @param throwable
//         * @param string
//         */
//        public void onError(Throwable throwable, String string);
//
//        /**
//         * 请求结束,不管是成功或失败都会走这里
//         */
//        public void onComplete();
//    }
//}
