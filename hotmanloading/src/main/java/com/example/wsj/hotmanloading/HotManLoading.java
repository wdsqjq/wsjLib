package com.example.wsj.hotmanloading;

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

    private Paint mPaint;
    private static int mRadius=30;
    private float mRate1=1;
    private float mRate2=0.66f;
    private float mRate3=0.33f;
    private float mRate4=0f;
    private ValueAnimator valueAnimator;

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
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
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

        canvas.drawCircle(-150,0,mRadius*mRate1,mPaint);

        canvas.drawCircle(0,0,mRadius*mRate2,mPaint);

//        canvas.drawCircle(60,0,mRadius*mRate3,mPaint);
//
        canvas.drawCircle(150,0,mRadius*mRate4,mPaint);

        start();
    }

    private void start() {
        if(valueAnimator==null){
            valueAnimator=ValueAnimator.ofFloat(0,1);
            valueAnimator.setDuration(1000);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRate1= (float) animation.getAnimatedValue();
                    mRate2=1-mRate1+0.5f;
                    mRate3=mRate1-0.5>=0?mRate1-0.5f:1+mRate1-0.5f;
//                    mRate4=mRate1-0.75>=0?mRate1-0.75f:1+mRate1-0.75f;
                    mRate4=1-mRate1;
                    HotManLoading.this.invalidate();
                }
            });

            valueAnimator.start();
        }
    }
}
