package com.ganggang.ganggangapp.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnMapClickListener;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.ganggang.frame.common.FlowLayout;
import com.ganggang.frame.common.MyModule_ImgLayout;
import com.ganggang.frame.common.MyScrollView;
import com.ganggang.frame.common.PageFlipper;
import com.ganggang.frame.freamwork.fragment.BaseFragment;
import com.ganggang.frame.util.MapUtils;
import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.Utils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Adv;
import com.ganggang.ganggangapp.bean.Business;
import com.ganggang.ganggangapp.bean.Celebrity;
import com.ganggang.ganggangapp.constant.Constant;
import com.ganggang.ganggangapp.constant.ConstantAction;
import com.ganggang.ganggangapp.constant.ConstantBundle;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.constant.ConstantFile;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.homelogic.IHomeLogic;
import com.ganggang.ganggangapp.ui.fragment.adapter.HomeBusiness_ExpandableList_adapter;
import com.ganggang.ganggangapp.ui.fragment.adapter.MingrenMingBo_ExpandableList_adapter;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.Shape;
import android.location.Location;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * 
 * 首页的fragment
 * 
 * @author 曾宝
 * @version [V1.00, 2015年7月28日]
 * @see [相关类/方法]
 * @since V1.00
 */
public class HomeFragment extends BaseFragment implements AMapLocationListener, LocationSource, OnClickListener
{
    private final String TAG = HomeFragment.class.getSimpleName();
    /**
     * 刚进入加载数据时的进度条
     */
    private ProgressBar fragment_module_home_progress;
    
    /**
     * 页面布局，数据获取后显示
     */
    private MyScrollView home_fragment_myscrollview;
    
    /**
     * viewPager 头部广告
     */
    private PageFlipper viewpager_module_home_adv;
    
    /**
     * 广告 显示 index
     */
    private LinearLayout layout_module_home_adv_index;
    
    /**
     * 地图
     */
    private AMap aMap;
    
    private MapView map_fragment_module_home;
    
    private LocationManagerProxy locationManagerProxy;
    
    private OnLocationChangedListener mListener;
    /**
     * 获取位置
     */
    public static AMapLocation MAPLOCATION;
    
    /**
     * 底部广告添加的地方
     */
    private LinearLayout layout_module_home_bottom_adv;
    
    /**
     * 主页自定义商家信息，名人名博的信息
     */
    private ExpandableListView fragment_home_business_info_layout, fragment_home_mingrenmingbo_info_layout;
    /**
     * 自定义商家 适配器
     */
    private HomeBusiness_ExpandableList_adapter adapter_expandableListView;
    /*
     * 名人名博 适配器
     */
    private MingrenMingBo_ExpandableList_adapter adapter_Mingrenmingbo;
    /**
     * 商家数据
     */
    private HashMap<String, List<Business>> map_business;
    /**
     * 名人名博 数据
     */
    private List<Celebrity> mlist_celebrity;
    
    private FlowLayout module_home_bottom_adv;
    
    /**
     * 香港热点，酒楼餐饮,数码影音,珠宝名牌
     */
    private MyModule_ImgLayout layout_module_redian, layout_module_jiuloucanyin, layout_module_shuma, layout_module_zhubao,
        /**
         * 购物天堂，保健药品,美容美体,服务
         */
        layout_module_gouwu, layout_module_yaopin, layout_module_meirong, layout_module_fuwu;
        
    /**
     * home 逻辑
     */
    private IHomeLogic logic;
    
    private boolean flag_translation = false;
    
    private static HomeFragment homeFragment;
    
    public static HomeFragment getNewInstance()
    {
        if (homeFragment == null)
            homeFragment = new HomeFragment();
            
        return homeFragment;
    }
    
