package com.ganggang.ganggangapp.constant;

/**
 * 
 * 
 * @author 曾宝
 * @version [V1.00, 2015年7月27日]
 * @see [相关类/方法]
 * @since V1.00
 */
public class ConstantMessage
{
    
    public static final int HTTP_ERR_MSG = -0XFFFFFFF;
    
    private static final int LOGIN_MSG = 0XFFFFFFF;
    
    private static final int HOME_MSG = 0X0FFFFF;
    
    private static final int MAP_MSG = 0X00FFFF;
    
    private static final int BUSINESS_MSG = 0X000FFF;
    
    private static final int BUSINESS_DETAIL = 0X0000FF;
    
    private static final int COMMENT_MSG = 0x00000F;
    
    private static final int BUSINESSBRAND_MSG = -0X0FFFFFF;
    
    private static final int SREACH_MSG = -0X00FFFF;
    
    private static final int USERINFO_MSG = -0X000FFF;
    
    public interface IUserinfo
    {
        public static final int USERINFO_OK_MSG = USERINFO_MSG - 1;
        
        public static final int USERINFO_ERR_MSG = USERINFO_MSG - 2;
        
        public static final int USERINFO_ADD_OK_MSG = USERINFO_MSG - 3;
        
        public static final int USERINFO_ADD_ERR_MSG = USERINFO_MSG - 4;
        
        public static final int USERINFO_MODIFY_OK_MSG = USERINFO_MSG - 5;
        
        public static final int USERINFO_MODIFY_ERR_MSG = USERINFO_MSG - 6;
    }
    
    public interface ISreach
    {
        public static final int HOTWORD_OK_MSG = SREACH_MSG - 1;
        
        public static final int HOTWORD_ERR_MSG = SREACH_MSG - 2;
        
        public static final int HOTWORD_SREACH_OK_MSG = SREACH_MSG - 3;
        
        public static final int HOTWORD_SREACH_ERR_MSG = SREACH_MSG - 4;
        
        public static final int SREACH_OK_MSG = SREACH_MSG - 5;
        
        public static final int SREACH_ERR_MSG = SREACH_MSG - 6;
        
    }
    
    public interface IBusinessBrand
    {
        public static final int BRAND_OK_MSG = BUSINESSBRAND_MSG - 1;
        
        public static final int BRAND_ERR_MSG = BUSINESSBRAND_MSG - 2;
    }
    
    public interface IComment
    {
        public static final int COMMENT_OK_MSG = COMMENT_MSG - 1;
        
        public static final int COMMENT_ERR_MSG = COMMENT_MSG - 2;
        
        public static final int COMMENT_USER_OK_MSG = COMMENT_MSG - 3;
        
        public static final int COMMENT_USER_ERR_MSG = COMMENT_MSG - 4;
    }
    
    public interface IBusinessDetail
    {
        // 查询OK
        public static final int BUSINESSDETAIL_OK_MSG = BUSINESS_DETAIL - 1;
        
        public static final int BUSINESSDETAIL_ERR_MSG = BUSINESS_DETAIL - 2;
        
    }
    
    // 商家
    public interface IBusiness
    {
        // 商家广告
        public static final int BUSINESS_ADV_OK_MSG = BUSINESS_MSG - 1;
        
        public static final int BUSINESS_ADV_ERR_MSG = BUSINESS_MSG - 2;
        
        // 商家类型
        public static final int BUSINESS_TYPE_OK_MSG = BUSINESS_MSG - 3;
        
        public static final int BUSINESS_TYPE_ERR_MSG = BUSINESS_MSG - 4;
        
        // 好评
        public static final int BUSINESS_REMARK_OK_MSG = BUSINESS_MSG - 5;
        
        public static final int BUSINESS_REMARK_ERR_MSG = BUSINESS_MSG - 6;
        
        // 人气最高
        public static final int BUSINESS_COUNT_OK_MSG = BUSINESS_MSG - 7;
        
        public static final int BUSINESS_COUNT_ERR_MSG = BUSINESS_MSG - 8;
        
        // 按距离
        public static final int BUSINESS_DISTANCE_OK_MSG = BUSINESS_MSG - 9;
        
        public static final int BUSINESS_DISTANCE_ERR_MSG = BUSINESS_MSG - 10;
        
        // 按品牌ID 查询
        public static final int BUSINESS_BRANDID_OK_MSG = BUSINESS_MSG - 11;
        
        public static final int BUSINESS_BRANDID_ERR_MSG = BUSINESS_MSG - 12;
        
        // 按品牌名称查询
        public static final int BUSINESS_BRANDNAME_OK_MSG = BUSINESS_MSG - 13;
        
        public static final int BUSINESS_BRANDNAME_ERR_MSG = BUSINESS_MSG - 14;
        
        // 热词搜索
        public static final int BUSINESS_TYPE_HOTWORD_OK_MSG = BUSINESS_MSG - 15;
        
        public static final int BUSINESS_TYPE_HOTWORD_ERR_MSG = BUSINESS_MSG - 16;
        
