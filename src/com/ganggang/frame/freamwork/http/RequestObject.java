package com.ganggang.frame.freamwork.http;

import java.util.HashMap;

import android.content.Context;

/**
 * 
 *  
 * @author 曾宝
 * @version  [V1.00, 2015-3-29]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class RequestObject
{
    /**
     * 界面
     */
    public Context mContext;
    
    /**
     * uri
     */
    public String uriAPI;
    
    /**
     *  请求参数
     */
    public HashMap<String, String> requestHashMap;
    
    /**
     * 最大字节
     */
    private long maxSize = 0;
    
    /**
     * code
     */
    private String encoding = null;
    
    public RequestObject(Context mContext, String uriAPI,
            HashMap<String, String> map)
    {
        this.mContext = mContext;
        this.uriAPI = uriAPI;
        this.requestHashMap = map;
    }
    
    public long getMaxSize()
    {
        return maxSize;
    }
    
    public void setMaxSize(long maxSize)
    {
        this.maxSize = maxSize;
    }
    
    public String getEncoding()
    {
        return encoding;
    }
    
    public void setEncoding(String encoding)
    {
        this.encoding = encoding;
    }
}