    private ImageView bottom_adv_1,bottom_adv_2,bottom_adv_3,bottom_adv_4;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_module_home, container, false);
        flag_translation = (Boolean)MyDataUtils.getData(getActivity(), ConstantFile.IMore.FileName, ConstantFile.IMore.MORE_TRANSLATION, Boolean.class);
        initView(view);
        //防止list 加载完数据后将scroll 顶上去
        fragment_home_business_info_layout.setFocusable(false);
        fragment_home_mingrenmingbo_info_layout.setFocusable(false);
        map_fragment_module_home = (MapView)view.findViewById(R.id.map_fragment_module_home);
        map_fragment_module_home.onCreate(savedInstanceState);
        initData();
        initListener();
        return view;
    }

  
    
    
    private void initListener()
    {
        
        fragment_home_business_info_layout.setOnGroupExpandListener(new OnGroupExpandListener()
        {
            @Override
            public void onGroupExpand(int groupPosition)
            {
             ViewUtils.setListViewHeightBasedOnChildren(fragment_home_business_info_layout);   
            }
        });
        
        fragment_home_business_info_layout.setOnGroupCollapseListener(new OnGroupCollapseListener()
        {
            @Override
            public void onGroupCollapse(int groupPosition)
            {
                ViewUtils.setListViewHeightBasedOnChildren(fragment_home_business_info_layout);   
            }
        });
        /**
         * 名人名博扩展
         */
        fragment_home_mingrenmingbo_info_layout.setOnGroupCollapseListener(new OnGroupCollapseListener()
        {
            @Override
            public void onGroupCollapse(int groupPosition)
            {
                ViewUtils.setListViewHeightBasedOnChildren(fragment_home_mingrenmingbo_info_layout);   
            }
        });
        
        fragment_home_mingrenmingbo_info_layout.setOnGroupExpandListener(new OnGroupExpandListener()
        {
            @Override
            public void onGroupExpand(int groupPosition)
            {
                ViewUtils.setListViewHeightBasedOnChildren(fragment_home_mingrenmingbo_info_layout);   
            }
        });
        
        fragment_home_business_info_layout.setOnChildClickListener(new OnChildClickListener()
        {
            
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {
                Intent intent = new Intent();
                intent.setAction(ConstantAction.BUSINESS_DETAIL_ACTION);
                String key = map_business.keySet().toArray()[groupPosition].toString();
                Business business = map_business.get(key).get(childPosition);
                intent.putExtra(ConstantBundle.BUSINESSDEATILID, business.getBusinessId());
                startActivity(intent);
                
     /*           getActivity().finish();*/
                //这里需要修改
                return false;
            }
        });
        
        // ----------------首页八大模块跳转
        layout_module_redian.setOnClickListener(this);
        layout_module_jiuloucanyin.setOnClickListener(this);
        layout_module_shuma.setOnClickListener(this);
        layout_module_zhubao.setOnClickListener(this);
        
        /**
         * 购物天堂，保健药品,美容美体,服务
         */
        layout_module_gouwu.setOnClickListener(this);
        layout_module_yaopin.setOnClickListener(this);
        layout_module_meirong.setOnClickListener(this);
        layout_module_fuwu.setOnClickListener(this);

        aMap.setOnMapClickListener(new OnMapClickListener()
        {
            @Override
            public void onMapClick(LatLng arg0)
            {
                Intent intent = new Intent();
                intent.setAction(ConstantAction.MAP_ACTION);
                intent.putExtra(ConstantBundle.IMapType.TO_MAP, ConstantEnum.IMap.TYPE_HOME);
                startActivity(intent);
            }
        });
        
    }
    
    private void initView(View view)
    {
        try
        {
            ViewUtils.initView(this, view);
            fragment_module_home_progress.setVisibility(View.VISIBLE);
            home_fragment_myscrollview.setVisibility(View.GONE);
        }
        catch (Exception e)
        {
            MyLogUtils.e(TAG, "init is err", e);
        }
    }
    
    private void initData()
    {
        if (aMap == null)
        {
            aMap = map_fragment_module_home.getMap();
            
          MapUtils.setUpMap(getActivity(),false,aMap,locationManagerProxy,this,this);

        }
        
        //自定义商家----------------
        map_business = new HashMap<String, List<Business>>();
        adapter_expandableListView = new HomeBusiness_ExpandableList_adapter(map_business, getActivity());
        fragment_home_business_info_layout.setAdapter(adapter_expandableListView);
        adapter_expandableListView.notifyDataSetChanged();
        
        //名人名博--------------------
        mlist_celebrity = new ArrayList<Celebrity>();
        adapter_Mingrenmingbo = new MingrenMingBo_ExpandableList_adapter(mlist_celebrity, getActivity());
        fragment_home_mingrenmingbo_info_layout.setAdapter(adapter_Mingrenmingbo);
        adapter_Mingrenmingbo.notifyDataSetChanged();
  
        
        logic.getTopAdv();
        logic.getBottomAdv();
        logic.getHomeBusiness();
        logic.getMingrenmingbo();
    }
    
  
    
  
    
    @Override
    protected void handleStateMessage(Message msg)
    {
        switch (msg.what)
        {
            case ConstantMessage.IHome.HOME_TOP_OK_MSG:
                if(msg.obj!=null)
                {
                    Object[] objs = (Object[])msg.obj;
                    List<Bitmap> list_bitmap = (List<Bitmap>)objs[0];
                    List<Adv> list_adv = (List<Adv>)objs[1];
                    //顶部广告
                    viewpager_module_home_adv.setViews(list_bitmap, layout_module_home_adv_index,list_adv);
                    //应对第一次进入app时，加载默认的广告，避免延迟加载
                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                    map1.put("fist_app", true);
                    MyDataUtils.putData(getActivity(), ConstantFile.IFileConfig.FileName, map1);
                }
                break;
            case ConstantMessage.IHome.HOME_BOTTOM_ADV_OK_MSG:
                //底部广告
                Object[] objs = (Object[])msg.obj;
                List<Bitmap> list_bottom_adv =(List<Bitmap>)objs[0];
                final List<Adv> list_adv = (List<Adv>)objs[1];
                for (int i = 0;list_bottom_adv!=null &&  i < list_bottom_adv.size(); i++)
                {
                    if(i==0)
                    {
                        bottom_adv_1.setImageBitmap(list_bottom_adv.get(i));
                        bottom_adv_1.setOnClickListener(new OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Utils.intentUrl(list_adv.get(0).getAdvUrl(), getActivity());
                                
                            }
                        });
                    }
                    else if(i==1)
                    {
                        bottom_adv_2.setImageBitmap(list_bottom_adv.get(i));
                        bottom_adv_2.setOnClickListener(new OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Utils.intentUrl(list_adv.get(1).getAdvUrl(), getActivity());
                                
                            }
                        });
                    }
                    else if(i==2)
                    {
                        bottom_adv_3.setImageBitmap(list_bottom_adv.get(i));
                        bottom_adv_3.setOnClickListener(new OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Utils.intentUrl(list_adv.get(2).getAdvUrl(), getActivity());
                                
                            }
                        });
                    }
                    else if(i==3)
                    {
                        bottom_adv_4.setImageBitmap(list_bottom_adv.get(i));
                        bottom_adv_4.setOnClickListener(new OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Utils.intentUrl(list_adv.get(3).getAdvUrl(), getActivity());
                                
                            }
                        });
                    }
                }
                break;
            case ConstantMessage.IHome.HOME_BUSINESS_OK_MSG:
                //获取自定义商家
                HashMap<String, List<Business>> map = (HashMap<String, List<Business>>)msg.obj;
                if(map!=null)
                {
                    map_business.clear();
                    map_business.putAll(map);
                    adapter_expandableListView.notifyDataSetChanged();
                    ViewUtils.expandGroup(adapter_expandableListView, fragment_home_business_info_layout);
                    ViewUtils.setListViewHeightBasedOnChildren(fragment_home_business_info_layout);   
                     
                }
                break;
            case ConstantMessage.IHome.CELEBRITY_OK_MSG:
               List<Celebrity> list_celebrity = (List<Celebrity>)msg.obj;
                mlist_celebrity.clear();
                mlist_celebrity.addAll(list_celebrity);
                adapter_Mingrenmingbo.notifyDataSetChanged();
                ViewUtils.expandGroup(adapter_Mingrenmingbo, fragment_home_mingrenmingbo_info_layout);
                ViewUtils.setListViewHeightBasedOnChildren(fragment_home_mingrenmingbo_info_layout);   
                break;
            case ConstantMessage.IHome.HOME_MAP_OK_MSG:
                //获取地图信息
                List<Business> mlist_business=(List<Business>)msg.obj;
                if(mlist_business!=null)
                {
                    MyLogUtils.i(TAG, "map size:"+mlist_business.size());
                    for(Business bus:mlist_business)
                    {
                        MyLogUtils.i(TAG, "首页商家："+bus.getBusinessLatitude()+":"+bus.getBusinessLongitude()+":"+bus.getBusinessBrand()[0]);
                    }
                }
                setMark(mlist_business);
                break;  
            case ConstantMessage.IHome.HOME_BUSINESS_ERR_MSG:
                // 获取信息失败，走本地路线
                break;   
            case ConstantMessage.IHome.CELEBRITY_ERR_MSG:
                //名人名博信息获取失败
                break;
            case ConstantMessage.IHome.HOME_MAP_ERR_MSG:
                //地图商家获取失败
                break;
            default:
                break;
        }
