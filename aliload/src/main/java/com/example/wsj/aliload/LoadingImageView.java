package com.example.wsj.aliload;

import android.content.Context;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import per.wsj.commonlib.utils.BitmapUtil;

/**
 * Created by shiju.wang on 2017/10/17.
 */

public class LoadingImageView extends View {

    private Drawable srcImg;
    private Drawable desImg;
    private BitmapShader srcShader;
    private BitmapShader desShader;

    public LoadingImageView(Context context) {
        super(context);
        init(context);
    }

    public LoadingImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        desImg = getResources().getDrawable(R.mipmap.wave);
        desImg=getResources().getDrawable(R.mipmap.heart);

        srcShader=new BitmapShader(BitmapUtil.drawable2Bitmap(srcImg), Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        desShader=new BitmapShader(BitmapUtil.drawable2Bitmap(desImg), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        ComposeShader composeShader=new ComposeShader(shader2,shader1, PorterDuff.Mode.SRC_ATOP);
        //创建LinearGradient，用以产生从左上角到右下角的颜色渐变效果
        LinearGradient linearGradient = new LinearGradient(0, 0, desImg.getIntrinsicWidth(), desImg.getIntrinsicHeight(), Color.GREEN, Color.BLUE, Shader.TileMode.CLAMP);
        //bitmapShader对应目标像素，linearGradient对应源像素，像素颜色混合采用MULTIPLY模式
        ComposeShader composeShader = new ComposeShader(desShader, srcShader, PorterDuff.Mode.DST_IN);

        Paint paint=new Paint();
        paint.setShader(composeShader);
        canvas.drawRect(0,0,desImg.getIntrinsicWidth(),desImg.getIntrinsicHeight(),paint);

    }
}
