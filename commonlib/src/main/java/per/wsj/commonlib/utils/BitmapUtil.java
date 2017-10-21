package per.wsj.commonlib.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by shiju.wang on 2017/10/17.
 */

public class BitmapUtil {

    /**
     * drawable转bitmap
     * @param drawable
     * @return
     */
    public static Bitmap drawable2Bitmap(Drawable drawable){
        BitmapDrawable db= (BitmapDrawable) drawable;
        return db.getBitmap();
    }

    /**
     * bitmap转drawable
     * @param bitmap
     * @return
     */
    public static Drawable bitmap2Drawable(Bitmap bitmap){
        return new BitmapDrawable(null,bitmap);
    }

}
