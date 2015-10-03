package com.ganggang.ganggangapp.bean;

import java.io.Serializable;

/**
 * 
 *热词  
 * @author 曾宝
 * @version  [V1.00, 2015年8月6日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class HotWord implements Serializable
{
    
    private Integer hotWordsId;
    
    private String hotWordsName;
    
    private String hotWordsNameComplex;

    public Integer getHotWordsId()
    {
        return hotWordsId;
    }

    public void setHotWordsId(Integer hotWordsId)
    {
        this.hotWordsId = hotWordsId;
    }

    public String getHotWordsName()
    {
        return hotWordsName;
    }

    public void setHotWordsName(String hotWordsName)
    {
        this.hotWordsName = hotWordsName;
    }

    public String getHotWordsNameComplex()
    {
        return hotWordsNameComplex;
    }

    public void setHotWordsNameComplex(String hotWordsNameComplex)
    {
        this.hotWordsNameComplex = hotWordsNameComplex;
    }
    
    
}
