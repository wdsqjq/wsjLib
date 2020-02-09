package per.wsj.commonlib.net;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * 针对直接返回实体的情况
 * 适配String类型，实体类型，List<T>类型
 * @param <T>
 */
public abstract class HttpCallback3<T> extends HttpCallback<T> {
    @Override
    public void onNext(ResponseBody value) {
        try {
            String responseBody = value.string();
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                Type actualTypeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
                T data;
                // 返回类型为String的话，直接返回responseBody
                String clazz = actualTypeArgument.toString().replace("class ","");
                if (clazz.equals(String.class.getName())) {
                    data = (T) responseBody;
                } else {
                    data = new Gson().fromJson(responseBody, actualTypeArgument);
                }
                if (data == null) {
                    onError(null, "请求失败");
                } else {
                    onSuccess(data, "200", "成功");
                }
            } else {
                onError(null, "请求失败");
            }
        } catch (Exception e) {
            onError(e);
        }
    }

    /*@Override
    public void onNext(ResponseBody value) {
        try {
            String responseBody = value.string();
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                Type actualTypeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
                Class<T> tClass = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
                T data;
                // 返回类型为String的话，直接返回responseBody
                if (tClass == String.class) {
                    data = (T) responseBody;
                } else {
                    data = new Gson().fromJson(responseBody, tClass);
                }
                if (data == null) {
                    onError(null, "请求失败");
                } else {
                    onSuccess(data, "200", "成功");
                }
            }else{
                onError(null, "请求失败");
            }
        } catch (Exception e) {
            onError(e);
        }
    }*/
}