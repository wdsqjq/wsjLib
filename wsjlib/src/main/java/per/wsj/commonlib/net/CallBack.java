package per.wsj.commonlib.net;

import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;

public interface CallBack<T> {

    void onStart(Disposable disposable);

    void onSuccess(T result, String code, String msg);

    /**
     * 请求失败
     *
     * @param throwable
     * @param string
     */
    void onError(@Nullable Throwable throwable, String string, String code);

    void onReLogin();

    void onFinished();
}
