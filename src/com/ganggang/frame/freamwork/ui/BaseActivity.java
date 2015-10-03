/**
 * 
 */
package com.ganggang.frame.freamwork.ui;

import java.util.HashSet;
import java.util.Set;

import com.ganggang.frame.freamwork.logic.BaseLogicBuilder;
import com.ganggang.frame.freamwork.logic.ILogic;
import com.ganggang.frame.util.MyLogUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;



/**
 * 
 *activity底层  
 * @author 曾宝
 * @version  [V1.00, 2015年7月14日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public abstract class BaseActivity extends FragmentActivity
{
    
    /**
     * DEBUG_TAG
     */
    private  final String TAG = "BaseActivity";
    
    /**
     * 逻辑绑定
     */
    private static BaseLogicBuilder mLogicBuilder = null;
    
    /**
     * Activity handler
     */
    private Handler mHanlder = null;
    
    /**
     * handler 属性
     */
    private boolean isPrivateHandler = false;
    
    /**
     * Logic
     */
    private Set<ILogic> mLogicSet = new HashSet<ILogic>();
    
    /**
     * 
     * 重写方法
     * @param savedInstanceState
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        MyLogUtils.d(TAG, "isInit() = " + isInit());
        if (!isInit())
        {
            MyLogUtils.e(TAG,
                    "Launched the first should be the LauncheActivity's subclass:"
                            + this.getClass().getName(),new Throwable());
            return;
        }
        
        if (!isPrivateHandler())
        {
            BaseActivity.mLogicBuilder.addHandlerToAllLogic(getHandler());
        }
        
        try
        {
            initLogic();
        }
        catch (Exception e)
        {
           /* Toast.makeText(this.getApplicationContext(), "Init logics failed :"
                    + e.getMessage(), Toast.LENGTH_LONG).show();;*/
            MyLogUtils.e(TAG, "Init logics failed :" + e.getMessage(), e);
        }
        

        
    }
    
    
    /**
     * 设置builder
     * <功能详细描述>
     * @param builder
     * @see [类、类#方法、类#成员]
     */
    protected static final void setLogicBuilder(BaseLogicBuilder builder)
    {
        BaseActivity.mLogicBuilder = builder;
    }
    
    /**
     *注册逻辑层 
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    protected abstract void initLogic();
    
    /**
     *获取handler 
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    private Handler getHandler()
    {
        if (null == mHanlder)
        {
            mHanlder = new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {
                    BaseActivity.this.handleStateMessage(msg);
                }
            };
        }
        return mHanlder;
    }
    
    protected boolean isPrivateHandler()
    {
        return isPrivateHandler;
    }
    
    /**
     *发送消息 
     * <功能详细描述>
     * @param msg
     * @see [类、类#方法、类#成员]
     */
    protected void handleStateMessage(Message msg)
    {
        
    }
    
    /**
     *初始化是否成功 
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    protected boolean isInit()
    {
        return mLogicBuilder != null;
    }
    
    /**
     * 获取注册好的逻辑层
     * <功能详细描述>
     * @param interfaceClass
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("null")
    protected final ILogic getLogicByInterfaceClass(Class<?> interfaceClass)
    {
        ILogic logic = mLogicBuilder.getLogicByInterface(interfaceClass);
        MyLogUtils.d(TAG, "logic = " + (null == logic));
        
        if (isPrivateHandler() && null != logic && !mLogicSet.contains(logic))
        {
            mLogicSet.add(logic);
        }
        
        if (null == logic)
        {
            MyLogUtils.e(TAG, "Logic not found: " + logic.getClass().getName());
            return null;
        }
        
        return logic;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void onDestroy()
    {
        Handler handler = getHandler();
        
        if (null != handler)
        {
            if (isPrivateHandler() && null != mLogicSet)
            {
                for (ILogic logic : mLogicSet)
                {
                    logic.removeHandler(handler);
                }
            }
            else if (null != mLogicBuilder)
            {
                mLogicBuilder.removeHandlerFromAllLogic(handler);
            }
        }
        super.onDestroy();
    }
}
