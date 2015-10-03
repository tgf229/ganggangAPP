package com.ganggang.ganggangapp.bean;

import java.io.Serializable;
/**
 * 
 * 首页自定义标题 
 * @author 曾宝
 * @version  [V1.00, 2015年8月9日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class BusinessTitle implements Serializable
{
    private Integer titleId;
    
    private String titleName;
    
    private String titleNameComplex;

    public Integer getTitleId()
    {
        return titleId;
    }

    public void setTitleId(Integer titleId)
    {
        this.titleId = titleId;
    }

    public String getTitleName()
    {
        return titleName;
    }

    public void setTitleName(String titleName)
    {
        this.titleName = titleName;
    }

    public String getTitleNameComplex()
    {
        return titleNameComplex;
    }

    public void setTitleNameComplex(String titleNameComplex)
    {
        this.titleNameComplex = titleNameComplex;
    }
    
    
}
