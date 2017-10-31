package per.wangsj.myview.loadingview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import per.wangsj.myview.R;

/**
 * Created by shiju.wang on 2017/10/17.
 */

public class AliLoadingView extends View {

    private Paint mPaint;
    private Bitmap dstBmp, srcBmp;
    private RectF dstRect, srcRect ;

    private Xfermode mXfermode;
    private PorterDuff.Mode mPorterDuffMode = PorterDuff.Mode.SRC_IN;
    private float maskY;
    private ObjectAnimator maskYAnimator;

    public AliLoadingView(Context context) {
        super(context);
        init(context);
    }

    public AliLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AliLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
        dstBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.heart2);
        srcBmp = BitmapFactory.decodeResource(getResources(),R.mipmap.wave);

        mXfermode = new PorterDuffXfermode(mPorterDuffMode);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //背景色设为白色，方便比较效果
//        canvas.drawColor(Color.WHITE);
        //将绘制操作保存到新的图层，因为图像合成是很昂贵的操作，将用到硬件加速，这里将图像合成的处理放到离屏缓存中进行
        int saveCount = canvas.saveLayer(dstRect, mPaint, Canvas.ALL_SAVE_FLAG);
        //绘制目标图
        canvas.drawBitmap(dstBmp, null, dstRect, mPaint);
        //设置混合模式
        mPaint.setXfermode(mXfermode);
        //绘制源图
        canvas.drawBitmap(srcBmp, null, srcRect, mPaint);
        //清除混合模式
        mPaint.setXfermode(null);
        //还原画布
        canvas.restoreToCount(saveCount);
        play();
    }

    public void play(){
        if(null==maskYAnimator) {
            maskYAnimator = ObjectAnimator.ofFloat(this, "maskY", 4.5f, -4.5f);
            maskYAnimator.setRepeatCount(ValueAnimator.INFINITE);
            maskYAnimator.setRepeatMode(ValueAnimator.REVERSE);
            maskYAnimator.setDuration(3000);
            maskYAnimator.setStartDelay(0);
            maskYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Log.d("LoadingImageView", "getMaskY():" + getMaskY());
                    srcRect=new RectF(srcRect.left,srcRect.top+getMaskY(),srcRect.right,srcRect.bottom+getMaskY());
                    AliLoadingView.this.invalidate();
                }
            });
            maskYAnimator.start();
        }
    }



    public float getMaskY() {
        return maskY;
    }

    public void setMaskY(float maskY) {
        this.maskY = maskY;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int width = w <= h ? w : h;
        int centerX = w/2;
        int centerY = h/2;
        int quarterWidth = width /4;
        srcRect = new RectF(centerX-quarterWidth, centerY-2*quarterWidth, centerX+quarterWidth, centerY+2*quarterWidth);
        dstRect = new RectF(centerX-quarterWidth, centerY-quarterWidth, centerX+quarterWidth, centerY+quarterWidth);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        maskYAnimator.cancel();
    }
}
