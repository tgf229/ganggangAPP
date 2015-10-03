package com.ganggang.frame.util;

import java.lang.reflect.Field;

/**
 * 
 *数据库 工具  
 * @author 曾宝
 * @version  [V1.00, 2015年8月3日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class DBUtil
{
    private static final String serialVersionUID="serialVersionUID";
    
    /**
     *根据class 生成创建表语句，目前不支持嵌套类型 
     * <功能详细描述>
     * @param cls
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public static String getClassForCreateTableString(String tableName,Class cls) throws Exception
    {
        if(cls==null)
           throw new Exception("class is null");
        
       
        return null;
    }
}
