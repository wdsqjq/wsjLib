package per.wsj.commonlib.permission;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Request implements IRequest, PermissionListener {

    private Context context;

    private String[] mPermissions;

    private Action mGranted;
    private Action mDenied;

    Request(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public IRequest permission(String... permissions) {
        this.mPermissions = permissions;
        return this;
    }

    @NonNull
    @Override
    public IRequest permission(String[]... groups) {
        List<String> permissionList = new ArrayList<>();
        for (String[] group : groups) {
            permissionList.addAll(Arrays.asList(group));
        }
        this.mPermissions = permissionList.toArray(new String[permissionList.size()]);
        return this;
    }

    @NonNull
    @Override
    public IRequest onGranted(Action granted) {
        this.mGranted = granted;
        return this;
    }

    @NonNull
    @Override
    public IRequest onDenied(Action denied) {
        this.mDenied = denied;
        return this;
    }

    @Override
    public void start() {
        if (PermissionUtil.hasPermission(context, mPermissions)) {
            callbackSucceed();
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                callbackFailed(mPermissions);
            } else {
                PermissionActivity.requestPermission(context, mPermissions, this);
            }
        }
    }

    /**
     * Callback acceptance status.
     */
    private void callbackSucceed() {
        if (mGranted != null) {
            try {
                mGranted.onAction(mPermissions);
            } catch (Exception e) {
                if (mDenied != null) {
                    mDenied.onAction(mPermissions);
                }
            }
        }
    }

    /**
     * Callback rejected state.
     */
    private void callbackFailed(@NonNull String[] deniedList) {
        if (mDenied != null) {
            mDenied.onAction(deniedList);
        }
    }

    @Override
    public void permissionGranted(@NonNull String[] permission) {
        callbackSucceed();
    }

    @Override
    public void permissionDenied(@NonNull String[] permission) {
        callbackFailed(permission);
    }
}
