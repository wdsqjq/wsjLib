package per.wangsj.myview.love;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by adm on 2016/12/26.
 */

public class DrawHeartView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder mHolder;
    MyThread myThread;
    int mWidth, mHeight;

    public DrawHeartView(Context context) {
        super(context);
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.e("MySurfaceView", "surfaceCreated");
        mWidth = getWidth();
        mHeight = getHeight();
        myThread = new MyThread();
        myThread.isRun = true;
        myThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.e("MySurfaceView", "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.e("MySurfaceView", "surfaceDestroyed");
        myThread.isRun = false;
    }

    class MyThread extends Thread {
        public boolean isRun;

        public MyThread() {
            isRun = true;
        }

        @Override
        public void run() {
            while (isRun) {
                Paint paint = new Paint();// 创建画笔
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(8);
                paint.setColor(Color.GREEN);
                Canvas canvas = null;
                Path path = new Path();
                for (int i = 0 + 180; i < 361 + 180; i++) {
                    try {
                        Thread.sleep(10);// 睡眠时间为1秒
                        synchronized (mHolder) {
                            canvas = mHolder.lockCanvas();// 锁定画布，一般在锁定后就可以通过其返回的画布对象Canvas，在其上面画图等操作了。

                            double sit = i * 2 * Math.PI / 360d;

                            double x = 10 * 16 * Math.pow(Math.sin(sit), 3);
                            double y = 10 * (13 * Math.cos(sit) - 5 * Math.cos(2 * sit) - 2 * Math.cos(3 * sit) - Math.cos(4 * sit));
                            if (i == 180) {
                                path.moveTo(mWidth / 2 + (float) (x), mHeight / 2 - (float) (y));
                            }
                            path.lineTo(mWidth / 2 + (float) (x), mHeight / 2 - (float) (y));

                            if (canvas != null)
                                canvas.drawPath(path, paint);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (canvas != null) {
                                mHolder.unlockCanvasAndPost(canvas);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
