package com.ganggang.ganggangapp.bean;

import java.io.Serializable;

/**
 * 
 * 商品
 * @author 曾宝
 * @version  [V1.00, 2015年8月6日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class Commodity implements Serializable
{
     private Integer  commodityId;
     
     private String commodityName;
     
     private String commodityNameComplex;
     
     private String commodityPrice;
     
     private String commodityRemark;
     
     private String commodityRemarkComplex;
     
     private String commodityPicture;
     
     
     

    public String getCommodityPicture()
    {
        return commodityPicture;
    }

    public void setCommodityPicture(String commodityPicture)
    {
        this.commodityPicture = commodityPicture;
    }

    @Override
    public String toString()
    {
        return "Commodity [commodityId=" + commodityId + ", commodityName=" + commodityName + ", commodityNameComplex=" + commodityNameComplex + ", commodityPrice=" + commodityPrice
            + ", commodityRemark=" + commodityRemark + ", commodityRemarkComplex=" + commodityRemarkComplex + "]";
    }

    public Integer getCommodityId()
    {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId)
    {
        this.commodityId = commodityId;
    }

    public String getCommodityName()
    {
        return commodityName;
    }

    public void setCommodityName(String commodityName)
    {
        this.commodityName = commodityName;
    }

    public String getCommodityNameComplex()
    {
        return commodityNameComplex;
    }

    public void setCommodityNameComplex(String commodityNameComplex)
    {
        this.commodityNameComplex = commodityNameComplex;
    }

    public String getCommodityPrice()
    {
        return commodityPrice;
    }

    public void setCommodityPrice(String commodityPrice)
    {
        this.commodityPrice = commodityPrice;
    }

    public String getCommodityRemark()
    {
        return commodityRemark;
    }

    public void setCommodityRemark(String commodityRemark)
    {
        this.commodityRemark = commodityRemark;
    }

    public String getCommodityRemarkComplex()
    {
        return commodityRemarkComplex;
    }

    public void setCommodityRemarkComplex(String commodityRemarkComplex)
    {
        this.commodityRemarkComplex = commodityRemarkComplex;
    }
     
     
}
