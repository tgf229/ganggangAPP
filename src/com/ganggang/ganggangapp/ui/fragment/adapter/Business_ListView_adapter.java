package com.ganggang.ganggangapp.ui.fragment.adapter;

import java.util.HashMap;
import java.util.List;

import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.ganggang.frame.freamwork.imgload.ImageLoader;
import com.ganggang.frame.freamwork.imgload.Imgloader_new;
import com.ganggang.frame.freamwork.ui.BasicActivity;
import com.ganggang.frame.util.BitmapUtils;
import com.ganggang.frame.util.ImageUtils;
import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.frame.util.Utils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Business;
import com.ganggang.ganggangapp.constant.ConstantAction;
import com.ganggang.ganggangapp.constant.ConstantBundle;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.constant.ConstantFile;
import com.ganggang.ganggangapp.constant.ConstantHttp;
import com.ganggang.ganggangapp.ui.fragment.HomeFragment;
import com.ganggang.ganggangapp.ui.module.HomeActivity;
import com.ganggang.ganggangapp.utils.ImageCallBack;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * 商家基本信息
 * 
 * @author 曾宝
 * @version [V1.00, 2015年7月31日]
 * @see [相关类/方法]
 * @since V1.00
 */
public class Business_ListView_adapter extends BaseAdapter
{
    private List<Business> mlist;
    
    private Context mContext;
    
    public static  HashMap<String, Bitmap> map = new HashMap<String, Bitmap>();
    private boolean flag;
    
    public Business_ListView_adapter(List<Business> mlist, Context mContext)
    {
        super();
        this.mlist = mlist;
        this.mContext = mContext;
        flag = (Boolean)MyDataUtils.getData(mContext, ConstantFile.IMore.FileName, ConstantFile.IMore.MORE_TRANSLATION, Boolean.class);
    }
    
    @Override
    public int getCount()
    {
        return mlist == null ? 0 : mlist.size();
    }
    
    @Override
    public Object getItem(int position)
    {
        return mlist.get(position);
    }
    
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Holder holder = null;
        if (convertView == null)
        {
//            Log.e("加载次数：",position+"");
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_homebusinessinfo, null);
            holder = new Holder();
            ViewUtils.initHolderView(holder, convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder)convertView.getTag();
        }
        final Business business = mlist.get(position);
        
        convertView.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setAction(ConstantAction.BUSINESS_DETAIL_ACTION);
                intent.putExtra(ConstantBundle.BUSINESSDEATILID, business.getBusinessId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
/*                ((BasicActivity)mContext).finish();*/
            }
        });
        holder.layout_homebusiness_img.setImageResource(R.drawable.moren_smail);
        if(business.getPreviewPicture()!=null)
        {
           String path =  BitmapUtils.getSDPath(ConstantEnum.IFileType.TYPE_BUSINESS);
           path+="/"+business.getPreviewPicture().substring(business.getPreviewPicture().lastIndexOf("/")+1);
//           Bitmap bitmap =  ImageUtils.showImage(path);
           if(map.containsKey(business.getPreviewPicture()))
           {
               holder.layout_homebusiness_img.setImageBitmap(map.get(business.getPreviewPicture()));
           }
           else
           {
           /*    ImageLoader imageLoader = new ImageLoader();
               imageLoader.execute(null, holder.layout_homebusiness_img, ConstantHttp.IP + business.getPreviewPicture(),ConstantEnum.IFileType.TYPE_BUSINESS);
           */
               Imgloader_new imgloader_new = new Imgloader_new();
               imgloader_new.loadDrawable(business.getPreviewPicture(), ConstantEnum.IFileType.TYPE_BUSINESS, new ImageCallBack(holder.layout_homebusiness_img, map));
           
           }
        /*   if(bitmap==null)
           {
               ImageLoader imageLoader = new ImageLoader();
               imageLoader.execute(null, holder.layout_homebusiness_img, ConstantHttp.IP + business.getPreviewPicture(),ConstantEnum.IFileType.TYPE_BUSINESS);
           }
           else
           {
               holder.layout_homebusiness_img.setImageBitmap(bitmap);
           }*/
        }
        holder.layout_homebusiness_name.setText(flag
            ? business.getBusinessBrandNameComplex()+""+business.getBusinessNameComplex()
            : business.getBusinessBrandName()+""+business.getBusinessName());
        holder.layout_homebusiness_location.setText(flag ? business.getBusinessAddressComplex() : business.getBusinessAddress());
        
        holder.layout_homebusiness_location_layout.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                Utils.intentMap(business, mContext);
                Utils.intentNavigation(business, mContext);
            }
        });
        
        String juli = "未知";
        if (HomeFragment.MAPLOCATION != null)
        {
            LatLng startLng = new LatLng(HomeFragment.MAPLOCATION.getLatitude(), HomeFragment.MAPLOCATION.getLongitude());
            LatLng endLng = new LatLng(business.getBusinessLatitude(), business.getBusinessLongitude());
            float juli_lng = AMapUtils.calculateLineDistance(startLng, endLng);
            juli =   Utils.meauseCompany(juli_lng);
            //            juli = juli_lng + "米";
        }
        holder.layout_homebusiness_location_juli.setText(juli);
        holder.layout_homebusiness_phone.setText(business.getBusinessPhone());
        holder.layout_homebusiness_phone.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Utils.sysPhone(business.getBusinessPhone(), mContext);
            }
        });
        
        return convertView;
    }
    
    class Holder
    {
        // 店 图
        ImageView layout_homebusiness_img;
        
        // 店名
        TextView layout_homebusiness_name;
        
        // 位置
        TextView layout_homebusiness_location;
        
        // 距离
        TextView layout_homebusiness_location_juli;
        
        // 号码
        TextView layout_homebusiness_phone;
        
        // 位置
        LinearLayout layout_homebusiness_location_layout;
        
    }
    
}
