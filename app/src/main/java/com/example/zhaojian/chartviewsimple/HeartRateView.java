package com.example.zhaojian.chartviewsimple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhaojian on 2018/2/27.
 * test
 */

public class HeartRateView extends View
{
    private float defaultMargin = 10;
    private float totalWidth = 0;
    private float totalHeight = 0;
    private float marginSide = 0;

    private int heartRate = 0;

    private Paint paint;

    public HeartRateView(Context context)
    {
        super(context);
    }

    public HeartRateView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public HeartRateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        drawHeartRatetText(canvas);
        drawNumber(canvas);
        drawRectangle(canvas);
    }

    /**
     * 画底部的矩形
     * @param canvas
     */
    private void drawRectangle(Canvas canvas)
    {
        float height = totalHeight/4;
        float y = totalHeight*3/4;
        paint = new Paint();
        //渐变色
        Shader mShader = new LinearGradient(0,y+height/2,totalWidth,y+height/2,new int[] {0xFFff3300,0xFFE6D229,0xFFff3300},null,Shader.TileMode.REPEAT);
        paint.setShader(mShader);
        RectF rectF = new RectF();
        rectF.left = marginSide;
        rectF.right = totalWidth-marginSide;
        rectF.top = y;
        rectF.bottom = y + height;
        //画圆角矩形
        canvas.drawRoundRect(rectF, height / 2, height / 2, paint);

        //画刻度
        paint.reset();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(3);
        //两个刻度之间的距离,共46个刻度，45个间距
        float unit = (totalWidth - height-marginSide*2)/45;
        //刻度的高度
        float unitHeight = 15;
        float unitX = height/2+marginSide;
        float unitY = y + height / 2;
        for (int i = 0; i < 46; i++)
        {
            if (i % 5 == 0)
            {
                canvas.drawLine(unitX,unitY,unitX,unitY-unitHeight*2,paint);
            }
            else
            {
                canvas.drawLine(unitX,unitY,unitX,unitY-unitHeight,paint);
            }
            unitX = unitX + unit;
        }
    }

    /**
     * 画60 90 数字
     * @param canvas
     */
    private void drawNumber(Canvas canvas)
    {
        float height = totalHeight/4;
        float y = totalHeight*2/4;
        paint = new Paint();
        paint.setTextSize(60);
        paint.setColor(0xff999999);
        Rect bounds = new Rect();
        paint.getTextBounds("60", 0, 2, bounds);
        float xDistance = height / 2 + marginSide + (totalWidth - height - marginSide * 2) * (1f / 3) - bounds.width() / 2;
        canvas.drawText("60", xDistance, y + height - defaultMargin, paint);
        xDistance = height / 2 + marginSide + (totalWidth - height - marginSide * 2) * (2f / 3) - bounds.width() / 2;
        canvas.drawText("90",xDistance,y + height - defaultMargin, paint);
    }

    /**
     * 画第一部分 心率 xxx bmp
     * @param canvas
     */
    private void drawHeartRatetText(Canvas canvas)
    {
        float rectangleHeight = totalHeight/4;
        //心率值对应图上的比重
        float weight = getWeight();
        float xDistance = (totalWidth - rectangleHeight - marginSide * 2) * weight + rectangleHeight / 2 + marginSide;
        paint = new Paint();
        paint.setTextSize(160);
        paint.setStrokeWidth(10);
        paint.setColor(0xff2CC1A4);
        Rect bounds = new Rect();
        String textStr = heartRate + "";
        paint.getTextBounds(textStr, 0, textStr.length(), bounds);
        float xOffSet = bounds.width()/2;
        canvas.drawText(textStr,xDistance-xOffSet,totalHeight/2, paint);

        paint.reset();
        paint.setColor(0xff999999);
        paint.setTextSize(50);
        paint.getTextBounds("心率", 0, 2, bounds);
        canvas.drawText("心率", xDistance - xOffSet - bounds.width() - defaultMargin * 2, totalHeight / 2, paint);

        paint.setColor(0xff2CC1A4);
        canvas.drawText("bmp", xDistance + xOffSet + defaultMargin*2, totalHeight / 2, paint);

        paint.reset();
        paint.setColor(0xff2CC1A4);
        Path path = new Path();
        float originX = xDistance;
        float height = totalHeight/4;
        float originY = totalHeight*3/4-defaultMargin;
        path.moveTo(originX,originY);
        path.lineTo(xDistance - height / 6, originY - height / 3);
        path.lineTo(xDistance - height / 6, originY - height * 2 / 3);
        path.lineTo(xDistance + height / 6, originY - height * 2 / 3);
        path.lineTo(xDistance + height / 6, originY - height / 3);
        path.lineTo(originX,originY);
        canvas.drawPath(path,paint);
    }

    private float getWeight()
    {
        float weight;
        if (heartRate > 30 && heartRate < 120)
        {
            weight = (heartRate-30)/90f;
        }
        else if (heartRate <= 30)
        {
            weight = 0;
        }
        else
        {
            weight = 1;
        }
        return weight;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        totalHeight = getHeight();
        totalWidth = getWidth();
        marginSide = getWidth()/5;
    }

    public void setHeartRate(int heartRate)
    {
        this.heartRate = heartRate;
        invalidate();
    }
}