//        fragment_module_home_progress.setVisibility(View.GONE);
        home_fragment_myscrollview.setVisibility(View.VISIBLE);
    }
    

    private void setMark(List<Business> mlist_business)
    {
        for(int i = 0;mlist_business!=null && i<mlist_business.size();i++)
        {
            MapUtils.addMarkersToMap(mlist_business.get(i),aMap, new AMap.OnMarkerClickListener()
            {
                @Override
                public boolean onMarkerClick(Marker arg0)
                {
                    return false;
                }
            });
        }
    }


    @Override
    protected void initLogic()
    {
        logic = (IHomeLogic)getLogicByInterfaceClass(IHomeLogic.class);
    }
   
    
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        map_fragment_module_home.onSaveInstanceState(outState);
    }
    
    @Override
    public void onResume()
    {
        MyLogUtils.i(TAG, " i am is Resume");
        super.onResume();
        aMap = map_fragment_module_home.getMap();
        if (aMap != null)
            MapUtils.setUpMap(getActivity(),false,aMap,locationManagerProxy,this,this);
    }
    
    @Override
    public void onDestroy()
    {
        MyLogUtils.i(TAG, " i am is onDestroy");
        super.onDestroy();
        map_fragment_module_home.onDestroy();
    }
    
    @Override
    public void onPause()
    {
        super.onPause();
        map_fragment_module_home.onPause();
        deactivate();
    }
    
    @Override
    public void onLocationChanged(Location location)
    {
   /*     if (mListener != null && location != null)
        {
            Log.i("map", "location:" + location.getLatitude() + "  " + location.getLongitude());
            mListener.onLocationChanged(location);// 显示系统小蓝点
        }*/
        
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
        if (arg0 != null && arg0.getAMapException().getErrorCode() == 0)
        {
            // 获取位置信息
          Double getLat = arg0.getLatitude();
            Double getLng = arg0.getLongitude();
/*            Double getLat =Constant.TEST_GETLAT;
            Double getLng = Constant.TEST_GETLAT;*/
            fragment_module_home_progress.setVisibility(View.GONE);
            arg0.setLatitude(getLat);
            arg0.setLongitude(getLng);
            logic.getMapBusiness(getLng, getLat, 0, 100);
            MyLogUtils.i(TAG, " 定位了："+getLat+" : "+getLng);
            MAPLOCATION = arg0;
            mListener.onLocationChanged(arg0);
        }
    }
    
    @Override
    public void activate(OnLocationChangedListener arg0)
    {
        mListener = arg0;
        if (locationManagerProxy == null)
        {
            MyLogUtils.i(TAG, "home开启定位");
            fragment_module_home_progress.setVisibility(View.VISIBLE);
            locationManagerProxy = LocationManagerProxy.getInstance(getActivity());
            locationManagerProxy.setGpsEnable(true);
            locationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, Constant.MAPREFRESHTIME, 10, this);
        }
        
    }
    
    @Override
    public void deactivate()
    {
        MyLogUtils.i(TAG, "home关闭定位");
        mListener = null;
        if (locationManagerProxy != null)
        {
            locationManagerProxy.removeUpdates(this);
            locationManagerProxy.destroy();
        }
        locationManagerProxy = null;
        
    }
    
    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent();
        switch (v.getId())
        {
            case R.id.layout_module_redian:
                // 香港热点
                intent.putExtra(ConstantBundle.HOME_BUSINESSBRAND, ConstantEnum.IHomeBusinessType.TYPE_ONE);
                intent.setAction(ConstantAction.BUSINESSBRAND_ACTION);
                getActivity().startActivity(intent);
                //getActivity().finish();
                break;
            case R.id.layout_module_jiuloucanyin:
                // 酒楼餐饮,
                intent.putExtra(ConstantBundle.HOME_BUSINESSBRAND, ConstantEnum.IHomeBusinessType.TYPE_TWO);
                intent.setAction(ConstantAction.BUSINESSBRAND_ACTION);
                getActivity().startActivity(intent);
                //getActivity().finish();
                break;
            case R.id.layout_module_shuma:
                // 数码影音
                intent.putExtra(ConstantBundle.HOME_BUSINESSBRAND, ConstantEnum.IHomeBusinessType.TYPE_THREE);
                intent.setAction(ConstantAction.BUSINESSBRAND_ACTION);
                getActivity().startActivity(intent);
                //getActivity().finish();
                break;
            case R.id.layout_module_zhubao:
                // ,珠宝名牌
                intent.putExtra(ConstantBundle.HOME_BUSINESSBRAND, ConstantEnum.IHomeBusinessType.TYPE_FOUR);
                intent.setAction(ConstantAction.BUSINESSBRAND_ACTION);
                getActivity().startActivity(intent);
                //getActivity().finish();
                break;
            case R.id.layout_module_gouwu:
                // 购物天堂
                intent.putExtra(ConstantBundle.HOME_BUSINESSBRAND, ConstantEnum.IHomeBusinessType.TYPE_FIVE);
                intent.setAction(ConstantAction.BUSINESSBRAND_ACTION);
                getActivity().startActivity(intent);
                //getActivity().finish();
                break;
            case R.id.layout_module_yaopin:
                // 保健药品
                intent.putExtra(ConstantBundle.HOME_BUSINESSBRAND, ConstantEnum.IHomeBusinessType.TYPE_SIX);
                intent.setAction(ConstantAction.BUSINESSBRAND_ACTION);
                getActivity().startActivity(intent);
                //getActivity().finish();
                break;
            case R.id.layout_module_meirong:
                // 美容美体
                intent.putExtra(ConstantBundle.HOME_BUSINESSBRAND, ConstantEnum.IHomeBusinessType.TYPE_SEVEN);
                intent.setAction(ConstantAction.BUSINESSBRAND_ACTION);
                getActivity().startActivity(intent);
                //getActivity().finish();
                break;
            case R.id.layout_module_fuwu:
                // 服务
                intent.putExtra(ConstantBundle.HOME_BUSINESSBRAND, ConstantEnum.IHomeBusinessType.TYPE_EIGHT);
                intent.setAction(ConstantAction.BUSINESSBRAND_ACTION);
                getActivity().startActivity(intent);
                //getActivity().finish();
                break;
            default:
                break;
        }
        
    }
    
}
