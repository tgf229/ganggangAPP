package com.ganggang.ganggangapp.logic.maplogic;

public interface IMapLogic
{
    
    /**
     *根据 个人位置搜索附近商家 
     * <功能详细描述>
     * @param userLongitude
     * @param userLatitude
     * @param startSize
     * @param pageSize
     * @see [类、类#方法、类#成员]
     */
    public void getBusinessByScope(double userLongitude,double userLatitude,int startSize, int pageSize);
    
    /**
     *搜索某品牌下指定位置范围内的分店 
     * <功能详细描述>
     * @param businessBrandId
     * @param userLongitude
     * @param userLatitude
     * @param startSize
     * @param pageSize
     * @see [类、类#方法、类#成员]
     */
    public void getSubBusinessByScopeAndBrand(int businessBrandId,double userLongitude,double userLatitude,int startSize,int pageSize);

    /**
     * 搜索某商家类型指定位置范围内的商家
     * <功能详细描述>
     * @param businessTypeCode
     * @param userLongitude
     * @param userLatitude
     * @param startSize
     * @param pageSize
     * @see [类、类#方法、类#成员]
     */
    public void getBusinessByScopeAndType(String businessTypeCode,double userLongitude,double userLatitude,int startSize,int pageSize);

}
