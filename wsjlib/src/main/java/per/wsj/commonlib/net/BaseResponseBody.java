package per.wsj.commonlib.net;



/**
 * Created by fan.feng on 2019/4/1.
 */
public class BaseResponseBody<E> {
    public String code;
    public String msg;
    public E detail;
}
