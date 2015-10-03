package com.ganggang.ganggangapp.ui.businessbrand;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ganggang.frame.constant.ProgersssDialog;
import com.ganggang.frame.constant.refresh.PullListView;
import com.ganggang.frame.constant.refresh.PullToRefreshLayout;
import com.ganggang.frame.freamwork.ui.BasicActivity;
import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.Utils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.BusinessBrand;
import com.ganggang.ganggangapp.constant.Constant;
import com.ganggang.ganggangapp.constant.ConstantAction;
import com.ganggang.ganggangapp.constant.ConstantBundle;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.constant.ConstantFile;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.businessbrandlogic.IBusinessBrandLogic;
import com.ganggang.ganggangapp.ui.businessbrand.adapter.BusinessBrand_ListView_adapter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * 品牌类
 * 
 * @author 曾宝
 * @version [V1.00, 2015年8月8日]
 * @see [相关类/方法]
 * @since V1.00
 */
public class BusinessBrandActivity extends BasicActivity implements OnClickListener, PullToRefreshLayout.OnRefreshListener
{
    private final String TAG = BusinessBrandActivity.class.getSimpleName();
    
    /**
     * 头部返回
     */
    private ImageView layout_top_businessbrand_location, layout_top_businessbrand_left_return, layout_top_businessbrand_find_business;
    
    /**
     * 出现的品牌类别
     */
    private TextView layout_top_businessbrand_centnet_text;
    
    /**
     * 搜索
     */
    private LinearLayout layout_top_businessbrand_sreach;
    
    /**
     * 下拉/上啦载体
     */
    private PullToRefreshLayout businessbrand_pull_layout;
    
    /**
     * listview
     */
    private PullListView listview_businessbreand;
    
    private List<BusinessBrand> mlist_businessBrand;
    
    private BusinessBrand_ListView_adapter adapter_businessbrand;
    
    private IBusinessBrandLogic logic;
    
    private boolean flag = false;
    
    private  int START = 0;
    
    private  int PAGESIZE = Constant.BUSINESSLISTSIZE;
    
    private String mBrandType = null;
    
    private String mBrandId = null;
    
