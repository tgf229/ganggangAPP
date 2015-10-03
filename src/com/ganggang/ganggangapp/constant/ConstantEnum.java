package com.ganggang.ganggangapp.constant;

/**
 * 
 * 枚举
 * 
 * @author 曾宝
 * @version [V1.00, 2015年8月1日]
 * @see [相关类/方法]
 * @since V1.00
 */
public class ConstantEnum
{
    public interface IMap
    {
        /**
         * 从首页头部点击到map上的显示地图
         */
        public static final int TYPE_HOME = 1;
        
        /**
         * 从商家详情点击到map上的显示地图--单家坐标
         */
        public static final int TYPE_DETAIL_SINGLE = 2;
        
        /**
         * 从商家详情点击到map 上的显示地图---单个品牌的所有店
         */
        public static final int TYPE_DETAIL_BUSINESSBRAND = 3;
        
        /**
         * 从品牌中跳转到地图
         */
        public static final int TYPE_BRAND_BUSINESS = 4;
        
        public static final int TYPE_BRANDBUSINESS_MAP = 5;
        
        public static final String TYPE_NAVIGATION = "to_navigation";
        /**
         * 到导航带入的商家地址
         */
        public static final String TYPE_ARGS_NAVIGATION_DEST="Type_args_navigation_dest";
        /**
         * 到导航带入的商家名称
         */
        public static final String TYPE_ARGS_NAVIGATION_DESTNAME="type_args_navigation_destname";
        
        
        public static final String TYPE_ARGS="to_maps";
        
        
        
    }
    /**
     * 
     * 回跳  
     * @author 曾宝
     * @version  [V1.00, 2015年8月18日]
     * @see  [相关类/方法]
     * @since V1.00
     */
    public interface IBack
    {
        public static String BACK="back";
        
        public static String BACK_ARGS = "back_args";
        
        public static String BACK_PAGE = "back_page";
        
        public interface IBackArgs
        {
            public static String BACK_HOME = "back_home";
            
            public static String BACK_BUSINESS = "back_business";
            
            public static String BACK_WO = "back_wo";
            
            public static String BACK_GENGDUO = "back_gengduo";
            
            public static String BACK_SREACH = "back_sreach";
            
            public static String BACK_BRAND="back_brand";
            
            public static String BACK_MAP="back_map";
        }
        
       
        
    }
    /**
     * 
     * 从其他页面上跳转到business 页面上，标记
     * 
     * @author 曾宝
     * @version [V1.00, 2015年8月14日]
     * @see [相关类/方法]
     * @since V1.00
     */
    public interface IBusinessFragment
    {
        
        public static String TYPE_INTENT = "type_intent_homeactivity";
        
        public static String TYPE="to_businessFragment";
        
        /**
         * 带参数
         */
        public static String TYPE_ARGS="type_args";
        
        /**
         *  1、搜索品牌名称 搜索商家
         */
        public static int TYPE_BRANDNAME_BUSINESS = 1;
        
        /**
         * 2。品牌ID 搜索商家
         */
        public static int TYPE_BRANDID_BUSINESS = 2;
        
        /**
         *  3、热词 搜索商家
         */
        public static int TYPE_HOTWORD_BUSINESS = 3;
        
        /**
         *  4、商家类型_搜索商家
         */
        public static int TYPE_BUSINESSTYPE_BUSINESS = 4;
        
        /**
         *  5、品牌类型_搜索商家
         */
        public static int TYPE_BRANDTYPE_BUSINESS = 5;
        
        /**
         *  6、默认排序_搜索商家
         */
        public static int TYPE_DEFAULT_BUSINESS = 6;
        
        /**
         *  7、好评优先_搜索商家
         */
        public static int TYPE_HIGHOPINION_BUSINESS = 7;
        
        /**
         *  8、人气最高_搜索商家
         */
        public static int TYPE_COUNT_BUSINESS = 8;
        
        /**
         *  9、离我最近_搜索商家
         */
        public static int TYPE_GPS_BUSINESS = 9;
        
        /**
         * 10、默认商家  类型 排序
         */
        public static int TYPE_DEFAULT_TYPE_BUSINESS = 10;
        
        /**
         * 11 好评商家 类型 排序
         */
        public static int TYPE_HIGHOPINION_TYPE_BUSINESS =11;
        
        /**
         * 12 点击数量 类型 排序
         */
        public static int TYPE_COUNT_TYPE_BUSINESS = 12;
        
        /**
         *13  按距离搜索商家 + 商家分类
         */
        public static int TYPE_GPS_TYPE_BUSINESS= 13;
    }
    
    
    public interface IFileType
    {
        public static int TYPE_PATH = -1;
        
         public static int TYPE_ADV = 0;
         
         public static int TYPE_BUSINESS = 1;
         
         public static int TYPE_MINGRENMINGBO = 2;
         
         public static int TYPE_BUSINESSBRANNDER=3;
         
         public interface IFileName
         {
             public static final String FILE_NAME_PATH="/GangGang";
             
             public static final String FILE_NAME_ADV="/adv";
             
             public static final String FILE_NAME_BUSINESS="/business";
             
             public static final String FILE_NAME_MINGRENMINGBO="/mingrengmingbo";
             
             public static final String FILE_NAME_BUSINESSBRANNDER="/businessBrand";
             
         }
        
    }
    /**
     * 广告枚举
     * 
     * @author 曾宝
     * @version [V1.00, 2015年8月1日]
     * @see [相关类/方法]
     * @since V1.00
     */
    public interface IAdv
    {
        
        public static final int TYPE = 0;
        
        /**
         * 引导页面
         */
        public static final String GUIDE_ADV_LOCATION = "_guide_adv_location";
        
        /**
         * 首页顶部广告
         */
        public static final String HOMEBANNER_ADV_LOCATION = "_homebanner_adv_location";
        
        /**
         * 首页底部广告
         */
        public static final String HOMEBOTTOM_ADV_LOCATION = "_homebottom_adv_location";
    }
    
    /**
     * 商家固定类型
     * 
     * @author 曾宝
     * @version [V1.00, 2015年8月4日]
     * @see [相关类/方法]
     * @since V1.00
     */
    public interface IHomeBusinessType
    {
        public static final String TYPE_MAN = "_type_business";
        
        public static final String TYPE_ONE = "_one_type_business";
        
        public static final String TYPE_TWO = "_two_type_business";
        
        public static final String TYPE_THREE = "_three_type_business";
        
        public static final String TYPE_FOUR = "_four_type_business";
        
        public static final String TYPE_FIVE = "_five_type_business";
        
        public static final String TYPE_SIX = "_six_type_business";
        
        public static final String TYPE_SEVEN = "_seven_type_business";
        
        public static final String TYPE_EIGHT = "_eight_type_business";
        
    }
}
