/**
 * 
 */
package com.ganggang.frame.freamwork.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.ganggangapp.constant.ConstantFile;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 *  appliction 底层
 * @author 曾宝
 * @version  [V1.00, 2015年7月14日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class MCActivityManager extends Application
{
    
    /**
     * DEBUG_TAG
     */
    private static final String TAG = "MCActivityManager";
    
    /**
     * Activity  */
    private Stack<Activity> mList = new Stack<Activity>();
    
    /**
     * 单例对象
     */
    private static MCActivityManager instance;
  
    /**
     * DEFAULT_CONSTRUCTOR
     */
    private MCActivityManager()
    {
     
    }
    

    public synchronized static MCActivityManager getInstance()
    {
        if (null == instance)
        {
            instance = new MCActivityManager();
        }
        return instance;
    }
    
    /**
     *添加Activity到Activity堆栈 
     * <功能详细描述>
     * @param activity
     * @see [类、类#方法、类#成员]
     */
    public void addActivity(Activity activity)
    {
        MyLogUtils.d(TAG, "activity = " + activity.getLocalClassName());
        mList.add(activity);
    }
    
    public void removeActivity(Activity activity)
    {
        mList.remove(activity);
    }
    
   /**
    * app退出时，关闭activity
    * <功能详细描述>
    * @see [类、类#方法、类#成员]
    */
    public void exit()
    {
        try
        {
            for (Activity activity : mList)
            {
                MyLogUtils.d(TAG,
                        mList.size() + "  " + "activity = "
                                + activity.getLocalClassName());
                if (activity != null)
                {
                    activity.finish();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            System.exit(0);
        }
    }
    
   /**
    * 清除activity 
    * <功能详细描述>
    * @see [类、类#方法、类#成员]
    */
    public void clearActivities()
    {
        try
        {
            for (Activity activity : mList)
            {
                MyLogUtils.d(TAG,
                        mList.size() + "  " + "activity = "
                                + activity.getLocalClassName());
                if (activity != null)
                {
                    activity.finish();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        System.gc();
    }
    
}