    private ProgersssDialog mProgerssDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void setContentView()
    {
        setContentView(R.layout.activity_businessbrand);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_top_businessbrand);
/*        mProgerssDialog = new ProgersssDialog(this);*/
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
            MyLogUtils.e(TAG, "init view is err", e);
        }
        
    }
    
    @Override
    public void initListeners()
    {
        layout_top_businessbrand_left_return.setOnClickListener(this);
        
        layout_top_businessbrand_location.setOnClickListener(this);
        businessbrand_pull_layout.setOnRefreshListener(this);
        
        layout_top_businessbrand_find_business.setOnClickListener(this);
        
        listview_businessbreand.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {// 点击品牌后，根据类型查找
                Intent intent = new Intent();
                BusinessBrand brand = mlist_businessBrand.get(position);
               // Log.i(TAG, brand.getBusinessBrandUri()==null?" null ":brand.getBusinessBrandUri());
                if(brand.getBusinessBrandUri()!=null && !brand.getBusinessBrandUri().isEmpty())
                {
                    Utils.intentUrl(brand.getBusinessBrandUri(), BusinessBrandActivity.this);
                }
                else
                {
                    intent.setAction(ConstantAction.BRANDBUSINESS_ACTION);
                    Bundle bundle = new Bundle();
                    bundle.putInt(ConstantEnum.IBusinessFragment.TYPE, ConstantEnum.IBusinessFragment.TYPE_BRANDID_BUSINESS);
                    bundle.putSerializable(ConstantEnum.IBusinessFragment.TYPE_ARGS, (Serializable)brand);
                    MyLogUtils.i(TAG, "brand:"+brand.getBusinessBrandName());
               //     intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtras(bundle);
                    // intent.putExtra(ConstantEnum.IBusinessFragment.TYPE_INTENT, bundle);
                    startActivity(intent);
                   
                }
            }
        });
    }
    
    @Override
    public void initData()
    {
        String brandType = getIntent().getStringExtra(ConstantBundle.HOME_BUSINESSBRAND);
        flag = (Boolean)MyDataUtils.getData(this, ConstantFile.IMore.FileName, ConstantFile.IMore.MORE_TRANSLATION, Boolean.class);
        mlist_businessBrand = new ArrayList<BusinessBrand>();
        
        adapter_businessbrand = new BusinessBrand_ListView_adapter(mlist_businessBrand, this);
        
        listview_businessbreand.setAdapter(adapter_businessbrand);
        
        adapter_businessbrand.notifyDataSetChanged();
        
        mBrandType = brandType;
        if (brandType != null)
        {
            String title = (String)MyDataUtils.getData(this, brandType, flag ? ConstantFile.IBusinessType.NAMECOMPLEX : ConstantFile.IBusinessType.NAME, String.class);
            layout_top_businessbrand_centnet_text.setText(title);
            
            logic.getBusinessBrand(mBrandType, START, PAGESIZE);
            
        }
        else
        {
            layout_top_businessbrand_centnet_text.setText("");
        }
        
    }
    
    @Override
    protected void onDestroy()
    {
/*        mlist_businessBrand.clear();
        adapter_businessbrand.notifyDataSetChanged();
        mlist_businessBrand=null;*/
        super.onDestroy();
    
    }
    
    @Override
    protected void handleStateMessage(Message msg)
    {
        businessbrand_pull_layout.refreshFinish(PullToRefreshLayout.SUCCEED);
//        mProgerssDialog.hide();
        switch (msg.what)
        {
            case ConstantMessage.IBusinessBrand.BRAND_OK_MSG:
                mlist_businessBrand.addAll((List<BusinessBrand>)msg.obj);
                adapter_businessbrand.notifyDataSetChanged();
                
                break;
            case ConstantMessage.IBusinessBrand.BRAND_ERR_MSG:
                break;
            case ConstantMessage.HTTP_ERR_MSG:
                showToast(R.string.err_network_http, Toast.LENGTH_LONG);
                break;
            default:
                break;
        }
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent();
            intent.setAction(ConstantAction.HOME_ACTION);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.layout_top_businessbrand_left_return:
                MyLogUtils.i(TAG, "返回");
//                Intent intent = new Intent();
//                intent.setAction(ConstantAction.HOME_ACTION);
//                startActivity(intent);
                finish();
                break;
            case R.id.layout_top_businessbrand_find_business:
                MyLogUtils.i(TAG, "跳转到商家界面");
                Intent intent_business = new Intent();
                intent_business.setAction(ConstantAction.HOME_ACTION);
                Bundle bundle = new Bundle();
                bundle.putInt(ConstantEnum.IBusinessFragment.TYPE, ConstantEnum.IBusinessFragment.TYPE_COUNT_TYPE_BUSINESS);
                bundle.putString(ConstantEnum.IBusinessFragment.TYPE_ARGS, mBrandType);
                intent_business.putExtras(bundle);
                startActivity(intent_business);
                break;
            case R.id.layout_top_businessbrand_location:
                Intent intent1 = new Intent();
                intent1.setAction(ConstantAction.MAP_ACTION);
                intent1.putExtra(ConstantBundle.IMapType.TO_MAP, ConstantEnum.IMap.TYPE_BRAND_BUSINESS);
                intent1.putExtra(ConstantBundle.IMapType.BRAND_MAP, mBrandType);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }
    
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout)
    {
        START += Constant.BUSINESSLISTSIZE;
        PAGESIZE += Constant.BUSINESSLISTSIZE ;
        logic.getBusinessBrand(mBrandType, START, PAGESIZE);
        
    }
    
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout)
    {
        START += Constant.BUSINESSLISTSIZE ;
        PAGESIZE += Constant.BUSINESSLISTSIZE ;
        logic.getBusinessBrand(mBrandType, START, PAGESIZE);
    }
    
    @Override
    protected void initLogic()
    {
        logic = (IBusinessBrandLogic)getLogicByInterfaceClass(IBusinessBrandLogic.class);
    }
    
}
