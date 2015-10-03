package com.ganggang.frame.freamwork.ui;

import java.util.Properties;

import com.ganggang.frame.freamwork.logic.BaseLogicBuilder;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.ganggangapp.logic.LogicBuilder;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Toast;

/**
 * 
 * activity
 * 
 * @author 曾宝
 * @version [V1.00, 2015年7月14日]
 * @see [相关类/方法]
 * @since V1.00
 */
public abstract class BasicActivity extends LauncheActivity
{
    /**
     * DEBUG_TAG
     */
    private static final String TAG = "BasicActivity";
    
    /**
     * 
     */
    private static BasicActivity mCurrentActivity;
    
    /**
     * Activity Manager
     */
    protected MCActivityManager mActivityManger = MCActivityManager.getInstance();
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        mActivityManger.addActivity(this);
        
        
        setContentView();
        initViews();
        initListeners();
        initData();
    }
    
    public abstract void setContentView();
    
    public abstract void initViews();
    
    public abstract void initListeners();
    
    public abstract void initData();
    
    private Properties getProperties()
    {
        Properties props = new Properties();
        try
        {
            int id = this.getResources().getIdentifier("theme_config", "raw", getPackageName());
            props.load(getResources().openRawResource(id));
        }
        catch (Exception e)
        {
            MyLogUtils.e(TAG, "Could not find the properties file.");
        }
        return props;
    }
    
    @Override
    protected void initSystem()
    {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void initLogic()
    {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected BaseLogicBuilder createLogicBuilder(Context context)
    {
        BaseLogicBuilder builder = LogicBuilder.getInstance(context);
        return builder;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void onResume()
    {
        mCurrentActivity = this;
        super.onResume();
        
    }
    
    /**
     * 
     * 
     * @param str
     *            
     */
    public void showToast(String str, int duration)
    {
        Toast.makeText(this, str, duration).show();
    }
    
    public void showToast(int resId, int duration)
    {
        Toast.makeText(this, resId, duration).show();
    }
    
    @Override
    public void finish()
    {
        MyLogUtils.d(TAG, "finish");
        mActivityManger.removeActivity(this);
        super.finish();
    }
    
    /**
     */
    public void onRightBtnClick()
    {
    
    }
    
    /**
     * 获取屏幕大小
     * 
     * @return
     */
    public String getscreen()
    {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int h = dm.heightPixels;
        int w = dm.widthPixels;
        return w + "x" + h;
    }
    
    /**
     * @param context
     * @return
     */
    public String getAppVersionName(Context context)
    {
        String versionName = "";
        try
        {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0)
            {
                return "";
            }
        }
        catch (Exception e)
        {
            MyLogUtils.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
    
    /**
     * 获取网络连接
     * 
     * @param context
     * @return
     */
    public String getNet(Context context)
    {
        if (context != null)
        {
            ConnectivityManager mConnectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo == null || !mNetworkInfo.isAvailable())
            {
                return "null";
            }
            else if (mNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                return "2g";
            }
            else if (mNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI)
            {
                return "wifi";
            }
        }
        return "null";
        
    }
    
}
