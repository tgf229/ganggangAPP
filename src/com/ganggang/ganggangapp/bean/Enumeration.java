package com.ganggang.ganggangapp.bean;

import java.io.Serializable;

/**
 * 
 *枚举表  
 * @author 曾宝
 * @version  [V1.00, 2015年8月4日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class Enumeration implements Serializable
{
    private String enumerationCode;
    
    private String enumerationName;
    
    private String enumerationNameComplex;

    @Override
    public String toString()
    {
        return "Enumeration [enumerationCode=" + enumerationCode + ", enumerationName=" + enumerationName + ", enumerationNameComplex=" + enumerationNameComplex + "]";
    }

    public String getEnumerationCode()
    {
        return enumerationCode;
    }

    public void setEnumerationCode(String enumerationCode)
    {
        this.enumerationCode = enumerationCode;
    }

    public String getEnumerationName()
    {
        return enumerationName;
    }

    public void setEnumerationName(String enumerationName)
    {
        this.enumerationName = enumerationName;
    }

    public String getEnumerationNameComplex()
    {
        return enumerationNameComplex;
    }

    public void setEnumerationNameComplex(String enumerationNameComplex)
    {
        this.enumerationNameComplex = enumerationNameComplex;
    }
    
    
}
