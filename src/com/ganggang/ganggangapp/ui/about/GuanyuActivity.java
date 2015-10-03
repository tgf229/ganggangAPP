package com.ganggang.ganggangapp.ui.about;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.ganggang.frame.freamwork.ui.BasicActivity;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.constant.ConstantAction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class GuanyuActivity extends BasicActivity implements OnClickListener
{
    private TextView activity_guangyu_fuwu;
    
    private TextView activity_guangyu_mianze;
    
    private ImageView layout_top_guanyu_return;

    private Intent intent;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
    }
    

    @Override
    public void setContentView()
    {
        setContentView(R.layout.activity_guanyu);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_top_guanyu);
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
    public void initListeners()
    {
        activity_guangyu_mianze.setOnClickListener(this);
        activity_guangyu_fuwu.setOnClickListener(this);
        layout_top_guanyu_return.setOnClickListener(this);
    }

    @Override
    public void initData()
    {
     
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.activity_guangyu_fuwu:
               intent = new Intent();
               intent.setAction(ConstantAction.FUWU_ACTION);
               startActivity(intent);
                break;
            case R.id.activity_guangyu_mianze:
                intent = new Intent();
                intent.setAction(ConstantAction.MIANZE_ACTION);
                startActivity(intent);
                break;
            case R.id.layout_top_guanyu_return:
                finish();
                break;
            default:
                break;
        }
        
    }
    
}
