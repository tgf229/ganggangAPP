package com.ganggang.ganggangapp.logic.sreachlogic;

public interface ISreachLogic
{
    /**
     * 获取热词
     * <功能详细描述>
     * 
     * @see [类、类#方法、类#成员]
     */
    public void getHotWord();
    
    public void getBusinessForHotWorld(String hotworld,int startSize,int pageSize);
    
    public void getBusinessForSreach(String sreach,int startSize,int pageSize);
 
 
}
