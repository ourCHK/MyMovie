package com.chk.mymovie.myview;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by chk on 17-5-22.
 */

public class MyTextView2 extends TextView {

    Paint paint1;
    Paint paint2;
    int mViewWidth;
    int mViewHeight;
    int mTranslate;
    LinearGradient mLinearGradient;
    Matrix mGradientMatrix;

    public MyTextView2(Context context) {
        super(context);
        init();
    }

    public MyTextView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        paint1 = new Paint();
        paint1.setColor(getResources().getColor(android.R.color.holo_blue_light));
        paint1.setStyle(Paint.Style.FILL);
        paint2 = new Paint();
        paint2.setColor(Color.YELLOW);
        paint2.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                paint1 = getPaint();
                mLinearGradient = new LinearGradient(0,0,mViewWidth,0,new int[] {Color.BLUE,0xff43fa32,Color.BLUE},null, Shader.TileMode.CLAMP);
                paint1.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),paint1);
//        canvas.drawRect(10,10,getMeasuredWidth()-10,getMeasuredHeight()-10,paint2);
//        canvas.save();
//        canvas.translate(10,0);
        super.onDraw(canvas);
//        canvas.restore();
        if (mGradientMatrix != null) {
            mTranslate += mViewWidth/10;
            if (mTranslate >2*mViewWidth) {
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate,0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(100);
        }
    }
}
