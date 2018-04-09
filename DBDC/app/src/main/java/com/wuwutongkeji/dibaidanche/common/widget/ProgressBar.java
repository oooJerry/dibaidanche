package com.wuwutongkeji.dibaidanche.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Mr.Bai on 17/10/13.
 */

public class ProgressBar extends View {

    Paint mBgPaint;
    Paint mProgressPaint;

    Rect mBgRect;
    Rect mProgressRect;

    float offset;

    public ProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        mBgPaint =  new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setColor(Color.parseColor("#eaeef3"));


        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setColor(Color.parseColor("#ff8400"));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        offset = getMeasuredWidth() / 100f;
        mBgRect = new Rect(0,0,getMeasuredWidth(),getMeasuredHeight());
        mProgressRect = new Rect(0,0,0,getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(null == mBgRect || null == mProgressRect){
            return;
        }
        canvas.drawRect(mBgRect,mBgPaint);
        canvas.drawRect(mProgressRect,mProgressPaint);
    }

    public void setProgress(int progress){
        if(null == mProgressRect){
            return;
        }
        mProgressRect.right = (int) (progress * offset);
        invalidate();
    }
}
