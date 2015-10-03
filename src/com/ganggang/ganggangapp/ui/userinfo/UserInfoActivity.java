package com.ganggang.ganggangapp.ui.userinfo;

import java.util.HashMap;

import com.ganggang.frame.freamwork.ui.BasicActivity;
import com.ganggang.frame.freamwork.ui.MCActivityManager;
import com.ganggang.frame.util.CheckUtils;
import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.UserInfo;
import com.ganggang.ganggangapp.constant.ConstantFile;
import com.ganggang.ganggangapp.constant.ConstantHttp;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.userinfologic.IUserInfoLogic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class UserInfoActivity extends BasicActivity implements OnClickListener
{
    
    private final String TAG = UserInfoActivity.class.getSimpleName();
    
    /**
     * 完成
     */
    private TextView layout_top_userinfo_ok;
    
    /**
     * 返回按钮
     */
    private ImageView layout_top_userinfo_return;
    
    private EditText userinfo_name, userinfo_email, userinfo_phone;
    
    private RadioGroup userinfo_sex;
    
    private RadioButton userinfo_male,userinfo_female;
    
    private IUserInfoLogic logic;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        
    }
    
    
    @Override
    public void setContentView()
    {
        setContentView(R.layout.activity_userinfo);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_top_userinfo);
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
        layout_top_userinfo_return.setOnClickListener(this);
        layout_top_userinfo_ok.setOnClickListener(this);
    }
    
    @Override
    public void initData()
    {
        int userid = (Integer)MyDataUtils.getData(this, ConstantFile.IUserFile.FileName, ConstantFile.IUserFile.USERID, Integer.class);
        logic.getUserInfo(userid);
    }
    
    @Override
    protected void handleStateMessage(Message msg)
    {
        switch (msg.what)
        {
            case ConstantMessage.IUserinfo.USERINFO_OK_MSG:
                if(msg.obj!=null)
                {
                  UserInfo info =  (UserInfo)msg.obj;
                  userinfo_name.setText(info.getUserName()==null?"":info.getUserName());
                  if(info.getUserGender().equals("F"))
                  {
                      userinfo_male.setChecked(true);
                  }
                  else
                      userinfo_female.setChecked(true);
                  
                  userinfo_email.setText(info.getEmail()==null?"":info.getEmail());
                  
                  userinfo_phone.setText(info.getMobile()==null?"":info.getMobile());
                }
                break;
            case ConstantMessage.IUserinfo.USERINFO_ADD_OK_MSG:
                showToast(R.string.str_login_ok, Toast.LENGTH_LONG);
               finish();
                break;
            default:
                break;
        }
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.layout_top_userinfo_return:
                finish();
                break;
            case R.id.layout_top_userinfo_ok:
                String username = userinfo_name.getText().toString().intern();
                int id  = userinfo_sex.getCheckedRadioButtonId();
                String sex="F";
                if(id==R.id.userinfo_female)
                {
                    sex ="M";
                }
                String phone = userinfo_phone.getText().toString().intern();
                String email = userinfo_email.getText().toString().intern();
                if(CheckUtils.isValidateEmailCheck(email, this))
                {
                    UserInfo info = new UserInfo();
                    int userind =  (Integer)MyDataUtils.getData(this, ConstantFile.IUserFile.FileName,ConstantFile.IUserFile.USERID, Integer.class);
                    info.setUserId(userind);
                    info.setUserName(username);
                    info.setMobile(phone);
                    info.setUserGender(sex);
                    info.setEmail(email);
                     logic.setUserInfo(info);
                }
                else
                {
                    showToast(R.string.err_validateemail, Toast.LENGTH_LONG);
                }
                break;
            default:
                break;
        }
        
    }
    
    @Override
    protected void initLogic()
    {
        logic = (IUserInfoLogic)getLogicByInterfaceClass(IUserInfoLogic.class);
    }
    
}
