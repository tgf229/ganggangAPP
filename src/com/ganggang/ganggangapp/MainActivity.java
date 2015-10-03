package com.ganggang.ganggangapp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

import com.ganggang.frame.constant.Constanct_Db;
import com.ganggang.frame.freamwork.ui.BasicActivity;
import com.ganggang.frame.util.BitmapUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.bean.Adv;
import com.ganggang.ganggangapp.constant.Constant;
import com.ganggang.ganggangapp.constant.ConstantAction;
import com.ganggang.ganggangapp.constant.ConstantBundle;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.constant.ConstantFile;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.mainlogic.IMainLogic;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;

import android.R.anim;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends BasicActivity implements AnimationListener
{
    private ImageView main_logo;
    
    private IMainLogic logic;
    
    private final String TAG = "MainActivity";
    
    private Intent intent = new Intent();

    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void setContentView()
    {
        setContentView(R.layout.activity_main);
    /*    //QQ 第三方登录打开debug打印
        StatConfig.setDebugEnable(true);
            
        StatService.trackCustomEvent(this, "onCreate", "");*/
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
            MyLogUtils.e(TAG, "INIT IS ERR", e);
        }
        
    }
    
    @Override
    public void initListeners()
    {
    
    }
    
    @Override
    protected void handleStateMessage(Message msg)
    {
        switch (msg.what)
        {
            case ConstantMessage.HTTP_ERR_MSG:
                showToast(msg.obj.toString(), Toast.LENGTH_LONG);
                break;
            default:
                break;
        }
   
    }
    
    @Override
    public void initData()
    {
        logic.getAdv();
        logic.getBusinessType();
        logic.getHomeBusinessTilte();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_main_logo);
        animation.start();
        main_logo.setAnimation(animation);
        animation.setAnimationListener(this);
    }
    
    @Override
    protected void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
    
  
    
    @Override
    public void onAnimationStart(Animation animation)
    {}
    
    @Override
    public void onAnimationEnd(Animation animation)
    {
     /*   myhandler.sendEmptyMessageDelayed(ConstantMessage.IAdv.MAN_OK_ADV, 5000);*/
        intent.setAction(ConstantAction.ADV_ACTION);
        startActivity(intent);
        finish();
    }
    
    @Override
    public void onAnimationRepeat(Animation animation)
    {}
    
    @Override
    protected void initLogic()
    {
        logic = (IMainLogic)getLogicByInterfaceClass(IMainLogic.class);
    }
    
}
