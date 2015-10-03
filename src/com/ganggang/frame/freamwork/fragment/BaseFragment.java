package com.ganggang.frame.freamwork.fragment;

import java.util.HashSet;
import java.util.Set;

import com.ganggang.frame.freamwork.logic.BaseLogicBuilder;
import com.ganggang.frame.freamwork.logic.ILogic;
import com.ganggang.ganggangapp.logic.LogicBuilder;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 *自定义fragment 的基础类  
 * @author 曾宝
 * @version  [V1.00, 2015-3-31]
 * @see  [相关类/方法]
 * @since V1.00
 */
public abstract class BaseFragment extends Fragment
{
    //TAG
    private static final  String TAG=BaseFragment.class.getSimpleName();
    //逻辑层注册
    private static BaseLogicBuilder mLogicBuilder = null;
    //消息
    private Handler mHandler = null;
    
    private boolean IsPrivateHandler = false;
    
    private Set<ILogic> mLogicSet = new HashSet<ILogic>();
    
    protected boolean isVisible;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(!IsPrivateHandler)
        {
            if(!Isinit())
            {
                BaseFragment.mLogicBuilder = LogicBuilder.getInstance(getActivity());
            }
            BaseFragment.mLogicBuilder.addHandlerToAllLogic(getHandler());
        }
        
        initLogic();
    }
    

    
    /**
     *注册逻辑层 
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    protected abstract void initLogic();

    
    private Handler getHandler()
    {
        if(null==mHandler)
        {
            mHandler = new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {
                  BaseFragment.this.handleStateMessage(msg);
                }
            };
        }
        return mHandler;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    /**
     *消息处理 
     * <功能详细描述>
     * @param msg
     * @see [类、类#方法、类#成员]
     */
    protected void handleStateMessage(Message msg)
    {
        
    }
    
    protected boolean Isinit()
    {
        return mLogicBuilder!=null;
    }
    
    protected boolean isPrivateHandler()
    {
        return IsPrivateHandler;
    }
    
    /**
     *获取绑定 的逻辑层 
     * <功能详细描述>
     * @param interfaceClass
     * @return
     * @see [类、类#方法、类#成员]
     */
    protected final ILogic getLogicByInterfaceClass(Class<?> interfaceClass)
    {
        ILogic iLogic = mLogicBuilder.getLogicByInterface(interfaceClass);
//        Log.d(TAG, "logic = " + (null == iLogic));
        if (isPrivateHandler() && null != iLogic && !mLogicSet.contains(iLogic))
        {
            mLogicSet.add(iLogic);
        }
        if(null == iLogic)
        {
          //  Log.e(TAG, "Logic not found: " + iLogic.getClass().getName());
            return null;
        }
        return iLogic;
    }
    
}
