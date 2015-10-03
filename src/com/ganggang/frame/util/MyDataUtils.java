package com.ganggang.frame.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MyDataUtils
{
    
   /**
    * 保存sharedPreferences 数据
    * <功能详细描述>
    * @param context
    * @param fileName
    * @param map
    * @see [类、类#方法、类#成员]
    */
    public static void putData(Context context, String fileName, Map<String, Object> map)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor edit = sharedPreferences.edit();
        for (Entry<String, Object> set : map.entrySet())
        {
            String key = set.getKey();
            Object value = set.getValue();
            if (value instanceof String)
            {
                edit.putString(key, (String)value);
            }
            else if (value instanceof Boolean)
            {
                edit.putBoolean(key, (Boolean)value);
            }
            else if (value instanceof Float)
            {
                edit.putFloat(key, (Float)value);
            }
            else if (value instanceof Long)
            {
                edit.putLong(key, (Long)value);
            }
            else
            {
                edit.putInt(key, (Integer)value);
            }
        }
        edit.commit();
    }
    
    /**
     * sharedPreference 中获取数据
     * <功能详细描述>
     * @param context
     * @param fileName
     * @param key
     * @param clazz
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Object getData(Context context, String fileName, String key, Class clazz)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        if (clazz.getName().equals(String.class.getName()))
        {
            return sharedPreferences.getString(key, "");
        }
        else if (clazz.getName().equals(Integer.class.getName()))
        {
            try
            {
                return sharedPreferences.getInt(key, 0);
            }
            catch (Exception e)
            {
                return sharedPreferences.getString(key,"-1");
            }
        }
        else if (clazz.getName().equals(Float.class.getName()))
        {
            return sharedPreferences.getFloat(key, 0);
        }
        else if (clazz.getName().equals(Long.class.getName()))
        {
            return sharedPreferences.getLong(key, 0);
        }
        else
        {
            return sharedPreferences.getBoolean(key, false);
        }
    }
    

}
