package com.ganggang.ganggangapp.bean;

import java.io.Serializable;
/**
 * 
 *  
 * @author 曾宝
 * @version  [V1.00, 2015年8月11日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class Celebrity implements Serializable
{
    private Integer celebrityId;
    
    private String celebrityTitle;
    
    private String celebrityPicture;
    
    private String celebrityUrl;
    
    private String[] celebrityUserName;

    public Integer getCelebrityId()
    {
        return celebrityId;
    }

    public void setCelebrityId(Integer celebrityId)
    {
        this.celebrityId = celebrityId;
    }

    public String getCelebrityTitle()
    {
        return celebrityTitle;
    }

    public void setCelebrityTitle(String celebrityTitle)
    {
        this.celebrityTitle = celebrityTitle;
    }

    public String getCelebrityPicture()
    {
        return celebrityPicture;
    }

    public void setCelebrityPicture(String celebrityPicture)
    {
        this.celebrityPicture = celebrityPicture;
    }

    public String getCelebrityUrl()
    {
        return celebrityUrl;
    }

    public void setCelebrityUrl(String celebrityUrl)
    {
        this.celebrityUrl = celebrityUrl;
    }

    public String[] getCelebrityUserName()
    {
        return celebrityUserName;
    }

    public void setCelebrityUserName(String[] celebrityUserName)
    {
        this.celebrityUserName = celebrityUserName;
    }
  
    
    
}
