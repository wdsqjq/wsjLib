package per.wsj.commonlib.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by dell 王世举 on 2017/9/3 11:20.
 */

public class ScreenUtil {
    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    public static int dip2px(Context context, float dpVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getApplicationContext().getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param context
     * @param spVal
     * @return
     */
    public static int sp2px(Context context, float spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getApplicationContext().getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal)
    {
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal)
    {
        return (pxVal / context.getApplicationContext().getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    private int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     * @return
     */
    private int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
