package com.ganggang.ganggangapp.ui.fragment;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.ganggang.frame.freamwork.fragment.BaseFragment;
import com.ganggang.frame.freamwork.ui.MCActivityManager;
import com.ganggang.frame.util.BitmapUtils;
import com.ganggang.frame.util.FileUtils;
import com.ganggang.frame.util.ImageUtils;
import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.constant.ConstantAction;
import com.ganggang.ganggangapp.constant.ConstantFile;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * 更多
 * 
 * @author 曾宝
 * @version [V1.00, 2015年7月30日]
 * @see [相关类/方法]
 * @since V1.00
 */
public class MoreFragment extends BaseFragment implements OnClickListener
{
    private static MoreFragment moreFragment;
    
    public static MoreFragment getNewInstance()
    {
        if (moreFragment == null)
            moreFragment = new MoreFragment();
            
        return moreFragment;
    }
    
    private final String TAG = MoreFragment.class.getSimpleName();
    
    /**
     * 简体，繁体 翻译， 日/夜 模式,2G/3G节省流量
     */
    private ImageButton more_translation, more_model, more_economize;
    
    /**
     * 节省流量
     */
    private RelativeLayout more_deletestorage, more_about;
    
    /**
     * 翻译，模式，节省
     */
    boolean flag_translation, flag_model, flag_economize;
    
    private TextView gengduo_file_size;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        
        View view = inflater.inflate(R.layout.fragment_module_gengduo, container, false);
        initView(view);
        initListener();
        initData();
        return view;
    }
    
    private void initData()
    {
        flag_translation = (Boolean)MyDataUtils.getData(getActivity(), ConstantFile.IMore.FileName, ConstantFile.IMore.MORE_TRANSLATION, Boolean.class);
        
        flag_model = (Boolean)MyDataUtils.getData(getActivity(), ConstantFile.IMore.FileName, ConstantFile.IMore.MORE_MODEL, Boolean.class);
        
        flag_economize = (Boolean)MyDataUtils.getData(getActivity(), ConstantFile.IMore.FileName, ConstantFile.IMore.MODE_ECONOMIZE, Boolean.class);
        
        boolean flag_first = (Boolean)MyDataUtils.getData(getActivity(), ConstantFile.IMore.FileName, ConstantFile.IMore.FIRST_APP, Boolean.class);
        
        if(!flag_first)
        {
            flag_translation = true;
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put(ConstantFile.IMore.FIRST_APP, true);
            MyDataUtils.putData(getActivity(), ConstantFile.IMore.FileName, map);
        } 
        more_translation.setImageResource(flag_translation ? R.drawable.gengduo_kaiguan_button : R.drawable.gengduo_guan_button);
        
        // more_model.setImageResource(flag_model?R.drawable.gengduo_kaiguan_button:R.drawable.gengduo_guan_button);
        
        more_economize.setImageResource(flag_economize ? R.drawable.gengduo_kaiguan_button : R.drawable.gengduo_guan_button);
        
        String path = BitmapUtils.getSDPath(-1);
        String length = FileUtils.getFileSize(path);
        gengduo_file_size.setText(length + "");
    }
    
    private void initListener()
    {
        more_translation.setOnClickListener(this);
        // more_model.setOnClickListener(this);
        more_economize.setOnClickListener(this);
        more_deletestorage.setOnClickListener(this);
        more_about.setOnClickListener(this);
    }
    
    private void initView(View view)
    {
        try
        {
            ViewUtils.initView(this, view);
        }
        catch (Exception e)
        {
            MyLogUtils.e(TAG, "INIT IS ERR", e);
        }
        
    }
    
    @Override
    protected void initLogic()
    {
        // TODO Auto-generated method stub
        
    }
    
    public void switchLanguage(Locale locale)
    {
        Resources resources = getResources();// 获得res资源对象
        Configuration config = resources.getConfiguration();// 获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
        config.locale = locale; // 简体中文
        resources.updateConfiguration(config, dm);
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        { // 翻译
            case R.id.more_translation:
                MyLogUtils.i(TAG, "简/繁 翻译");
                more_translation.setImageResource(flag_translation ? R.drawable.gengduo_guan_button : R.drawable.gengduo_kaiguan_button);
                flag_translation = !flag_translation;
                if (flag_translation)
                    switchLanguage(Locale.TAIWAN);
                else
                    switchLanguage(Locale.CHINA);
                
                Intent i = new Intent("com.ganggang.ganggangapp.switchreceiver");
                getActivity().finish();
                getActivity().sendBroadcast(i);
                
                break;
            /*
             * case R.id.more_model:
             * MyLogUtils.i(TAG, "日/夜 转换");
             * more_model.setImageResource(flag_model?R.drawable.gengduo_guan_button:R.drawable.gengduo_kaiguan_button);
             * flag_model = !flag_model;
             * break;
             */
            case R.id.more_economize:
                MyLogUtils.i(TAG, "省流量");
                more_economize.setImageResource(flag_economize ? R.drawable.gengduo_guan_button : R.drawable.gengduo_kaiguan_button);
                flag_economize = !flag_economize;
                break;
            case R.id.more_deletestorage:
                MyLogUtils.i(TAG, "清理缓存");
                String path = BitmapUtils.getSDPath(-1);
                FileUtils.deleteFile(path);
                gengduo_file_size.setText("0B");
                HashMap<String,Object> map = new HashMap<String, Object>();
                map.put(ConstantFile.IMore.FIRST_APP, false);
                MyDataUtils.putData(getActivity(), ConstantFile.IMore.FileName, map);
                break;
            case R.id.more_about:
                MyLogUtils.i(TAG, "关于");
                Intent intent = new Intent();
                intent.setAction(ConstantAction.GUANYU_ACTION);
                startActivity(intent);
                break;
            default:
                break;
        }
        
    }
    
    @Override
    public void onPause()
    {
        super.onPause();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantFile.IMore.MORE_TRANSLATION, flag_translation);
        map.put(ConstantFile.IMore.MORE_MODEL, flag_model);
        map.put(ConstantFile.IMore.MODE_ECONOMIZE, flag_economize);
        MyDataUtils.putData(getActivity(), ConstantFile.IMore.FileName, map);
        
    }
    
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantFile.IMore.MORE_TRANSLATION, flag_translation);
        map.put(ConstantFile.IMore.MORE_MODEL, flag_model);
        map.put(ConstantFile.IMore.MODE_ECONOMIZE, flag_economize);
        MyDataUtils.putData(getActivity(), ConstantFile.IMore.FileName, map);
    }
    
}
