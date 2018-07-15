package per.wangsj.myview;


import per.wsj.commonlib.net.BaseInterceptor;
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
        super(BaseApplication.getContext(), url, "", new BaseInterceptor());
    }

    private NetManager(String url, String cer) {
        super(BaseApplication.getContext(), url, cer,null);
    }


}