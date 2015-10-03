package com.ganggang.frame.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Adv;
import com.ganggang.ganggangapp.constant.ConstantEnum;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;

public class ImageUtils
{
    private  static final String TAG=ImageUtils.class.getSimpleName();
    public static  Bitmap showImage(String path)
    {
        MyLogUtils.i("showImage", "loading:" + path);
        BitmapFactory.Options bfOptions = new BitmapFactory.Options();
        bfOptions.inDither = false;
        bfOptions.inPurgeable = true;
        bfOptions.inInputShareable = true;
        bfOptions.inTempStorage = new byte[32 * 1024];
        
        File file = new File(path);
        FileInputStream fs = null;
        try
        {
            fs = new FileInputStream(file);
        }
        catch (FileNotFoundException e)
        {
            MyLogUtils.e(TAG, "FileNotFoundException", e);
            return null;
        }
        
        try
        {
            if (fs != null)
                return BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
            else
                return null;
        }
        catch (IOException e)
        {
            MyLogUtils.e(TAG, "IOException", e);
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
    
    public static  List<Bitmap> setImageView(List<Adv> list)
    {
        if(list==null)
            return null;
        List<Bitmap> mlist = new ArrayList<Bitmap>();
        for (int i = 0; list != null && i < list.size(); i++)
        {
            final Adv adv = list.get(i);
            String path = BitmapUtils.getSDPath(ConstantEnum.IFileType.TYPE_ADV);
            Bitmap bitmap = showImage(path + "/" + adv.getAdvName());
       /*     Bitmap bitmap = BitmapFactory.decodeFile(path + "/" + adv.getAdvName());*/
            mlist.add(bitmap);
        }
        return mlist.size() == 0 ? null : mlist;
    }
    
    
    public static List<Bitmap> setDefaultImageView(Context mContext,int[] imgs)
    {
        if(imgs==null)
            return null;
        List<Bitmap> mlist = new ArrayList<Bitmap>();
        for (int i = 0; i <imgs.length; i++)
        {
            Bitmap bitmap = null;
            Options options = new Options();
            options.inDither = false;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inTempStorage = new byte[32 * 1024];
            bitmap = BitmapFactory.decodeResource(mContext.getResources(), imgs[i],options);
            mlist.add(bitmap);
        }
        
        return mlist.size() == 0 ? null : mlist;
    }
}
