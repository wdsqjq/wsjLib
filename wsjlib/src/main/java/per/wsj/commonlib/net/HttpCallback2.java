package per.wsj.commonlib.net;

import okhttp3.ResponseBody;

/**
 * 针对Put和Delete请求时无返回结果的情况
 * @param <T>
 */
public abstract class HttpCallback2<T> extends HttpCallback<T> {

    @Override
    public void onNext(ResponseBody value) {
        try {
            String responseBody = value.string();
            onSuccess((T) responseBody,"200","success");
        } catch (Exception e) {
            onError(e);
        }
    }
}