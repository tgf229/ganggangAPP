package com.ganggang.ganggangapp.constant;
/**
 * 常量字段  
 * @author 曾宝
 * @version  [V1.00, 2015年8月1日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class Constant
{
      
      public static final int BUSINESSLISTSIZE = 10;
      
      public static final   double TEST_GETLNG =114.16879584;//118.780283;// 114.13863716;
      
      public static final double  TEST_GETLAT =22.30371875;//32.058876;// 22.27736634;
      /**
       * 地图刷新 时间
       */
      public static final int MAPREFRESHTIME = 1000*60;
      
      public static final int NEARBYBUSINESS=5000;
      
      public static final String  MAP_KEY = "9a4bafddab028f9774cbf8730e0c624f";
      
      
      public interface IComment
      {
          public static final String TYPE_WO = "comment_wo";
          
          public static final String TYPE_BUSINESS="comment_business";
      }
      
      public interface IBusinessDetail
      {
          public static final String BUSINESS_DETAIL ="business_detail";
          
          public static final String BUSINESS_BRAND = "business_Brand";
          //商品
          public static final String MLIST_COMMODITY = "mlist_Commodity";
          //评论
          public static final String MLIST_COMMENT = "mlist_comment";
          
          public static final String MLIST_SUB_ALL = "mlist_sub_all";
          
          public static final String MLIST_SUB_FUJIN = "mlist_sub_fujin";
          
          public static final String MLIST_HOTWORD="mlist_hotword";
          
          
      }
}
