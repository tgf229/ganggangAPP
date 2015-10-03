package com.ganggang.frame.freamwork.imgload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.aps.bf;
import com.ganggang.frame.util.BitmapUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.StringUtils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.constant.ConstantHttp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * 
 * Image 异步下载
 * 
 * @author 曾宝
 * @version [V1.00, 2015年8月1日]
 * @see [相关类/方法]
 * @since V1.00
 */
public class ImageLoader extends AsyncTask<Object, Integer, Bitmap>
{
    private final String TAG = ImageLoader.class.getSimpleName();
    
    private ImageView img;
    
    private HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();
    
    @Override
    protected Bitmap doInBackground(Object... params)
    {
        img = (ImageView)params[1];
        // 下载路径
        String path = (String)params[2];
        
        int type = Integer.parseInt(params[3].toString());
        
        Bitmap bitmap = null;
        try
        {
            /*
             * if(imageCache.containsKey(path))
             * {
             * SoftReference<Bitmap> softReference = imageCache.get(path);
             * Bitmap drawable = softReference.get();
             * return drawable;
             * }
             * else
             * {
             */
            bitmap = loadimage(path, type);
            // imageCache.put(path, new SoftReference<Bitmap>(bitmap));
            // }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bitmap;
    }
    
    private Bitmap loadimage(String path, int type)
        throws IOException
    {
        String DIR_PATH = BitmapUtils.getSDPath(type);
        String name = path.substring(path.lastIndexOf("/") + 1);
        // MyLogUtils.i(TAG, "图片名：" + name);
        Bitmap bitmap = null;
        FileOutputStream outputStream = null;
        InputStream is = null;
        FileInputStream fs = null;
        try
        {
            File file_mkdir = new File(DIR_PATH);
            if (!file_mkdir.exists())
            {// 存在 不需要在下载
                file_mkdir.mkdirs();
            }
            File file2 = new File(DIR_PATH + "/" + name);
            if (!file2.createNewFile())
            {// 建立失败，存在
                MyLogUtils.i(TAG, "图片已存在：" + file2.getAbsolutePath());
                // bitmap = BitmapFactory.decodeFile(file2.getAbsolutePath());
                bitmap = showImage(file2.getAbsolutePath());
                return bitmap;
            }
            else
            {
                MyLogUtils.i(TAG, "图片路径 建立成功，下载图片：" + path);
                path = StringUtils.findSpaceAndFind(path);
                HttpGet httpRequest = new HttpGet(path);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = (HttpResponse)httpclient.execute(httpRequest);
                HttpEntity entity = response.getEntity();
                BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(entity);
                is = bufferedHttpEntity.getContent();
                if (response.getStatusLine().getStatusCode() == 200)
                {
                    return showInputImage(is, file2);
                }
                else
                {
                    return null;
                }
                
            }
        }
        catch (Exception e)
        {
            MyLogUtils.e(TAG, "IMGLOADER ISERR", e);
            return bitmap;
        }
        finally
        {
            if (outputStream != null)
                outputStream.close();
                
            if (is != null)
                is.close();
                
            if (fs != null)
                fs.close();
        }
    }
    
    private Bitmap showInputImage(InputStream is, File file2)
    {
        if (is == null)
            return null;
        BitmapFactory.Options bfOptions = new BitmapFactory.Options();
        bfOptions.inDither = false;
        bfOptions.inPurgeable = true;
        bfOptions.inInputShareable = true;
        // bfOptions.inTempStorage = new byte[32 * 1024];
        bfOptions.inJustDecodeBounds = true;
        
        // Log.i(TAG, "测算出来的高/宽：" + options.outWidth + " : " + options.outHeight);
        int be = (int)(bfOptions.outHeight / (float)100);
        
        if (be <= 0)
            be = 1;
        bfOptions.inSampleSize = be;
        
        FileOutputStream outputStream = null;
        try
        {
            outputStream = new FileOutputStream(file2);
            
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, bfOptions);
            if (bitmap != null)
            {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            }
            return bitmap;
        }
        catch (FileNotFoundException e)
        {
            MyLogUtils.e(TAG, "FileNotFoundException:", e);
            return null;
        }
        finally
        {
            if(is!=null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
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
                    MyLogUtils.e(TAG, "IOException", e);
                    return null;
                }
            }
        }
        
    }
    
    private Bitmap showImage(String path)
    {
        File file = new File(path);
        FileInputStream fs = null;
        try
        {
            fs = new FileInputStream(file);
            // Log.i("showImage", "loading:" + path);
            BitmapFactory.Options bfOptions = new BitmapFactory.Options();
            bfOptions.inDither = false;
            bfOptions.inPurgeable = true;
            bfOptions.inInputShareable = true;
            // bfOptions.inTempStorage = new byte[32 * 1024];
            bfOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
            // Log.i(TAG, "测算出来的高/宽：" + options.outWidth + " : " + options.outHeight);
            int be = (int)(bfOptions.outHeight / (float)100);
            
            if (be <= 0)
                be = 1;
            bfOptions.inSampleSize = be;
            
            bfOptions.inJustDecodeBounds = false;
            if (fs != null)
                return BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
            else
                return null;
        }
        catch (FileNotFoundException e)
        {
            MyLogUtils.e(TAG, "FileNotFoundException", e);
            return null;
        }
        catch (IOException e)
        {
            MyLogUtils.e(TAG, "FileNotFoundException", e);
            return null;
        }
        finally
        {
            if (fs != null)
            {
                try
                {
                    fs.close();
                }
                catch (IOException e)
                {
                    MyLogUtils.e(TAG, "IOException", e);
                    return null;
                }
            }
        }
    }
    
    @Override
    protected void onPreExecute()
    {
        // TODO Auto-generated method stub
        super.onPreExecute();
    }
    
    @Override
    protected void onPostExecute(Bitmap result)
    {
        if (result == null)
        {
        
        }
        else
        {
            float height = img.getMeasuredHeight();
            float width = img.getMeasuredWidth();
            
            /*
             * Bitmap bitmap = ViewUtils.AdjustBitmap(result, width / result.getWidth(), height / result.getHeight());
             */
            img.setImageBitmap(result);
            
            /*
             * if (!result.isRecycled())
             * {
             * result.recycle(); // 回收图片所占的内存
             * System.gc(); // 提醒系统及时回收
             * }
             */
        }
        super.onPostExecute(result);
    }
    
    @Override
    protected void onProgressUpdate(Integer... values)
    {
        super.onProgressUpdate(values);
    }
}
