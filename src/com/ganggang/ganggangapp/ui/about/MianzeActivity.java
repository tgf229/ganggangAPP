package com.ganggang.ganggangapp.ui.about;

import com.ganggang.frame.freamwork.ui.BasicActivity;
import com.ganggang.frame.util.Utils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class MianzeActivity extends BasicActivity implements OnClickListener
{
    private TextView activity_mianze_text;
    
    private ImageView layout_top_mianze_return;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        
    }
    
    @Override
    public void setContentView()
    {
        setContentView(R.layout.activity_mianze);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_top_mianze);
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
        layout_top_mianze_return.setOnClickListener(this);
        
    }
    
    @Override
    public void initData()
    {
        String str=   Utils.getFromAssets(this, "mianze.txt");
        str = Html.fromHtml(str).toString();
        activity_mianze_text.setText(str);
        
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.layout_top_mianze_return:
                finish();
                break;
                
            default:
                break;
        }
        
    }
    
}
