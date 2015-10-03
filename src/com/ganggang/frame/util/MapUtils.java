package com.ganggang.frame.util;

import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Business;
import com.ganggang.ganggangapp.constant.Constant;

import android.content.Context;
/**
 * 
 * 地图工具类 
 * @author 曾宝
 * @version  [V1.00, 2015年8月16日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class MapUtils
{
    
    /**
     *设置地图map 
     * <功能详细描述>
     * @param business
     * @param aMap
     * @param listener
     * @see [类、类#方法、类#成员]
     */
    public static  void addMarkersToMap(Business business,AMap aMap,AMap.OnMarkerClickListener listener)
    {
        MarkerOptions markerOption = new MarkerOptions();
        LatLng latLng = new LatLng(business.getBusinessLatitude(), business.getBusinessLongitude());
        markerOption.position(latLng);
        if(business.getBusinessBrand()!=null)
        {
            markerOption.title(business.getBusinessBrand()[0]);
        }
        else if(business.getBusinessBrandName()!=null)
        {
            markerOption.title(business.getBusinessBrandName());
        }
        markerOption.snippet(business.getBusinessName());
        //是/否允许用户 自己移动mark
        markerOption.draggable(false);
        markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.ditushangjia));
        Marker marker = aMap.addMarker(markerOption);
        marker.setVisible(true);
        marker.setObject(business);
        aMap.setOnMarkerClickListener(listener);
    }
    
    
    
    /**
     * 设置地图信息
     * <功能详细描述>
     * @param context
     * @param aMap
     * @param locationManagerProxy
     * @param source
     * @param listener
     * @see [类、类#方法、类#成员]
     */
    public static  void setUpMap(Context context,boolean isGestureEnable,AMap aMap,LocationManagerProxy locationManagerProxy,LocationSource source,AMapLocationListener  listener)
    {
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);
        uiSettings.setZoomControlsEnabled(false);
        // 手势关闭
        uiSettings.setZoomGesturesEnabled(isGestureEnable);
        uiSettings.setScrollGesturesEnabled(isGestureEnable);
        // 手势缩放
//        uiSettings.setZoomGesturesEnabled(false);
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.wodeweizhi));// 设置小蓝点的图标
       myLocationStyle.strokeColor(android.R.color.transparent);
        myLocationStyle.radiusFillColor(android.R.color.transparent);
     
        aMap.setMyLocationStyle(myLocationStyle);
        // 缩放
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        locationManagerProxy = LocationManagerProxy.getInstance(context);
        locationManagerProxy.setGpsEnable(true);
        locationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, Constant.MAPREFRESHTIME, 10, listener);
        aMap.setLocationSource(source);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);
    }
 
}
