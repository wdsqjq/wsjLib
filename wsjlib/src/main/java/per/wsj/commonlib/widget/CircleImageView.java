package per.wsj.commonlib.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

/**
 * Created by shiju.wang on 2017/10/30.
 */

public class CircleImageView extends androidx.appcompat.widget.AppCompatImageView {

    // 控件宽高
    private int mWidth, mHeight;
    // 缩放矩阵
    private Matrix matrix;
    // 画笔
    private Paint paint;
    // 着色器
    private BitmapShader shader;
    // 图片的实际宽高
    private int bWidth, bHeight;

    public CircleImageView(Context context) {
        super(context);
        init();
    }

    public CircleImageView(Context context, @androidx.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleImageView(Context context, @androidx.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        matrix = new Matrix();
        paint = new Paint();
        paint.setAntiAlias(true);

        Bitmap bitmap = ((BitmapDrawable) getDrawable()).getBitmap();
        //根据bitmap创建Shader
        shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        // 获取缩放比例
        bWidth = bitmap.getWidth();
        bHeight = bitmap.getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        float scaleX = (float) mWidth / bWidth;
        float scaleY = (float) mHeight / bHeight;
        float scale = Math.min(scaleX, scaleY);

        matrix.reset();
        // 创建矩阵设置缩放比例
        matrix.postScale(scale, scale);
        // 计算缩放矩阵
        if (scaleX < scaleY) {
            matrix.postTranslate(-(bWidth * scale - mWidth) / 2, 0);
        } else {
            matrix.postTranslate(0, -(bHeight * scale - mHeight) / 2);
        }

        // shader设置矩阵
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        if (null == getDrawable()) {
            super.onDraw(canvas);
            return;
        }

        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, paint);
    }
}
