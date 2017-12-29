package per.wsj.commonlib.utils;

import android.content.Context;
import android.os.StatFs;
import android.text.format.Formatter;

import java.io.File;

/**
 * Created by shiju.wang on 2017/11/10.
 */

public class FileUtil {
    /**
     * 获取文件大小
     * @param file
     * @return
     */
    public static String generateSize(File file) {
        if (file.isFile()) {
            long result = file.length();
            long gb = 2 << 29;
            long mb = 2 << 19;
            long kb = 2 << 9;
            if (result < kb) {
                return result + "B";
            } else if (result >= kb && result < mb) {
                return String.format("%.2fKB", result / (double) kb);
            } else if (result >= mb && result < gb) {
                return String.format("%.2fMB", result / (double) mb);
            } else if (result >= gb) {
                return String.format("%.2fGB", result / (double) gb);
            }
        }
        return null;
    }

    /**
     * 获取sd卡总大小
     * @param context
     * @return
     */
    public static String getSdTotalSize(Context context){
        StatFs sf = new StatFs("/mnt/sdcard");
        long blockSize = sf.getBlockSize();
        long totalBlocks = sf.getBlockCount();
        return Formatter.formatFileSize(context, blockSize*totalBlocks);
    }

    /**
     * 获取sd卡总大小
     * @param context
     * @return
     */
    public static String getSdAvailableSize(Context context){
        StatFs sf = new StatFs("/mnt/sdcard");
        long blockSize = sf.getBlockSize();
        long availableBlocks = sf.getAvailableBlocks();
        return Formatter.formatFileSize(context, blockSize*availableBlocks);
    }
}
