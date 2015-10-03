package com.ganggang.ganggangapp.ui.map;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.LocationSource.OnLocationChangedListener;
import com.ganggang.frame.freamwork.ui.BasicActivity;
import com.ganggang.frame.util.MapUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.constant.Constant;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

/**
 * 
 *这里的地图展现 周围商家的信息  
 * @author 曾宝
 * @version  [V1.00, 2015年8月20日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class MyMapActivity extends BasicActivity implements LocationSource,AMapLocationListener
{
    
    private final String TAG = MyMapActivity.class.getSimpleName();
    
    private MapView activity_mymap;
    
    private AMap aMap;
    
    private LocationManagerProxy mLocationManagerProxy;
    
    private OnLocationChangedListener mListener;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        activity_mymap.onCreate(savedInstanceState);
    }
    
    @Override
    public void setContentView()
    {
        setContentView(R.layout.activity_mymap);
    }
    
    @Override
    public void initViews()
    {
        try
        {
            ViewUtils.initView(this);
            activity_mymap = (MapView)findViewById(R.id.activity_mymap);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    @Override
    public void initListeners()
    {
      
        
    }
    
    @Override
    public void initData()
    {
        if(aMap==null)
        {
            aMap = activity_mymap.getMap();
            MapUtils.setUpMap(this, true, aMap, mLocationManagerProxy, this, this);
        }
        
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        activity_mymap.onSaveInstanceState(outState);
    }
    
 
    
    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        activity_mymap.onResume();
    }
    
   
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        activity_mymap.onDestroy();
    }

    @Override
    public void onLocationChanged(Location location)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onProviderEnabled(String provider)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onProviderDisabled(String provider)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onLocationChanged(AMapLocation arg0)
    {
        if (mListener != null && arg0 != null)
        {
          //  Log.i("map", "arg0:" + arg0.getLatitude() + "  " + arg0.getLongitude());
         // 显示系统小蓝点
            mListener.onLocationChanged(arg0);
        }
    }

    @Override
    public void activate(OnLocationChangedListener arg0)
    {
        mListener = arg0;
        if (mLocationManagerProxy == null)
        {
            mLocationManagerProxy = LocationManagerProxy.getInstance(this);
            mLocationManagerProxy.setGpsEnable(true);
            mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, Constant.MAPREFRESHTIME, 10, this);
        }
        
    }

    @Override
    public void deactivate()
    {
        mListener = null;
        if (mLocationManagerProxy != null)
        {
            mLocationManagerProxy.removeUpdates(this);
            mLocationManagerProxy.destroy();
        }
        mLocationManagerProxy = null;
    }
    
}
