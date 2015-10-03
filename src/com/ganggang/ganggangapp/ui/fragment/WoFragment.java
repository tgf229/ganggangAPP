package com.ganggang.ganggangapp.ui.fragment;

import java.util.HashMap;

import com.ganggang.frame.freamwork.fragment.BaseFragment;
import com.ganggang.frame.util.CheckUtils;
import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.constant.Constant;
import com.ganggang.ganggangapp.constant.ConstantAction;
import com.ganggang.ganggangapp.constant.ConstantFile;
import com.ganggang.ganggangapp.constant.ConstantMessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 * 
 * 我
 * 
 * @author 曾宝
 * @version [V1.00, 2015年7月30日]
 * @see [相关类/方法]
 * @since V1.00
 */
public class WoFragment extends BaseFragment implements OnClickListener
{
    private final String TAG = WoFragment.class.getSimpleName();
    
    private static WoFragment woFragment;
    
    public static WoFragment getNewInstance()
    {
        if (woFragment == null)
            woFragment = new WoFragment();
            
        return woFragment;
    }
    
    /**
     * 评论,个人信息，注册,绑定手机
     */
    private RelativeLayout layout_wo_commit, layout_wo_info, layout_wo_register, layout_wo_bindphone,
        /**
         * 绑定邮箱，修改密码。退出登录
         */
        layout_wo_bindemail, layout_modify, layout_exitlogin;
    
    /**
     * 点击登录
     */
    private TextView text_layout_wo_login;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_module_wo, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }
    
    private void initData()
    {
       String name =  (String)MyDataUtils.getData(getActivity(), ConstantFile.IUserFile.FileName, ConstantFile.IUserFile.LOGINNAME, String.class);
       if(CheckUtils.isEmpty(name))
       {
           text_layout_wo_login.setVisibility(View.VISIBLE);
           layout_wo_register.setVisibility(View.VISIBLE);
           layout_exitlogin.setVisibility(View.GONE);
           layout_wo_register.setVisibility(View.VISIBLE);
           layout_wo_info.setVisibility(View.GONE);
           layout_wo_commit.setVisibility(View.GONE);
           layout_modify.setVisibility(View.GONE);
       }
       else
       {
           text_layout_wo_login.setVisibility(View.GONE);
           layout_wo_register.setVisibility(View.GONE);
           layout_exitlogin.setVisibility(View.VISIBLE);
           layout_wo_register.setVisibility(View.GONE);
           layout_wo_info.setVisibility(View.VISIBLE);
           layout_wo_commit.setVisibility(View.VISIBLE);
           layout_modify.setVisibility(View.VISIBLE);
       }
    
    }

    private void initView(View view)
    {
        try
        {
            ViewUtils.initView(this, view);
        }
        catch (Exception e)
        {
            MyLogUtils.e(TAG, "init is err", e);
        }
    }
    
    private void initListener()
    {
        layout_wo_commit.setOnClickListener(this);
        layout_wo_info.setOnClickListener(this);
        layout_wo_register.setOnClickListener(this);
/*        layout_wo_bindphone.setOnClickListener(this);
        layout_wo_bindemail.setOnClickListener(this);*/
        layout_modify.setOnClickListener(this);
        layout_exitlogin.setOnClickListener(this);
        text_layout_wo_login.setOnClickListener(this);
    }
    
    @Override
    protected void initLogic()
    {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        initData();
    }
    
    @Override
    public void onPause()
    {
        // TODO Auto-generated method stub
        super.onPause();
        initData();
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.text_layout_wo_login:
                MyLogUtils.i(TAG, "点击登录");
                Intent intent = new Intent();
                intent.setAction(ConstantAction.LOGIN_ACTION);
                startActivity(intent);
                break;
            case R.id.layout_wo_commit:
                MyLogUtils.i(TAG, "评论");
                Intent intent2 = new Intent();
                intent2.putExtra(Constant.IComment.TYPE_WO, true);
                intent2.setAction(ConstantAction.COMMENT_ACTION);
                startActivity(intent2);
                break;
            case R.id.layout_wo_info:
                MyLogUtils.i(TAG, "我的信息");
                Intent intent3 = new Intent();
                intent3.setAction(ConstantAction.USERINFO_ACTION);
                startActivity(intent3);
                break;
            case R.id.layout_wo_register:
                MyLogUtils.i(TAG, "注册");
                Intent intent1 = new Intent();
                intent1.setAction(ConstantAction.REGISTER_ACTION);
                startActivity(intent1);
                break;
       /*     case R.id.layout_wo_bindphone:
                MyLogUtils.i(TAG, "绑定号码");
                break;
            case R.id.layout_wo_bindemail:
                MyLogUtils.i(TAG, "绑定邮箱");
                break;*/
            case R.id.layout_modify:
                MyLogUtils.i(TAG, "修改密码");
                Intent intent4 = new Intent();
                intent4.setAction(ConstantAction.MODIFY_ACTION);
                startActivity(intent4);
                break;
            case R.id.layout_exitlogin:
                MyLogUtils.i(TAG, "退出登录");
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put(ConstantFile.IUserFile.LOGINNAME, "");
                map.put(ConstantFile.IUserFile.USERID, -1);
                map.put(ConstantFile.IUserFile.LOGINPASSWORD, "");
                MyDataUtils.putData(getActivity(), ConstantFile.IUserFile.FileName, map);
                initData();
                break;
            default:
                break;
        }
        
    }
    
}
