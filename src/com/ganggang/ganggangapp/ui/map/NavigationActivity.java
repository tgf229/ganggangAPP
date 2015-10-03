package com.ganggang.ganggangapp.ui.map;

import com.ganggang.frame.freamwork.ui.BasicActivity;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.navigation.INavigationLogic;
import com.ganggang.ganggangapp.ui.fragment.HomeFragment;

import android.os.Bundle;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * 导航
 * 
 * @author 曾宝
 * @version [V1.00, 2015年8月20日]
 * @see [相关类/方法]
 * @since V1.00
 */
public class NavigationActivity extends BasicActivity implements OnClickListener
{
    private WebView navigation_web;
    
    private ImageView navigation_layout_map_drive, navigation_layout_map_bus, navigation_layout_map_walk;
    
    private Bundle mBundle;
    
    private INavigationLogic logic;
    
    private ProgressBar navigation_progress;
    
    private RelativeLayout navigation_layout;
    
    private LinearLayout navigation_layout_top;
    
    private final String TAG = NavigationActivity.class.getSimpleName();

    private String end;

    private String destName;

    private String start;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void setContentView()
    {
        setContentView(R.layout.acitivity_navigation);
    }
    
    @Override
    public void initViews()
    {
        try
        {
//            ViewUtils.initView(this);
            navigation_progress = (ProgressBar)findViewById(R.id.navigation_progress);
            navigation_layout = (RelativeLayout)findViewById(R.id.navigation_layout);
//            navigation_layout_top = (LinearLayout)navigation_layout.findViewById(R.id.navigation_layout_top);
            
            navigation_web = (WebView)navigation_layout.findViewById(R.id.navigation_web);
   /*         navigation_layout_map_drive = (ImageView)navigation_layout_top.findViewById(R.id.navigation_layout_map_drive);
            navigation_layout_map_bus = (ImageView)navigation_layout_top.findViewById(R.id.navigation_layout_map_bus);
            navigation_layout_map_walk = (ImageView)navigation_layout_top.findViewById(R.id.navigation_layout_map_walk);*/
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
   /*     navigation_layout_map_drive.setOnClickListener(this);
        navigation_layout_map_bus.setOnClickListener(this);
        navigation_layout_map_walk.setOnClickListener(this);*/
    }
    
    @Override
    public void initData()
    {
        mBundle = getIntent().getExtras();
        
        if (mBundle != null)
        {
            navigation_progress.setVisibility(View.VISIBLE);
            navigation_layout.setVisibility(View.GONE);
            initWebView();
             end = mBundle.getString(ConstantEnum.IMap.TYPE_ARGS_NAVIGATION_DEST);
            destName = mBundle.getString(ConstantEnum.IMap.TYPE_ARGS_NAVIGATION_DESTNAME);
            start = HomeFragment.MAPLOCATION.getLongitude() + "," + HomeFragment.MAPLOCATION.getLatitude();
            MyLogUtils.i(TAG, "start:" + start + "  end:" + end + " destName:" + destName);
            String uri = "http://m.amap.com/navi/?start=" + start + "&dest=" + end + "&destName=" + "" + "&naviBy=&key=16d0416871ad4bb405d484906b76ceb4";
            navigation_web.loadUrl(uri);
            navigation_web.setWebViewClient(new WebViewClient()
            {
                @Override
                public void onPageFinished(WebView view, String url)
                {
                    navigation_progress.setVisibility(View.GONE);
                    navigation_layout.setVisibility(View.VISIBLE);
                    super.onPageFinished(view, url);
                }
                
            });
        }
        else
        {
            MyLogUtils.i(TAG, "----------bundle is null");
            finish();
        }
    }
    
    @Override
    protected void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    
    }
    
    private void initWebView()
    {
        
        navigation_web.getSettings().setJavaScriptEnabled(true);
        navigation_web.getSettings().setRenderPriority(RenderPriority.HIGH);
        navigation_web.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // 设置 缓存模式
        navigation_web.getSettings().setDomStorageEnabled(true);
        // 开启 database storage API 功能
        navigation_web.getSettings().setDatabaseEnabled(true);
        
    }
    
    @Override
    protected void handleStateMessage(Message msg)
    {
        switch (msg.what)
        {
            case ConstantMessage.IMap.NAVIGATION_OK_MSG:
                MyLogUtils.i(TAG, msg.obj.toString());
                
                break;
                
            default:
                break;
        }
    }
    
    @Override
    protected void initLogic()
    {
    
    }

    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        
    }

 
    
}
