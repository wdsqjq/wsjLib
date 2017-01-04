package cailu.org.mysurfaceview;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by adm on 2017/1/4.
 */

public class FreeFall extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private int mWidth, mHeight;
    private int radius = 20; //球的半径
    private int L = 400; // 线的长度
    private Path path;// 线的路径
    Activity context;
    private float g; //重力加速度
    private float density;

    public FreeFall(Activity context) {
        super(context);
        this.context=context;
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mWidth = getWidth();
        mHeight = getHeight();
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        density = metric.densityDpi;// 屏幕密度（0.75 / 1.0 / 1.5） 一英寸上有几个像素
        g= (float) (density/2540);

        new DrawThread().start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    class DrawThread extends Thread {

        public DrawThread() {

        }

        @Override
        public void run() {
            super.run();
            Paint paint = new Paint();
            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);

            Paint linePaint = new Paint();
            linePaint.setStyle(Paint.Style.STROKE);
            linePaint.setStrokeWidth(8);
            linePaint.setColor(Color.GREEN);

            Canvas canvas = null;
//            canvas = holder.lockCanvas();
//            canvas.translate(mWidth/2,mHeight/2);
//            canvas.drawCircle(0, 0, radius, paint);
//            canvas.drawCircle(0, (float) (density/2.54), radius, paint);

            float x = mWidth / 2;
            float y = 0;
            for (int i = 0; i < 180; i++) {
                try {
                    Thread.sleep(1);
                    canvas = holder.lockCanvas();

                    y= (float) (g*Math.pow(i,2)/2);

                    canvas.drawColor(Color.BLACK);

                    canvas.drawCircle(x, y, radius, paint);

                    holder.unlockCanvasAndPost(canvas);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

//            String str = "想不到一句很经典的话来表达此刻的想法";
//            for (int i = 0; i < str.length()+1; i++) {
//                synchronized (holder) {
//                    try {
//                        Thread.sleep(500);
//                        canvas = holder.lockCanvas(new Rect(100,76,100+24*(i+1),124));
//                        canvas.drawText(str.substring(0, i), 100, 100, paint);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } finally {
//                        try {
//                            if (canvas != null) {
//                                holder.unlockCanvasAndPost(canvas);
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
}