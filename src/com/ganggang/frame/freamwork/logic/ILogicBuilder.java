package com.ganggang.frame.freamwork.logic;

import android.os.Handler;

/**
 * 
 *  logic绑定
 * @author 曾宝
 * @version  [V1.00, 2015年7月14日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public interface ILogicBuilder
{

	/**
	 * 通过Logic接口返回对应的Logic对象
	 * 
	 * @param interfaceClass
	 *            Logic接口Class
	 * @return
	 */
	public ILogic getLogicByInterface(Class<?> interfaceClass);

	/**
	 * 对缓存中的所有logic对象增加hander<BR>
	 * 对缓存中的所有logic对象增加hander，在该UI的onCreated时被框架执行�?	 * 如果该logic对象里执行了sendMessage方法，则�?��的活动的UI对象接收到�?知�?
	 * 
	 * @param handler
	 *            UI的handler对象
	 */
	public void addHandlerToAllLogic(Handler handler);

	/**
	 * 对缓存中的所有logic对象移除hander对象<BR>
	 * 在该UI的onDestory时被框架执行，如果该logic对象 执行了sendMessage方法，则发送到绑定的UI
	 * @param handler
	 *            UI的handler对象
	 */
	public void removeHandlerFromAllLogic(Handler handler);

}
