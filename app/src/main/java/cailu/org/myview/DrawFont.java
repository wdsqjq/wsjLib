package cailu.org.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import per.wsj.commonlib.utils.Font32;


/**
 * 文字转Font32
 * Created by dell 王世举 on 2017/1/2 12:19.
 */

public class DrawFont extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private Font32 font32;

    public DrawFont(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        font32=new Font32(context);
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
            boolean[][] arr = new boolean[32][32]; // 插入的数组
            arr = font32.drawString("爱");
            Paint paint=new Paint();
            paint.setColor(Color.GREEN);
            Canvas canvas=null;
            canvas=holder.lockCanvas();

            for(int i=0;i<32;i++) {
                for (int j = 0; j < 32; j++) {
                    if(arr[i][j]){
                        canvas.drawText("0",10+j*10,10+i*10,paint);
                    }
                }
            }
            holder.unlockCanvasAndPost(canvas);
        }
    }
}
