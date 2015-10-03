package com.ganggang.ganggangapp.ui.login;

import java.util.ArrayList;
import java.util.List;

import com.ganggang.frame.constant.Constanct_Db;
import com.ganggang.frame.freamwork.ui.BasicActivity;
import com.ganggang.frame.util.BitmapUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Adv;
import com.ganggang.ganggangapp.constant.ConstantAction;
import com.ganggang.ganggangapp.constant.ConstantBundle;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.advlogic.IAdvLogic;
import com.ganggang.ganggangapp.ui.login.adapter.Adv_ViewPager_adapter;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * 广告页面
 * 
 * @author 曾宝
 * @version [V1.00, 2015年7月27日]
 * @see [相关类/方法]
 * @since V1.00
 */
public class AdvActivity extends BasicActivity implements OnPageChangeListener
{
    private final String TAG = "AdvActivity";
    
    private IAdvLogic logic;
    
    private ViewPager adv_viewpager;
    
    private Adv_ViewPager_adapter adapter;
    
    private List<RelativeLayout> mlist;
    
    private List<ImageView> mlist_rounds;
    
    private LinearLayout adv_rounds_layout;
    
    private TextView adv_text_go;
    
    private Intent mIntent;
    
    private ImageView adv_return;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void setContentView()
    {
        setContentView(R.layout.activity_adv);
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
            MyLogUtils.e(TAG, "init err", e);
        }
        
    }
    
    @Override
    public void initListeners()
    {
        adv_viewpager.setOnPageChangeListener(this);
        adv_return.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                mIntent = new Intent();
                mIntent.setAction(ConstantAction.HOME_ACTION);
                startActivity(mIntent);
                finish();
            }
        });
    }
    
    @Override
    public void initData()
    {
        mlist = new ArrayList<RelativeLayout>();
        
        logic.getAdv();
    
        mlist_rounds = new ArrayList<ImageView>();
        adapter = new Adv_ViewPager_adapter(mlist, this);
        adv_viewpager.setAdapter(adapter);
    }
    @Override
    protected void handleStateMessage(Message msg)
    {
        switch (msg.what)
        {
            case ConstantMessage.IAdv.ADV_GET_MSG:
                List<Bitmap> mlist_obj = (List<Bitmap>)msg.obj;
                mlist.clear();
                for(int i = 0;mlist_obj!=null && i<mlist_obj.size();i++)
                {
                    RelativeLayout layout = (RelativeLayout)LayoutInflater.from(this).inflate(R.layout.layout_adv_img_item, null, false);
                    ImageView img = (ImageView)layout.findViewById(R.id.adv_img);
                    TextView text = (TextView)layout.findViewById(R.id.adv_img_go);
                    text.setOnClickListener(new OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            mIntent = new Intent();
                            mIntent.setAction(ConstantAction.HOME_ACTION);
                            startActivity(mIntent);
                            finish();
                        }
                    });
                    if(i==mlist_obj.size()-1)
                    {
                        text.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        text.setVisibility(View.GONE);
                    }
                    img.setImageBitmap(mlist_obj.get(i));
                    mlist.add(layout);
                }
                
                adapter.notifyDataSetChanged();
                setRounds(mlist.size());
                break;
            default:
                break;
        }
    }
 
    /**
     * 设置圆点的数量
     * <功能详细描述>
     * 
     * @param size
     * @see [类、类#方法、类#成员]
     */
    private void setRounds(int size)
    {
        mlist_rounds.clear();
        for (int i = 0; i < size; i++)
        {
            ImageView imageView = new ImageView(this);
            imageView.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.setMargins(30, 0, 30,0);
            imageView.setLayoutParams(params);
            
            if (i == 0)
                imageView.setImageResource(R.drawable.round_white);
            else
                imageView.setImageResource(R.drawable.round_black);
                
            adv_rounds_layout.addView(imageView);
            
            mlist_rounds.add(imageView);
        }
        
    }
    
    @Override
    protected void initLogic()
    {
        logic = (IAdvLogic)getLogicByInterfaceClass(IAdvLogic.class);
    }
    
    @Override
    public void onPageScrollStateChanged(int arg0)
    {
    
    }
    
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void onPageSelected(int arg0)
    {
        for (int i = 0; mlist_rounds != null && i < mlist_rounds.size(); i++)
        {
            mlist_rounds.get(arg0).setImageResource(R.drawable.round_white);
            if (arg0 != i)
            {
                mlist_rounds.get(i).setImageResource(R.drawable.round_black);
            }
        }

        
    }
}
