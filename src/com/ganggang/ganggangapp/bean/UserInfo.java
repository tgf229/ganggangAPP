package com.ganggang.ganggangapp.bean;

import java.io.Serializable;

public class UserInfo implements Serializable
{
    private Integer userId;
    
    private String userLoginname;
    
    private String userPassword;
    
    private String userName;
    
    private String userGender;
    
    private String mobile;
    
    private String email;
    
    

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserGender()
    {
        return userGender;
    }

    public void setUserGender(String userGender)
    {
        this.userGender = userGender;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public String getUserLoginname()
    {
        return userLoginname;
    }

    public void setUserLoginname(String userLoginname)
    {
        this.userLoginname = userLoginname;
    }

    public String getUserPassword()
    {
        return userPassword;
    }

    public void setUserPassword(String userPassword)
    {
        this.userPassword = userPassword;
    }
    
    
}
