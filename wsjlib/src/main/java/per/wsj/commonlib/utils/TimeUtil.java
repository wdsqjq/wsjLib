package per.wsj.commonlib.utils;

import java.text.SimpleDateFormat;

/**
 * Created by shiju.wang on 2018/1/16.
 */

public class TimeUtil {
    /**
     * 返回当前时间的格式为 yy-MM-dd日 HH:mm:ss
     *
     * @return
     */
    public static String ts2Str(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(time);
    }
}
