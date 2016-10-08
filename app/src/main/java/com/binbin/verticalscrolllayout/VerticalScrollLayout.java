package com.binbin.verticalscrolllayout;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by -- on 2016/9/30.
 * 可用做广告启动页，垂直滑动
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class VerticalScrollLayout extends LinearLayout {
    /** 用于滑动的类*/
    private Scroller mScroller;
    /** 用来跟踪触摸速度的类*/
    private VelocityTracker mVelocityTracker;
    /** 最小滑动的速度*/
    private static final int SNAP_VELOCITY = 400;
    /**最小滑动距离，超过了，才认为开始滑动  */
    private int mTouchSlop = 0 ;
    /**上次触摸的X坐标*/
    private float mLastX = -1;
    /**上次触摸的Y坐标*/
    private float mLastY = -1;
    private Context mContext;
    private List<View> mViews=new ArrayList<View>();
    /**view的高度*/
    private int CONTENT_HEIGHT=0;
    /**是否正在左右滑动*/
    private boolean isSliding=false;
    private int currentScreen=0;
    private boolean isFinished=false;
    private int totalScrollY;
    private int velocityY;
    /**滑动比例*/
    private int ratio;


    public VerticalScrollLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public VerticalScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public VerticalScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        init(context);
    }

	private void init(Context context) {
        this.mContext=context;
        mScroller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setOrientation(VERTICAL);
    }
	
    public void addViews(List<View> mViews){
    	this.mViews=mViews;
    	for(int i=0;i<mViews.size();i++){
    		addView(mViews.get(i),i,new LayoutParams(-1,-1));
    	}
    }

    /**
     * 首先执行onMeasure，然后就会执行onLayout
     * 为子View指定位置：相对父控件的位置！！！！！！
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if(changed&&CONTENT_HEIGHT==0){
            CONTENT_HEIGHT=((Activity)getContext()).getWindow().getDecorView().findViewById(Window.ID_ANDROID_CONTENT).getHeight();
            ratio=CONTENT_HEIGHT/3;
        }
//        Log.e("tianbin",CONTENT_HEIGHT+"#########"+right+"#"+bottom);

        for(int i=0;i<mViews.size();i++){
        	mViews.get(i).layout(0, i*CONTENT_HEIGHT, right, (i+1)*CONTENT_HEIGHT);
        }
    }

    /**
     * 滚动，startX, startY为开始滚动的位置（包括移出屏幕的地方的坐标，一般以滑动的距离为起点）
     * dx,dy为滚动的偏移量（正值代表向左，向上移动，负值代表向右，向下移动）
     * duration为完成滚动的时间
     * mScroller.startScroll(int startX, int startY, int dx, int dy)
     * 使用默认完成时间250ms
     * mScroller.startScroll(int startX, int startY, int dx, int dy, int duration)
     */
    @SuppressLint("NewApi")
	@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (mVelocityTracker == null) {
            // 使用obtain方法得到VelocityTracker的一个对象
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);
//        mVelocityTracker.computeCurrentVelocity(1000);
//        // 获得当前的速度
//        int velocityY = (int) mVelocityTracker.getYVelocity();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX=ev.getX();
                mLastY=ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.computeCurrentVelocity(1000);
                // 获得当前的速度
                velocityY = (int) mVelocityTracker.getYVelocity();
                // 计算当前的速度
                if(Math.abs(velocityY)>SNAP_VELOCITY||
                		Math.abs(mLastY-ev.getY())>mTouchSlop&&
                		Math.abs(mLastX-ev.getX())<mTouchSlop){
                    isSliding=true;

                    int deltaY = (int) (mLastY - ev.getY());
                    mLastY = ev.getY();
                    mLastX=ev.getX();
                    totalScrollY+=deltaY;

                    scrollBy(0,deltaY);
                    Log.e("tianbin",getScrollY()+"tianbin@@@@@moveeeeeeeee@@@@@@@"+totalScrollY);
                }
                break;
            default:
                //总共滑动的距离：getScrollY()（负值代表向下）
                isSliding=false;
                //速度加滑动距离满足一个即自动显示或隐藏
                int delta=0;
                int scrollY=getScrollY()%CONTENT_HEIGHT;

                Log.e("tianbin",getScrollY()+"tianbin@@@@@uppppppp"+"###"+totalScrollY+"###"+scrollY);
                
                //当前为第一屏或者滑动距离不够的时候，向下滑动自动向上回弹
                if((Math.abs(totalScrollY)<ratio||
                		currentScreen==0)&&
                		totalScrollY<0){
                	if(currentScreen==0){
                		delta=getScrollY();
                	}else{
                		//非第一屏，getScrollY()小于屏幕高度，但为正值
                		delta=currentScreen*CONTENT_HEIGHT-getScrollY();
                	}
                    mScroller.startScroll(0,getScrollY(), 0,Math.abs(delta));
                    invalidate();
                }
                //当前为最后一屏或者滑动距离不够的时候，向上滑动自动向下回弹
                if((Math.abs(totalScrollY)<ratio||
                		currentScreen==mViews.size()-1)&&
                		totalScrollY>0){
                	delta=getScrollY()-currentScreen*CONTENT_HEIGHT;
                    mScroller.startScroll(0,getScrollY(), 0,-Math.abs(delta));
                    invalidate();
                }
                
                
                //当前屏是中间屏，且滑动距离或者滑动速度满足需求，则自动向上或者向下滑一屏
                if(/**Math.abs(velocityY)>SNAP_VELOCITY||*/
                		Math.abs(totalScrollY)>=ratio){
                	if(currentScreen!=mViews.size()-1&&totalScrollY>0){
                		//向上滑动，自动切下一屏
                		delta=(currentScreen+1)*CONTENT_HEIGHT-getScrollY();
                        mScroller.startScroll(0,getScrollY(), 0,Math.abs(delta));
                        invalidate();
                        currentScreen++;
                        isFinished=true;//切屏才算结束，回弹不算
                	}
                	if(currentScreen!=0&&totalScrollY<0){
                		delta=getScrollY()-(currentScreen-1)*CONTENT_HEIGHT;
                    	mScroller.startScroll(0,getScrollY(), 0,-Math.abs(delta));
                        invalidate();
                    	currentScreen--;
                        isFinished=true;//切屏才算结束，回弹不算
                	}
                }
                
                totalScrollY=0;
                if (mVelocityTracker != null) {
                	mVelocityTracker.clear();
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
        }
        return true;
    }

    /**
     * ViewGroup在分发绘制自己的孩子的时候，会对其子View调用computeScroll()方法
     */
    @SuppressLint("NewApi")
	@Override
    public void computeScroll() {
        // TODO Auto-generated method stub
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }else{//滑动结束后
            if(isFinished){
                isFinished=false;
                Log.e("tianbin","tianbincomplete-------------"+getScrollY());
                Log.e("tianbin","tianbincomplete3333-------------"+getScrollY());
            }
        }
    }
}
