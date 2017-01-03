package cailu.org.mysurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 画文字动画
 * Created by dell 王世举 on 2017/1/2 12:19.
 */

public class DrawText extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;

    public DrawText(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
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
            paint.setTextSize(24);
            Canvas canvas = null;

//            Xfermode xFermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);
//            paint.setXfermode(xFermode);

            String str = "想不到一句很经典的话来表达此刻的想法";
            for (int i = 0; i < str.length()+1; i++) {
                synchronized (holder) {
                    try {
                        Thread.sleep(500);
                        canvas = holder.lockCanvas(new Rect(100,76,100+24*(i+1),124));
//                        canvas.drawColor(Color.TRANSPARENT, android.graphics.PorterDuff.Mode.OVERLAY);
                        canvas.drawText(str.substring(0, i), 100, 100, paint);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (canvas != null) {
                                holder.unlockCanvasAndPost(canvas);
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