        // 默认搜索
        public static final int BUSINESS_TYPE_DEFAULT_OK_MSG = BUSINESS_MSG - 17;
        
        public static final int BUSINESS_TYPE_DEFAULT_ERR_MSG = BUSINESS_MSG - 18;
        
        // 距离搜索商家 + 商家分类查询
        public static final int BUSINESS_DISTANCEANDTYPE_OK_MSG = BUSINESS_MSG - 19;
        
        public static final int BUSINESS_DISTANCEANDTYPE_ERR_MSG = BUSINESS_MSG - 20;
        
        // 按点击数搜索商家（人气最旺排序）+商家分类
        public static final int BUSINESS_COUNTSANDTYPE_OK_MSG = BUSINESS_MSG - 21;
        
        public static final int BUSINESS_COUNTSANDTYPE_ERR_MSG = BUSINESS_MSG - 22;
        
        // 按好评搜索商家 + 商家条件
        public static final int BUSINESS_REMARKANDTYPE_OK_MSG = BUSINESS_MSG - 23;
        
        public static final int BUSINESS_REMARKANDTYPE_ERR_MSG = BUSINESS_MSG - 24;
        
        // 按默认搜索商家+商家分类
        public static final int BUSINESS_DEFAULTANDTYPE_OK_MSG = BUSINESS_MSG - 25;
        
        public static final int BUSINESS_DEFAULTANDTYPE_ERR_MSG = BUSINESS_MSG - 26;
        
        /*
         * 
         * //品牌搜索商家
         * public static final int BUSINESS_BRAND_OK_MSG = BUSINESS_MSG-9;
         * 
         * public static final int BUSINESS_BRAND_ERR_MSG = BUSINESS_MSG-10;
         * //按区域
         * public static final int BUSINESS_TYPE_DISTRICT_OK_MSG=BUSINESS_MSG-5;
         * 
         * public static final int BUSINESS_TYPE_DISTRICT_ERR_MSG = BUSINESS_MSG - 6;
         */
        
    }
    
    public interface IMap
    {
        public static final int BUSINESSINFO_OK_MSG = MAP_MSG - 1;
        
        public static final int BUSINESSINFO_ERR_MSG = MAP_MSG - 2;
        
        // 附近的商家信息
        public static final int BUSINESSBYSCOPE_OK_MSG = MAP_MSG - 3;
        
        public static final int BUSINESSBYSCOPE_ERR_MSG = MAP_MSG - 4;
        
        // 附近的品牌 所有商家信息
        public static final int BUSINESSBYSCOPEANDBRAND_OK_MSG = MAP_MSG - 5;
        
        public static final int BUSINESSBYSCOPEANDBRAND_ERR_MSG = MAP_MSG - 6;
        
        // 附近的类型的商家
        public static final int BUSINESSBYSCOPEANDTYPE_OK_MSG = MAP_MSG - 7;
        
        public static final int BUSINESSBYSCOPEANDTYPE_ERR_MSG = MAP_MSG - 8;
        
        public static final int NAVIGATION_OK_MSG = MAP_MSG - 9;
        
        public static final int NAVIGATION_ERR_MSG = MAP_MSG - 10;
    }
    
    public interface IAdv
    {
        // 获取广告
        public static final int ADV_OK_MSG = LOGIN_MSG - 1;
        
        // 广告翻到最后一页
        public static final int ADV_LAST_MSG = LOGIN_MSG - 2;
        
        public static final int ADV_ERR_MSG = LOGIN_MSG - 3;
        
        public static final int ADV_GET_MSG = LOGIN_MSG - 4;
        
        public static final int MAN_OK_ADV = LOGIN_MSG - 5;
        
        /**
         * 登录
         */
        public static final int LOGIN_OK_MSG = LOGIN_MSG - 6;
        
        public static final int LOGIN_ERR_MSG = LOGIN_MSG - 7;
        
        public static final int REGISTER_OK_MSG = LOGIN_MSG - 8;
        
        public static final int REGISTER_ERR_MSG = LOGIN_MSG - 9;
    }
    
    public interface IHome
    {
        public static final int HOME_TOP_OK_MSG = HOME_MSG - 1;
        
        public static final int HOME_TOP__ERR_MSG = HOME_MSG - 2;
        
        public static final int HOME_BOTTOM_ADV_OK_MSG = HOME_MSG - 3;
        
        public static final int HOME_BOTTOM_ADV_ERR_MSG = HOME_MSG - 4;
        
        public static final int HOME_BUSINESS_OK_MSG = HOME_MSG - 5;
        
        public static final int HOME_BUSINESS_ERR_MSG = HOME_MSG - 6;
        
        public static final int CELEBRITY_OK_MSG = HOME_MSG - 7;
        
        public static final int CELEBRITY_ERR_MSG = HOME_MSG - 8;
        
        // 首页地图显示的信息
        public static final int HOME_MAP_OK_MSG = HOME_MSG - 9;
        
        public static final int HOME_MAP_ERR_MSG = HOME_MSG - 10;
    }
}
