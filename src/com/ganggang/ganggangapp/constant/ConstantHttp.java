package com.ganggang.ganggangapp.constant;

/**
 * 
 * HTTP 连接
 * 
 * @author 曾宝
 * @version [V1.00, 2015年8月1日]
 * @see [相关类/方法]
 * @since V1.00
 */
public class ConstantHttp
{
    /**
     * 连接IP
     */
    // public static final String IP = "http://10.32.10.242:8080/ganggang/";
//    public static final String IP = "http://10.32.10.247:8080/ganggang/";
    
//    public static final String IP = "http://www.njtench.com:8000/ganggang/";
     public static final String IP="http://120.24.100.32:8080/ganggang/";
    /**
     * 所有的广告请求
     */
    public static final String ADV_ALL_HTTP = "getAdvAll.do?";
    
    /**
     * 广告请求-特定广告请求
     */
    public static final String ADV_HTTP = "getAdvByLocation.do?";
    
    /**
     * 首页请求推荐商家信息
     */
    public static final String HOMEBUSINESS_HTTP = "getBusinessByRecommend.do?";
    
    /**
     * 商家类型
     */
    public static final String BUSINESSTYPE_HTTP = "getEnumerationByType.do?";
    
    /**
     * 商家默认排序
     */
    public static final String BUSINESSBYDEFAULT = "getBusinessByDefault.do?";
    
    /**
     * 商家详情
     */
    public static final String BUSINESSDETAIL="getBusinessDetail.do?";
    
    /**
     * 新增评论
     */
    public static final String ADDCOMMENT= "addComment.do?";
    
    /**
     * 品牌类型
     */
    public static final String BRANDBYTYPE="getBrandByType.do?";
    /**
     *获取所有自定义标题
     */
    public static final String GETTITLELIST="getTitleList.do?";
    
    /**
     * 根据商家类型来搜索商家
     */
    public  static final String GETBUSINESSBYTYPE_HTTP="getBusinessByType.do?";
    
    /**
     * 按照首页推荐
     */
    public static final String GETBUSINESSBYTITLE="getBusinessByTitle.do?";
    
    /**
     * 好评优先
     */
    public static final String GETBUSINESSBYREMARK = "getBusinessByRemark.do?";
    
    public static final String GETBUSINESSBYCOUNTS="getBusinessByCounts.do?";
    
    public static final String GETBUSINESSBYDISTANCE="getBusinessByDistance.do?";
    
    /**
     * 搜索附近商家信息
     */
    public static final String GETBUSINESSBYSCOPE="getBusinessByScope.do?";
    
    /**
     * 获取所有热词
     */
    public static final String GETHOTWORDSLIST= "getHotwordsList.do?";
    
    /**
     * 根据品牌 搜索 规定范围内的商家
     */
    public static final String GETSUBBUSINESSBYSCOPEANDBRAND="getSubBusinessByScopeAndBrand.do?";
    
    /**
     * 根据商家类型搜索 商家
     */
    public static final String getBusinessByScopeAndType="getBusinessByScopeAndType.do?";
    
    public static final String GETBUSINESSBYBRANDNAME="getBusinessByBrandName.do?";
    
    /**
     * 按默认搜索商家+商家分类
     */
    public static final String GETBUSINESSBYDEFAULTANDTYPE="getBusinessByDefaultAndType.do?";
    
    /**
     * 按好评搜索商家 + 商家条件
     */
    public static final String GETBUSINESSBYREMARKANDTYPE="getBusinessByRemarkAndType.do?";
    
    /**
     * 按点击数搜索商家（人气最旺排序）+商家分类
     */
    public static final String GETBUSINESSBYCOUNTSANDTYPE="getBusinessByCountsAndType.do?";
    
    /**
     * 按距离搜索商家 + 商家分类getBusinessByDistanceAndType
     */
    public static final String GETBUSINESSBYDISTANCEANDTYPE="getBusinessByDistanceAndType.do?";
    
    public static final String GETUSERINFO = "getUserInfo.do?";
    
    public static final String ADDUSERINFO="addUserInfo.do?";
    
  
    public static final String USERLONGITUDE="userLongitude";
    
    public static final String USERLATITUDE="userLatitude";
    
    public static final String STARTSIZE="startSize";
    
    public static final String PAGESIZE="pageSize";
   
   public static final String BUSINESSLONGITUDE="businessLongitude";
    
    public static final String BUSINESSLATITUDE="businessLatitude";
    
    public static final String BUSINESSBRANDNAME="businessBrandName";
    /**
     * 获取名人名博
     */
    public static final String GETCELEBRITY="getCelebrityByUserId.do?";
    
    /**
     * 根据热词获取 商家信息
     */
    public static final String GETBUSINESSBYHOTWORDS="getBusinessByHotWords.do?";
    
    /**
     * 根据品牌获取商家信息
     */
    public static final String GETBUSINESSBYBRAND="getBusinessByBrand.do?";
    
    /**
     * 登录
     */
    public static final String LOGIN="login.do?";
    /**
     * 注册
     */
    public static final String REGISTE="registe.do?";
    
    /**
     * 修改密码
     */
    public static final String MODIFYPASSWORD="modifyPassWord.do?";
    
    
    public static final String USERGENDER="userGender";
    
    public static final String USERNAME = "userName";
    
    public static final String EMAIL="email";
    
    public static final String MOBILE="mobile";
    
    /**
     * 个人评论
     */
    public static final String COMMENT_USER="getCommentByUserId.do?";
    
    public static final String USERLOGINNAME="userLoginname";
    
    public static final String USERPASSWORD="userPassword";
    
    public static final String BUSINESSBRANDID="businessBrandId";

    public static final String HOTWORDSNAME="hotWordsName";
    
    
    public interface IGetBusinessType
    {
        public static final String BUSINESSTYPECODE="businessTypeCode";
        
        public static final String STARTSIZE="startSize";
        
        public static final String PAGESIZE="pageSize"; 
    }
    
    /**
     * 
     *品牌类型的参数  
     * @author 曾宝
     * @version  [V1.00, 2015年8月8日]
     * @see  [相关类/方法]
     * @since V1.00
     */
    public interface IBrandByType
    {
        public static final String BUSINESSBRANDTYPECODE="businessBrandTypeCode";
        
        public static final String STARTSIZE="startSize";
        
        public static final String PAGESIZE="pageSize";
    }
    /**
     * 
     * 新增评论的参数  
     * @author 曾宝
     * @version  [V1.00, 2015年8月8日]
     * @see  [相关类/方法]
     * @since V1.00
     */
    public interface IComment
    {
        
        public static final String USERID="userId";
        
        public static final String COMMENTCONTENT = "comment";
        
        public static final String BUSINESSID = "businessId";
        
        public static final String CREATEUSERID="createUserId";
    }
    
    public interface IBusinessDetail
    {
        public static final String BUSINESSID="businessId";
    }
    
    public interface IBusinessList
    {
        public static final String STARTSIZE="startSize";
        
        public static final String PAGESIZE="pageSize";
    }
    
    public interface IBusinessType
    {
        public static final String ENUMERATIONTYPEID = "enumerationTypeId";
    }
    
    public interface IAdv
    {
        public static final String ADVLOCATIONCODE = "advLocationCode";
    }
    
}
