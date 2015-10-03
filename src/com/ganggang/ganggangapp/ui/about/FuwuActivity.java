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
import android.webkit.WebView;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class FuwuActivity extends BasicActivity implements OnClickListener
{
    private TextView activity_fuwuxieyi_text;
    
    private ImageView layout_top_fuwuxieyi_return;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        
    }
    
    @Override
    public void setContentView()
    {
        setContentView(R.layout.activity_fuwuxieyi);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_top_fuwuxieyi);
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
        layout_top_fuwuxieyi_return.setOnClickListener(this);
        
    }
    
    @Override
    public void initData()
    {
       String str=   Utils.getFromAssets(this, "yonghuxieyi.txt");
       str = Html.fromHtml(str).toString();
       activity_fuwuxieyi_text.setText(str);
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.layout_top_fuwuxieyi_return:
                finish();
                break;
                
            default:
                break;
        }
        
    }
    
}
