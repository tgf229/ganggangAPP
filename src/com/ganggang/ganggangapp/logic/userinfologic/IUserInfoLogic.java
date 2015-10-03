package com.ganggang.ganggangapp.logic.userinfologic;

import com.ganggang.ganggangapp.bean.UserInfo;

/**
 * 
 * 个人信息接口 
 * @author 曾宝
 * @version  [V1.00, 2015年8月27日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public interface IUserInfoLogic
{
        public void getUserInfo(int userid);
        
        public void setUserInfo(UserInfo info);
}
