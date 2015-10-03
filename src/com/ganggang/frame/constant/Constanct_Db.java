package com.ganggang.frame.constant;

import android.net.Uri;

/**
 * 
 *  
 * @author 曾宝
 * @version  [V1.00, 2015年7月14日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class Constanct_Db
{
   /**
    * 数据库名
    */
    public static final String DBNAME = "ganggang_database";
    
    /**
     * 版本
     */
    public static final int VSERSIONID = 1;
    
    /**
     * 数据库连接provider
     */
    public static final String AUTOHORITY = "com.ganggang.frame.freamwork.db.mynewprovider";
    
    /**
     * 多内容
     */
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir" + "/" + AUTOHORITY;
    
    /**
     * 单内容
     */
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item" + "/" + AUTOHORITY;
    
    public interface DataType
    {
        public static final String INT=" integer ";
        
        public static final String STR=" text ";
        
        public static final String PRIMARY = " PRIMARY KEY ";
        
        public static final String AUTO = " AUTOINCREMENT ";
        
        public static final String SPLIT=" , ";
    }
    /**
     * 
     * 广告数据库
     * @author 曾宝
     * @version  [V1.00, 2015年8月3日]
     * @see  [相关类/方法]
     * @since V1.00
     */
    public interface IAdv
    {   
        //表名
          public static final String TABLENAME = "adv_table";
              
          public static final String ID="advId";
          
          public static final String ADVNAME="advName";
          
          public static final String ADVPICTURE="advPicture";
          
          public static final String ADVLOCATIONCODE="advLocationCode";
          
          public static final String ADVURL="advUrl";
          
          public static final String ADVNUMBER="advNumber";
    }
}
