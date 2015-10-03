package com.ganggang.ganggangapp.ui.module;

import java.io.Serializable;

import com.ganggang.frame.freamwork.ui.BasicActivity;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.BusinessBrand;
import com.ganggang.ganggangapp.constant.ConstantAction;
import com.ganggang.ganggangapp.constant.ConstantBundle;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.ui.businessbrand.adapter.BusinessBrand_ListView_adapter;
import com.ganggang.ganggangapp.ui.fragment.BusinessFragment;
import com.ganggang.ganggangapp.ui.fragment.HomeFragment;
import com.ganggang.ganggangapp.ui.fragment.MoreFragment;
import com.ganggang.ganggangapp.ui.fragment.WoFragment;
import com.ganggang.ganggangapp.ui.fragment.adapter.HomeBusiness_ExpandableList_adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

/**
 * 
 * 首页
 * 
 * @author 曾宝
 * @version [V1.00, 2015年7月28日]
 * @see [相关类/方法]
 * @since V1.00
 */
public class HomeActivity extends BasicActivity implements OnCheckedChangeListener
{
    
    private ProgressBar activity_home_progress;
    
    /**
     * 多选
     */
    private RadioGroup group_activity_home;
    
    private RadioButton radiobtn_activity_home_home, radiobtn_activity_home_business, radiobtn_activity_home_wo, radiobtn_activity_home_more;
    
    /**
     * 替换的地方
     */
    private FrameLayout frame_activity_home;
    
    private FragmentManager fragmentManager;
    
    private FragmentTransaction fragmentTransaction;
    
    private final String TAG = "HomeActivity";
    
    private Fragment mContent = null;
    
    // **********头部*********************
/*    private LinearLayout layout_top_sreach, layout_top_bussiness_sreach;
    
    private ImageView layout_top_location,layout_top_bussiness_location;*/
    
    private boolean[] flag_top_clicks = new boolean[4];
    
