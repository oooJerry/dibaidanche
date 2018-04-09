package com.wuwutongkeji.dibaidanche.common.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Mr.Bai on 17/9/19.
 */

public class ProgressLoaingView extends View {

    private long countDownTime = 1000 * 20; //  秒到 95%

    private float offset; // 每个单位的长度 w / 100

    private int maxLength = 100;

    ValueAnimator valueAnimator;

    Paint mBgPaint;
    Paint mProgressPaint;

    Rect mBgRect;
    Rect mProgressRect;

    public ProgressLoaingView(Context context, AttributeSet attrs) {
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
        offset = getMeasuredWidth() / maxLength;

        mBgRect = new Rect(0,0,getMeasuredWidth(),getMeasuredHeight());
        mProgressRect = new Rect(0,0,0,getMeasuredHeight());
        onStart();
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

    public void onStart(){

        valueAnimator = ValueAnimator.ofInt(0,95)
                .setDuration(countDownTime);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                int value = (int) valueAnimator.getAnimatedValue();

                mProgressRect.right = (int) (value * offset);

                if(null != onUpdateListener){
                    onUpdateListener.onProgress(value);
                }
                invalidate();
            }
        });

        valueAnimator.start();
    }

    public void onStop(){
        valueAnimator.cancel();
        mProgressRect.right = getMeasuredWidth();
        if(null != onUpdateListener){
            onUpdateListener.onProgress(maxLength);
        }
        mProgressRect.right = mBgRect.right;
        invalidate();
    }

    private UpdateListener onUpdateListener;

    public void setOnUpdateListener(UpdateListener onUpdateListener){
        this.onUpdateListener = onUpdateListener;
    }
    public interface UpdateListener{
        void onProgress(int progress);
    }
}
