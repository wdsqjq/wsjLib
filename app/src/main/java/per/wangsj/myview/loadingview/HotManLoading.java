package per.wangsj.myview.loadingview;

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
 * Created by shiju.wang on 2017/11/2.
 */

public class HotManLoading extends View {

    private int mWidth,mHeight;

    private Paint mPaint1,mPaint2,mPaint3,mPaint4;
    private static int mRadius=30;
    private float mRate1=1;
    private float mRate2=0f;
    private float mRate3=0f;
    private float mRate4=0f;
    private ValueAnimator valueAnimator1,valueAnimator2,valueAnimator3,valueAnimator4;

    public HotManLoading(Context context) {
        super(context);
        init(context);
    }

    public HotManLoading(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HotManLoading(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint1=new Paint();
        mPaint1.setAntiAlias(true);
        mPaint1.setColor(Color.rgb(255,106,106));

        mPaint2=new Paint();
        mPaint2.setAntiAlias(true);
        mPaint2.setColor(Color.rgb(221,106,221));

        mPaint3=new Paint();
        mPaint3.setAntiAlias(true);
        mPaint3.setColor(Color.rgb(255,165,0));

        mPaint4=new Paint();
        mPaint4.setAntiAlias(true);
        mPaint4.setColor(Color.rgb(0,191,255));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=MeasureSpec.getSize(widthMeasureSpec);
        mHeight=MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth/2,mHeight/2);

        canvas.drawCircle(-150,0,mRadius*mRate1,mPaint1);

        canvas.drawCircle(-60,0,mRadius*mRate2,mPaint2);

        canvas.drawCircle(60,0,mRadius*mRate3,mPaint3);
//
        canvas.drawCircle(150,0,mRadius*mRate4,mPaint4);

        start();
    }

    private void start() {
        if(valueAnimator1==null){
            valueAnimator1=getValueAnimator(0);
            valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRate1= (float) animation.getAnimatedValue();
                    HotManLoading.this.invalidate();
                }
            });
            valueAnimator1.start();

            valueAnimator2=getValueAnimator(333);
            valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRate2= (float) animation.getAnimatedValue();
                }
            });
            valueAnimator2.start();

            valueAnimator3=getValueAnimator(666);
            valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRate3= (float) animation.getAnimatedValue();
                }
            });
            valueAnimator3.start();

            valueAnimator4=getValueAnimator(1000);
            valueAnimator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRate4= (float) animation.getAnimatedValue();
                }
            });
            valueAnimator4.start();
        }
    }

    private ValueAnimator getValueAnimator(long delay){
        ValueAnimator animator=ValueAnimator.ofFloat(0,1);
        animator.setDuration(1000);
        animator.setStartDelay(delay);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }
}
