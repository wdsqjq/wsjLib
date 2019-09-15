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
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * 圆形ImageView
 */

public class RoundImageView extends AppCompatImageView {

    private Paint mPaint;
    private Paint mEdgePaint;

    private int mStroke = 1;
    private int mPadding = 5;
    private Rect rectSrc;
    private Rect rectDest;
    private Bitmap circleBitmap;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mEdgePaint = new Paint();
        mEdgePaint.setAntiAlias(true);
        mEdgePaint.setStyle(Paint.Style.STROKE);
        mEdgePaint.setColor(Color.parseColor("#999999"));
        mEdgePaint.setStrokeWidth(mStroke);
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        mEdgePaint.setPathEffect(effects);

        Drawable drawable = getDrawable();
        if(null!=drawable){
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            circleBitmap = getCircleBitmap(bitmap);
        }else{
            circleBitmap = null;
        }
        mPaint.reset();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        rectDest = new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    /**
     * 绘制圆形图片
     */
    @Override
    protected void onDraw(Canvas canvas) {

        if (null != circleBitmap) {
            canvas.drawBitmap(circleBitmap, rectSrc, rectDest, mPaint);
            drawEdge(canvas, circleBitmap);
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
     * @return Bitmap
     */
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        mPaint.setAntiAlias(true);

        int x = bitmap.getWidth();
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(x / 2, x / 2, (x - 2 * mStroke - mPadding) / 2, mPaint);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        rectSrc = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.drawBitmap(bitmap, rectSrc, rectSrc, mPaint);
        return output;
    }
}
