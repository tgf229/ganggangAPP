package com.ganggang.frame.common;

import java.util.ArrayList;
import java.util.List;

import com.ganggang.frame.util.CheckUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.Utils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Adv;
import com.tencent.mm.sdk.platformtools.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.LinearGradient;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.LinearLayout.LayoutParams;

public class PageFlipper extends ViewPager
{
    
    private String TAG = PageFlipper.class.getSimpleName();
    
    private List<View> views;
    
    private List<Adv> mlist_adv;
    
    private LinearLayout layout;
    
    private PagerAdapter adapter = new PagerAdapter()
    {
        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            View v = views.get(position);
            // final Adv adv = mlist_adv.get(position);
            // MyLogUtils.i(TAG, adv.getAdvUrl());
            container.addView(v);
            return v;
        }
        
        @Override
        public boolean isViewFromObject(View arg0, Object arg1)
        {
            return arg0 == arg1;
        }
        
        @Override
        public int getItemPosition(Object object)
        {
            return views.indexOf(object);
        }
        
        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View)object);
        }
        
        @Override
        public int getCount()
        {
            return views == null ? 0 : views.size();
        }
    };
    
    private OnPageChangeListener listener = new OnPageChangeListener()
    {
        
        private boolean mStop;
        
        /**
         * 将控件位置转化为数据集中的位置
         */
        public int convert(int position)
        {
            return position == 0 ? views.size() - 1 : (position > views.size() ? 0 : position - 1);
        }
        
        @Override
        public void onPageSelected(int position)
        {
            if (listener2 != null)
            {
                listener2.onPageSelected(convert(position));
            }
            
            for (int i = 0; mlist_rounds != null && i < mlist_rounds.size(); i++)
            {
                int po = position - 1;
                if (po < 0)
                    return;
                if (po == mlist_rounds.size())
                    return;
                mlist_rounds.get(po).setImageResource(R.drawable.round_white);
                if (po != i)
                {
                    mlist_rounds.get(i).setImageResource(R.drawable.round_black);
                }
            }
            final String url = views.get(position).getTag().toString();
            views.get(position).setOnClickListener(new OnClickListener()
            {
                
                @Override
                public void onClick(View v)
                {
                    Utils.intentUrl(url, getContext());
                }
            });
            
        }
        
        @Override
        public void onPageScrolled(int position, float percent, int offset)
        {
            if (listener2 != null)
            {
                listener2.onPageScrolled(convert(position), percent, offset);
            }
            if (views == null)
            {
                MyLogUtils.i(TAG, "---views is null");
            }
            else
            {
                if (percent == 0)
                {
                    if (position == 0) // 切换到倒数第二页
                        setCurrentItem((views.size() - 2) % views.size(), false);
                    else if (position == views.size() - 1) // 切换到正数第二页
                        setCurrentItem(1, false);
                }
            }
            
        }
        
        @Override
        public void onPageScrollStateChanged(int state)
        {
            if (listener2 != null)
            {
                listener2.onPageScrollStateChanged(state);
            }
            
            switch (state)
            {
                case SCROLL_STATE_IDLE: // 闲置
                    
                    if (!handler.hasMessages(START_FLIPPING))
                        handler.sendEmptyMessageDelayed(START_FLIPPING, 3000); // 延时滚动
                        
                    break;
                case SCROLL_STATE_DRAGGING: // 拖动中
                    MyLogUtils.i(TAG, "------------拖动中。。");
                    handler.sendEmptyMessage(STOP_FLIPPING); // 取消滚动
                    
                    break;
                case SCROLL_STATE_SETTLING: // 拖动结束
                    
                    break;
            }
        }
    }, listener2;
    
    private final int START_FLIPPING = 0;
    
    private final int STOP_FLIPPING = 1;
    
    private List<ImageView> mlist_rounds;
    
    /**
     * 设置圆点的数量
     * <功能详细描述>
     * 
     * @param size
     * @see [类、类#方法、类#成员]
     */
    private void setRounds(int size)
    {
        mlist_rounds = new ArrayList<ImageView>();
        if (layout != null)
            layout.removeAllViews();
        for (int i = 0; i < size; i++)
        {
            ImageView imageView = new ImageView(getContext());
            imageView.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            params.setMargins(30, 0, 0, 30);
            imageView.setLayoutParams(params);
            
            if (i == 1)
                imageView.setImageResource(R.drawable.round_white);
            else
                imageView.setImageResource(R.drawable.round_black);
                
            layout.addView(imageView);
            
            mlist_rounds.add(imageView);
        }
        
    }
    
    private Handler handler = new Handler()
    {
        
        public void handleMessage(Message msg)
        {
            
            switch (msg.what)
            {
                case START_FLIPPING:
                    if (views != null)
                    {
                        if (views.size() > 3) // 因为前后页是辅助页，所以此处3也就是只有1页
                            setCurrentItem((getCurrentItem() + 1) % views.size());
                            
                        handler.sendEmptyMessageDelayed(START_FLIPPING, 3000); // 延时滚动
                    }
                    break;
                case STOP_FLIPPING:
                    
                    handler.removeMessages(START_FLIPPING);
                    
                    break;
            }
        }
    };
    
    public PageFlipper(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }
    
    public PageFlipper(Context context)
    {
        super(context);
        init();
    }
    
    private void init()
    {
        
        setOffscreenPageLimit(1); // 最大页面缓存数量
        setAdapter(adapter); // 适配器
        super.setOnPageChangeListener(listener); // 监听器
        
        handler.sendEmptyMessageDelayed(START_FLIPPING, 3000); // 延时滚动
    }
    
    public void setViews(List<Bitmap> list, LinearLayout layout, List<Adv> list_adv)
    {
        this.views = new ArrayList<View>();
        this.mlist_adv = new ArrayList<Adv>();
        this.layout = layout;
        // this.mlist_adv = list_adv;
        for (int i = 0; i < list.size() + 2; i++)
        {
            ImageView img = new ImageView(getContext());
            img.setImageBitmap(list.get(i == 0 ? list.size() - 1 : (i > list.size() ? 0 : i - 1)));
            Adv adv = list_adv.get(i == 0 ? list.size() - 1 : (i > list.size() ? 0 : i - 1));
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            img.setTag(adv.getAdvUrl());
            
            this.views.add(img);
            this.mlist_adv.add(adv);
        }
        setRounds(this.views.size() - 2);
        this.adapter.notifyDataSetChanged();
        setCurrentItem(1); // 首页
    }
    
    public void setViews(int[] ids)
    {
        this.views = new ArrayList<View>();
        for (int i = 0; i < ids.length + 2; i++)
        { // 头部新增一个尾页，尾部新增一个首页
            
            ImageView iv = new ImageView(getContext());
            iv.setImageResource(ids[i == 0 ? ids.length - 1 : (i > ids.length ? 0 : i - 1)]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            
            this.views.add(iv);
        }
        setCurrentItem(1); // 首页
        this.adapter.notifyDataSetChanged();
    }
    
    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener)
    {
        this.listener2 = listener;
    }
    
}
