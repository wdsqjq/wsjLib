package per.wsj.commonlib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

    /**
     * bitmap添加水印
     * @param src
     * @param watermark
     * @return
     */
    private Bitmap addWatermark(Bitmap src, Bitmap watermark) {
        if (src == null || watermark  == null) {
            return src;
        }

        int sWid = src.getWidth();
        int sHei = src.getHeight();
        int wWid = watermark.getWidth();
        int wHei = watermark.getHeight();
        if (sWid == 0 || sHei == 0) {
            return null;
        }

        if (sWid < wWid || sHei < wHei) {
            return src;
        }

        Bitmap bitmap = Bitmap.createBitmap(sWid, sHei, Bitmap.Config.ARGB_8888);
        try {
            Canvas cv = new Canvas(bitmap);
            cv.drawBitmap(src, 0, 0, null);
            cv.drawBitmap(watermark, sWid - wWid - 5, sHei - wHei - 5, null);
            cv.save(Canvas.ALL_SAVE_FLAG);
            cv.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }
        return bitmap;
    }

    /**
     * Bitmap保存为图片文件
     * @param bitmap
     * @param fileName
     * @return
     */
    public static boolean saveBitmap(Context context, Bitmap bitmap, String fileName) {
        String  imagePath = context.getFilesDir().getAbsolutePath() + "/temp.png";
        File file = new File(imagePath);
        if (!file.exists()) {
            file.mkdir();
        }
        File imageFile = new File(file, fileName);
        try {
            imageFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}
