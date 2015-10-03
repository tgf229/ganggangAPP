package com.ganggang.ganggangapp.logic.businesslogic;

/**
 * 
 * 商家逻辑层
 * 
 * @author 曾宝
 * @version [V1.00, 2015年7月31日]
 * @see [相关类/方法]
 * @since V1.00
 */
public interface IBusinessLogic
{
    /**
     * 获取商家头部广告
     * <功能详细描述>
     * 
     * @see [类、类#方法、类#成员]
     */
    public void getBusinessTopAdv();
    
    /**
     * 默认排序获取
     * <功能详细描述>
     * 
     * @param startSize
     * @param pageSize
     * @see [类、类#方法、类#成员]
     */
    public void getBusinessDefault(int startSize, int pageSize);
    
    /**
     * 根据商家类型搜索
     * <功能详细描述>
     * 
     * @param type
     * @param startsize
     * @param pagesize
     * @see [类、类#方法、类#成员]
     */
    public void getBusinessType(String type, int startsize, int pagesize);
    
    /**
     * 好评查找
     * <功能详细描述>
     * 
     * @param startsize
     * @param pagesize
     * @see [类、类#方法、类#成员]
     */
    public void getBusinessByRemark(int startsize, int pagesize);
    
    /**
     * 人气最高
     * <功能详细描述>
     * 
     * @param startsize
     * @param pagesize
     * @see [类、类#方法、类#成员]
     */
    public void getBusinessByCounts(int startsize, int pagesize);
    
    /**
     * 离我最近
     * <功能详细描述>
     * 
     * @param businessLongitude
     * @param businessLatitude
     * @param startSize
     * @param pageSize
     * @see [类、类#方法、类#成员]
     */
    public void getBusinessByDistance(double businessLongitude, double businessLatitude, int startSize, int pageSize);
    
    /**
     * 根据品牌ID 查找商家
     * <功能详细描述>
     * 
     * @param businessBrandId
     * @param startSize
     * @param pageSize
     * @see [类、类#方法、类#成员]
     */
    public void getBusinessByBrand(int businessBrandId, int startSize, int pageSize);
    
    public void getBusinessByHotWord(String hotWordsName, int startSize, int pageSize);
    
    /**
     * 
     * <一句话功能简述>
     * <功能详细描述>
     * 
     * @param businessBrandName
     * @param startSize
     * @see [类、类#方法、类#成员]
     */
    public void getBusinessByBrandName(String businessBrandName, int startSize, int pageSize);
    
    /**
     * 
     * 按距离搜索商家 + 商家分类
     * <功能详细描述>
     * 
     * @param businessTypeCode
     * @param d
     * @param e
     * @param startSize
     * @param pageSize
     * @see [类、类#方法、类#成员]
     */
    public void getBusinessByDistanceAndType(String businessTypeCode, double businessLongitude, double businessLatitude, int startSize, int pageSize);
    
    /**
     * 
     * 按点击数搜索商家（人气最旺排序）+商家分类
     * <功能详细描述>
     * 
     * @param businessTypeCode
     * @param startSize
     * @param pageSize
     * @see [类、类#方法、类#成员]
     */
    public void getBusinessByCountsAndType(String businessTypeCode, int startSize, int pageSize);
    
    /**
     * 
     * 按好评搜索商家 + 商家条件
     * <功能详细描述>
     * 
     * @param businessTypeCode
     * @param startSize
     * @see [类、类#方法、类#成员]
     */
    public void getBusinessByRemarkAndType(String businessTypeCode, int startSize, int pageSize);
    
    /**
     * 
     * 按默认搜索商家+商家分类
     * <功能详细描述>
     * 
     * @param businessTypeCode
     * @param startSize
     * @param pageSize
     * @see [类、类#方法、类#成员]
     */
    public void getBusinessByDefaultAndType(String businessTypeCode, int startSize, int pageSize);
}
