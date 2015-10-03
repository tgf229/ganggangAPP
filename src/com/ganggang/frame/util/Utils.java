package com.ganggang.frame.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.ganggang.ganggangapp.bean.Business;
import com.ganggang.ganggangapp.constant.ConstantAction;
import com.ganggang.ganggangapp.constant.ConstantBundle;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.ui.fragment.HomeFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class Utils
{
    public static String getFromAssets(Context mContext,String fileName)
    {
        try
        {
            InputStreamReader inputReader = new InputStreamReader(mContext.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    
    public static String meauseCompany(float juli)
    {
        if(juli<=0)
            return "未知";
        String company[] = {"m","km"};
        
        float val = juli%1000;
        if(val!=0)
        {
            return (int)juli/1000+company[1];
        }
        else
        {
            return (int)juli+company[0];
        }
    }
    
    public static float meauseLocation(double Latitude,double Longitude,AMapLocation mapLocation)
    {
        float juli = -1;
        if(mapLocation!=null)
        {
            LatLng startLng = new LatLng(mapLocation.getLatitude(), mapLocation.getLongitude());
            LatLng endLng = new LatLng(Latitude, Longitude);
            float juli_lng  = AMapUtils.calculateLineDistance(startLng, endLng);
            juli = juli_lng;
        }
        return juli;
    }
    
    public static void intentNavigation(Business business,Context mContext)
    {
        if(business==null)
            return;
        Intent intent = new Intent();
        intent.setAction(ConstantAction.NAVIGATION_ACTION);
        Bundle bundle = new Bundle();
        bundle.putString(ConstantEnum.IMap.TYPE_ARGS_NAVIGATION_DEST, business.getBusinessLongitude()+","+business.getBusinessLatitude());
        bundle.putString(ConstantEnum.IMap.TYPE_ARGS_NAVIGATION_DESTNAME, business.getBusinessName());
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        
        
    }
    
    public static void intentMap(Business business,Context mContext)
    {
        if(business==null)
            return;
        
        Intent intent = new Intent();
        intent.setAction(ConstantAction.MAP_ACTION);
        intent.putExtra(ConstantBundle.IMapType.TO_MAP, ConstantEnum.IMap.TYPE_DETAIL_SINGLE);
        intent.putExtra(ConstantBundle.IMapType.BUSINESS_DETAIL_MAP, business);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
   
    public static void intentUrl(String url,Context mContext)
    {
        try
        {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            intent = Intent.createChooser(intent, null);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
        catch (Exception e)
        {
            Toast.makeText(mContext, "无法进web界面", Toast.LENGTH_LONG);
        }
    }
    
    /**
     *跳转到拨号页面 
     * <功能详细描述>
     * @param phone
     * @param context
     * @see [类、类#方法、类#成员]
     */
    public static void sysPhone(String phone, Context context)
    {
        try
        {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        catch (Exception e)
        {
            Toast.makeText(context, "无法进拨打电话界面", Toast.LENGTH_LONG);
        }
    }
}
