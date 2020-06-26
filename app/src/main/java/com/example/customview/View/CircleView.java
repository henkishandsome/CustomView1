package com.example.customview.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CircleView extends View {
    private Paint mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mColor= Color.BLUE;
    public CircleView(Context context){
        super(context);
        initDraw();
    }
    public CircleView(Context context, AttributeSet attrs){
        super(context, attrs);
        initDraw();
    }
    public CircleView(Context context, AttributeSet attrs,int defStyleAttr){
        super(context, attrs, defStyleAttr);
        initDraw();
    }
    private void initDraw(){
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth((float) 1.5);
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        int width=getWidth();
        int height=getHeight();
        canvas.drawCircle(width/2,height/2,100,mPaint);
    }
}
