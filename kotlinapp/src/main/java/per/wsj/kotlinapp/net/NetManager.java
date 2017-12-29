package per.wsj.kotlinapp.net;

import android.content.Context;

import per.wsj.commonlib.net.NetLib;
import per.wsj.kotlinapp.Constants;

/**
 * Created by shiju.wang on 2017/12/20.
 */

public class NetManager extends NetLib {

    public NetManager(Context context) {
        this(context, Constants.BASE_URL);
    }

    public NetManager(Context context,String url) {
        super(context,url);
    }
}
