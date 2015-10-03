package com.ganggang.frame.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.amap.api.maps2d.model.BitmapDescriptor;
import com.ganggang.ganggangapp.constant.Constant;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.constant.ConstantHttp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.renderscript.Type;
import android.util.Log;

/**
 * 
 * 图片工具类
 * 
 * @author 曾宝
 * @version [V1.00, 2015年7月14日]
 * @see [相关类/方法]
 * @since V1.00
 */
public class BitmapUtils
{
    private final static String TAG = BitmapUtils.class.getSimpleName();
    
    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels)
    {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8)
        {
            roundedSize = 1;
            while (roundedSize < initialSize)
            {
                roundedSize <<= 1;
            }
        }
        else
        {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }
    
    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels)
    {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int)Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int)Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound)
        {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1))
        {
            return 1;
        }
        else if (minSideLength == -1)
        {
            return lowerBound;
        }
        else
        {
            return upperBound;
        }
    }
    
    public static void downIMG_SaveSD(final String urlStr, final String fileName, final int num)
    {
        Runnable runnable = new Runnable()
        {
            InputStream is = null;
            
            FileOutputStream outputStream = null;
            
            @Override
            public void run()
            {
                try
                {
                    String path = getSDPath(num);
                    File file_mkdir = new File(path);
                    if (!file_mkdir.exists())
                    {// 存在 不需要在下载
                        file_mkdir.mkdirs();
                    }
                    
                    File file2 = new File(path + "/" + fileName);
                    if (!file2.exists())
                    {
                        HttpGet httpRequest = new HttpGet(ConstantHttp.IP + urlStr);
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpResponse response = (HttpResponse)httpclient.execute(httpRequest);
                        HttpEntity entity = response.getEntity();
                        BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(entity);
                        InputStream is = bufferedHttpEntity.getContent();
                        BitmapFactory.Options bfOptions = new BitmapFactory.Options();
                        bfOptions.inDither = false;
                        bfOptions.inPurgeable = true;
                        bfOptions.inInputShareable = true;
                        /* bfOptions.inPreferredConfig = Bitmap.Config.ARGB_8888; */
                        bfOptions.inTempStorage = new byte[100 * 1024];
                        bfOptions.inSampleSize = 1;
                        bfOptions.inJustDecodeBounds = false;
                        FileOutputStream outputStream = null;
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        outputStream = new FileOutputStream(file2);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    if (is != null)
                    {
                        try
                        {
                            is.close();
                        }
                        catch (IOException e)
                        {
                            MyLogUtils.e(TAG, "is.close", e);
                        }
                    }
                    if (outputStream != null)
                    {
                        try
                        {
                            outputStream.close();
                        }
                        catch (IOException e)
                        {
                            MyLogUtils.e(TAG, "outputStream.close", e);
                        }
                    }
                }
                
            }
        };
        
        new Thread(runnable).start();
        
    }
    
    /**
     * 获取存储路径
     * <功能详细描述>
     * 
     * @param num
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getSDPath(int num)
    {
        String path = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            // Log.e(TAG, "external:-----------");
            path = Environment.getExternalStoragePublicDirectory(Environment.MEDIA_MOUNTED_READ_ONLY).getAbsolutePath();
            // path = Environment.getExternalStorageDirectory().toString();
        }
        else
        {
            // Log.e(TAG, "Download:-----------");
            path = Environment.getDownloadCacheDirectory().toString();
        }
        MyLogUtils.i(TAG, "父路径：" + path);
        if (path != null)
        {
            if (num == ConstantEnum.IFileType.TYPE_ADV)
            {
                path = path + ConstantEnum.IFileType.IFileName.FILE_NAME_PATH + ConstantEnum.IFileType.IFileName.FILE_NAME_ADV;
            }
            else if (num == ConstantEnum.IFileType.TYPE_BUSINESS)
            {
                path = path + ConstantEnum.IFileType.IFileName.FILE_NAME_PATH + ConstantEnum.IFileType.IFileName.FILE_NAME_BUSINESS;
            }
            else if (num == ConstantEnum.IFileType.TYPE_MINGRENMINGBO)
            {
                path = path + ConstantEnum.IFileType.IFileName.FILE_NAME_PATH + ConstantEnum.IFileType.IFileName.FILE_NAME_MINGRENMINGBO;
            }
            else if (num == ConstantEnum.IFileType.TYPE_PATH)
            {
                path = path + ConstantEnum.IFileType.IFileName.FILE_NAME_PATH;
            }
            else if(num==ConstantEnum.IFileType.TYPE_BUSINESSBRANNDER)
            {
                path = path + ConstantEnum.IFileType.IFileName.FILE_NAME_PATH + ConstantEnum.IFileType.IFileName.FILE_NAME_BUSINESSBRANNDER;
            }
            return path;
        }
        return null;
    }
    
    public static Bitmap getScaleBitmapByUri(Uri uri, Context context)
        throws FileNotFoundException
    {
        Bitmap bitmap;
        int w;
        InputStream is = context.getContentResolver().openInputStream(uri);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeStream(is, null, options);
        w = options.outWidth;
        if (w > MyConstants.BITMAP_STANDRD_WIDTH)
        {
            options.inSampleSize = w / MyConstants.BITMAP_STANDRD_WIDTH;
            options.inJustDecodeBounds = false;
            String path = getPicPathByUri(uri, context);
            bitmap = BitmapFactory.decodeFile(path, options);
        }
        else
        {
            bitmap = BitmapFactory.decodeStream(is);
        }
        return bitmap;
    }
    
    public static String getPicPathByUri(Uri uri, Context context)
    {
        String path = "";
        Uri imageUri = uri;
        Cursor cursor = context.getContentResolver().query(imageUri, null, null, null, null);
        if (cursor.moveToNext())
        {
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        }
        return path;
    }
}
