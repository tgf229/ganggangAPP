package com.ganggang.frame.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * 
 * 网络连接的工具类 
 * @author 曾宝
 * @version  [V1.00, 2015年7月14日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class NetWorkUtils
{
    public static boolean checkNetwork(Context context)
    {
        boolean flag = false;
        ConnectivityManager cwjManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cwjManager != null)
        {
            NetworkInfo info = cwjManager.getActiveNetworkInfo();
            if (info != null)
            {
                flag = info.isAvailable();
            }
        }
        return flag;
    }
    
}
