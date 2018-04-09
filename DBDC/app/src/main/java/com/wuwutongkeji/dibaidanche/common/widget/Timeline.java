package com.wuwutongkeji.dibaidanche.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.common.util.DeviceUtil;

/**
 * Created by Mr.Bai on 2017/9/12.
 */

public class Timeline extends View {

    private int currentIndex;

    public Timeline(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        if(null == attrs){
            throw  new RuntimeException("AttributeSet is Null ");
        }

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Timeline);

        currentIndex = array.getInteger(R.styleable.Timeline_currentIndex,0);

        array.recycle();
        initPaint();
    }


    int spaceX;
    int spaceY;
    int imgWH;

    Bitmap mCompleteBmp;
    Bitmap mProceedBmp;
    Bitmap mNotStartedBmp;

    Paint mBmpPaint;
    Paint mLinePaint;
    Paint mTxtPaint;

    String[] mTitle = new String[] {"手机验证" , "交纳押金" , "实名认证" , "完成"};

    private void initPaint(){

        mCompleteBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_complete_launch);
        mProceedBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_proceed_launch);
        mNotStartedBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_notstarted_launch);

        imgWH = mCompleteBmp.getWidth();

        mBmpPaint = new Paint();
        mBmpPaint.setAntiAlias(true);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);


        mTxtPaint = new Paint();
        mTxtPaint.setAntiAlias(true);
        mTxtPaint.setTextSize(DeviceUtil.dp2sp(12));
        mTxtPaint.setTextAlign(Paint.Align.CENTER);
        mTxtPaint.setColor(Color.parseColor("#555555"));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        spaceX = getMeasuredWidth() / 4 / 2;
        spaceY = getMeasuredHeight() / 2 -  imgWH / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i = 0 ; i < 4; i ++){

            Bitmap bmp ;

            if(i < currentIndex){
                bmp = mCompleteBmp;
            }else if( i == currentIndex){
                bmp = mProceedBmp;
            }else{
                bmp = mNotStartedBmp;
            }

            mLinePaint.setColor(i > currentIndex ? Color.parseColor("#D8D8D8")
                    : Color.parseColor("#58cd72"));
            int offsetX = getScrollOffsetX(i);
            canvas.drawBitmap(bmp,offsetX , spaceY,mBmpPaint);

            float xPos = offsetX + DeviceUtil.getTextWidth(mTxtPaint,mTitle[i])
                    / mTitle[i].length();
            float yPos = spaceY + imgWH * 1.5f + DeviceUtil.dp2px(8);
            canvas.drawText(mTitle[i],xPos,yPos,mTxtPaint);

            if(i > 0){

                int preOffsetX = getScrollOffsetX( i - 1);

                RectF rect = new RectF();
                rect.left = preOffsetX + imgWH + DeviceUtil.dp2px(8);
                rect.top = spaceY - DeviceUtil.dp2px(2) + imgWH / 2;
                rect.right = offsetX - DeviceUtil.dp2px(8);
                rect.bottom = rect.top + DeviceUtil.dp2px(2);

                float angle = DeviceUtil.dp2px(1);
                canvas.drawRoundRect(rect,angle,angle,mLinePaint);
            }
        }
    }

    private int getScrollOffsetX(int p){
        return (2 * ( p + 1 ) - 1) * spaceX;
    }
}
