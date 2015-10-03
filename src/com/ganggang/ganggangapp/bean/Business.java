package com.ganggang.ganggangapp.bean;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import com.ganggang.frame.util.TextViewUtil;

import android.content.Intent;

/**
 * 
 * 商家信息
 * 
 * @author 曾宝
 * @version [V1.00, 2015年7月29日]
 * @see [相关类/方法]
 * @since V1.00
 */
public class Business implements Serializable, Comparator<Business>
{
    private Integer businessId;
    
    private String businessBrandCode;
    
    /**
     * 简体
     */
    private String businessName;
    
    /**
     * 繁体
     */
    private String businessNameComplex;
    
    /**
     * 区域
     */
    private String businessDistrictCode;
    
    private String businessAddress;
    
    private String businessAddressComplex;
    
    private String businessBusLine;
    
    private String businessBusLineComplex;
    
    private String businessPhone;
    
    private double businessLongitude;
    
    private double businessLatitude;
    
    private String businessTypeCode;
    
    private int highOpinion;
    
    private Integer counts;
    
    /**
     * 缩略图
     */
    private String previewPicture;
    
    private String[] businessBrand;
    
    private String businessBrandName;
    
    private String businessBrandNameComplex;
    
    private float juli;
    
    public float getJuli()
    {
        return juli;
    }
    
    public void setJuli(float juli)
    {
        this.juli = juli;
    }
    
    public String getPreviewPicture()
    {
        return previewPicture;
    }
    
    public void setPreviewPicture(String previewPicture)
    {
        this.previewPicture = previewPicture;
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
    
    /**
     * 营业时间
     */
    private String businessHours;
    
    /**
     * 商店图片
     */
    private List<String> businessPicture_list;
    

    public Integer getCounts()
    {
        return counts;
    }
    
    public void setCounts(Integer counts)
    {
        this.counts = counts;
    }
    
    public Integer getBusinessId()
    {
        return businessId;
    }
    
    public void setBusinessId(Integer businessId)
    {
        this.businessId = businessId;
    }
    
    public String getBusinessBrandCode()
    {
        return businessBrandCode;
    }
    
    public void setBusinessBrandCode(String businessBrandCode)
    {
        this.businessBrandCode = businessBrandCode;
    }
    
    public String getBusinessName()
    {
        return businessName;
    }
    
    public void setBusinessName(String businessName)
    {
        this.businessName = businessName;
    }
    
    public String getBusinessNameComplex()
    {
        return businessNameComplex;
    }
    
    public void setBusinessNameComplex(String businessNameComplex)
    {
        this.businessNameComplex = businessNameComplex;
    }
    
    public String getBusinessDistrictCode()
    {
        return businessDistrictCode;
    }
    
    public void setBusinessDistrictCode(String businessDistrictCode)
    {
        this.businessDistrictCode = businessDistrictCode;
    }
    
    public String getBusinessAddress()
    {
        /*
         * if(businessAddress!=null)
         * {
         * String address = TextViewUtil.ToSBC(businessAddress);
         * address = TextViewUtil.stringFilter(address);
         * return address;
         * }
         * else
         */
        return businessAddress;
    }
    
    public void setBusinessAddress(String businessAddress)
    {
        this.businessAddress = businessAddress;
    }
    
    public String getBusinessAddressComplex()
    {
        /*
         * if(businessAddressComplex!=null)
         * {
         * String address = TextViewUtil.ToSBC(businessAddressComplex);
         * address = TextViewUtil.stringFilter(address);
         * return address;
         * }
         * else
         */
        return businessAddressComplex;
    }
    
    public void setBusinessAddressComplex(String businessAddressComplex)
    {
        this.businessAddressComplex = businessAddressComplex;
    }
    
    public String getBusinessBusLine()
    {
        return businessBusLine;
    }
    
    public void setBusinessBusLine(String businessBusLine)
    {
        this.businessBusLine = businessBusLine;
    }
    
    public String getBusinessBusLineComplex()
    {
        return businessBusLineComplex;
    }
    
    public void setBusinessBusLineComplex(String businessBusLineComplex)
    {
        this.businessBusLineComplex = businessBusLineComplex;
    }
    
    public String getBusinessPhone()
    {
        return businessPhone;
    }
    
    public void setBusinessPhone(String businessPhone)
    {
        this.businessPhone = businessPhone;
    }
    
    public double getBusinessLongitude()
    {
        return businessLongitude;
    }
    
    public void setBusinessLongitude(double businessLongitude)
    {
        this.businessLongitude = businessLongitude;
    }
    
    public double getBusinessLatitude()
    {
        return businessLatitude;
    }
    
    public void setBusinessLatitude(double businessLatitude)
    {
        this.businessLatitude = businessLatitude;
    }
    
    public String getBusinessTypeCode()
    {
        return businessTypeCode;
    }
    
    public void setBusinessTypeCode(String businessTypeCode)
    {
        this.businessTypeCode = businessTypeCode;
    }
    
    public String getBusinessHours()
    {
        return businessHours;
    }
    
    public void setBusinessHours(String businessHours)
    {
        this.businessHours = businessHours;
    }
    
    public List<String> getBusinessPicture_list()
    {
        return businessPicture_list;
    }
    
    public void setBusinessPicture_list(List<String> businessPicture_list)
    {
        this.businessPicture_list = businessPicture_list;
    }
    
    public int getHighOpinion()
    {
        return highOpinion;
    }
    
    public void setHighOpinion(int highOpinion)
    {
        this.highOpinion = highOpinion;
    }
    
    public String[] getBusinessBrand()
    {
        return businessBrand;
    }
    
    public void setBusinessBrand(String[] businessBrand)
    {
        this.businessBrand = businessBrand;
    }
    
    @Override
    public int compare(Business object1, Business object2)
    {
        if (object1.getJuli() > object2.getJuli())
            return 1;
        else if (object1.getJuli() < object2.getJuli())
            return -1;
        else
            return 0;
    }
    
}
