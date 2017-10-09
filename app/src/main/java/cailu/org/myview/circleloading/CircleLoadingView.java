package cailu.org.myview.circleloading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 圆形加载,中间显示进度值
 * Created by shiju.wang on 2017/9/20.
 */

public class CircleLoadingView extends View {

    private int mWidth;
    private int mHeight;
    private Paint redPaint;
    private Paint garyPaint;
    private Paint textpaint;
    private int progress=0;
    private onCompletedInterface onCompletedInterface;

    public CircleLoadingView(Context context) {
        super(context);
        init(context);
    }

    public CircleLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CircleLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        garyPaint=new Paint();
        garyPaint.setAntiAlias(true);
        garyPaint.setStyle(Paint.Style.STROKE);
        garyPaint.setColor(Color.GRAY);
        garyPaint.setStrokeWidth(5);

        redPaint=new Paint();
        redPaint.setAntiAlias(true);
        redPaint.setStyle(Paint.Style.STROKE);
        redPaint.setColor(Color.RED);
        redPaint.setStrokeWidth(5);

        textpaint=new Paint();
        textpaint.setAntiAlias(true);
        textpaint.setColor(Color.BLACK);
        textpaint.setTextSize(30);
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
        canvas.drawColor(Color.WHITE);
        //0点移动到屏幕中心
        canvas.translate(mWidth/2,mHeight/2);

        canvas.drawCircle(0,0,100,garyPaint);
        String text=progress+"%";
        // 获取文字宽度
        float textWidth=textpaint.measureText(text);

        //获取文字基线
        Paint.FontMetricsInt fontMetricsInt = textpaint.getFontMetricsInt();
        float dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        //画文字
        canvas.drawText(text,-textWidth/2,dy,textpaint);

        RectF rectF=new RectF(-100,-100,100,100);
        float angle=progress/100f*360;
        //花进度
        canvas.drawArc(rectF,0,angle,false,redPaint);
        if(progress==100&&onCompletedInterface!=null){
            onCompletedInterface.complete();
        }
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public CircleLoadingView.onCompletedInterface getOnCompletedInterface() {
        return onCompletedInterface;
    }

    public void setOnCompletedInterface(CircleLoadingView.onCompletedInterface onCompletedInterface) {
        this.onCompletedInterface = onCompletedInterface;
    }

    public interface onCompletedInterface{
        void complete();
    }
}
