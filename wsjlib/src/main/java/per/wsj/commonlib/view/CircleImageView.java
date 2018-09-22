package per.wsj.commonlib.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by shiju.wang on 2017/10/30.
 */

public class CircleImageView extends android.support.v7.widget.AppCompatImageView {

    private int mWidth, mHeight;

    private Matrix matrix;
    private Paint paint;

    public CircleImageView(Context context) {
        super(context);
        init();
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        matrix = new Matrix();
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        if (null == getDrawable()) {
            super.onDraw(canvas);
            return;
        }

        Bitmap bitmap = ((BitmapDrawable) getDrawable()).getBitmap();
        //根据bitmap创建Shader
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        // 获取缩放比例
        int bWidth = bitmap.getWidth();
        int bHeight = bitmap.getHeight();
        float scaleX = (float) mWidth / bWidth;
        float scaleY = (float) mHeight / bHeight;
        float scale = Math.min(scaleX, scaleY);

        // 创建矩阵设置缩放比例
        matrix.postScale(scale, scale);
        //
        if (scaleX < scaleY) {
            matrix.postTranslate(-(bWidth * scale - mWidth) / 2, 0);
        } else {
            matrix.postTranslate(0, -(bHeight * scale - mHeight) / 2);
        }
        // shader设置矩阵
        shader.setLocalMatrix(matrix);

        paint.setShader(shader);

        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, paint);
    }
}
