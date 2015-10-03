package com.ganggang.ganggangapp.logic.businessbrandlogic;
/**
 * 
 *品牌 接口  
 * @author 曾宝
 * @version  [V1.00, 2015年8月8日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public interface IBusinessBrandLogic
{
    /**
     *获取品牌信息 
     * <功能详细描述>
     * @param businessBrandTypeCode
     * @param startSize
     * @param pageSize
     * @see [类、类#方法、类#成员]
     */
   public void getBusinessBrand(String businessBrandTypeCode,int startSize,int pageSize);
}
