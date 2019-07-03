package per.wsj.commonlib.permission;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.PermissionChecker;
import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by shiju.wang on 2018/3/1.
 */

public class PermissionUtil {

    private PermissionUtil() {

    }

    public static void requestPermission(Context context, PermissionListener listener, String... permission) {
        requestPermission(context, listener, permission, true);
    }

    /**
     * 申请授权，当用户拒绝时，可以设置是否显示Dialog提示用户，也可以设置提示用户的文本内容
     *
     * @param context
     * @param listener
     * @param permission 需要申请授权的权限
     * @param showTip    当用户拒绝授权时，是否显示提示
     */
    public static void requestPermission(@NonNull Context context, @NonNull PermissionListener listener
            , @NonNull String[] permission, boolean showTip) {
        if (PermissionUtil.hasPermission(context, permission)) {
            listener.permissionGranted(permission);
        } else {
            if (Build.VERSION.SDK_INT < 23) {
                listener.permissionDenied(permission);
            } else {
                PermissionActivity.requestPermission(context, permission, listener, showTip);
            }
        }
    }

    /**
     * 判断权限是否授权
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean hasPermission(@NonNull Context context, @NonNull String... permissions) {
        if (permissions.length == 0) {
            return false;
        }
        for (String per : permissions) {
            int result = PermissionChecker.checkSelfPermission(context, per);
            if (result != PermissionChecker.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一组授权结果是否为授权通过
     *
     * @param grantResult
     * @return
     */
    public static boolean isGranted(@NonNull int... grantResult) {
        if (grantResult.length == 0) {
            return false;
        }
        for (int result : grantResult) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 跳转到当前应用对应的设置页面
     *
     * @param context
     */
    public static void gotoSetting(@NonNull Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }
}
