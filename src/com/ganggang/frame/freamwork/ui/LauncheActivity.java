package com.ganggang.frame.freamwork.ui;



import com.ganggang.frame.freamwork.logic.BaseLogicBuilder;
import com.ganggang.frame.util.MyLogUtils;

import android.content.Context;
import android.os.Bundle;



/**
 * 
 *  
 * @author 曾宝
 * @version  [V1.00, 2015年7月14日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public abstract class LauncheActivity extends BaseActivity
{
    
    /**
     * DEBUG_TAG
     */
    private static final String TAG = "LauncheActivity";
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if (!isInit())
        {
            BaseLogicBuilder builder = createLogicBuilder(this.getApplicationContext());
            super.setLogicBuilder(builder);
            MyLogUtils.d(TAG, "LogicBuilder loaded successfully!");
            initSystem();
        }
        super.onCreate(savedInstanceState);
    }
    
    /**
     * 
     * <功能详细描述>
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     */
    protected abstract BaseLogicBuilder createLogicBuilder(Context context);
    
    /**
     * 
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    protected abstract void initSystem();
    
}
