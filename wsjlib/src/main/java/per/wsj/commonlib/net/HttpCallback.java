package per.wsj.commonlib.net;

import com.google.gson.Gson;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class HttpCallback<T> implements Observer<ResponseBody> {

    @Override
    public void onNext(ResponseBody value) {
        try {
            String responseBody = value.string();

            Type type = getClass().getGenericSuperclass();
            if(type instanceof ParameterizedType){
                Type[] types = ((ParameterizedType)type).getActualTypeArguments();
                Type ty = new ParameterizedTypeImpl(BaseResponseBody.class, new Type[]{types[0]});
                BaseResponseBody<T> data = new Gson().fromJson(responseBody, ty);

                if(data == null || data.code == null){
                    onError(null,"请求失败");
                }else{
                    onSuccess(data.detail,data.code,data.msg);
                }
            }else{
                onError(null,"未知异常");
            }
        } catch (Exception e) {
            onError(e);
        }
    }

    @Override
    public void onError(Throwable e) {
//        if (e instanceof SocketTimeoutException) {
//            callBack.onError(e, mContext.getString(R.string.net_error_timeout));
//        } else if (e instanceof HttpException
//                || e instanceof ConnectException
//                || e instanceof UnknownHostException) {
//            callBack.onError(e, mContext.getString(R.string.net_error_nonet));
//        } else if (e instanceof SSLHandshakeException) {
//            callBack.onError(e, mContext.getString(R.string.net_error_proxy));
//        } else {
//            callBack.onError(e, e.toString());
//        }
        onError(e,e.toString());
        onComplete();
    }

    @Override
    public void onSubscribe(Disposable d) {
        onStart(d);
    }

    public abstract void onStart(Disposable disposable);

    public abstract void onSuccess(T result, String code, String msg);

    /**
     * 请求失败
     *
     * @param throwable
     * @param string
     */
    public abstract void onError(@Nullable Throwable throwable, String string);

}