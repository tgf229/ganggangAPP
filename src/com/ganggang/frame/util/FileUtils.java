package com.ganggang.frame.util;

import java.io.File;
import java.text.DecimalFormat;

import android.util.Log;

/**
 * 
 * 文件工具类
 * 
 * @author 曾宝
 * @version [V1.00, 2015年8月27日]
 * @see [相关类/方法]
 * @since V1.00
 */
public class FileUtils
{
    public static void deleteFile(String path)
    {
        File file = new File(path);
        if (file.exists())
        {
            deleteAllFiles(file);
        }
    }
    
    private static void deleteAllFiles(File f)
    {
        if (f.exists())
        {
            File[] files = f.listFiles();
            if (files != null)
            {
                for (File file : files)
                {
                    if (file.isDirectory())
                    {
                        deleteAllFiles(file);
                        file.delete(); // 删除目录下的所有文件后，该目录变成了空目录，可直接删除
                    }
                    else if (file.isFile())
                    {
                        file.delete();
                    }
                }
            }
            f.delete(); // 删除最外层的目录
        }
    }
    
    /**
     * 获取文件大小
     * <功能详细描述>
     * 
     * @param path
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getFileSize(String path)
    {
        File file = new File(path);
        if (!file.exists())
        {// 路径不存在
            return "";
        }
        long fileLength = getTotalSizeOfFilesInDir(file);
       // Log.i("size:", "文件的大小" + fileLength);
        String length = FormetFileSize(fileLength);
        return length;
    }
    
    private static long getTotalSizeOfFilesInDir(File file)
    {
        if (file.isFile())
            return file.length();
        File[] children = file.listFiles();
        long total = 0;
        if (children != null)
            for (File child : children)
            {
             //   Log.i("文件名：", child.getName());
                total += getTotalSizeOfFilesInDir(child);
            }
        return total;
    }
    
    // 转换文件大小
    public static String FormetFileSize(long fileS)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024)
        {
            fileSizeString = df.format((double)fileS) + "B";
        }
        else if (fileS < 1048576)
        {
            fileSizeString = df.format((double)fileS / 1024) + "K";
        }
        else if (fileS < 1073741824)
        {
            fileSizeString = df.format((double)fileS / 1048576) + "M";
        }
        else
        {
            fileSizeString = df.format((double)fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
    
}
