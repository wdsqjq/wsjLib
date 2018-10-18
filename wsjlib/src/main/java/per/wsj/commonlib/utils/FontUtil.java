package per.wsj.commonlib.utils;

import android.graphics.Paint;
import android.graphics.Rect;

/**
 * create by shiju.wang
 */
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

    /**
     * 获取文字开始绘制的Y(从中心到baseline的距离）
     *
     * @param paint
     * @return
     */
    public static float getTextStartY(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
    }
}
