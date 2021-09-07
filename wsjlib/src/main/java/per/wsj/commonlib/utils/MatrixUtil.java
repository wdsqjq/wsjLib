package per.wsj.commonlib.utils;

import android.graphics.Matrix;
import android.util.Log;

/**
 * 矩阵工具
 * Created by shiju.wang on 2017/10/14.
 */

public class MatrixUtil {

    /**
     * 打印矩阵
     * @param matrix
     */
    public static void printMatrix(Matrix matrix){
        float[] f=new float[9];
        matrix.getValues(f);
        Log.d("ZoomImageView", "++++++++++++++++++++++");
        for (int i = 0; i < 3; i++) {
            Log.d("ZoomImageView", f[i*3]+"\t"+f[i*3+1]+"\t"+f[i*3+2]);
        }
        Log.d("ZoomImageView", "----------------------");
    }
}
