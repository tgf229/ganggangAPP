package com.ganggang.frame.constant.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
/**
 * 加载的listview，用来控制是/否 能上拉，或者下拉
 * @author 曾宝
 * @version  [V1.00, 2015年8月5日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class PullListView extends ListView implements Pullable
{
    /** 
     * <默认构造函数>
     */
    public PullListView(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }

    /** 
     * <默认构造函数>
     */
    public PullListView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    /** 
     * <默认构造函数>
     */
    public PullListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }


    @Override
    public boolean canPullDown()
    {
        if(getCount()==0)
        {//listview 没有加载内容,
            return true;
        }
        else if(getFirstVisiblePosition()==0 && getChildAt(0).getTop()>=0)
        {
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean canPullUp()
    {
        if(getCount()==0)
        {
            return true;
        }
        else if(getLastVisiblePosition()==(getCount()-1))
        {
            if(getChildAt(getLastVisiblePosition()-getFirstVisiblePosition())!=null
                &&getChildAt(
                    getLastVisiblePosition()
                    - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
            {
                return true;
            }
        }
        return false;
    }

}
