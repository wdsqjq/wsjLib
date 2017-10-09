package cailu.org.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.view.View;

/**
 * Shader 图像渲染
 * Created by dell 王世举 on 2017/1/8 18:32.
 */

public class TestView extends View {

    private Paint mPaintCircle;//绘制渐变圆
    private int w, h;//屏幕的宽高
    private Matrix mMatrix;//绘制圆的矩阵

    private Handler mHandler;
    int start ;//表示每次矩阵的角度

    public TestView(Context context) {
        super(context);
        mPaintCircle = new Paint();
        //mPaintCircle.setColor(0x9D00ff00);
        mPaintCircle.setColor(Color.parseColor("#9d00ff00"));
        mPaintCircle.setAntiAlias(true);

        mMatrix=new Matrix();
        mHandler=new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                start+=4;
                mMatrix.setRotate(start,w/2,h/2);
                TestView.this.invalidate();
                mHandler.postDelayed(this,20);
            }
        });

        setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w=w;
        this.h=h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 获取取色器:控制画笔的透明度 从透明变为半透明
        Shader shader = new SweepGradient(w / 2, h / 2, Color.TRANSPARENT, Color.parseColor("#aaaaaaaa"));
        //改变画笔的取色器
        mPaintCircle.setShader(shader);
        canvas.concat(mMatrix);
        //画出扫面的圆
        canvas.drawCircle(w / 2, h / 2, 7 * h / 16, mPaintCircle);
    }
}
