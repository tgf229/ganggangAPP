package com.ganggang.frame.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

/**
 * 
 * 控制打印  
 * @author 曾宝
 * @version  [V1.00, 2015-4-27]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class MyLogUtils
{
    /**
     *  DEBUG 打印开启
     */
     public static  boolean ISDEBUG = false; 
     /**
      * INFO 打印开启
      */
     public static  boolean ISINFO = false;
     
     /**
      * err打印
      */
     public static boolean ISERR=true;
     
     /**
      *err打印出异常 
      * <功能详细描述>
      * @param tag
      * @param msg
      * @param throwable
      * @see [类、类#方法、类#成员]
      */
     public static void e(String tag,String msg,Throwable throwable)
     {
         if(ISERR)
         {
             Log.e(tag, msg, throwable);
          
             
         }
     }
     
     /**
      *ERR打印 
      * <功能详细描述>
      * @param tag
      * @param msg
      * @see [类、类#方法、类#成员]
      */
     public static void e(String tag,String msg)
     {
         if(ISERR)
         {
             Log.e(tag, msg);
         }
     }
     /**
      * debug 打印
      * @param tag
      * @param msg
      * @see [类、类#方法、类#成员]
      */
     public static void d(String tag,String msg)
     {
         if(ISDEBUG)
             Log.d(tag, msg);
     }
     
     /**
      * info 打印
      * @param tag
      * @param msg
      * @see [类、类#方法、类#成员]
      */
     public static void i(String tag,String msg)
     {
         if(ISINFO)
             Log.d(tag, msg);
     }
     
     /**
      * 获取Log 信息
      * <功能详细描述>
      * @param tag
      * @return
      * @throws IOException
      * @see [类、类#方法、类#成员]
      */
     public static String GETLOGCATINFO(String tag) throws IOException
     {
         List<String> list = new ArrayList<String>();
         StringBuffer sb = new StringBuffer();
         list.add("logcat");
         //debug 
         list.add("-d");
         //指定tag
         list.add("-s");
         list.add(tag);
         Process process = Runtime.getRuntime().exec(list.toArray(new String[list.size()]));
         InputStream inputStream = process.getInputStream();
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
         String str = null;
         while((str = bufferedReader.readLine())!=null)
         {
             sb.append(str);
             sb.append("\n");
         }
         return sb.toString();
     }
}
