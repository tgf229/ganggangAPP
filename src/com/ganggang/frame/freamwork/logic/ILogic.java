package com.ganggang.frame.freamwork.logic;

import android.content.Context;
import android.os.Handler;

/**
 * 
 *logic 接口  
 * @author 曾宝
 * @version  [V1.00, 2015年7月14日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public interface ILogic
{
    
    /**
     * Logic的初始化方法，在被注册到LogicBuilder后立即执行的初始化方�?     * 
     * @param context
     */
    public void init(Context context);
    
    /**
     * 给Logic加入Handler
     * 
     * @param handler
     *            UI传入的Handler
     */
    public void addHandler(Handler handler);
    
    /**
     * 移除Handler
     * 
     * @param handler
     *            UI传入的Handler
     */
    public void removeHandler(Handler handler);
    
}
