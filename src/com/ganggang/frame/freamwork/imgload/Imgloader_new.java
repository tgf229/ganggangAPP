package com.ganggang.frame.freamwork.imgload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.ganggang.frame.util.BitmapUtils;
import com.ganggang.frame.util.ImageUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.StringUtils;
import com.ganggang.ganggangapp.constant.ConstantHttp;
import com.tencent.mm.sdk.openapi.ShowMessageFromWX;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Imgloader_new
{
    private HashMap<String, SoftReference<Bitmap>> imageCache;
    
    private final String TAG = Imgloader_new.class.getSimpleName();
    
    /**
     * <默认构造函数>
     */
    public Imgloader_new()
    {
        imageCache = new HashMap<String, SoftReference<Bitmap>>();
    }
    
    public Bitmap loadDrawable(final String imageUrl, int type, final ImageCallback imageCallback)
    {
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        // 获取对应的存储位置,拼接一个完整的本地url
        final String mPath = BitmapUtils.getSDPath(type) + "/" + fileName;
        
        final Handler handler = new Handler()
        {
            public void handleMessage(Message message)
            {
                imageCallback.imageLoaded((Bitmap)message.obj, imageUrl);
            }
        };
        new Thread()
        {
            @Override
            public void run()
            {
                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                Bitmap drawable = loadImageFromUrl(imageUrl, mPath);
                // 存储一个零时的位置
             //   imageCache.put(imageUrl, new SoftReference<Bitmap>(drawable));
                Message message = handler.obtainMessage(0, drawable);
                handler.sendMessage(message);
            }
        }.start();
        return null;
    }
    
    private Bitmap loadImageFromUrl(String url, String path)
    {
        Bitmap bm = null;
        InputStream is = null;
        try
        {
            File file = new File(path);
            if (!file.exists())
            { // 如果不存在,开启下载
               Log.i(TAG, "开始网络下载：" + url);
                bm = getNetWorkFile(url, file);
            }
            else
            {
                // 本地读取图片
                 Log.i(TAG, "开始提取本地信息：" + file.getAbsolutePath());
                bm = getBitmapForFile(file);
                // Log.i(TAG, "bm:" + bm == null ? "null" : "not null");
            }
            
        }
        catch (Exception e)
        {
            MyLogUtils.e(TAG, "imgloader_new", e);
            bm = null;
        }
        return bm;
    }
    
    /**
     * 本地 请求图片
     * <功能详细描述>
     * 
     * @param file
     * @return
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    private Bitmap getBitmapForFile(File file)
        throws IOException
    {
        FileInputStream f = null;
        Bitmap bitmap = null;
        try
        {
            f = new FileInputStream(file);
            FileDescriptor fd = f.getFD();
            // 获取图片的时间宽度/高度
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            // options.inPreferredConfig = Bitmap.Config.ARGB_4444;
            options.inPurgeable = true;
            options.inInputShareable = true;
            // 开始获取图片
            options.inJustDecodeBounds = false;
            BitmapFactory.decodeFileDescriptor(fd, null, options);
            // Log.i(TAG, "测算出来的高/宽：" + options.outWidth + " : " + options.outHeight);
            int be = (int)(options.outHeight / (float)100);
            if (be <= 0)
                be = 1;
            options.inSampleSize = be;
            // 图片质量的优化
            bitmap = BitmapFactory.decodeFileDescriptor(fd, null, options);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            if (f != null)
                f.close();
        }
        return bitmap;
        
    }
    
    /**
     * 网络请求下载图片
     * <功能详细描述>
     * 
     * @param url ：请求的网络地址
     * @param path :本地的路径-下载完 保存
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    private Bitmap getNetWorkFile(String url, File file)
        throws Exception
    {
        // 过滤掉可能出现的空格，替换成%20
        url = StringUtils.findSpaceAndFind(url);
        Log.e(TAG, "网络下载保存的地址："+file.getAbsolutePath());
        HttpGet httpRequest = new HttpGet(ConstantHttp.IP + url);
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = (HttpResponse)httpclient.execute(httpRequest);
        HttpEntity entity = response.getEntity();
        BufferedHttpEntity bufferedHttpEntity = null;
        InputStream is = null;
        Bitmap bitmap = null;
        try
        {
            if (entity != null)
            {
                // bufferedHttpEntity = new BufferedHttpEntity(entity);
                is = entity.getContent();
                // InputStream is = bufferedHttpEntity.getContent();
                if (response.getStatusLine().getStatusCode() == 200)
                {
                    byte[] result = readInputStream(is);
                    BitmapFactory.Options bfOptions = new BitmapFactory.Options();
                    bfOptions.inPreferredConfig = Bitmap.Config.RGB_565;
                    bfOptions.inPurgeable = true;
                    bfOptions.inInputShareable = true;
                    bfOptions.inJustDecodeBounds = true;
                    // bfOptions.inTempStorage = new byte[100 * 1024];
                    // 获取字节流大小
                    int len = result.length;
                    Log.e(TAG, "图片的大小："+len);
                    BitmapFactory.decodeByteArray(result, 0, len, bfOptions);
                    // is.reset();
                    bfOptions.inJustDecodeBounds = false;
                    int be = (int)(bfOptions.outHeight / (float)100);
                    if (be <= 0)
                        be = 1;
                    bfOptions.inSampleSize = be;
                    bitmap = BitmapFactory.decodeByteArray(result, 0, len, bfOptions);
                    is.close();
                    if (bitmap != null)
                        setBitmapForFile(bitmap, file);
                }
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            if (is != null)
                is.close();
        }
        return bitmap;
        
    }
    
    /**
     * 将输入流转为byte数组
     * 
     * @param in
     * @return
     * @throws Exception
     */
    private static byte[] readInputStream(InputStream in)
        throws Exception
    {
        // System.gc();
        ByteArrayOutputStream baos = null;
        try
        {
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = in.read(buffer)) != -1)
            {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            
            return baos.toByteArray();
        }
        catch (Exception e)
        {
            System.gc();
            return null;
        }
        finally
        {
            if (baos != null)
                baos.close();
            if (in != null)
                in.close();
        }
        
    }
    
    private void setBitmapForFile(Bitmap bitmap, File file)
        throws IOException
    {
        String path = file.getAbsoluteFile().toString();
        String root_path = file.getAbsoluteFile().toString().substring(0, path.lastIndexOf("/"));
        File root_file = new File(root_path);
        Log.d("正在保存", root_path+"  : "+path);
        if (!root_file.exists())
        {
            root_file.mkdirs();
        }
        if(file.exists())
        {
            file.delete();
        }
        if (!file.exists())
        {
            FileOutputStream outputStream = null;
            try
            {
                outputStream = new FileOutputStream(file);
               bitmap.compress(Bitmap.CompressFormat.PNG,90,outputStream);
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally
            {
                if (outputStream != null)
                {
                    outputStream.flush();
                    outputStream.close();
                }
            }
        }
    }
    
    public interface ImageCallback
    {
        public void imageLoaded(Bitmap imageDrawable, String imageUrl);
    }
    
}
