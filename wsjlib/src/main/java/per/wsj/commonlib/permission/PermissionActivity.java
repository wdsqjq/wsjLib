package per.wsj.commonlib.permission;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;

import java.io.Serializable;

public class PermissionActivity extends Activity {

    private static final int PERMISSION_REQUEST_CODE = 64;
    private boolean isRequireCheck;
    private String[] permission;
    private boolean showTip;

    private final String defaultTitle = "提示";
    private final String defaultContent = "当前应用缺少必要权限。\n \n 请点击 \"设置\"-\"权限\"-打开所需权限。";
    private final String defaultCancel = "取消";
    private final String defaultEnsure = "设置";

    private static PermissionListener mPermissionListener;
    private static final String KEY_INPUT_PERMISSIONS = "KEY_INPUT_PERMISSIONS";
    private static final String KEY_SHOW_TIP = "KEY_SHOW_TIP";

    /**
     * Request for permissions.
     */
    public static void requestPermission(Context context, String[] permissions, PermissionListener permissionListener, boolean showTip) {
        mPermissionListener = permissionListener;

        Intent intent = new Intent(context, PermissionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KEY_INPUT_PERMISSIONS, permissions);
        intent.putExtra(KEY_SHOW_TIP, showTip);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || !getIntent().hasExtra(KEY_INPUT_PERMISSIONS)) {
            finish();
            return;
        }
        isRequireCheck = true;
        permission = getIntent().getStringArrayExtra(KEY_INPUT_PERMISSIONS);
        showTip = getIntent().getBooleanExtra(KEY_SHOW_TIP, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isRequireCheck) {
            if (PermissionUtil.hasPermission(this, permission)) {
                permissionsGranted();
            } else {
                requestPermissions(permission); // 请求权限,回调时会触发onResume
                isRequireCheck = false;
            }
        } else {
            isRequireCheck = true;
        }
    }

    // 请求权限兼容低版本
    private void requestPermissions(String[] permission) {
        ActivityCompat.requestPermissions(this, permission, PERMISSION_REQUEST_CODE);
    }


    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //部分厂商手机系统返回授权成功时，厂商可以拒绝权限，所以要用PermissionChecker二次判断
        if (requestCode == PERMISSION_REQUEST_CODE && PermissionUtil.isGranted(grantResults)
                && PermissionUtil.hasPermission(this, permissions)) {
            permissionsGranted();
        } else if (showTip) {
            showMissingPermissionDialog();
        } else { //不需要提示用户
            permissionsDenied();
        }
    }

    // 显示缺失权限提示
    private void showMissingPermissionDialog() {
        new AlertDialog.Builder(this)
                .setTitle(defaultTitle)
                .setMessage(defaultContent)
                .setNegativeButton(defaultCancel, (dialog, which) ->
                        permissionsDenied())
                .setPositiveButton(defaultEnsure, (dialog, which) ->
                        PermissionUtil.gotoSetting(PermissionActivity.this)).setCancelable(false)
                .show();
    }

    private void permissionsDenied() {
        if(mPermissionListener!=null){
            mPermissionListener.permissionDenied(permission);
        }
        finish();
    }

    // 全部权限均已获取
    private void permissionsGranted() {
        if(mPermissionListener!=null){
            mPermissionListener.permissionGranted(permission);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        mPermissionListener = null;
        super.onDestroy();
    }

}
