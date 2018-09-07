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

    private static final String STATE_INSTANCE = "state_instance";
    private static final String STATE_TYPE = "state_type";
    private static final String STATE_BORDER_RADIUS = "state_border_radius";
    private int mWidth,mHeight;

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=getMeasuredWidth();
        mHeight=getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        if(null==getDrawable()){
            super.onDraw(canvas);
            return;
        }

        Bitmap bitmap=((BitmapDrawable)getDrawable()).getBitmap();
        //根据bitmap创建Shader
        BitmapShader shader=new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        // 获取缩放比例
        int bWidth=bitmap.getWidth();
        int bHeight=bitmap.getHeight();
        float scaleX=(float) mWidth/bWidth;
        float scaleY=(float)mHeight/bHeight;
        float scale=Math.max(scaleX,scaleY);

        // 创建矩阵设置缩放比例
        Matrix matrix=new Matrix();
        matrix.postScale(scale,scale);
        //
        if(scaleX<scaleY){
            matrix.postTranslate(-(bWidth*scale-mWidth)/2,0);
        }else{
            matrix.postTranslate(0,-(bHeight*scale-mHeight)/2);
        }
        // shader设置矩阵
        shader.setLocalMatrix(matrix);
        
        Paint paint=new Paint();
        paint.setShader(shader);

        canvas.drawCircle(mWidth/2,mHeight/2,mWidth/2,paint);
        
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
//        bundle.putInt(STATE_TYPE, type);
//        bundle.putInt(STATE_BORDER_RADIUS, mBorderRadius);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle)
        {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(((Bundle) state)
                    .getParcelable(STATE_INSTANCE));
//            this.type = bundle.getInt(STATE_TYPE);
//            this.mBorderRadius = bundle.getInt(STATE_BORDER_RADIUS);
        } else
        {
            super.onRestoreInstanceState(state);
        }
    }
    
}
