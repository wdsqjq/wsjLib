package per.wsj.commonlib.utils;

import android.util.Log;

public class LogUtil {
    private static boolean DEBUG = true;
    public static final String TAG = "Wsj_Lib";

    public static void LOGI(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void LOGD(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void LOGE(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void LOGI(String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void LOGD(String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void LOGE(String msg) {
        if (DEBUG) {
            Log.e(TAG, msg);
        }
    }

}
