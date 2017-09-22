package cailu.org.mysurfaceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import cailu.org.mysurfaceview.util.BitmapCache;
import cailu.org.mysurfaceview.util.Font32;

/**
 * Created by dell 王世举 on 2016/12/31 16:45.
 */

public class Love extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder holder;
    // 图片软引用
    BitmapCache bitmapcache;

    private Font32 font32;
    private android.content.Context mContext;

    public Love(Context context) {
        super(context);
        this.bitmapcache = BitmapCache.getInstance();
        font32 = new Font32(context);
        mContext = context;
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new DrawThread("芹", 0).start();
        new DrawThread("芹", 1).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    class DrawThread extends Thread {
        String str = "";
        int position;

        public DrawThread(String str, int position) {
            this.str = str;
            this.position = position;
        }

        @Override
        public void run() {
            switch (position) {
                case 0:
                    show_font32(str, 50, 50, 8, 2);
                    break;
                case 1:
                    show_font32(str, 400, 50, 8, 2);
                    break;
            }
        }
    }

    public void show_font32(String s, int startx, int starty,
                            int bei, int type) {
        boolean[][] arr = new boolean[32][32]; // 插入的数组
        arr = font32.drawString(s);
        Bitmap bitmap = bitmapcache.getBitmap(R.mipmap.love,
                mContext);
        int bw = bitmap.getWidth();
        int bh = bitmap.getHeight();
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                float xx = (float) j;
                float yy = (float) i;
                if (arr[i][j]) {
                    synchronized (holder) {
                        Canvas c = null;
                        try {
                            // 不要轻易去锁定整个屏幕
                            c = holder.lockCanvas(new Rect(startx + (int) xx
                                    * bei, starty + (int) yy * bei, startx
                                    + (int) xx * bei + bw, starty + (int) yy
                                    * bei + bh));

                            Paint p = new Paint(); // 创建画笔
                            p.setColor(Color.RED);
                            // 下面这段是保证双缓冲能都画上东西，从而不会闪烁

                            c.drawBitmap(bitmap, startx + xx * bei, starty + yy
                                    * bei, p);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (c != null) {
                                    holder.unlockCanvasAndPost(c);// 结束锁定画图，并提交改变。
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
}
