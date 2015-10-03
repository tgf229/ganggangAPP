package com.ganggang.ganggangapp.bean;

import java.io.Serializable;

/**
 * 
 * 品牌类
 * 
 * @author 曾宝
 * @version [V1.00, 2015年8月6日]
 * @see [相关类/方法]
 * @since V1.00
 */
public class BusinessBrand implements Serializable
{
    private Integer businessBrandId;
    
    private String businessBrandName;
    
    private String businessBrandNameComplex;
    
    private String businessBrandRemark;
    
    private String businessBrandRemarkComplex;
    
    private String businessBrandPicture;
    
    private String businessBrandUri;
    
    
    
    
    public String getBusinessBrandUri()
    {
        return businessBrandUri;
    }

    public void setBusinessBrandUri(String businessBrandUri)
    {
        this.businessBrandUri = businessBrandUri;
    }

    @Override
    public String toString()
    {
        return "BusinessBrand [businessBrandId=" + businessBrandId + ", businessBrandName=" + businessBrandName + ", businessBrandNameComplex=" + businessBrandNameComplex + ", businessBrandRemark="
            + businessBrandRemark + ", businessBrandRemarkComplex=" + businessBrandRemarkComplex + ", businessBrandPicture=" + businessBrandPicture + "]";
    }

    public Integer getBusinessBrandId()
    {
        return businessBrandId;
    }
    
    public void setBusinessBrandId(Integer businessBrandId)
    {
        this.businessBrandId = businessBrandId;
    }
    
    public String getBusinessBrandName()
    {
        return businessBrandName;
    }
    
    public void setBusinessBrandName(String businessBrandName)
    {
        this.businessBrandName = businessBrandName;
    }
    
    public String getBusinessBrandNameComplex()
    {
        return businessBrandNameComplex;
    }
    
    public void setBusinessBrandNameComplex(String businessBrandNameComplex)
    {
        this.businessBrandNameComplex = businessBrandNameComplex;
    }
    
    public String getBusinessBrandRemark()
    {
        return businessBrandRemark;
    }
    
    public void setBusinessBrandRemark(String businessBrandRemark)
    {
        this.businessBrandRemark = businessBrandRemark;
    }
    
    public String getBusinessBrandRemarkComplex()
    {
        return businessBrandRemarkComplex;
    }
    
    public void setBusinessBrandRemarkComplex(String businessBrandRemarkComplex)
    {
        this.businessBrandRemarkComplex = businessBrandRemarkComplex;
    }
    
    public String getBusinessBrandPicture()
    {
        return businessBrandPicture;
    }
    
    public void setBusinessBrandPicture(String businessBrandPicture)
    {
        this.businessBrandPicture = businessBrandPicture;
    }
    
}
