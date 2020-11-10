package per.wsj.commonlib.net;


import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * 针对Put和Delete请求时无返回结果的情况
 *
 * @param <T>
 */
public abstract class HttpCallback2<T> implements Observer<T>, CallBack<T> {
    @Override
    public void onNext(T value) {
        try {
//            {protocol=http/1.1, code=204, message=No Content, url=https://api.github.com/user/starred/ekZffs/facewind}
//            Response{protocol=http/1.1, code=401, message=Unauthorized, url=https://api.github.com/user/starred/ekZffs/facewind}
            String pattern = "code=[0-9]+";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value.toString());
            String code = "";
            if (m.find()) {
                code = m.group().replace("code=", "");
            }
            if (code.equals("204") || code.equals("202")) {
                onSuccess(null, code, "success");
            } else if (code.equals("307")) {
                // Response{protocol=http/1.1, code=307, message=Temporary Redirect, url=https://api.github.com/user/starred/FlowCI/flow-platform-x}
                onError(null, "该仓库有点小异常，请在web端操作该仓库", "307");
            } else if (code.equals("401")) {
                onError(null, "请重新登录", code);
            } else if (code.equals("404")) {
                onError(null, "没有记录", code);
            } else if (code.equals("403")) {
                onError(null, "你的github账号邮箱验证已过期，请登录github进行邮箱验证", code);
            } else {
                onError(null, "请求失败", "0000");
            }
        } catch (Exception e) {
            onError(e);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            onError(e, "请求超时,请重试", "0000");
        } else if (e instanceof HttpException
                || e instanceof ConnectException
                || e instanceof UnknownHostException) {
            onError(e, "网络错误，请确保网络通畅", "0000");
        } else if (e instanceof SSLHandshakeException) {
            onError(e, "网络异常，如使用网络代理，请关闭后重试", "0000");
        } else {
            onError(e, e.toString(), "0000");
        }
        onFinished();
    }

    @Override
    public void onComplete() {
        onFinished();
    }

    @Override
    public void onSubscribe(Disposable d) {
        onStart(d);
    }

    @Override
    public void onNotLogin() {

    }
}