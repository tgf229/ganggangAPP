package com.ganggang.ganggangapp.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.protocol.RequestExpectContinue;

import com.ganggang.frame.freamwork.ui.BasicActivity;
import com.ganggang.frame.util.CheckUtils;
import com.ganggang.frame.util.MD5;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.constant.ConstantAction;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.registerlogic.IRegisterLogic;

public class RegisterActivity extends BasicActivity implements OnClickListener
{
    private ImageView activity_register_return;
    
    private Button acitivity_register_register_button, activity_register_login_btn;
    
    private EditText activity_register_name_edit, activity_register_password_edit, activity_register_password_agin_edit;
    
    private IRegisterLogic logic;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        
    }
    
    @Override
    public void setContentView()
    {
        setContentView(R.layout.activity_register);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_top_register);
        
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
        activity_register_return.setOnClickListener(this);
        
        acitivity_register_register_button.setOnClickListener(this);
        
        activity_register_login_btn.setOnClickListener(this);
    }
    
    @Override
    public void initData()
    {
     
    }
    
    @Override
    protected void handleStateMessage(Message msg)
    {
        switch (msg.what)
        {
            case ConstantMessage.HTTP_ERR_MSG:
                showToast(msg.obj.toString(), Toast.LENGTH_LONG);
                break;
            case ConstantMessage.IAdv.REGISTER_OK_MSG:
                showToast(R.string.ok_register, Toast.LENGTH_LONG);
                Intent intent = new Intent();
                intent.setAction(ConstantAction.LOGIN_ACTION);
                startActivity(intent);
                finish();
                break;
            case ConstantMessage.IAdv.REGISTER_ERR_MSG:
                showToast(R.string.err_register, Toast.LENGTH_LONG);
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
            case R.id.activity_register_return:
 /*               Intent intent = new Intent();
                intent.setAction(ConstantAction.LOGIN_ACTION);
                startActivity(intent);*/
                finish();
                break;
            case R.id.activity_register_login_btn:
                Intent intent1 = new Intent();
                intent1.setAction(ConstantAction.LOGIN_ACTION);
                startActivity(intent1);
                finish();
                break;
            case R.id.acitivity_register_register_button:
                String name = activity_register_name_edit.getText().toString();
                String password = activity_register_password_edit.getText().toString();
                String again_password = activity_register_password_agin_edit.getText().toString();
                
                if (CheckUtils.isUserNameCheck(name, this) && CheckUtils.isPasswordCheck(password, this) && CheckUtils.isPasswordCheck(again_password, this))
                {
                    if (password.equals(again_password))
                    {
                        password = MD5.GetMD5Code(password);
                        logic.Register(name, password);
                    }
                    else
                        showToast(R.string.err_password_again, Toast.LENGTH_LONG);
                }
                
                break;
            default:
                break;
        }
        
    }
    
    @Override
    protected void initLogic()
    {
        logic = (IRegisterLogic)getLogicByInterfaceClass(IRegisterLogic.class);
    }
    
}
