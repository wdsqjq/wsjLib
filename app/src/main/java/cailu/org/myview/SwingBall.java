package cailu.org.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 画文字动画
 * Created by dell 王世举 on 2017/1/2 12:19.
 */

public class SwingBall extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private int mWidth, mHeight;
    private int radius = 20; //球的半径
    private int L = 400; // 线的长度
    private Path path;// 线的路径


    public SwingBall(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mWidth = getWidth();
        mHeight = getHeight();
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

            int flag=1;
            int a = 45;
            while (a >= -45) {
                try {
                    Thread.sleep(6);
                    canvas = holder.lockCanvas();
                    canvas.translate(mWidth / 2, mHeight / 2);
                    canvas.drawColor(Color.BLACK);

                    double b = a * Math.PI / 180;
                    float x = (float) (L * Math.sin(b));
                    float y = (float) (L * (Math.cos(b) - 1));

                    path = new Path();
                    path.moveTo(0, -L);
                    path.lineTo(x, y);
                    canvas.drawCircle(x, y, radius, paint);

                    canvas.drawPath(path, linePaint);

                    holder.unlockCanvasAndPost(canvas);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(flag>0)
                    a--;
                else a++;
                if(a==-45)
                    flag=-1;
                if(a==45)
                    flag=1;
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
}
