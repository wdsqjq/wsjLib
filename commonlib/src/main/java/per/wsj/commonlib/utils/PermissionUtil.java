package per.wsj.commonlib.utils;

import android.Manifest;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;

/**
 * Created by shiju.wang on 2018/1/5.
 */

public class PermissionUtil {
    
    public void checkPermission(Activity context){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PermissionChecker.PERMISSION_GRANTED){

        }else{
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        }

//        @Override
//        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//            if (requestCode == PERMISSIONS_REQUEST) {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                } else {
//                    Toast.makeText(this, "没有文件存储的权限!", Toast.LENGTH_SHORT).show();
//                }
//            }
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
    }
}
