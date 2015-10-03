package com.ganggang.ganggangapp.logic.homelogic;
/**
 * 
 *主页 的数据  
 * @author 曾宝
 * @version  [V1.00, 2015年7月28日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public interface IHomeLogic
{
    public void  getTopAdv();
    
    public void getBottomAdv();
    
    public void getHomeBusiness();
    
    public void getMingrenmingbo();
    
    public void getMapBusiness(double userLongitude,double userLatitude,int startSize,int pageSize);
}