    private long firstTime = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
    /*
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0)
        {
            finish();
            return;
        }*/
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_top_home);
        setHomeTop();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        // super.onSaveInstanceState(outState);
    }
    
    
    
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000)
                { // 如果两次按键时间间隔大于2秒，则不退出
                    showToast(R.string.back_two, Toast.LENGTH_SHORT);
                    firstTime = secondTime;// 更新firstTime
                    return true;
                }
                else
                { // 两次按键小于2秒时，退出应用
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
    
    @Override
    public void setContentView()
    {
        setContentView(R.layout.activity_home);
        
    }
    
    @Override
    public void initViews()
    {
        try
        {
            ViewUtils.initView(this);
            /*
             * activity_home_progress.setVisibility(View.VISIBLE);
             * activity_home_layout.setVisibility(View.GONE);
             */
        }
        catch (Exception e)
        {
            MyLogUtils.e(TAG, "init err", e);
        }
        
    }
    
    @Override
    public void initListeners()
    {
        
        group_activity_home.setOnCheckedChangeListener(this);
        
    }
    
    @Override
    protected void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.e(TAG, "HOME 被销毁了......................");
        HomeBusiness_ExpandableList_adapter.bitmaps.clear();
    }
    
    @Override
    public void initData()
    {
        MyLogUtils.i(TAG, "home 走这里了么-----------------------------------------------");
        fragmentManager = getSupportFragmentManager();
        try
        {
            Bundle bundle = getIntent().getExtras();
            if (bundle == null)
            {
                MyLogUtils.i(TAG, "选择跳入home");
                getWindow().clearFlags(Window.FEATURE_CUSTOM_TITLE);
                getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_top_home);
                setHomeTop();
                replaceFragment(HomeFragment.getNewInstance());
            }
            else
            {
                try
                {
                    Integer type = bundle.getInt(ConstantEnum.IBusinessFragment.TYPE);
                    MyLogUtils.i(TAG, "选择跳入business:" + type);
                    if (type >= 0)
                    {
                        getWindow().clearFlags(Window.FEATURE_CUSTOM_TITLE);
                        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_top_business);
                       setBusinessTop();
                        replaceFragment(BusinessFragment.getNewIntance());
                        BusinessFragment.getNewIntance().setArguments(bundle);
                        radiobtn_activity_home_business.setChecked(true);
                    }
                    else
                    {
                        getWindow().clearFlags(Window.FEATURE_CUSTOM_TITLE);
                        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_top_home);
                        setHomeTop();
                        replaceFragment(HomeFragment.getNewInstance());
                        radiobtn_activity_home_home.setChecked(true);
                    }
                }
                catch (Exception e)
                {
                    getWindow().clearFlags(Window.FEATURE_CUSTOM_TITLE);
                    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_top_business);
                    setHomeTop();
                    replaceFragment(BusinessFragment.getNewIntance());
                    radiobtn_activity_home_home.setChecked(true);
                }
                
            }
        }
        catch (Exception e)
        { // 异常 加载首页
            getWindow().clearFlags(Window.FEATURE_CUSTOM_TITLE);
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_top_home);
            setHomeTop();
            replaceFragment(HomeFragment.getNewInstance());
            radiobtn_activity_home_home.setChecked(true);
        }
        
    }
    
    @Override
    protected void onRestart()
    {
        super.onRestart();
    }
    
    
    
    private void replaceFragment(Fragment fragment)
    {
        try
        {
            if (mContent == null)
            {
                fragmentTransaction = fragmentManager.beginTransaction();
                
                fragmentTransaction.replace(R.id.frame_activity_home, fragment);
                
                fragmentTransaction.addToBackStack(null);
                
                fragmentTransaction.commit();
                
            }
            else if (mContent != fragment)
            {
                FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                if (!fragment.isAdded())
                { // 先判断是否被add过
                    transaction.hide(mContent).add(R.id.frame_activity_home, fragment).commit(); // 隐藏当前的fragment，add下一个到Activity中
                }
                else
                {
                    transaction.hide(mContent).show(fragment).commit(); // 隐藏当前的fragment，显示下一个
                }
            }
            mContent = fragment;
        }
        catch (Exception e)
        {
           MyLogUtils.e(TAG, "REPLACE",e);
        }
    }
    
    public void TopClick(View view)
    {
        Intent intent = new Intent();
        switch (view.getId())
        {
            case R.id.layout_top_sreach:
                if(!flag_top_clicks[0])
                    return;
                MyLogUtils.i(TAG, "点击搜索框");
                intent.setAction(ConstantAction.SREACH_ACTION);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(ConstantEnum.IBack.BACK_ARGS, ConstantEnum.IBack.IBackArgs.BACK_HOME);
                startActivity(intent);
                 finish();
                break;
            case R.id.layout_top_location:
                if(!flag_top_clicks[1])
                    return;
                MyLogUtils.i(TAG, "点击定位");
                intent.setAction(ConstantAction.MAP_ACTION);
                intent.putExtra(ConstantBundle.IMapType.TO_MAP, ConstantEnum.IMap.TYPE_HOME);
                startActivity(intent);
                break;
            case R.id.layout_top_bussiness_sreach:
                if(!flag_top_clicks[2])
                    return;
                MyLogUtils.i(TAG, "点击搜索2");
                intent.setAction(ConstantAction.SREACH_ACTION);
                intent.putExtra(ConstantEnum.IBack.BACK_ARGS, ConstantEnum.IBack.IBackArgs.BACK_BUSINESS);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.layout_top_bussiness_location:
                if(!flag_top_clicks[3])
                    return;
                MyLogUtils.i(TAG, "点击定位2");
                intent.setAction(ConstantAction.MAP_ACTION);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        switch (checkedId)
        {
            case R.id.radiobtn_activity_home_home:
                MyLogUtils.i(TAG, "首页");
                getWindow().clearFlags(Window.FEATURE_CUSTOM_TITLE);
                getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_top_home);
                setHomeTop();
                replaceFragment(HomeFragment.getNewInstance());
                break;
            case R.id.radiobtn_activity_home_business:
                MyLogUtils.i(TAG, "商家");
                getWindow().clearFlags(Window.FEATURE_CUSTOM_TITLE);
                getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_top_business);
                setBusinessTop();
                replaceFragment(BusinessFragment.getNewIntance());
                break;
            case R.id.radiobtn_activity_home_wo:
                MyLogUtils.i(TAG, "我");
                getWindow().clearFlags(Window.FEATURE_CUSTOM_TITLE);
           
                getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_top_wo);
                setTopClickGone();
                replaceFragment(WoFragment.getNewInstance());
                break;
            case R.id.radiobtn_activity_home_more:
                MyLogUtils.i(TAG, "更多");
                getWindow().clearFlags(Window.FEATURE_CUSTOM_TITLE);
                getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_top_more);
                setTopClickGone();
                replaceFragment(MoreFragment.getNewInstance());
                break;
            default:
                break;
        }
        
    }
    
    private void setBusinessTop()
    {
        flag_top_clicks[0] = false;
        flag_top_clicks[1] = false;
        flag_top_clicks[2] = true;
        flag_top_clicks[3] = true;
    }
    
    private void setHomeTop()
    {
        flag_top_clicks[0] = true;
        flag_top_clicks[1] = true;
        flag_top_clicks[2] = false;
        flag_top_clicks[3] = false;
  
    }
    
    private void setTopClickGone()
    {
        flag_top_clicks[0] = false;
        flag_top_clicks[1] = false;
        flag_top_clicks[2] = false;
        flag_top_clicks[3] = false;
    }
    
}
