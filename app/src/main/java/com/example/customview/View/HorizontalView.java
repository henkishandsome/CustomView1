package com.example.customview.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class HorizontalView extends ViewGroup {
    private int childWidth=0;
    private int lastInterceptX=0;
    private int lastInterceptY=0;
    private int lastX;
    private int lastY;
    private int currentIndex=0;
    private Scroller scroller;
    private VelocityTracker tracker;
    public HorizontalView(Context context) {
        super(context);
        init();
    }

    public HorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public void init(){
        scroller=new Scroller(getContext());
        tracker=VelocityTracker.obtain();
    }
    //处理滑动冲突
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event){
        boolean intercept=false;
        int x=(int) event.getX();
        int y=(int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercept=false;
                if (!scroller.isFinished()){
                    scroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX=x-lastInterceptX;
                int deltaY=y-lastInterceptY;
                if (Math.abs(deltaX)-Math.abs(deltaY)>0){
                    intercept=true;
                }else{
                    intercept=false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept=false;
                break;
        }
        lastX=x;
        lastY=y;
        lastInterceptX=x;
        lastInterceptY=y;
        return intercept;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        tracker.addMovement(event);
        int x=(int) event.getX();
        int y=(int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()){
                    scroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX=x-lastX;
                scrollBy(-deltaX,0);
                break;
            case MotionEvent.ACTION_UP:
                int distance=getScrollX()-currentIndex*childWidth;
                if (Math.abs(distance)>childWidth/2){
                    if (distance>0){
                        currentIndex++;
                    }else {
                        currentIndex--;
                    }
                }
                else {
                    tracker.computeCurrentVelocity(1000);
                    float xV=tracker.getXVelocity();
                    if (Math.abs(xV)>50){
                        //切换到上一个页面
                        if (xV>0){
                            currentIndex--;
                            //切换到下一个页面
                        }else {
                            currentIndex++;
                        }
                    }
                }
                currentIndex=currentIndex<0?0:currentIndex>getChildCount()-1?getChildCount()-1:currentIndex;
                smoothScrollTo(currentIndex*childWidth,0);
                tracker.clear();
                break;
                default:
                    break;
        }
        lastX=x;
        lastY=y;
        return true;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        //如果没有子元素，就设置宽和高都为0
        if (getChildCount()==0){
            setMeasuredDimension(0,0);
        }
        //宽和高都是AT_MOST,则宽度设置为所有子元素宽度的和，高度设置为第一个子元素的高度
        else if (widthMode==MeasureSpec.AT_MOST&&heightMode==MeasureSpec.AT_MOST){
            View childOne=getChildAt(0);
            int childWidth=childOne.getMeasuredWidth();
            int childHeight=childOne.getMeasuredHeight();
            setMeasuredDimension(childWidth*getChildCount(),childHeight);
        }
        //宽度是AT_MOST,则宽度为所有子元素的和
        else if (widthMode==MeasureSpec.AT_MOST){
            View childOne=getChildAt(0);
            int childWidth=childOne.getMeasuredWidth();
            setMeasuredDimension(childWidth*getChildCount(),heightSize);
        }
        //高度为AT_MOST,则高度为第一个子元素的高度
        else if (heightMode==MeasureSpec.AT_MOST){
            int childHeight=getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(widthSize,childHeight);
        }
    }
    @Override
    public void computeScroll(){
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            postInvalidate();
        }
    }
    //弹性滑动到指定位置
    public void smoothScrollTo(int destX,int destY){
        scroller.startScroll(getScrollX(),getScrollY(),destX-getScrollX(),destY-getScrollY(),1000);
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed,int l,int t,int r,int b){
        int childCount=getChildCount();
        int left=0;
        View child;
        for (int i=0;i<childCount;i++){
            child=getChildAt(i);
            if (child.getVisibility()!=View.GONE){
                int width=child.getMeasuredWidth();
                childWidth=width;
                child.layout(left,0,left+width,child.getMeasuredHeight());
                left+=width;
            }
        }
    }


}
