package com.ganggang.frame.freamwork;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View
{

    /** 
     * <默认构造函数>
     */
    public MyView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    /** 
     * <默认构造函数>
     */
    public MyView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    /** 
     * <默认构造函数>
     */
    public MyView(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
     
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
