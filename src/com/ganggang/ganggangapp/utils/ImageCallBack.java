package com.ganggang.ganggangapp.utils;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import com.ganggang.frame.freamwork.imgload.Imgloader_new.ImageCallback;
import com.ganggang.ganggangapp.R;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageCallBack implements ImageCallback
{
    private ImageView img;
    
    private HashMap<String, Bitmap> mBitmaps;
    /**
     * <默认构造函数>
     */
    public ImageCallBack(ImageView img, HashMap<String, Bitmap> mBitmaps)
    {
        super();
        this.img = img;
        this.mBitmaps = mBitmaps;
    }
    
    @Override
    public void imageLoaded(Bitmap imageDrawable, String imageUrl)
    {
        if(imageDrawable==null)
            this.img.setImageResource(R.drawable.moren_smail);
        else
        {
            this.img.setImageBitmap(imageDrawable);
           this.mBitmaps.put(imageUrl, imageDrawable);
        }
    }
    
}
