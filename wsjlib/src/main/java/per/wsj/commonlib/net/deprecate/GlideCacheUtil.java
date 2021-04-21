package per.wsj.commonlib.net.deprecate;

//import android.content.Context;
//import android.os.Looper;
//import android.text.TextUtils;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
//import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
//
//import java.io.File;
//
//import per.wsj.commonlib.utils.FileUtil;
//
///**
// * Created by shiju.wang on 2018/4/13.
// *
// * @author shiju.wang
// */
//
//public class GlideCacheUtil {
//    private static GlideCacheUtil inst;
//
//    public static GlideCacheUtil getInstance() {
//        if (inst == null) {
//            inst = new GlideCacheUtil();
//        }
//        return inst;
//    }
//
//    /**
//     * 清除图片磁盘缓存
//     */
//    public void clearImageDiskCache(final Context context) {
//        try {
//            if (Looper.myLooper() == Looper.getMainLooper()) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Glide.get(context).clearDiskCache();
//                        // BusUtil.getBus().post(new GlideCacheClearSuccessEvent());
//                    }
//                }).start();
//            } else {
//                Glide.get(context).clearDiskCache();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 清除图片内存缓存
//     */
//    public void clearImageMemoryCache(Context context) {
//        try {
//            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
//                Glide.get(context).clearMemory();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 清除图片所有缓存
//     */
//    public void clearImageAllCache(Context context) {
//        clearImageDiskCache(context);
//        clearImageMemoryCache(context);
//        String ImageExternalCatchDir=context.getExternalCacheDir()+ ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
//        deleteFolderFile(ImageExternalCatchDir, true);
//    }
//
//    /**
//     * 获取Glide造成的缓存大小
//     *
//     * @return CacheSize
//     */
//    public String getCacheSize(Context context) {
//        try {
//            File file = new File(context.getCacheDir() + "/"+ InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR);
//            return FileUtil.generateSize(file);
////            return getFormatSize(getFolderSize(file));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    /**
//     * 获取指定文件夹内所有文件大小的和
//     *
//     * @param file file
//     * @return size
//     */
//    private long getFolderSize(File file) throws Exception {
//        long size = 0;
//        try {
//            File[] fileList = file.listFiles();
//            for (File aFileList : fileList) {
//                if (aFileList.isDirectory()) {
//                    size = size + getFolderSize(aFileList);
//                } else {
//                    size = size + aFileList.length();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return size;
//    }
//
//    /**
//     * 删除指定目录下的文件，这里用于缓存的删除
//     *
//     * @param filePath filePath
//     * @param deleteThisPath deleteThisPath
//     */
//    private void deleteFolderFile(String filePath, boolean deleteThisPath) {
//        if (!TextUtils.isEmpty(filePath)) {
//            try {
//                File file = new File(filePath);
//                if (file.isDirectory()) {
//                    File[] files = file.listFiles();
//                    for (File file1 : files) {
//                        deleteFolderFile(file1.getAbsolutePath(), true);
//                    }
//                }
//                if (deleteThisPath) {
//                    if (!file.isDirectory()) {
//                        file.delete();
//                    } else {
//                        if (file.listFiles().length == 0) {
//                            file.delete();
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
