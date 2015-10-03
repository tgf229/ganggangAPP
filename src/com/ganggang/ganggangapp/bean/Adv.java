package com.ganggang.ganggangapp.bean;

import java.io.Serializable;
import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
/**
 * 
 *  广告
 * @author 曾宝
 * @version  [V1.00, 2015年8月1日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class Adv implements Serializable
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2963439599431496712L;

    private Integer advId;
    
    private String advName;
    
    private String advPicture;
    
    private String advLocationCode;
    
    private String advUrl;
    
    private String advNumber;

    
    
    /** 
     * <默认构造函数>
     */
    public Adv()
    {
        super();
    }

    /** 
     * <默认构造函数>
     */
    public Adv(Integer advId, String advName, String advPicture, String advLocationCode, String advUrl, String advNumber)
    {
        super();
        this.advId = advId;
        this.advName = advName;
        this.advPicture = advPicture;
        this.advLocationCode = advLocationCode;
        this.advUrl = advUrl;
        this.advNumber = advNumber;
    }

    public Integer getAdvId()
    {
        return advId;
    }

    public void setAdvId(Integer advId)
    {
        this.advId = advId;
    }

    public String getAdvName()
    {
        return advName;
    }

    public void setAdvName(String advName)
    {
        this.advName = advName;
    }

    public String getAdvPicture()
    {
        return advPicture;
    }

    public void setAdvPicture(String advPicture)
    {
        this.advPicture = advPicture;
    }

    public String getAdvLocationCode()
    {
        return advLocationCode;
    }

    public void setAdvLocationCode(String advLocationCode)
    {
        this.advLocationCode = advLocationCode;
    }

    public String getAdvUrl()
    {
        return advUrl;
    }

    public void setAdvUrl(String advUrl)
    {
        this.advUrl = advUrl;
    }

    public String getAdvNumber()
    {
        return advNumber;
    }

    public void setAdvNumber(String advNumber)
    {
        this.advNumber = advNumber;
    }

  
}
