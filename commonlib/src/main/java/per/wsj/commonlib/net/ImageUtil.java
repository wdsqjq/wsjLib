package per.wsj.commonlib.net;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.wtk.corelib.BaseApplication;

import java.io.File;

/**
 * Created by shiju.wang on 2018/2/6.
 */

public class ImageUtil {
    private static final ImageUtil ourInstance = new ImageUtil();
    private Context context;
    public static ImageUtil getInstance() {
        return ourInstance;
    }

    private ImageUtil() {
        context= BaseApplication.getContext();
    }

    /**
     * 无占位图和失败图
     * @param url
     * @param imageView
     */
    public void loadImg(String url, ImageView imageView){
        loadImg(url,imageView,0,0);
    }

    /**
     * 占位图
     * @param url
     * @param imageView
     * @param placeholder
     */
    public void loadImg(String url, ImageView imageView,int placeholder){
        loadImg(url,imageView,placeholder,0);
    }
    /**
     * 占位图+失败图
     * @param url
     * @param imageView
     * @param placeholder
     */
    public void loadImg(String url, ImageView imageView,int placeholder,int error){
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL).skipMemoryCache(false).placeholder(placeholder).error(error))
                .into(imageView);
    }

    public void loadCircleImg(String url,ImageView imageView,int placeholder){
        Glide.with(context).load(url)
                .apply(RequestOptions.bitmapTransform(new CircleCrop())
                        .skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(placeholder)
//                        .error(error)
                )
                        .into(imageView);
    }

    /**
     * 加载本地图片
     * @param file
     * @param imageView
     */
    public void loadImg(File file, ImageView imageView){
        Glide.with(context)
                .load(file)
                .into(imageView);
    }
}
