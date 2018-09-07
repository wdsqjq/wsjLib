package per.wsj.commonlib.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.text.format.Formatter;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by shiju.wang on 2017/11/10.
 */

public class FileUtil {
    /**
     * 获取文件大小
     *
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
                return String.format(Locale.CHINESE, "%.2fKB", result / (double) kb);
            } else if (result >= mb && result < gb) {
                return String.format(Locale.CHINESE, "%.2fMB", result / (double) mb);
            } else if (result >= gb) {
                return String.format(Locale.CHINESE, "%.2fGB", result / (double) gb);
            }
        }
        return null;
    }

    /**
     * 获取sd卡总大小
     *
     * @param context
     * @return
     */
    public static String getSdTotalSize(Context context) {
        StatFs sf = new StatFs("/mnt/sdcard");
        long blockSize = sf.getBlockSize();
        long totalBlocks = sf.getBlockCount();
        return Formatter.formatFileSize(context, blockSize * totalBlocks);
    }

    /**
     * 获取sd卡可用大小
     *
     * @param context
     * @return
     */
    public static String getSdAvailableSize(Context context) {
        StatFs sf = new StatFs("/mnt/sdcard");
        long blockSize = sf.getBlockSize();
        long availableBlocks = sf.getAvailableBlocks();
        return Formatter.formatFileSize(context, blockSize * availableBlocks);
    }

    /**
     * Try to return the absolute file path from the given Uri  兼容了file:///开头的 和 content://开头的情况
     * Uri转File路径
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * @param path 传入路径字符串
     * @return File
     */
    public static File creatFileIfNotExist(String path) {
        System.out.println("cr");
        File file = new File(path);
        if (!file.exists()) {
            try {
                new File(path.substring(0, path.lastIndexOf(File.separator)))
                        .mkdirs();
                file.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return file;
    }

    /**
     * @param path 传入路径字符串
     * @return File
     */
    public static File creatDirIfNotExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.mkdirs();

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return file;
    }

    /**
     * @param path
     * @return
     */
    public boolean IsExist(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 创建新的文件，如果有旧文件，先删除再创建
     *
     * @param path
     * @return
     */
    public File creatNewFile(String path) {
        File file = new File(path);
        if (IsExist(path))
            file.delete();
        creatFileIfNotExist(path);
        return file;
    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     */
    public boolean deleteFile(String path) {
        File file = new File(path);
        if (IsExist(path))
            file.delete();
        return true;
    }

    // 删除一个目录
    public boolean deleteFileDir(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!IsExist(path)) {
            return flag;
        }
        if (!file.isDirectory()) {

            file.delete();
            return true;
        }
        String[] filelist = file.list();
        File temp = null;
        for (int i = 0; i < filelist.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + filelist[i]);
            } else {
                temp = new File(path + File.separator + filelist[i]);
            }
            if (temp.isFile()) {

                temp.delete();
            }
            if (temp.isDirectory()) {
                deleteFileDir(path + "/" + filelist[i]);// 先删除文件夹里面的文件
            }
        }
        file.delete();

        flag = true;
        return flag;
    }

    // 删除文件夹
    // param folderPath 文件夹完整绝对路径

    public void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 删除指定文件夹下所有文件
    // param path 文件夹完整绝对路径
    public boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 外部存储是否可读写
     *
     * @return
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}
