package per.wangsj.myview;

import android.app.Application;
import android.content.Context;

/**
 * Created by shiju.wang on 2018/7/5.
 *
 * @author shiju.wang
 */
public class BaseApplication extends Application {
    protected static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }

}
