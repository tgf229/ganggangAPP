/**
 * 
 */
package com.ganggang.frame.freamwork.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.ganggang.frame.util.MyLogUtils;

import android.content.Context;
import android.os.Handler;

/**
 * 
 *逻辑层绑定   
 * @author 曾宝
 * @version  [V1.00, 2015年7月14日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public abstract class BaseLogicBuilder implements ILogicBuilder
{
    /**
     * DEBUG_TAG
     */
    private static final String TAG = "BaseLogicBuilder";
    
    /**
     * Logic对象缓存
     */
    private Map<String, ILogic> mCacheLogicMap = new HashMap<String, ILogic>();
    
    /**
     * DEFAULT_CONSTRUCTOR
     * 
     * @param context
     *            Context
     */
    public BaseLogicBuilder(Context context)
    {
        initLogic(context);
        
        initAllLogics(context);
    }
    
    /**
     * 初始化Logic
     * 
     * @param context
     *            Context
     */
    public abstract void initLogic(Context context);
    
    /**
     * 初始化所有Logic
     * 
     * @param context
     *            Context
     */
    private void initAllLogics(Context context)
    {
        Set<Entry<String, ILogic>> logics = mCacheLogicMap.entrySet();
        
        for (Entry<String, ILogic> logicEntry : logics)
        {
            logicEntry.getValue().init(context);
        }
    }
    
   /**
    * 注册绑定logic
    * <功能详细描述>
    * @param interfaceClass
    * @param logic
    * @see [类、类#方法、类#成员]
    */
    protected void registerLogic(Class<?> interfaceClass, ILogic logic)
    {
        String implName = interfaceClass.getName();
        if (!mCacheLogicMap.containsKey(implName))
        {
            if (isInterface(logic.getClass(), interfaceClass.getName())
                    && isInterface(logic.getClass(), ILogic.class.getName()))
            {
                mCacheLogicMap.put(implName, logic);
            }
            else
            {
                MyLogUtils.d(TAG,
                        "interface " + implName + " register failed!"+(new Throwable()));
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addHandlerToAllLogic(Handler handler)
    {
        Set<Entry<String, ILogic>> logics = mCacheLogicMap.entrySet();
        for (Entry<String, ILogic> logicEntry : logics)
        {
            ILogic logic = logicEntry.getValue();
            logic.addHandler(handler);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeHandlerFromAllLogic(Handler handler)
    {
        Set<Entry<String, ILogic>> logics = mCacheLogicMap.entrySet();
        for (Entry<String, ILogic> logicEntry : logics)
        {
            ILogic logic = logicEntry.getValue();
            logic.removeHandler(handler);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ILogic getLogicByInterface(Class<?> interfaceClass)
    {
        
        return mCacheLogicMap.get(interfaceClass.getName());
    }
    
    /**
     * 判断之类是否接口基类
     * <功能详细描述>
     * @param interfaceClass
     * @param className
     * @return
     * @see [类、类#方法、类#成员]
     */
    private boolean isInterface(Class<?> interfaceClass, String className)
    {
        Class<?>[] face = interfaceClass.getInterfaces();
        for (int i = 0, j = face.length; i < j; i++)
        {
            if (face[i].getName().equals(className))
            {
                return true;
            }
            else
            {
                Class<?>[] face1 = face[i].getInterfaces();
                for (int x = 0; x < face1.length; x++)
                {
                    if (face1[x].getName().equals(className))
                    {
                        return true;
                    }
                    else if (isInterface(face1[x], className))
                    {
                        return true;
                    }
                }
            }
        }
        if (null != interfaceClass.getSuperclass())
        {
            return isInterface(interfaceClass.getSuperclass(), className);
        }
        return false;
    }
}
