package per.wangsj.myview.loadingview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 仿照去哪网刷票动画
 * Created by shiju.wang on 2017/9/22.
 */

public class RefreshTicketView extends View {

    private Paint circlePaint;
    private Paint whitePaint;
    private Paint refreshPaint;
    private Paint refreshPaint2;
    private Paint textPaint;
    private Paint countPaint;
    private int width;
    private int height;
    private Matrix mMatrix=new Matrix();//绘制圆的矩阵
    private String title="抢票次数";
    private int count=0;
    private float xPos=0;
    private float countXPos=0;
    private ValueAnimator animator;

    public RefreshTicketView(Context context) {
        super(context);
        init(context);
    }

    public RefreshTicketView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshTicketView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        circlePaint=new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.parseColor("#99ffffff"));
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(4);

        whitePaint=new Paint();
        whitePaint.setAntiAlias(true);
        whitePaint.setColor(Color.WHITE);

        refreshPaint=new Paint();
        refreshPaint.setAntiAlias(true);
        // 获取取色器:控制画笔的透明度 从透明变为半透明
        Shader shader = new SweepGradient(0, 0, new int[]{Color.parseColor("#33ffffff"), Color.WHITE,Color.WHITE,Color.WHITE},new float[]{0f,0.5f,0.75f,1.0f});
        //改变画笔的取色器
        refreshPaint.setShader(shader);
        refreshPaint.setStyle(Paint.Style.STROKE);
        refreshPaint.setStrokeWidth(4);
        refreshPaint.setStrokeJoin(Paint.Join.ROUND);

        refreshPaint2=new Paint();
        refreshPaint2.setAntiAlias(true);
        // 获取取色器:控制画笔的透明度 从透明变为半透明
        Shader shader2 = new SweepGradient(0, 0, new int[]{Color.parseColor("#33ffffff"), Color.WHITE,Color.WHITE,Color.WHITE},new float[]{0f,0.5f,0.75f,1.0f});
        Matrix matrix=new Matrix();
        matrix.setRotate(180);
        shader2.setLocalMatrix(matrix);
        //改变画笔的取色器
        refreshPaint2.setShader(shader2);
        refreshPaint2.setStyle(Paint.Style.STROKE);
        refreshPaint2.setStrokeWidth(4);

        countPaint=new Paint();
        countPaint.setAntiAlias(true);
        countPaint.setTextSize(60);
        countPaint.setColor(Color.rgb(44,171,196));
        countXPos=-countPaint.measureText(count+"")/2;

        textPaint=new Paint();
        textPaint.setTextSize(30);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);

        xPos=-textPaint.measureText(title)/2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width=getMeasuredWidth();
        height=getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.rgb(44,171,196));
        
        canvas.translate(width/2,height/2);
        canvas.drawCircle(0,0,200,circlePaint);
        canvas.drawCircle(0,0,200-2,whitePaint);

        canvas.drawText(title,xPos,-50,textPaint);
        canvas.drawText(count+"",countXPos,30,countPaint);
        canvas.concat(mMatrix);
//        canvas.drawCircle(0,0,206,refreshPaint);

        RectF rectF=new RectF(-206,-206,206,206);
        canvas.drawArc(rectF,0,180,false,refreshPaint);

        canvas.drawArc(rectF,180,180,false,refreshPaint2);
    }

    public void start(){
        animator=ValueAnimator.ofInt(0,360);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int rotate= (int) valueAnimator.getAnimatedValue();
                Log.d("RefreshTicketView", "rotate:" + rotate);
                mMatrix.setRotate(rotate,0,0);
                invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                setCount(++count);
            }
        });
        animator.start();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        countXPos=-countPaint.measureText(count+"")/2;
        this.count = count;
        invalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.cancel();
    }
}
