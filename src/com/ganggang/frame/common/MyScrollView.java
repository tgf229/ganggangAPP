package com.ganggang.frame.common;

import com.ganggang.frame.util.MyLogUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;
/**
 * 
 *主要是为了解决与ViewPager 的时间冲突  
 * @author 曾宝
 * @version  [V1.00, 2015年7月28日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class MyScrollView extends ScrollView
{
    private float xDistance;
    
    private float yDistance;
    
    private float xLast;
    
    private float yLast;
    
    private final String TAG=MyScrollView.class.getSimpleName();
    
    public MyScrollView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }
    
    public MyScrollView(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }
    
    public MyScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0.0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
//               MyLogUtils.i(TAG, curX+":"+curY);
                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                
                if (xDistance > yDistance)
                    return false;
                    
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
