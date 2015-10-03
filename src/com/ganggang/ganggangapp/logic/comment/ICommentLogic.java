package com.ganggang.ganggangapp.logic.comment;
/**
 * 
 *评论接口  
 * @author 曾宝
 * @version  [V1.00, 2015年8月8日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public interface ICommentLogic
{
    /**
     * 保存评论 
     * <功能详细描述>
     * @param comment
     * @param businessId
     * @param createUserId
     * @see [类、类#方法、类#成员]
     */
    public void sendCommnet(String comment,int businessId,int createUserId);
    
    /**
     * 获取个人评论 
     * <功能详细描述>
     * @param userid
     * @see [类、类#方法、类#成员]
     */
    public void getComment(int userid);
}
