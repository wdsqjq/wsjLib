package per.wsj.commonlib.net;

import io.reactivex.disposables.Disposable;

/**
 * Created by wangsj on 2018/7/15.
 *
 * @author wangsj
 */
public interface BaseCallBack<T> {
    /**
     * 开始请求
     *
     * @param disposable
     */
    void onStart(Disposable disposable);


    /**
     * 请求失败
     *
     * @param throwable
     * @param string
     */
    void onError(Throwable throwable, String string);

    /**
     * 请求结束,不管是成功或失败都会走这里
     */
    void onComplete();
}
