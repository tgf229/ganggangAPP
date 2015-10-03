package com.ganggang.ganggangapp.ui.businessbrand;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ganggang.frame.constant.refresh.PullListView;
import com.ganggang.frame.constant.refresh.PullToRefreshLayout;
import com.ganggang.frame.constant.refresh.PullToRefreshLayout.OnRefreshListener;
import com.ganggang.frame.freamwork.ui.BasicActivity;
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
import com.ganggang.ganggangapp.logic.businesslogic.IBusinessLogic;
import com.ganggang.ganggangapp.ui.fragment.adapter.Business_ListView_adapter;
import com.ganggang.ganggangapp.utils.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * 品牌点击后 显示的商品界面
 * 
 * @author 曾宝
 * @version [V1.00, 2015年8月18日]
 * @see [相关类/方法]
 * @since V1.00
 */
public class BrandBusinessActivity extends BasicActivity implements OnClickListener, OnRefreshListener
{
 
    private final String TAG= BrandBusinessActivity.class.getSimpleName();
    
    private PullToRefreshLayout activity_brand_business_pulltorefresh;
    
    /**
     * 信息listview
     */
    private PullListView listview_business;
    
    /**
     * 返回,显示位置
     */
    private ImageView layout_top_brand_business_left_return, layout_top_brand_business_location;
    
    /**
     * 显示品牌名称
     */
    private TextView layout_top_brand_business_centnet_text;
    
    private Business_ListView_adapter adapter;
    
    private List<Business> mlist_business;
    
    private IBusinessLogic logic;

    private BusinessBrand brand;
    
    private  int STARTSIZE = 0;
    
    private  int PAGESIZE = Constant.BUSINESSLISTSIZE;
    
    private boolean FLAG_MORE_TRANSLATION = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void setContentView()
    {
        setContentView(R.layout.activity_brand_business);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_top_brand_business);
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    @Override
    protected void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
        Business_ListView_adapter.map.clear();
    }
    @Override
    public void initListeners()
    {
        layout_top_brand_business_left_return.setOnClickListener(this);
        
        layout_top_brand_business_location.setOnClickListener(this);
        
        activity_brand_business_pulltorefresh.setOnRefreshListener(this);
    }
    
    @Override
    public void initData()
    {
        mlist_business = new ArrayList<Business>();
        adapter = new Business_ListView_adapter(mlist_business, this);
        listview_business.setAdapter(adapter);
        Business_ListView_adapter.map.clear();
        adapter.notifyDataSetChanged();
        Bundle bundle = getIntent().getExtras();
       brand = (BusinessBrand)bundle.getSerializable(ConstantEnum.IBusinessFragment.TYPE_ARGS);
 /*      if(logic==null)
       {
           MyLogUtils.i("TAG", "================");
       }
       if(brand==null)
       {
           MyLogUtils.i("TAG", "======wwww==========");
       }*/
       FLAG_MORE_TRANSLATION = Utils.getTranslation(this);
       layout_top_brand_business_centnet_text.setText(FLAG_MORE_TRANSLATION?brand.getBusinessBrandNameComplex():brand.getBusinessBrandName());
        logic.getBusinessByBrand(brand.getBusinessBrandId(), STARTSIZE, PAGESIZE);
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.layout_top_brand_business_left_return:
                finish();
                break;
            case R.id.layout_top_brand_business_location:
                Intent intent2 = new Intent();
                Bundle bundle2 = new Bundle();
                 bundle2.putInt(ConstantBundle.IMapType.TO_MAP,ConstantEnum.IMap.TYPE_DETAIL_BUSINESSBRAND);
                 bundle2.putSerializable(ConstantBundle.IMapType.BUSINESS_DETAIL_MAP, (Serializable)mlist_business);
                /*ntent2.putExtra(ConstantBundle.IMapType.TO_MAP, ConstantEnum.IMap.TYPE_DETAIL_BUSINESSBRAND);
                intent2.putExtra(ConstantBundle.IMapType.BUSINESS_DETAIL_MAP, (Serializable)mlist_sub_all);*/
                intent2.setAction(ConstantAction.MAP_ACTION);
                intent2.putExtras(bundle2);
                startActivity(intent2);
              /*  Intent intent = new Intent();
                intent.setAction(ConstantAction.MAP_ACTION);
                Bundle bundle = new Bundle();
                //从品牌 商家列表 跳到map 上
                bundle.putSerializable(ConstantEnum.IMap.TYPE_ARGS, (Serializable)mlist_business);
                startActivity(intent);*/
                break;
            default:
                break;
        }
    }
    
    @Override
    protected void handleStateMessage(Message msg)
    {     
        
        activity_brand_business_pulltorefresh.refreshFinish(PullToRefreshLayout.SUCCEED);
        switch (msg.what)
        {
            case ConstantMessage.IBusiness.BUSINESS_BRANDID_OK_MSG:
                if(msg.obj!=null)
                {
                    List<Business> list = (List<Business>)msg.obj;
                    for(int i = 0 ;i<list.size();i++)
                    {
                        mlist_business.add(i, list.get(i));
                    }
                    adapter.notifyDataSetChanged();
                }
                break;
            case ConstantMessage.IBusiness.BUSINESS_BRANDID_ERR_MSG:
                
                break;
            case ConstantMessage.HTTP_ERR_MSG:
                
                break;
            default:
                break;
        }
    }
    
    @Override
    protected void initLogic()
    {
        logic = (IBusinessLogic)getLogicByInterfaceClass(IBusinessLogic.class);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout)
    {
          STARTSIZE+=Constant.BUSINESSLISTSIZE;
          PAGESIZE+=Constant.BUSINESSLISTSIZE;
          logic.getBusinessByBrand(brand.getBusinessBrandId(), STARTSIZE, PAGESIZE);
        
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout)
    {
        MyLogUtils.i(TAG, "上啦加载");
        STARTSIZE+=Constant.BUSINESSLISTSIZE;
        PAGESIZE+=Constant.BUSINESSLISTSIZE;
        logic.getBusinessByBrand(brand.getBusinessBrandId(), STARTSIZE, PAGESIZE);
        
    }
    
}
