package com.ganggang.ganggangapp.constant;

public interface ConstantBundle
{
    public final static String MAIN_BUNDLE_KEY="main_bundle_adv";
    
    /**
     * 商家 跳转map
     */
    public final static String BUSINESS_TO_MAP_BUNDLE_KEY="business_bundle_map";
    
    public final static String BUSINESSDEATILID ="businessdeatilid";
    
    public final static String BUSINESS_COMMENT="business_comment";
    
    public final static String BUSINESS_COMMENT_LIST = "business_comment_list";
    /**
     * 首页8大模块跳转
     */
    public final static String HOME_BUSINESSBRAND = "home_businessBrand";
    
  
    public final static String ACTIVITY_TO_BUSINESS_TYPE = "activity_to_business_type";
    
    public final static String BRAND_TO_BUSINESS_TYPE="brand_to_business_type";
    
    public interface IMapType
    {
        public final static String TO_MAP = "home_to_map";
        
        /**
         * 商家 详情 传递到map
         */
        public final static String BUSINESS_DETAIL_MAP = "business_detail_map";
        
        /**
         * 品牌跳到map
         */
        public final static String BRAND_MAP = "brand_map";
    }
    
    public interface IBusinessBrand
    {
        public final static String BRAND_TO_BUSSINESS="brand_to_bussiness";
    }
    
    public interface ISreachHotWord
    {
        public final static String SREACH_TO_BUSINESS_HOTWORD = "Sreach_to_business_hotword";
    }
}
