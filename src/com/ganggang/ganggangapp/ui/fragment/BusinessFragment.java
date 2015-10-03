package com.ganggang.ganggangapp.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import com.ganggang.frame.common.PageFlipper;
import com.ganggang.frame.constant.refresh.PullListView;
import com.ganggang.frame.constant.refresh.PullToRefreshLayout;
import com.ganggang.frame.constant.refresh.PullToRefreshLayout.OnRefreshListener;
import com.ganggang.frame.freamwork.fragment.BaseFragment;
import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Adv;
import com.ganggang.ganggangapp.bean.Business;
import com.ganggang.ganggangapp.bean.BusinessBrand;
import com.ganggang.ganggangapp.constant.Constant;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.constant.ConstantFile;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.businesslogic.IBusinessLogic;
import com.ganggang.ganggangapp.ui.fragment.adapter.Business_ListView_adapter;
import com.ganggang.ganggangapp.ui.fragment.adapter.GroupAdapter;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class BusinessFragment extends BaseFragment implements OnClickListener, OnRefreshListener
{
    private final String TAG = BusinessFragment.class.getSimpleName();
    
    private ProgressBar fragment_module_business_progress;
    
    /**
     * 上啦加载，下拉加载 控件
     */
    private PullToRefreshLayout fragment_module_business_refresh;
    
    /**
     * 信息listview
     */
    private PullListView listview_business;
    
    /**
     * 广告
     */
    private PageFlipper viewpager_module_business_adv;
    
    /**
     * 广告原点
     */
    private LinearLayout layout_module_business_adv_index;
    
    /**
     * listview 适配器
     */
    private Business_ListView_adapter listView_adapter;
    
    /**
     * listveiw资源
     */
    private List<Business> mlist_business;
    
    /**
     * 翻页搜索前缀
     */
    private int START_DEFAULT = 0;
    
    /**
     * 翻页搜索后缀
     */
    private int PAGESIZE_DEFAULT = Constant.BUSINESSLISTSIZE;
    
    /**
     * 分类查询，顺序查询
     */
    private LinearLayout layout_module_business_type, layout_module_business_desc;
    
    /**
     * 是否选中 2个分类
     */
    private TextView[] textarr = new TextView[2];
    
    private ImageView[] imgarr = new ImageView[2];
    
    private boolean[] tabStateArr = new boolean[2];
    
    /**
     * 加载菜单
     */
    private ListView listview_popwindow_group;
    
    /**
     * 菜单
     */
    private PopupWindow mPopupWindow = null;
    
    /**
     * 菜单的样式
     */
    private View showPupWindow = null;
    
    /**
     * 菜单内容list
     */
    private List<String> list_group;
    
    /**
     * 菜单出现的动画效果
     */
    private TranslateAnimation animation;
    
    /**
     * 菜单适配器
     */
    private GroupAdapter adapter_listview_popwindow_group;
    
    /**
     * 菜单 显示的高度
     */
    private int popwindowHeight;
    
    private String[] paixu_arr = new String[] {"默认排序", "好评优先", "人气最高", "离我最近"};
    
    /**
     * 广告 layout
     */
    private LinearLayout layout_top;
    
    /**
     * 简繁体 的flag
     */
    private boolean flag_more_translation;
    
    /**
     * 逻辑层
     */
    private IBusinessLogic logic;
    
    /**
     * 跳转到商家 的类型
     */
    private Integer mType_to_businessFragment;
    
    private Bundle mBundle;
    
    /**
     * 单例模式
     */
    private static BusinessFragment businessFragment;
    
    public static BusinessFragment getNewIntance()
    {
        if (businessFragment == null)
            businessFragment = new BusinessFragment();
            
        return businessFragment;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_module_business, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }
    
    private void initListener()
    {
        // 上啦/下拉 加载信息监听
        fragment_module_business_refresh.setOnRefreshListener(this);
        
        viewpager_module_business_adv.getParent().requestDisallowInterceptTouchEvent(true);
        layout_module_business_type.setOnClickListener(this);
        
        layout_module_business_desc.setOnClickListener(this);
        
        /*
         * listview_business.setOnScrollListener(new OnScrollListener()
         * {
         * int lastItem = 0;
         * 
         * @Override
         * public void onScrollStateChanged(AbsListView view, int scrollState)
         * {
         * // listView_adapter.notifyDataSetChanged();//提醒adapter更新
         * // listview_business.setSelection(lastItem-1);
         * }
         * 
         * @Override
         * public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
         * {
         * Log.e("------onScroll:",visibleItemCount+"");
         * }
         * });
         */
        // layout_module_business_district.setOnClickListener(this);
    }
    
    private void initData()
    {
        // 简/繁体切换
        flag_more_translation = (Boolean)MyDataUtils.getData(getActivity(), ConstantFile.IMore.FileName, ConstantFile.IMore.MORE_TRANSLATION, Boolean.class);
        
        initData_popwindow();
        initData_listview();
        
        logic.getBusinessTopAdv();
        
        // 获取内容
        mBundle = getArguments();
        mType_to_businessFragment = null;
        START_DEFAULT = 0;
        PAGESIZE_DEFAULT = Constant.BUSINESSLISTSIZE;
        // 根据传过来的信息就行本页的查询操作
        setGetBusiness(mBundle);
        
    }
    
    private void setGetBusiness(Bundle bundle)
    {
        /*
         * fragment_module_business_progress.setVisibility(View.VISIBLE);
         * fragment_module_business_refresh.setVisibility(View.GONE);
         */
        try
        {
            // 参数类型获取
            mType_to_businessFragment = bundle.getInt(ConstantEnum.IBusinessFragment.TYPE);
        }
        catch (Exception e)
        {
            // 没有获取到类型的情况下，选取默认 的排序
            mType_to_businessFragment = ConstantEnum.IBusinessFragment.TYPE_DEFAULT_BUSINESS;
            logic.getBusinessDefault(START_DEFAULT, PAGESIZE_DEFAULT);
            return;
        }
        
        String type_brand = bundle.getString(ConstantEnum.IBusinessFragment.TYPE_ARGS);
        MyLogUtils.i(TAG, "从品牌 类型 搜索商家：" + type_brand);
        if (type_brand != null && type_brand.length() > 0)
        {
            String title = (String)MyDataUtils.getData(getActivity(), type_brand, flag_more_translation ? ConstantFile.IBusinessType.NAMECOMPLEX : ConstantFile.IBusinessType.NAME, String.class);
            textarr[0].setText(title);
            textarr[0].setTag(type_brand);
            
        }
        
        switch (mType_to_businessFragment)
        {
            case ConstantEnum.IBusinessFragment.TYPE_BRANDNAME_BUSINESS:
                // 从搜索栏 查询 商家信息,传过来的是品牌名称进行模糊查询
                String businessBrandName = bundle.getString(ConstantEnum.IBusinessFragment.TYPE_ARGS);
                MyLogUtils.i(TAG, "从搜索栏 查询 商家信息:" + businessBrandName);
                logic.getBusinessByBrandName(businessBrandName, START_DEFAULT, PAGESIZE_DEFAULT);
                break;
            case ConstantEnum.IBusinessFragment.TYPE_BRANDID_BUSINESS:
                // 品牌ID 搜索商家
                BusinessBrand businessBrand = (BusinessBrand)bundle.getSerializable(ConstantEnum.IBusinessFragment.TYPE_ARGS);
                MyLogUtils.i(TAG, "品牌ID  查询 商家信息:" + businessBrand.getBusinessBrandId());
                logic.getBusinessByBrand(businessBrand.getBusinessBrandId(), START_DEFAULT, PAGESIZE_DEFAULT);
                break;
            case ConstantEnum.IBusinessFragment.TYPE_HOTWORD_BUSINESS:
                // 从热词 搜索商家
                String hotword = bundle.getString(ConstantEnum.IBusinessFragment.TYPE_ARGS);
                MyLogUtils.i(TAG, "提取热词：" + hotword);
                logic.getBusinessByHotWord(hotword, START_DEFAULT, PAGESIZE_DEFAULT);
                break;
            case ConstantEnum.IBusinessFragment.TYPE_BUSINESSTYPE_BUSINESS:
                // 从商家类型 搜索商家
                String businessType = bundle.getString(ConstantEnum.IBusinessFragment.TYPE_ARGS);
                MyLogUtils.i(TAG, "商家类型 搜索商家：" + businessType);
                logic.getBusinessByDefaultAndType(businessType, START_DEFAULT, PAGESIZE_DEFAULT);
                break;
            case ConstantEnum.IBusinessFragment.TYPE_BRANDTYPE_BUSINESS:
                // 从品牌 类型 搜索商家
       /*         String type_brand2 = bundle.getString(ConstantEnum.IBusinessFragment.TYPE_ARGS);
                MyLogUtils.i(TAG, "从品牌 类型 搜索商家：" + type_brand);
                String title = (String)MyDataUtils.getData(getActivity(), type_brand, flag_more_translation ? ConstantFile.IBusinessType.NAMECOMPLEX : ConstantFile.IBusinessType.NAME, String.class);
                textarr[0].setText(title);
                textarr[0].setTag(type_brand);*/
                
                logic.getBusinessByCountsAndType(type_brand, START_DEFAULT, PAGESIZE_DEFAULT);
                break;
            case ConstantEnum.IBusinessFragment.TYPE_DEFAULT_BUSINESS:
                // 商家默认排序
                MyLogUtils.i(TAG, "商家默认排序：");
                logic.getBusinessDefault(START_DEFAULT, PAGESIZE_DEFAULT);
                break;
            case ConstantEnum.IBusinessFragment.TYPE_HIGHOPINION_BUSINESS:
                // 好评优先排序
                MyLogUtils.i(TAG, "好评优先排序：");
                logic.getBusinessByRemark(START_DEFAULT, PAGESIZE_DEFAULT);
                break;
            case ConstantEnum.IBusinessFragment.TYPE_COUNT_BUSINESS:
                // 人气 优先排序
                MyLogUtils.i(TAG, "人气 优先排序：");
                logic.getBusinessByCounts(START_DEFAULT, PAGESIZE_DEFAULT);
                break;
            case ConstantEnum.IBusinessFragment.TYPE_GPS_BUSINESS:
                // 离我最近
                MyLogUtils.i(TAG, "人气 优先排序：");
                if (HomeFragment.MAPLOCATION != null)
                {
                    logic.getBusinessByDistance(HomeFragment.MAPLOCATION.getLongitude(), HomeFragment.MAPLOCATION.getLatitude(), START_DEFAULT, PAGESIZE_DEFAULT);
                }
                else
                {
                    logic.getBusinessDefault(START_DEFAULT, PAGESIZE_DEFAULT);
                }
                break;
            case ConstantEnum.IBusinessFragment.TYPE_DEFAULT_TYPE_BUSINESS:
                MyLogUtils.i(TAG, "默认 带类型 排序");
                String businessTypeCode = bundle.getString(ConstantEnum.IBusinessFragment.TYPE_ARGS);
                logic.getBusinessByDefaultAndType(businessTypeCode, START_DEFAULT, PAGESIZE_DEFAULT);
                break;
            case ConstantEnum.IBusinessFragment.TYPE_COUNT_TYPE_BUSINESS:
                MyLogUtils.i(TAG, "点击数量 带类型排序");
                String businessTypeCode_count = bundle.getString(ConstantEnum.IBusinessFragment.TYPE_ARGS);
                logic.getBusinessByCountsAndType(businessTypeCode_count, START_DEFAULT, PAGESIZE_DEFAULT);
                break;
            case ConstantEnum.IBusinessFragment.TYPE_HIGHOPINION_TYPE_BUSINESS:
                MyLogUtils.i(TAG, "好评 带类型 排序");
                String businessTypeCode_remark = bundle.getString(ConstantEnum.IBusinessFragment.TYPE_ARGS);
                logic.getBusinessByRemarkAndType(businessTypeCode_remark, START_DEFAULT, PAGESIZE_DEFAULT);
                break;
            case ConstantEnum.IBusinessFragment.TYPE_GPS_TYPE_BUSINESS:
                String businessTypeCode_gps = bundle.getString(ConstantEnum.IBusinessFragment.TYPE_ARGS);
                MyLogUtils.i(TAG, "离我最近 带类型排序:" + businessTypeCode_gps);
                if (HomeFragment.MAPLOCATION != null)
                {
                    if (businessTypeCode_gps == null)
                        businessTypeCode_gps = "";
                    logic.getBusinessByDistanceAndType(businessTypeCode_gps, HomeFragment.MAPLOCATION.getLongitude(), HomeFragment.MAPLOCATION.getLatitude(), START_DEFAULT, PAGESIZE_DEFAULT);
                }
                else
                {
                    logic.getBusinessByDefaultAndType(businessTypeCode_gps, START_DEFAULT, PAGESIZE_DEFAULT);
                }
                break;
                
            default:
                break;
        }
    }
    
    /**
     * 菜单 列表初始化
     * <功能详细描述>
     * 
     * @see [类、类#方法、类#成员]
     */
    private void initData_popwindow()
    {
        // 菜单初始化
        list_group = new ArrayList<String>();
        
        int[] location = new int[2];
        listview_business.getLocationOnScreen(location);// 获取控件在屏幕中的位置,方便展示Popupwindow
        // 菜单 显示的动画
        animation = new TranslateAnimation(0, 0, -700, location[1]);
        animation.setDuration(500);
    }
    
    /**
     * 初始化listview 监听
     * <功能详细描述>
     * 
     * @see [类、类#方法、类#成员]
     */
    private void initData_listview()
    {
        mlist_business = new ArrayList<Business>();
        
        listView_adapter = new Business_ListView_adapter(mlist_business, getActivity());
        
        listview_business.setAdapter(listView_adapter);
        
        listView_adapter.notifyDataSetChanged();
    }
    
    private void initView(View view)
    {
        try
        {
            ViewUtils.initView(this, view);
            // 广告头部位置
            layout_top = (LinearLayout)LayoutInflater.from(getActivity()).inflate(R.layout.layout_business_top, null);
            listview_business.addHeaderView(layout_top);
            // 广告栏
            viewpager_module_business_adv = (PageFlipper)layout_top.findViewById(R.id.viewpager_module_business_adv);
            // 广告数量
            layout_module_business_adv_index = (LinearLayout)layout_top.findViewById(R.id.layout_module_business_adv_index);
            // 分类
            layout_module_business_type = (LinearLayout)layout_top.findViewById(R.id.layout_module_business_type);
            // 排序
            layout_module_business_desc = (LinearLayout)layout_top.findViewById(R.id.layout_module_business_desc);
            
            // -------分类 点击 需要改变的控件样式
            textarr[0] = (TextView)layout_module_business_type.findViewById(R.id.layout_module_business_type_textview);
            
            textarr[1] = (TextView)layout_module_business_desc.findViewById(R.id.layout_module_business_desc_textview);
            
            imgarr[0] = (ImageView)layout_module_business_type.findViewById(R.id.layout_module_business_type_img);
            
            imgarr[1] = (ImageView)layout_module_business_desc.findViewById(R.id.layout_module_business_desc_img);
        }
        catch (Exception e)
        {
            MyLogUtils.e(TAG, "init is err", e);
        }
        
    }
    
    @Override
    protected void handleStateMessage(Message msg)
    {
        fragment_module_business_refresh.refreshFinish(PullToRefreshLayout.SUCCEED);
        switch (msg.what)
        {
            case ConstantMessage.IBusiness.BUSINESS_ADV_OK_MSG:
                // 广告
                if (msg.obj != null)
                {
                    Object[] objs = (Object[])msg.obj;
                    List<Bitmap> list_bitmap = (List<Bitmap>)objs[0];
                    List<Adv> list_adv = (List<Adv>)objs[1];
                    viewpager_module_business_adv.setViews(list_bitmap, layout_module_business_adv_index, list_adv);
                }
                break;
            case ConstantMessage.IBusiness.BUSINESS_TYPE_DEFAULT_OK_MSG:
                // 好评搜索
                if (msg.obj != null)
                {
                    isClearList(ConstantEnum.IBusinessFragment.TYPE_DEFAULT_BUSINESS, (List<Business>)msg.obj);
                }
                break;
            case ConstantMessage.IBusiness.BUSINESS_BRANDID_OK_MSG:
                // 品牌ID 搜索
                if (msg.obj != null)
                {
                    isClearList(ConstantEnum.IBusinessFragment.TYPE_BRANDID_BUSINESS, (List<Business>)msg.obj);
                }
                break;
            case ConstantMessage.IBusiness.BUSINESS_BRANDNAME_OK_MSG:
                // 按品牌名称
                if (msg.obj != null)
                {
                    isClearList(ConstantEnum.IBusinessFragment.TYPE_BRANDNAME_BUSINESS, (List<Business>)msg.obj);
                }
                break;
            case ConstantMessage.IBusiness.BUSINESS_COUNT_OK_MSG:
                // 数量
                if (msg.obj != null)
                {
                    isClearList(ConstantEnum.IBusinessFragment.TYPE_COUNT_BUSINESS, (List<Business>)msg.obj);
                }
                break;
            case ConstantMessage.IBusiness.BUSINESS_DISTANCE_OK_MSG:
                // 位置
                if (msg.obj != null)
                {
                    isClearList(ConstantEnum.IBusinessFragment.TYPE_GPS_BUSINESS, (List<Business>)msg.obj);
                }
                break;
            case ConstantMessage.IBusiness.BUSINESS_REMARK_OK_MSG:
                // 好评
                if (msg.obj != null)
                {
                    isClearList(ConstantEnum.IBusinessFragment.TYPE_HIGHOPINION_BUSINESS, (List<Business>)msg.obj);
                }
                break;
            case ConstantMessage.IBusiness.BUSINESS_TYPE_HOTWORD_OK_MSG:
                // 热词
                if (msg.obj != null)
                {
                    isClearList(ConstantEnum.IBusinessFragment.TYPE_HOTWORD_BUSINESS, (List<Business>)msg.obj);
                }
                break;
            case ConstantMessage.IBusiness.BUSINESS_TYPE_OK_MSG:
                // 品牌类型+商家类型
                if (msg.obj != null)
                {
                    isClearList(ConstantEnum.IBusinessFragment.TYPE_BUSINESSTYPE_BUSINESS, (List<Business>)msg.obj);
                }
                break;
            case ConstantMessage.IBusiness.BUSINESS_DISTANCEANDTYPE_OK_MSG:
                if (msg.obj != null)
                {
                    isClearList(ConstantEnum.IBusinessFragment.TYPE_GPS_TYPE_BUSINESS, (List<Business>)msg.obj);
                }
                break;
            case ConstantMessage.IBusiness.BUSINESS_COUNTSANDTYPE_OK_MSG:
                if (msg.obj != null)
                {
                    isClearList(ConstantEnum.IBusinessFragment.TYPE_COUNT_TYPE_BUSINESS, (List<Business>)msg.obj);
                }
                break;
            case ConstantMessage.IBusiness.BUSINESS_DEFAULTANDTYPE_OK_MSG:
                if (msg.obj != null)
                {
                    isClearList(ConstantEnum.IBusinessFragment.TYPE_DEFAULT_TYPE_BUSINESS, (List<Business>)msg.obj);
                }
                break;
            case ConstantMessage.IBusiness.BUSINESS_REMARKANDTYPE_OK_MSG:
                if (msg.obj != null)
                {
                    isClearList(ConstantEnum.IBusinessFragment.TYPE_HIGHOPINION_TYPE_BUSINESS, (List<Business>)msg.obj);
                }
                break;
            case ConstantMessage.HTTP_ERR_MSG:
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.err_network_http), Toast.LENGTH_LONG);
                break;
            default:
                break;
        }
    }
    
    /**
     * 判断是/否清楚list数据
     * <功能详细描述>
     * 
     * @param typeDefaultBusiness
     * @see [类、类#方法、类#成员]
     */
    private void isClearList(int typeDefaultBusiness, List<Business> mlist)
    {
        MyLogUtils.e(TAG, "mType_to_businessFragment:" + mType_to_businessFragment + " typeDefaultBusiness:" + typeDefaultBusiness);
        if (mType_to_businessFragment != typeDefaultBusiness)
        {
            START_DEFAULT = 0;
            PAGESIZE_DEFAULT = Constant.BUSINESSLISTSIZE;
            mlist_business.clear();
            mType_to_businessFragment = typeDefaultBusiness;
        }
        
        MyLogUtils.i(TAG, "mlist的数量：" + mlist.size());
        if (mlist_business.size() != 0)
        {
            /*
             * for (int i = 0; mlist != null && i < mlist.size(); i++)
             * {
             * mlist_business.add(i, mlist.get(i));
             * 
             * }
             */
            MyLogUtils.i(TAG, "数量增加了么1？" + mlist.size() + ":" + mlist_business.size());
            mlist_business.addAll(mlist);
            MyLogUtils.i(TAG, "数量增加了么2？" + mlist_business.size() + "");
        }
        else
        {
            mlist_business.clear();
            MyLogUtils.i(TAG, "数量增加了么3？" + mlist.size() + ":" + mlist_business.size());
            mlist_business.addAll(mlist);
            MyLogUtils.i(TAG, "数量增加了么4？" + mlist.size() + ":" + mlist_business.size());
        }
        listView_adapter.notifyDataSetChanged();
    }
    
    @Override
    protected void initLogic()
    {
        logic = (IBusinessLogic)getLogicByInterfaceClass(IBusinessLogic.class);
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.layout_module_business_type:
                tabStateArr[0] = !tabStateArr[0];
                tabStateArr[1] = false;
                setTabState();
                if (tabStateArr[0])
                {// 判断是否需要关闭弹出层
                    showPupupWindow(R.id.layout_module_business_type);
                }
                else
                {
                    popwindowDismiss();
                }
                break;
            case R.id.layout_module_business_desc:
                tabStateArr[0] = false;
                tabStateArr[1] = !tabStateArr[1];
                
                setTabState();
                if (tabStateArr[1])
                {// 判断是否需要关闭弹出层
                    showPupupWindow(R.id.layout_module_business_desc);
                }
                else
                {
                    popwindowDismiss();
                }
                break;
            default:
                break;
        }
    }
    
    private void showPupupWindow(int rid)
    {
        if (mPopupWindow == null)
        {
            showPupWindow = LayoutInflater.from(getActivity()).inflate(R.layout.listview_popwindow_cascade, null);
            initPopuWindow(showPupWindow);
            
            listview_popwindow_group = (ListView)showPupWindow.findViewById(R.id.listview_popwindow_group);
        }
        if (rid == R.id.layout_module_business_type)
        {// 获取所有外部类型
            list_group.clear();
            list_group.add((String)MyDataUtils.getData(getActivity(),
                ConstantEnum.IHomeBusinessType.TYPE_ONE,
                flag_more_translation ? ConstantFile.IBusinessType.NAMECOMPLEX : ConstantFile.IBusinessType.NAME,
                String.class));
            list_group.add((String)MyDataUtils.getData(getActivity(),
                ConstantEnum.IHomeBusinessType.TYPE_TWO,
                flag_more_translation ? ConstantFile.IBusinessType.NAMECOMPLEX : ConstantFile.IBusinessType.NAME,
                String.class));
            list_group.add((String)MyDataUtils.getData(getActivity(),
                ConstantEnum.IHomeBusinessType.TYPE_THREE,
                flag_more_translation ? ConstantFile.IBusinessType.NAMECOMPLEX : ConstantFile.IBusinessType.NAME,
                String.class));
            list_group.add((String)MyDataUtils.getData(getActivity(),
                ConstantEnum.IHomeBusinessType.TYPE_FOUR,
                flag_more_translation ? ConstantFile.IBusinessType.NAMECOMPLEX : ConstantFile.IBusinessType.NAME,
                String.class));
            list_group.add((String)MyDataUtils.getData(getActivity(),
                ConstantEnum.IHomeBusinessType.TYPE_FIVE,
                flag_more_translation ? ConstantFile.IBusinessType.NAMECOMPLEX : ConstantFile.IBusinessType.NAME,
                String.class));
            list_group.add((String)MyDataUtils.getData(getActivity(),
                ConstantEnum.IHomeBusinessType.TYPE_SIX,
                flag_more_translation ? ConstantFile.IBusinessType.NAMECOMPLEX : ConstantFile.IBusinessType.NAME,
                String.class));
            list_group.add((String)MyDataUtils.getData(getActivity(),
                ConstantEnum.IHomeBusinessType.TYPE_SEVEN,
                flag_more_translation ? ConstantFile.IBusinessType.NAMECOMPLEX : ConstantFile.IBusinessType.NAME,
                String.class));
            list_group.add((String)MyDataUtils.getData(getActivity(),
                ConstantEnum.IHomeBusinessType.TYPE_EIGHT,
                flag_more_translation ? ConstantFile.IBusinessType.NAMECOMPLEX : ConstantFile.IBusinessType.NAME,
                String.class));
        }
        else if (rid == R.id.layout_module_business_desc)
        {// 获取所有排序
            list_group.clear();
            if (flag_more_translation)
            {
                list_group.add(getString(R.string.str_paixu_moren));
                list_group.add(getString(R.string.str_paixu_haoping));
                list_group.add(getString(R.string.str_paixu_renqi));
                list_group.add(getString(R.string.str_paixu_zuijin));
            }
            else
            {
                list_group.add(getString(R.string.str_paixu_moren));
                list_group.add(getString(R.string.str_paixu_haoping));
                list_group.add(getString(R.string.str_paixu_renqi));
                list_group.add(getString(R.string.str_paixu_zuijin));
            }
            
        }
        adapter_listview_popwindow_group = new GroupAdapter(getActivity(), list_group);
        if (listview_popwindow_group == null)
        {
            listview_popwindow_group = (ListView)showPupWindow.findViewById(R.id.listview_popwindow_group);
        }
        listview_popwindow_group.setAdapter(adapter_listview_popwindow_group);
        
        listview_popwindow_group.setOnItemClickListener(new MyItemClick());
        
        // MyLogUtils.i(TAG, "pop位置：" + popwindowHeight + ":" + layout_top.getMeasuredHeight());
        popwindowHeight = layout_top.getMeasuredHeight();
        // 设定窗体 弹出位置
        mPopupWindow.showAsDropDown(layout_top, 0, 0);
    }
    
    private void popwindowDismiss()
    {
        if (mPopupWindow != null)
        {
            mPopupWindow.dismiss();
            tabStateArr[0] = false;
            tabStateArr[1] = false;
            setTabState();
        }
        
    }
    
    class MyItemClick implements OnItemClickListener
    {
        
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            MyLogUtils.e(TAG, "MyItemClick:" + position);
            adapter_listview_popwindow_group.setSelectedPosition(position);
            if (tabStateArr[0])
            {
                MyLogUtils.e(TAG, "选择类型：" + position + " -" + adapter_listview_popwindow_group.getItem(position));
                String title = "";
                switch (position)
                {
                    case 0:
                        title = ConstantEnum.IHomeBusinessType.TYPE_ONE;
                        break;
                    case 1:
                        title = ConstantEnum.IHomeBusinessType.TYPE_TWO;
                        break;
                    case 2:
                        title = ConstantEnum.IHomeBusinessType.TYPE_THREE;
                        break;
                    case 3:
                        title = ConstantEnum.IHomeBusinessType.TYPE_FOUR;
                        break;
                    case 4:
                        title = ConstantEnum.IHomeBusinessType.TYPE_FIVE;
                        break;
                    case 5:
                        title = ConstantEnum.IHomeBusinessType.TYPE_SIX;
                        break;
                    case 6:
                        title = ConstantEnum.IHomeBusinessType.TYPE_SEVEN;
                        break;
                    case 7:
                        title = ConstantEnum.IHomeBusinessType.TYPE_EIGHT;
                        break;
                    default:
                        break;
                }
                START_DEFAULT = 0;
                PAGESIZE_DEFAULT = Constant.BUSINESSLISTSIZE;
                mBundle = new Bundle();
                mlist_business.clear();
                mType_to_businessFragment = ConstantEnum.IBusinessFragment.TYPE_DEFAULT_TYPE_BUSINESS;
                mBundle.putInt(ConstantEnum.IBusinessFragment.TYPE, ConstantEnum.IBusinessFragment.TYPE_COUNT_TYPE_BUSINESS);
                mBundle.putString(ConstantEnum.IBusinessFragment.TYPE_ARGS, title);
                setGetBusiness(mBundle);
                String showTitle = (String)MyDataUtils.getData(getActivity(), title, flag_more_translation ? ConstantFile.IBusinessType.NAMECOMPLEX : ConstantFile.IBusinessType.NAME, String.class);
                textarr[0].setText(showTitle);
                textarr[0].setTag(title);
                // textarr[1].setText("排序");
            }
            else
            {
                MyLogUtils.i(TAG, "选择顺序：" + position + " -" + adapter_listview_popwindow_group.getItem(position));
                START_DEFAULT = 0;
                PAGESIZE_DEFAULT = Constant.BUSINESSLISTSIZE;
                mlist_business.clear();
                mBundle = new Bundle();
                Log.e(TAG, "position:" + position + ":" + textarr[0].getText());
                if (position == 0)
                {// 默认排序
                    if (textarr[0].getText().equals("分类"))
                    {
                        mBundle.putInt(ConstantEnum.IBusinessFragment.TYPE, ConstantEnum.IBusinessFragment.TYPE_DEFAULT_BUSINESS);
                    }
                    else
                    {
                        if (textarr[0].getTag() != null)
                        {
                            mBundle.putString(ConstantEnum.IBusinessFragment.TYPE_ARGS, textarr[0].getTag().toString());
                            mBundle.putInt(ConstantEnum.IBusinessFragment.TYPE, ConstantEnum.IBusinessFragment.TYPE_DEFAULT_TYPE_BUSINESS);
                        }
                        else
                        {
                            mBundle.putInt(ConstantEnum.IBusinessFragment.TYPE, ConstantEnum.IBusinessFragment.TYPE_DEFAULT_BUSINESS);
                        }
                    }
                }
                else if (position == 1)
                {// 好评优先
                    if (textarr[0].getText().equals("分类"))
                    {
                        mBundle.putInt(ConstantEnum.IBusinessFragment.TYPE, ConstantEnum.IBusinessFragment.TYPE_HIGHOPINION_BUSINESS);
                    }
                    else
                    {
                        if (textarr[0].getTag() != null)
                        {
                            mBundle.putString(ConstantEnum.IBusinessFragment.TYPE_ARGS, textarr[0].getTag().toString());
                            mBundle.putInt(ConstantEnum.IBusinessFragment.TYPE, ConstantEnum.IBusinessFragment.TYPE_HIGHOPINION_TYPE_BUSINESS);
                        }
                        else
                        {
                            mBundle.putInt(ConstantEnum.IBusinessFragment.TYPE, ConstantEnum.IBusinessFragment.TYPE_HIGHOPINION_BUSINESS);
                        }
                    }
                }
                else if (position == 2)
                {// 人气最高
                    if (textarr[0].getText().equals("分类"))
                    {
                        mBundle.putInt(ConstantEnum.IBusinessFragment.TYPE, ConstantEnum.IBusinessFragment.TYPE_COUNT_BUSINESS);
                    }
                    else
                    {
                        if (textarr[0].getTag() != null)
                        {
                            mBundle.putString(ConstantEnum.IBusinessFragment.TYPE_ARGS, textarr[0].getTag().toString());
                            mBundle.putInt(ConstantEnum.IBusinessFragment.TYPE, ConstantEnum.IBusinessFragment.TYPE_COUNT_TYPE_BUSINESS);
                        }
                        else
                        {
                            mBundle.putInt(ConstantEnum.IBusinessFragment.TYPE, ConstantEnum.IBusinessFragment.TYPE_COUNT_BUSINESS);
                        }
                    }
                }
                else if (position == 3)
                {// 离我最近
                    if (textarr[0].getText().equals("分类") || textarr[0].getTag() == null)
                    {
                        mBundle.putInt(ConstantEnum.IBusinessFragment.TYPE, ConstantEnum.IBusinessFragment.TYPE_GPS_TYPE_BUSINESS);
                    }
                    else
                    {
                        if (textarr[0].getTag() != null)
                        {
                            mBundle.putString(ConstantEnum.IBusinessFragment.TYPE_ARGS, textarr[0].getTag().toString());
                            mBundle.putInt(ConstantEnum.IBusinessFragment.TYPE, ConstantEnum.IBusinessFragment.TYPE_GPS_TYPE_BUSINESS);
                        }
                        else
                        {
                            mBundle.putInt(ConstantEnum.IBusinessFragment.TYPE, ConstantEnum.IBusinessFragment.TYPE_GPS_BUSINESS);
                        }
                    }
                }
                MyLogUtils.i(TAG, "---------调用次数-------------");
                setGetBusiness(mBundle);
                textarr[1].setText(paixu_arr[position]);
            }
            popwindowDismiss();
        }
        
    }
    
    private void initPopuWindow(View view)
    {
        /* 第一个参数弹出显示view 后两个是窗口大小 */
        DisplayMetrics display = ViewUtils.getscreen(getActivity());
        mPopupWindow = new PopupWindow(view, display.widthPixels, display.heightPixels - popwindowHeight);
        /* 设置背景显示 */
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.mypop_bg));
        /* 设置触摸外面时消失 */
        mPopupWindow.setOutsideTouchable(true);
        
        mPopupWindow.update();
        mPopupWindow.setTouchable(true);
        /* 设置点击menu以外其他地方以及返回键退出 */
        // mPopupWindow.setFocusable(true);
        
        view.setFocusableInTouchMode(true);
        
    }
    
    /**
     * 记录选中
     * <功能详细描述>
     * 
     * @param img
     * @param textView
     * @param state
     * @see [类、类#方法、类#成员]
     */
    private void setTabState()
    {
        for (int i = 0; i < tabStateArr.length; i++)
        {
            if (tabStateArr[i])
            {
                textarr[i].setTextColor(getResources().getColor(R.color.ganggang_color));
                imgarr[i].setBackgroundResource(R.drawable.up);
            }
            else
            {
                imgarr[i].setBackgroundResource(R.drawable.down);
                textarr[i].setTextColor(getResources().getColor(R.color.tab_text_color));
            }
        }
        
    }
    
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout)
    {
        MyLogUtils.i(TAG, "下拉加载");
        START_DEFAULT += Constant.BUSINESSLISTSIZE;
        PAGESIZE_DEFAULT += Constant.BUSINESSLISTSIZE;
        if (mBundle == null)
        {
            Log.e(TAG, "mBundle is null");
        }
        else
        {
            Log.e(TAG, mBundle.getString(ConstantEnum.IBusinessFragment.TYPE_ARGS));
            
        }
        // logic.getBusinessDefault(START_DEFAULT, PAGESIZE_DEFAULT);
        setGetBusiness(mBundle);
    }
    
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout)
    {
        MyLogUtils.i(TAG, "上啦加载");
        START_DEFAULT += Constant.BUSINESSLISTSIZE;
        PAGESIZE_DEFAULT += Constant.BUSINESSLISTSIZE;
        // logic.getBusinessDefault(START_DEFAULT, PAGESIZE_DEFAULT);
        setGetBusiness(mBundle);
    }
    
}
