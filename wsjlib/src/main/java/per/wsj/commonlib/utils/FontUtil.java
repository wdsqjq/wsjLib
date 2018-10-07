package per.wsj.commonlib.utils;

import android.graphics.Paint;
import android.graphics.Rect;

public class FontUtil {

    /**
     * 获取文字宽度
     *
     * @param text
     * @param paint
     * @return
     */
    public static int getTextWidth(String text, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int width = bounds.left + bounds.width();
        return width;
    }

    /**
     * 获取文字高度
     *
     * @param text
     * @param paint
     * @return
     */
    public static int getTextHeight(String text, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int height = bounds.bottom + bounds.height();
        return height;
    }

}
