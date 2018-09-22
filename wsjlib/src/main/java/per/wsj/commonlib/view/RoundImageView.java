package per.wsj.commonlib.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * 圆形ImageView
 * Created by huanglongfei on 2017/7/22.
 */

public class RoundImageView extends AppCompatImageView {

    private Paint mPaint;
    private Paint mEdgePaint;

    private int mStroke = 1;
    public final int mPadding = 5;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //TODO:动画获取属性

        mPaint = new Paint();
        mEdgePaint = new Paint();
        mEdgePaint.setAntiAlias(true);
        mEdgePaint.setStyle(Paint.Style.STROKE);
        mEdgePaint.setColor(Color.parseColor("#999999"));
        mEdgePaint.setStrokeWidth(mStroke);
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        mEdgePaint.setPathEffect(effects);
    }


    /**
     * 绘制圆形图片
     */
    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();
        if (null != drawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Bitmap b = getCircleBitmap(bitmap, 14);
            final Rect rectSrc = new Rect(0, 0, b.getWidth(), b.getHeight());
            final Rect rectDest = new Rect(0, 0, getWidth(), getHeight());
            mPaint.reset();
            canvas.drawBitmap(b, rectSrc, rectDest, mPaint);
            drawEdge(canvas, b);
        } else {
            super.onDraw(canvas);
        }
    }

    private void drawEdge(Canvas canvas, Bitmap b) {
        int x = getWidth();
        canvas.drawCircle(x / 2, x / 2, x/2 - mStroke , mEdgePaint);
    }

    /**
     * 获取圆形图片方法
     *
     * @param bitmap
     * @param pixels
     * @return Bitmap
     */
    private Bitmap getCircleBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        mPaint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        mPaint.setColor(color);
        int x = bitmap.getWidth();

        canvas.drawCircle(x / 2, x / 2, (x - 2 * mStroke - mPadding) / 2, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, mPaint);
        return output;
    }


}
