package com.ganggang.ganggangapp.ui.login.adapter;

import java.util.List;

import com.ganggang.ganggangapp.bean.Adv;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class Adv_ViewPager_adapter extends PagerAdapter
{
    private List<RelativeLayout> mlist;
    
    private Context mContext;
    
    public Adv_ViewPager_adapter(List<RelativeLayout> mlist, Context mContext)
    {
        super();
        this.mlist = mlist;
        this.mContext = mContext;
    }
    
    @Override
    public int getCount()
    {
        return mlist == null ? 0 : mlist.size();
    }
    
    @Override
    public boolean isViewFromObject(View arg0, Object arg1)
    {
        return arg0 == arg1;
    }
    
    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView(mlist.get(position));
    }
    
    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        RelativeLayout img = mlist.get(position);
        container.addView(img);
        return img;
    }
    
}
