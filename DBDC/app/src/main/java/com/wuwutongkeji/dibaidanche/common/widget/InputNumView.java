package com.wuwutongkeji.dibaidanche.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.wuwutongkeji.dibaidanche.common.util.DeviceUtil;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mr.Bai on 17/9/20.
 */

public class InputNumView extends View {

    public static int MAX_NUMBER_LENGTH = 4; // 输入数字长度

    List<RectF> mTextRect = new LinkedList<>();

    String mText;

    Paint mBorderPaint;
    Paint mTxtPaint;

    public InputNumView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mBorderPaint = new Paint();
        mBorderPaint.setColor(Color.parseColor("#D9D9D9"));
        mBorderPaint.setStrokeWidth(DeviceUtil.dp2px(1));
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);

        mTxtPaint = new Paint();
        mTxtPaint.setColor(Color.parseColor("#ff8400"));
        mTxtPaint.setTextSize(DeviceUtil.sp2px(40));
        mTxtPaint.setTextAlign(Paint.Align.CENTER);
        mTxtPaint.setAntiAlias(true);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(!mTextRect.isEmpty()){
            return;
        }
        int childWidth = DeviceUtil.dp2px(40);
        int childHeight = DeviceUtil.dp2px(56);

        int avgWidth = getMeasuredWidth() / MAX_NUMBER_LENGTH;

        for(int i = 0 ; i < MAX_NUMBER_LENGTH ; i++){

            RectF rectF = new RectF();

            if(i == 0){
                rectF.left = ( avgWidth - childWidth ) / 2;
            }else{
                rectF.left = avgWidth * i + ( avgWidth - childWidth ) / 2;
            }

            rectF.top = 1 ;
            rectF.right = rectF.left + childWidth;
            rectF.bottom = rectF.top + childHeight;

            mTextRect.add(rectF);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i = 0 ; i < mTextRect.size() ; i ++){

            RectF rectF = mTextRect.get(i);

            float angle = DeviceUtil.dp2px(4);
            canvas.drawRoundRect(rectF,angle,angle,mBorderPaint);

            Paint.FontMetrics fontMetrics = mTxtPaint.getFontMetrics();
            float top = fontMetrics.top;
            float bottom = fontMetrics.bottom;

            int baseLineY = (int) (rectF.centerY() - top/2 - bottom/2);

            if(!TextUtil.isEmpty(mText) && mText.length() > i){
                canvas.drawText(String.valueOf(mText.charAt(i)),rectF.centerX(),baseLineY,mTxtPaint);
            }

        }
    }

    public void setTxt(String mText){
        this.mText = mText;
        invalidate();
    }
}
