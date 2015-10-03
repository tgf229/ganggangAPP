package com.ganggang.frame.common;



import com.ganggang.ganggangapp.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyModule_ImgLayout extends RelativeLayout
{
    private ImageView img;
    
    private TextView text;
    
    private RelativeLayout.LayoutParams params_img;
    
    private RelativeLayout.LayoutParams params_text;

    @SuppressLint("ResourceAsColor") public MyModule_ImgLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        
        img = new ImageView(context,attrs);
        img.setPadding(0, 0, 0, 0);
        img.setId(2);
        
        text = new TextView(context,attrs);
        text.setPadding(0, 0, 0, 0);
        text.setTextColor(R.color.black);
        text.setTextSize(14);
        text.setTypeface(Typeface.DEFAULT_BOLD);
        
        params_img = new RelativeLayout.LayoutParams(100,100);
        params_img.setMargins(0,10, 0, 5);
        params_img.addRule(RelativeLayout.CENTER_HORIZONTAL);
        
        params_text = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        params_text.setMargins(0, 5, 0, 0);
        params_text.addRule(RelativeLayout.BELOW, img.getId());
        params_text.addRule(RelativeLayout.CENTER_HORIZONTAL);
        
   
        setClickable(true);
        setFocusable(true);
        
        addView(img, params_img);
        addView(text, params_text);
    }
    
}
