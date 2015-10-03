package com.ganggang.frame.freamwork.http;

/**
 * 
 *  
 * @author 曾宝
 * @version  [V1.00, 2015-3-29]
 * @see  [相关类/方法]
 * @since V1.00
 */
public interface IHttpResponseListener
{
    /**
     * 开启请求
     * <功能详细描述>
     * @param json
     * @see [类、类#方法、类#成员]
     */
    public void doHttpResponse(String json);

    /**
     * 请求出错
     * <功能详细描述>
     * @param errStr
     * @see [类、类#方法、类#成员]
     */
    public void onError(String errStr);
}
