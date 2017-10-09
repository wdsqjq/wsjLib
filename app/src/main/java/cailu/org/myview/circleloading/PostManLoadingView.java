package cailu.org.myview.circleloading;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 仿照postman的加载效果
 * Created by shiju.wang on 2017/9/21.
 */

public class PostManLoadingView extends View {

    private Paint circlePaint;
    private Paint pointPaint;
    private Paint bollPaint;
    //第一个小圆圆心坐标
    private float X1;
    private float Y1;    //第二个小圆圆心坐标
    private float X2;
    private float Y2;    //第三个小圆圆心坐标
    private float X3;
    private float Y3;

    public PostManLoadingView(Context context) {
        super(context);
        init(context);
    }

    public PostManLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PostManLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        circlePaint=new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.RED);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(3);

        pointPaint=new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setColor(Color.RED);
        pointPaint.setStyle(Paint.Style.FILL);

        bollPaint=new Paint();
        bollPaint.setAntiAlias(true);
        bollPaint.setColor(Color.RED);
        bollPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height=getMeasuredHeight();
        int width=getMeasuredWidth();
        canvas.translate(width/2, height/2);

        canvas.drawCircle(0,0,40,pointPaint);
        canvas.drawCircle(0,0,100,circlePaint);
        canvas.drawCircle(0,0,150,circlePaint);
        canvas.drawCircle(0,0,200,circlePaint);

        canvas.drawCircle(X1,Y1,16,bollPaint);
        canvas.drawCircle(X2,Y2,16,bollPaint);
        canvas.drawCircle(X3,Y3,16,bollPaint);
    }

    private int mRandius = 50;
    private int mInterval = 50;//空心圆之间的间隔

    public void start() {
        ValueAnimator animator1 = getValueAnimator(mRandius + mInterval);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float deg = (float) valueAnimator.getAnimatedValue();
                float rad = (float) (Math.PI * deg / 180);
                Y1 = (float) (Math.sin(rad) * (mRandius + mInterval));
                X1 = (float) (Math.cos(rad) * (mRandius + mInterval));
            }
        });
        animator1.start();

        ValueAnimator animator2 = getValueAnimator(mRandius + mInterval + mInterval);
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float deg = (float) valueAnimator.getAnimatedValue();
                float rad = (float) (Math.PI * deg / 180);
                Y2 = (float) (Math.sin(rad) * (mRandius + mInterval + mInterval));
                X2 = (float) (Math.cos(rad) * (mRandius + mInterval + mInterval));
            }
        });
        animator2.start();

        ValueAnimator animator3 = getValueAnimator(mRandius + mInterval + mInterval + mInterval);
        animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float deg = (float) valueAnimator.getAnimatedValue();
                float rad = (float) (Math.PI * deg / 180);

                Y3 = (float) (Math.sin(rad) * (mRandius + mInterval + mInterval + mInterval));
                X3 = (float) (Math.cos(rad) * (mRandius + mInterval + mInterval + mInterval));
                invalidate();
            }
        });
        animator3.start();
    }

    public ValueAnimator getValueAnimator(final int mRandius) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 360);
        animator.setDuration(mRandius * 30);//这里动画时间只是随便写的，可以自己设置
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }
}
