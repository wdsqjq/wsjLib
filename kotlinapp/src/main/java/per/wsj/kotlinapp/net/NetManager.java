package per.wsj.kotlinapp.net;

import android.content.Context;

import per.wsj.commonlib.net.NetLib;

/**
 * Created by shiju.wang on 2017/12/20.
 */

public class NetManager extends NetLib {

//    public String BASE_URL="http://wangsj.cn:8080/kotlinapp/";

    public NetManager(Context context) {
        this(context,"http://wangsj.cn:8080/kotlinapp/");
    }

    public NetManager(Context context,String url) {
        super(context,url);
    }
}
