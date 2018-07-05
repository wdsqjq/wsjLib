package per.wangsj.myview;


import per.wsj.commonlib.net.HttpUtil;

/**
 * Created by shiju.wang on 2018/2/28.
 */

public class NetManager extends HttpUtil {
    private static final NetManager ourInstance = new NetManager();

    public static NetManager getInstance() {
        return ourInstance;
    }

    private NetManager() {
        this("http://wangsj.cn:8000/");
//        this(new SharedPrefManager().getIpConfig(),"");
    }

    private NetManager(String url) {
        super(BaseApplication.getContext(), url, "");
    }

    private NetManager(String url, String cer) {
        super(BaseApplication.getContext(), url, cer);
    }

    public <T> void get(String url, Class clazz, ListCallBack<T> callBack) {
        super.get(url, null, clazz, callBack);
    }

    public <T> void get(String url, Class clazz, CallBack<T> callBack) {
        super.get(url, null, clazz, callBack);
    }

    public <T> void post(String url, Class clazz, ListCallBack<T> callBack) {
        super.post(url,null,clazz,callBack);
    }

    public <T> void post(String url, Class clazz, CallBack<T> callBack) {
        super.post(url,null,clazz,callBack);
    }
}
