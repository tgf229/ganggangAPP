package com.ganggang.frame.freamwork.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnCameraChangeListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.overlay.BusRouteOverlay;
import com.amap.api.maps2d.overlay.DrivingRouteOverlay;
import com.amap.api.maps2d.overlay.WalkRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.RouteSearch.BusRouteQuery;
import com.amap.api.services.route.RouteSearch.DriveRouteQuery;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.RouteSearch.WalkRouteQuery;
import com.amap.api.services.route.WalkRouteResult;
import com.ganggang.frame.freamwork.ui.BasicActivity;
import com.ganggang.frame.util.CheckUtils;
import com.ganggang.frame.util.MapUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Business;
import com.ganggang.ganggangapp.bean.BusinessBrand;
import com.ganggang.ganggangapp.constant.Constant;
import com.ganggang.ganggangapp.constant.ConstantAction;
import com.ganggang.ganggangapp.constant.ConstantBundle;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.constant.ConstantMessage.IBusiness;
import com.ganggang.ganggangapp.logic.maplogic.IMapLogic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyMap extends BasicActivity implements OnClickListener, LocationSource, AMapLocationListener, OnMarkerClickListener, OnRouteSearchListener, OnPoiSearchListener, OnCameraChangeListener
{
    private final String TAG = MyMap.class.getSimpleName();
    
    private MapView mapView;
    
    private AMap aMap;
    
    private LocationManagerProxy locationManager;
    
    private OnLocationChangedListener mListener;
    
    private Button map_my_location, map_return_left;
    
    private TextView map_bus, map_drive, map_walk, map_bus_time, map_content;
    
    private LinearLayout map_show_layout;
    
    private IMapLogic logic;
    
    private IBusiness logic_business;
    
    private List<Business> mlist = new ArrayList<Business>();
    
    private ImageView map_shuaxin;
    
    private int type = -1;;
    // -----------------------------------------------------------
    
    private ProgressDialog progDialog = null;// 搜索时进度条
    // 路线规划类
    
    private RouteSearch routeSearch;
    
    private int busMode = RouteSearch.BusDefault;// 公交默认模式
    // 驾车默认模式
    
    private int drivingMode = RouteSearch.DrivingDefault;
    
    // 步行默认模式
    private int walkMode = RouteSearch.WalkDefault;
    
    private BusRouteResult busRouteResult;// 公交模式查询结果
    
    private DriveRouteResult driveRouteResult;// 驾车模式查询结果
    
    private WalkRouteResult walkRouteResult;// 步行模式查询结果
    
    private int routeType = 1;// 1代表公交模式，2代表驾车模式，3代表步行模式
    
    private String strStart;
    
    private String strEnd;
    
    private LatLonPoint startPoint = null;
    
    private LatLonPoint endPoint = null;
    
    private PoiSearch.Query startSearchQuery;
    
    private PoiSearch.Query endSearchQuery;
    
    private Marker startMk, targetMk;
    
    private String code;
    
    private String city;
    
    private AMapLocation mstartlocation;
    
    /**
     * 商家详情
     */
    private Business business_detail;
    
    private BusinessBrand businessbrand_detail;
    
    private String mBrand_type;
    
    private HashMap<String, Business> mBusinessMap;
    
    private boolean mOnGetCenter = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);
    }
    
    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    
    @Override
    public void setContentView()
    {
        setContentView(R.layout.activity_map);
    }
    
    @Override
    public void initViews()
    {
        try
        {
            ViewUtils.initView(this);
        }
        catch (Exception e)
        {
            MyLogUtils.e(TAG, "init is err", e);
        }
        mapView = (MapView)findViewById(R.id.map);
    }
    
    @Override
    public void initListeners()
    {
        map_bus.setOnClickListener(this);
        map_drive.setOnClickListener(this);
        map_walk.setOnClickListener(this);
        map_return_left.setOnClickListener(this);
        map_shuaxin.setOnClickListener(this);
    }
    
    @Override
    public void initData()
    {
        mBusinessMap = new HashMap<String, Business>();
        if (aMap == null)
        {
            aMap = mapView.getMap();
            MapUtils.setUpMap(this, true, aMap, locationManager, this, this);
            // setMap();
            routeSearch = new RouteSearch(this);
            routeSearch.setRouteSearchListener(this);
            aMap.setOnCameraChangeListener(this);
            Bundle bundle = getIntent().getExtras();
            if (bundle != null)
            {
                Object obj = bundle.getSerializable(ConstantBundle.IMapType.BUSINESS_DETAIL_MAP);
                // Object obj = getIntent().getSerializableExtra(ConstantBundle.IMapType.BUSINESS_DETAIL_MAP);
                mlist.clear();
                if (obj != null)
                {
                    if (obj instanceof Business)
                    {
                        mlist.add((Business)obj);
                    }
                    else
                    {
                        mlist.addAll((List<Business>)obj);
                    }
                    for (int i = 0; mlist != null && i < mlist.size(); i++)
                    {
                        Business business = mlist.get(i);
                        MapUtils.addMarkersToMap(business, aMap, this);
                    }
                }
                
            }
            type = getIntent().getIntExtra(ConstantBundle.IMapType.TO_MAP, -1);
            if (type == ConstantEnum.IMap.TYPE_HOME)
            {
            }
            else if (type == ConstantEnum.IMap.TYPE_DETAIL_SINGLE)
            {// 获取单家点的详情
                Object obj = getIntent().getSerializableExtra(ConstantBundle.IMapType.BUSINESS_DETAIL_MAP);
                if (obj == null)
                {
                    showToast("商家地址坐标为录入", Toast.LENGTH_LONG);
                }
                else
                {
                    business_detail = (Business)obj;
                    MapUtils.addMarkersToMap(business_detail, aMap, this);
                    
                }
            }
            else if (type == ConstantEnum.IMap.TYPE_DETAIL_BUSINESSBRAND)
            {
                Object obj = getIntent().getSerializableExtra(ConstantBundle.IMapType.BUSINESS_DETAIL_MAP);
                mlist.clear();
                mlist.addAll((List<Business>)obj);
                for (int i = 0; mlist != null && i < mlist.size(); i++)
                {
                    Business business = mlist.get(i);
                    MapUtils.addMarkersToMap(business, aMap, this);
                }
            }
            else if (type == ConstantEnum.IMap.TYPE_BRAND_BUSINESS)
            {
                Object obj = getIntent().getStringExtra(ConstantBundle.IMapType.BRAND_MAP);
                mBrand_type = obj.toString();
            }
        }
        
    }
    
    @Override
    protected void handleStateMessage(Message msg)
    {
        switch (msg.what)
        {
            case ConstantMessage.IMap.BUSINESSBYSCOPE_OK_MSG:
                List<Business> list_business = (List<Business>)msg.obj;
                getMap(list_business);
                break;
            case ConstantMessage.IMap.BUSINESSBYSCOPEANDBRAND_OK_MSG:
                List<Business> list_business1 = (List<Business>)msg.obj;
                getMap(list_business1);
                break;
            case ConstantMessage.IMap.BUSINESSBYSCOPEANDTYPE_OK_MSG:
                List<Business> list_business2 = (List<Business>)msg.obj;
                getMap(list_business2);
                break;
            case ConstantMessage.IMap.BUSINESSBYSCOPEANDTYPE_ERR_MSG:
                
                break;
            case ConstantMessage.IMap.BUSINESSBYSCOPE_ERR_MSG:
                
                break;
            case ConstantMessage.IMap.BUSINESSBYSCOPEANDBRAND_ERR_MSG:
                
                break;
            default:
                break;
        }
    }
    
    private void getMap(List<Business> list)
    {
        for (int i = 0; list != null && i < list.size(); i++)
        {
            Business business = list.get(i);
            if (mBusinessMap.containsKey(business.getBusinessNameComplex()))
            {
                continue;
            }
            else
            {
                mBusinessMap.put(business.getBusinessBrandNameComplex(), business);
                MapUtils.addMarkersToMap(business, aMap, this);
                // addMarkersToMap(business);
            }
        }
        
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        mapView.onPause();
        deactivate();
    }
    
    @Override
    protected void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
        mapView.onDestroy();
    }
    
    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        mapView.onResume();
    }
    
    @Override
    public void onClick(View v)
    {
        
        switch (v.getId())
        {
            case R.id.map_shuaxin:
                mOnGetCenter = true;
                break;
            case R.id.map_bus:
                MyLogUtils.i(TAG, "bus导航");
                /*
                 * routeType = 1;// 标识为公交模式
                 * busMode = RouteSearch.BusDefault;
                 * searchRoute();
                 */
                if (map_content.getVisibility() == View.VISIBLE && !CheckUtils.isEmpty(map_content.getText().toString()))
                {
                    Business business = (Business)map_content.getTag();
                    Intent intent = new Intent();
                    intent.setAction(ConstantAction.NAVIGATION_ACTION);
                    Bundle bundle = new Bundle();
                    bundle.putString(ConstantEnum.IMap.TYPE_ARGS_NAVIGATION_DEST, business.getBusinessLongitude() + "," + business.getBusinessLatitude());
                    bundle.putString(ConstantEnum.IMap.TYPE_ARGS_NAVIGATION_DESTNAME, business.getBusinessNameComplex());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else
                    showToast(R.string.str_map_not_click, Toast.LENGTH_LONG);
                break;
            case R.id.map_drive:
                MyLogUtils.i(TAG, "驾车路线");
                routeType = 2;// 标识为驾车模式
                drivingMode = RouteSearch.DrivingDefault;
                // startPoint = new LatLonPoint(mstartlocation.getLatitude(), mstartlocation.getLongitude());
                
                searchRoute();
                break;
            case R.id.map_walk:
                MyLogUtils.i(TAG, "行程");
                routeType = 3;// 标识为步行模式
                walkMode = RouteSearch.WalkMultipath;
                searchRoute();
                break;
            case R.id.map_return_left:
                MyLogUtils.i(TAG, "返回");
                finish();
                break;
            default:
                break;
        }
        
    }
    
    @Override
    public void activate(OnLocationChangedListener arg0)
    {
        mListener = arg0;
        if (locationManager == null)
        {
            locationManager = LocationManagerProxy.getInstance(this);
            locationManager.setGpsEnable(true);
            locationManager.requestLocationData(LocationProviderProxy.AMapNetwork, Constant.MAPREFRESHTIME, 10, this);
        }
        
    }
    
    @Override
    public void deactivate()
    {
        mListener = null;
        if (locationManager != null)
        {
            locationManager.removeUpdates(this);
            locationManager.destroy();
        }
        locationManager = null;
    }
    
    @Override
    public void onLocationChanged(Location location)
    {
    
    }
    
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
    }
    
    @Override
    public void onProviderEnabled(String provider)
    {
    }
    
    @Override
    public void onProviderDisabled(String provider)
    {
    }
    
    @Override
    public void onLocationChanged(AMapLocation arg0)
    {
        if (mListener != null && arg0 != null)
        {
            // Log.i("map", "arg0:" + arg0.getLatitude() + " " + arg0.getLongitude());
            mstartlocation = arg0;
            // 22.3205400000,114.2379000000
            /*
             * arg0.setLongitude(Constant.TEST_GETLNG);
             * arg0.setLatitude(Constant.TEST_GETLAT);
             * arg0.setCity("香港");
             * arg0.setCityCode("1852");
             * 
             * code = arg0.getCityCode();
             * city = arg0.getCity();
             */
            /*
             * code = "00852";
             * 
             * city="香港";
             */
            // MyLogUtils.i(TAG, "code:"+code+"-- city"+city);
            mListener.onLocationChanged(arg0);// 显示系统小蓝点
            if (type == ConstantEnum.IMap.TYPE_HOME)
            {
                logic.getBusinessByScope(arg0.getLongitude(), arg0.getLatitude(), 0, 100);
            }
            else if (type == ConstantEnum.IMap.TYPE_DETAIL_BUSINESSBRAND)
            {
                logic.getSubBusinessByScopeAndBrand(businessbrand_detail.getBusinessBrandId(), arg0.getLongitude(), arg0.getLatitude(), 0, 100);
            }
            else if (type == ConstantEnum.IMap.TYPE_BRAND_BUSINESS)
            {
                logic.getBusinessByScopeAndType(mBrand_type, arg0.getLongitude(), arg0.getLatitude(), 0, 100);
            }
            
        }
        
    }
    
    /**
     * 地图上标签点击的监听
     * 重写方法
     * 
     * @param arg0
     * @return
     * @see com.amap.api.maps2d.AMap.OnMarkerClickListener#onMarkerClick(com.amap.api.maps2d.model.Marker)
     */
    @Override
    public boolean onMarkerClick(Marker arg0)
    {
        // Log.i("tag", arg0.getObject() == null ? "null" : arg0.getObject().toString());
        map_content.setVisibility(View.VISIBLE);
        Business business = (Business)arg0.getObject();
        map_content.setText(business.getBusinessAddress());
        map_content.setTag(business);
        return false;
    }
    
    @Override
    protected void initLogic()
    {
        logic = (IMapLogic)getLogicByInterfaceClass(IMapLogic.class);
    }
    
    // *****************************路线接口回调************************************************
    /**
     * 调用搜索
     * <一句话功能简述>
     * <功能详细描述>
     * 
     * @see [类、类#方法、类#成员]
     */
    public void searchRoute()
    {
        
        if (map_content.getVisibility() == View.VISIBLE && !CheckUtils.isEmpty(map_content.getText().toString()))
        {
            startPoint = new LatLonPoint(mstartlocation.getLatitude(), mstartlocation.getLongitude());
            Business business = (Business)map_content.getTag();
            endPoint = new LatLonPoint(business.getBusinessLatitude(), business.getBusinessLongitude());
            startSearchResult();// 开始搜终点
        }
        else
            showToast(R.string.str_map_not_click, Toast.LENGTH_LONG);
    }
    
    /**
     * 开始查询结果
     * <功能详细描述>
     * 
     * @see [类、类#方法、类#成员]
     */
    public void startSearchResult()
    {
        if (startPoint != null)
        {
            endSearchResult();
        }
        else
        {
            showProgressDialog();
            startSearchQuery = new PoiSearch.Query(strStart, "", code); // 第一个参数表示查询关键字，第二参数表示poi搜索类型，第三个参数表示城市区号或者城市名
            startSearchQuery.setPageNum(0);// 设置查询第几页，第一页从0开始
            startSearchQuery.setPageSize(20);// 设置每页返回多少条数据
            PoiSearch poiSearch = new PoiSearch(MyMap.this, startSearchQuery);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.searchPOIAsyn();// 异步poi查询
        }
    }
    
    /**
     * 查询路径规划终点
     */
    public void endSearchResult()
    {
        if (endPoint != null)
        {
            searchRouteResult(startPoint, endPoint);
        }
        else
        {
            showProgressDialog();
            endSearchQuery = new PoiSearch.Query(strEnd, "", code); // 第一个参数表示查询关键字，第二参数表示poi搜索类型，第三个参数表示城市区号或者城市名
            endSearchQuery.setPageNum(0);// 设置查询第几页，第一页从0开始
            endSearchQuery.setPageSize(20);// 设置每页返回多少条数据
            
            PoiSearch poiSearch = new PoiSearch(MyMap.this, endSearchQuery);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.searchPOIAsyn(); // 异步poi查询
        }
    }
    
    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(LatLonPoint startPoint, LatLonPoint endPoint)
    {
        showProgressDialog();
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(startPoint, endPoint);
        if (routeType == 1)
        {// 公交路径规划
            MyLogUtils.i(TAG, "CITY:" + city);
            BusRouteQuery query = new BusRouteQuery(fromAndTo, busMode, city, 1);// 第一个参数表示路径规划的起点和终点，第二个参数表示公交查询模式，第三个参数表示公交查询城市区号，第四个参数表示是否计算夜班车，0表示不计算
            routeSearch.calculateBusRouteAsyn(query);// 异步路径规划公交模式查询
            
        }
        else if (routeType == 2)
        {// 驾车路径规划
            DriveRouteQuery query = new DriveRouteQuery(fromAndTo, drivingMode, null, null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            routeSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        }
        else if (routeType == 3)
        {// 步行路径规划
            WalkRouteQuery query = new WalkRouteQuery(fromAndTo, walkMode);
            routeSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
        }
    }
    
    /**
     * 显示进度框
     */
    private void showProgressDialog()
    {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在搜索");
        progDialog.show();
    }
    
    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog()
    {
        if (progDialog != null)
        {
            progDialog.dismiss();
        }
    }
    
    @Override
    public void onBusRouteSearched(BusRouteResult result, int rCode)
    {
        dissmissProgressDialog();
        MyLogUtils.i(TAG, "驾车路线查询：" + rCode);
        if (rCode == 0)
        {
            if (result != null && result.getPaths() != null && result.getPaths().size() > 0)
            {
                busRouteResult = result;
                BusPath busPath = busRouteResult.getPaths().get(0);
                aMap.clear();// 清理地图上的所有覆盖物
                BusRouteOverlay routeOverlay = new BusRouteOverlay(this, aMap, busPath,
                
                busRouteResult.getStartPos(), busRouteResult.getTargetPos()
                
                );
                routeOverlay.removeFromMap();
                routeOverlay.addToMap();
                routeOverlay.zoomToSpan();
            }
            else
            {
                showToast(R.string.no_result, Toast.LENGTH_LONG);
            }
        }
        else if (rCode == 27)
        {
            showToast(R.string.error_network, Toast.LENGTH_LONG);
        }
        else if (rCode == 32)
        {
            showToast(R.string.error_key, Toast.LENGTH_LONG);
        }
        else
        {
            showToast(R.string.error_other, Toast.LENGTH_LONG);
        }
        
    }
    
    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int rCode)
    {
        dissmissProgressDialog();
        if (rCode == 0)
        {
            if (result != null && result.getPaths() != null && result.getPaths().size() > 0)
            {
                driveRouteResult = result;
                DrivePath drivePath = driveRouteResult.getPaths().get(0);
                aMap.clear();// 清理地图上的所有覆盖物
                DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(this, aMap, drivePath, driveRouteResult.getStartPos(), driveRouteResult.getTargetPos());
                drivingRouteOverlay.removeFromMap();
                drivingRouteOverlay.addToMap();
                drivingRouteOverlay.zoomToSpan();
            }
            else
            {
                showToast(R.string.no_result, Toast.LENGTH_LONG);
            }
        }
        else if (rCode == 27)
        {
            showToast(R.string.error_network, Toast.LENGTH_LONG);
        }
        else if (rCode == 32)
        {
            showToast(R.string.error_key, Toast.LENGTH_LONG);
        }
        else
        {
            showToast(R.string.error_other, Toast.LENGTH_LONG);
        }
    }
    
    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int rCode)
    {
        dissmissProgressDialog();
        if (rCode == 0)
        {
            if (result != null && result.getPaths() != null && result.getPaths().size() > 0)
            {
                walkRouteResult = result;
                WalkPath walkPath = walkRouteResult.getPaths().get(0);
                aMap.clear();// 清理地图上的所有覆盖物
                WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(this, aMap, walkPath, walkRouteResult.getStartPos(), walkRouteResult.getTargetPos());
                walkRouteOverlay.removeFromMap();
                walkRouteOverlay.addToMap();
                walkRouteOverlay.zoomToSpan();
            }
            else
            {
                showToast(R.string.no_result, Toast.LENGTH_LONG);
            }
        }
        else if (rCode == 27)
        {
            showToast(R.string.error_network, Toast.LENGTH_LONG);
        }
        else if (rCode == 32)
        {
            showToast(R.string.error_key, Toast.LENGTH_LONG);
        }
        else
        {
            showToast(R.string.error_other, Toast.LENGTH_LONG);
        }
        
    }
    
    // --------------查询
    @Override
    public void onPoiItemDetailSearched(PoiItemDetail arg0, int arg1)
    {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * 搜索回调结果
     * 重写方法
     * 
     * @param arg0
     * @param arg1
     * @see com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener#onPoiSearched(com.amap.api.services.poisearch.PoiResult,
     *      int)
     */
    @Override
    public void onPoiSearched(PoiResult arg0, int arg1)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void onCameraChange(CameraPosition arg0)
    {
        LatLng tragLng = arg0.target;
        MyLogUtils.i(TAG, "地图中心点：" + tragLng.longitude + ":" + tragLng.latitude);
        if (mOnGetCenter)
        {
            mOnGetCenter = false;
            logic.getBusinessByScope(tragLng.longitude, tragLng.latitude, 0, 100);
        }
    }
    
    @Override
    public void onCameraChangeFinish(CameraPosition arg0)
    {
        // TODO Auto-generated method stub
        
    }
    
}
