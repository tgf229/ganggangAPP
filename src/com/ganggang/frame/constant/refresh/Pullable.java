package com.ganggang.frame.constant.refresh;
/**
 * 
 *接口文件  
 * @author 曾宝
 * @version  [V1.00, 2015年8月5日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public interface Pullable
{
    /**
     * 判断是否可以进行下拉刷新，不能则返回false
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    boolean canPullDown();
    
    /**
     *判断是否可以进行上啦刷新，不能则返回false 
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    boolean canPullUp();
}
