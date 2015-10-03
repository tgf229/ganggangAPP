package com.ganggang.ganggangapp.ui.businessbrand.adapter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.ganggang.frame.constant.Constanct_Db;
import com.ganggang.frame.freamwork.imgload.ImageLoader;
import com.ganggang.frame.freamwork.imgload.Imgloader_new;
import com.ganggang.frame.util.BitmapUtils;
import com.ganggang.frame.util.ImageUtils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.BusinessBrand;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.constant.ConstantHttp;
import com.ganggang.ganggangapp.utils.ImageCallBack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class BusinessBrand_ListView_adapter extends BaseAdapter
{
    private List<BusinessBrand> mlist_bitmap;
    
    private Context mContext;
    
    public  HashMap<String, Bitmap> bitmaps = new HashMap<String, Bitmap>();
    
    /**
     * <默认构造函数>
     */
    public BusinessBrand_ListView_adapter(List<BusinessBrand> mlist_bitmap, Context mContext)
    {
        super();
        this.mlist_bitmap = mlist_bitmap;
//        bitmaps.clear();
        this.mContext = mContext;
    }
    
    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return mlist_bitmap == null ? 0 : mlist_bitmap.size();
    }
    
    @Override
    public Object getItem(int position)
    {
        // TODO Auto-generated method stub
        return mlist_bitmap == null ? null : mlist_bitmap.get(position);
    }
    
    @Override
    public long getItemId(int position)
    {
        // TODO Auto-generated method stub
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Holder holder = null;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_businessbanrd, null);
            // ImageView img = new ImageView(mContext);
            
            holder = new Holder();
            ViewUtils.initHolderView(holder, convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder)convertView.getTag();
        }
        BusinessBrand businessBrand = mlist_bitmap.get(position);
        if (businessBrand.getBusinessBrandPicture() != null)
        {
            holder.layout_businessbrand_img.setScaleType(ScaleType.FIT_XY);
//            holder.layout_businessbrand_img.setImageResource(R.drawable.moren_img);
          Log.e("BRAND", "读取本地内存" + bitmaps.containsKey(businessBrand.getBusinessBrandPicture()));
            if (bitmaps.containsKey(businessBrand.getBusinessBrandPicture()))
            {
                Log.e("BRAND", "读取本地内存");
                holder.layout_businessbrand_img.setImageBitmap(bitmaps.get(businessBrand.getBusinessBrandPicture()));
            }
            else
            {
                holder.layout_businessbrand_img.setImageResource(R.drawable.moren_img);
                Imgloader_new imgloader_new = new Imgloader_new();
                imgloader_new.loadDrawable(businessBrand.getBusinessBrandPicture(), ConstantEnum.IFileType.TYPE_BUSINESS, new ImageCallBack(holder.layout_businessbrand_img, bitmaps));
                
         }
            
        }
        
        return convertView;
    }
    
    class Holder
    {
        ImageView layout_businessbrand_img;
    }
    
}
