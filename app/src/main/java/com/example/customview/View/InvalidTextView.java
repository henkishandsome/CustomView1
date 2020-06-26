package com.example.customview.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;
import android.annotation.SuppressLint;
@SuppressLint("AppCompatCustomView")

public class InvalidTextView extends TextView {
    private Paint mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    public InvalidTextView(Context context){
        super(context);
        initDraw();
    }
    public InvalidTextView(Context context, AttributeSet attrs){
        super(context,attrs);
        initDraw();
    }
    public InvalidTextView(Context context, AttributeSet attrs,int defStyleAttr){
        super(context, attrs, defStyleAttr);
        initDraw();
    }
    private void initDraw(){
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth((float) 1.5);
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        int width=getWidth();
        int height=getHeight();
        canvas.drawLines(new float[]{
                0,height/3,width,height/3,
                0,height*2/3,width,height*2/3
        },mPaint);
    }
}
