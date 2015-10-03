package com.ganggang.frame.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class StringUtils
{
    /**
     * 替换空格 成%20 
     * <功能详细描述>
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String findSpaceAndFind(String str)
    {
        if(str==null || str.isEmpty())
                return "";
        
        if(str.indexOf(" ")>=0)
        {
          return   str.replaceAll(" ", "%20");
        }
        else
            return str;
    }
    
    public static String Inputstr2Str_Reader(InputStream in, String encode)
    {
        
        String str = "";
        try
        {
            if (encode == null || encode.equals(""))
            {
                // 默认以utf-8形式
                encode = "utf-8";
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, encode));
            StringBuffer sb = new StringBuffer();
            
            while ((str = reader.readLine()) != null)
            {
                sb.append(str).append("\n");
            }
            return sb.toString();
        }
        catch (UnsupportedEncodingException e1)
        {
            e1.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return str;
    }
}
