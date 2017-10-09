package cailu.org.myview.weixinradar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

//import java.util.logging.Handler;

/**
 * 一个视图界面是怎样在Android中显示出来的:
 * 1,测量:确定视图的宽和高----onMeasure()
 *
 * 2,布局  onLayout
 *
 * 3,绘制  onDraw
 *
 * Created by wangsj on 2015/9/5.
 */
public class MyRadarView extends View {
    private Paint mPaintNormal;//绘制普通圆圈和线的画笔
    private Paint mPaintCircle;//绘制渐变圆
    private int w, h;//屏幕的宽高

    private Handler mHandler= new Handler();
    private Matrix mMatrix;//绘制圆的矩阵
    //通过线程来控制矩阵的变化
    //每次改变我们的矩阵让其顺时针改变角度
    int start ;//表示每次矩阵的角度
    Runnable run=new Runnable() {
        @Override
        public void run() {
            start+=4;
            mMatrix=new Matrix();
            mMatrix.setRotate(start,w/2,h/2);
            MyRadarView.this.invalidate();
            //让其循环执行,
            mHandler.postDelayed(run,20);//每隔20毫秒更新
        }
    };

    public MyRadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        setBackgroundResource(R.drawable.ai);
        initPaint();//初始化画笔
        this.w=getResources().getDisplayMetrics().widthPixels;
        this.h=getResources().getDisplayMetrics().heightPixels;
        //启动线程
        mHandler.post(run);
    }


    /**
     * @author Wangsj 未婚,很帅 QQ:1521192337
     */
    private void initPaint() {
        mPaintNormal = new Paint();
        mPaintNormal.setColor(Color.parseColor("#ffffff"));
        mPaintNormal.setStrokeWidth(3);//设置比较细的线条
        mPaintNormal.setAntiAlias(true);//抗锯齿
        mPaintNormal.setStyle(Paint.Style.STROKE);//画笔类型---空心,实心

        mPaintCircle = new Paint();
        //mPaintCircle.setColor(0x9D00ff00);
        mPaintCircle.setColor(Color.parseColor("#9d00ff00"));
        mPaintCircle.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画4个同心圆
        canvas.drawCircle(w / 2, h / 2, w / 6, mPaintNormal);
        canvas.drawCircle(w / 2, h / 2, 2 * w / 6, mPaintNormal);
        canvas.drawCircle(w / 2, h / 2, 11 * w / 20, mPaintNormal);
        canvas.drawCircle(w / 2, h / 2, 7 * h / 16, mPaintNormal);

        // 获取取色器:控制画笔的透明度 从透明变为半透明
        Shader shader = new SweepGradient(w / 2, h / 2, Color.TRANSPARENT, Color.parseColor("#aaaaaaaa"));
        //改变画笔的取色器
        mPaintCircle.setShader(shader);
        canvas.concat(mMatrix);
        //画出扫面的圆
        canvas.drawCircle(w / 2, h / 2, 7 * h / 16, mPaintCircle);

        super.onDraw(canvas);
    }

}
