package per.wsj.commonlib.net;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import per.wsj.commonlib.R;

/**
 * Created by jason.huang on 2016/9/29 0029.
 */

public class GlideUtils {


    /**
     * Glide加载
     * resouce类型-本地图片、资源文件、网络图片、gif、视频（只能加载本地视频）
     */
    public static void load(Context context, Object resource, ImageView targetImageView) {
        load(context, resource, R.mipmap.ic_default, R.mipmap.ic_default, targetImageView);
    }


    /**
     * Glide加载,带占位图
     */
    public static void load(Context context, Object resource, int placeholder, ImageView targetImageView) {
        load(context, resource, placeholder, R.mipmap.ic_default, targetImageView);
    }


    /**
     * Glide加载,带占位图和错误图片
     */
    public static void load(Context context, Object resource, int placeholder, int error, ImageView targetImageView) {
        Glide
                .with(context)
                .load(resource)//加载资源
                .placeholder(placeholder) // can also be a drawable
                .error(error) // will be displayed if the image cannot be loaded
                .dontAnimate()//don't use animate
                .into(targetImageView);
    }


    /**
     * Glide加载,带占位图和错误图片
     * <p>
     * DiskCacheStrategy.NONE 什么都不缓存，就像刚讨论的那样
     * DiskCacheStrategy.SOURCE 仅仅只缓存原来的全分辨率的图像。在我们上面的例子中，将会只有一个 1000x1000 像素的图片
     * DiskCacheStrategy.RESULT 仅仅缓存最终的图像，即，降低分辨率后的（或者是转换后的）
     * DiskCacheStrategy.ALL 缓存所有版本的图像（默认行为）
     */
    public static void loadNoCache(Context context, Object resource, int placeholder, int error, ImageView
            targetImageView) {
        Glide
                .with(context)
                .load(resource)//加载资源
                .placeholder(placeholder) // can also be a drawable
                .error(error) // will be displayed if the image cannot be loaded
                .dontAnimate()//don't use animate
                .skipMemoryCache(true)//跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)//跳过磁盘缓存
                .into(targetImageView);
    }


    /**
     * Glide加载,带占位图和错误图片
     * <p>
     * Priority.LOW
     * Priority.NORMAL
     * Priority.HIGH
     * Priority.IMMEDIATE
     */
    public static void loadPriority(Context context, Object resource, int placeholder, int error, Priority priority,
                                    ImageView targetImageView) {
        Glide
                .with(context)
                .load(resource)//加载资源
                .placeholder(placeholder) // can also be a drawable
                .error(error) // will be displayed if the image cannot be loaded
                .dontAnimate()//don't use animate
                .priority(priority)
                .into(targetImageView);
    }


    /**
     * Glide加载,带占位图和错误图片，centerCrop模式
     */
    public static void loadCenterCrop(Context context, Object resource, int placeholder, int error, ImageView
            targetImageView) {
        Glide
                .with(context)
                .load(resource)//加载资源
                .placeholder(placeholder) // can also be a drawable
                .error(error) // will be displayed if the image cannot be loaded
                .dontAnimate()//don't use animate
                .centerCrop()
                .into(targetImageView);
    }

    /**
     * Glide加载,带占位图和错误图片，FitCenter模式
     */
    public static void loadFitCenter(Context context, Object resource, int placeholder, int error, ImageView
            targetImageView) {
        Glide
                .with(context)
                .load(resource)//加载资源
                .placeholder(placeholder) // can also be a drawable
                .error(error) // will be displayed if the image cannot be loaded
                .dontAnimate()//don't use animate
                .fitCenter()
                .into(targetImageView);
    }


}
