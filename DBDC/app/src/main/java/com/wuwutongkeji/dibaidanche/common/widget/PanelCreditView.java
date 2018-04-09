package com.wuwutongkeji.dibaidanche.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.common.util.DeviceUtil;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;


/**
 * Created by Mr.Bai on 2017/9/26.
 */

public class PanelCreditView extends View{

    final int max_num = 90;

    float angle = 360f / max_num;

    int driverHeight = DeviceUtil.dp2px(10);

    String credit;


    Paint mDriverPaint;
    Paint mTxtPaint;

    public PanelCreditView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mDriverPaint = new Paint();
        mDriverPaint.setAntiAlias(true);
        mDriverPaint.setStrokeWidth(DeviceUtil.dp2px(1));
        mDriverPaint.setColor(getResources().getColor(R.color.colorAccent));

        mTxtPaint = new Paint();
        mTxtPaint.setAntiAlias(true);
        mTxtPaint.setTextSize(DeviceUtil.sp2px(50));
        mTxtPaint.setColor(getResources().getColor(R.color.colorAccent));
        mTxtPaint.setTextAlign(Paint.Align.CENTER);

        setCredit("128");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int radiusX = getWidth() / 2;
        int radiusY = getHeight() / 2;

        canvas.translate( radiusX , radiusY );
        canvas.save();

        float startX = 0;
        float startY = -radiusY;
        float endX = 0 ;
        float endY = - (radiusY - driverHeight);

        for(int i = 0 ; i < max_num ; i ++){
            canvas.rotate(angle, 0, 0);
            canvas.drawLine(startX, startY, endX,endY, mDriverPaint);
            canvas.save();
        }

        Paint.FontMetrics fontMetrics = mTxtPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;

        Rect rect = new Rect(-radiusX,-radiusY,radiusX,radiusY);

        int baseLineY = (int) (rect.centerY() - top/2 - bottom/2);

        if(!TextUtil.isEmpty(credit)){
            canvas.drawText(credit,rect.centerX(),baseLineY,mTxtPaint);
        }
    }

    public void setCredit(String credit){
        this.credit = credit;
        invalidate();
    }
}
