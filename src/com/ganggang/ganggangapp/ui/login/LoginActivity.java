package com.ganggang.ganggangapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.ganggang.frame.freamwork.ui.BasicActivity;
import com.ganggang.frame.util.CheckUtils;
import com.ganggang.frame.util.MD5;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.TextViewUtil;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.constant.AppConstant;
import com.ganggang.ganggangapp.constant.ConstantAction;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.loginlogic.ILoginLogic;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class LoginActivity extends BasicActivity implements OnClickListener
{
    /**
     * 登录 注册按钮
     */
    private Button activity_login_btn, activity_login_register_btn;
    
    private ImageView activity_login_qq_login_btn, activity_login_weixin_login_btn;
    
    /**
     * 填写 用户名，密码
     */
    private EditText activity_login_name_edit, activity_login_password_edit;
    
    private final String TAG = LoginActivity.class.getSimpleName();
    
    private ILoginLogic logic;
    
    private TextView activity_login_cancel;
    
    private Tencent mTencent;
    
    private String mAppid;
    
    private IUiListener loginListener; // 授权登录监听器
    
    private IUiListener userInfoListener; // 获取用户信息监听器
    
    private String scope; // 获取信息的范围参数
    
    private UserInfo userInfo; // qq用户信息
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        
    }
    
    @Override
    public void setContentView()
    {
        setContentView(R.layout.activity_login);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_top_login);
        
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
            MyLogUtils.e(TAG, "ERR", e);
        }
        
    }
    
    @Override
    public void initListeners()
    {
        activity_login_btn.setOnClickListener(this);
        
        activity_login_register_btn.setOnClickListener(this);
        activity_login_cancel.setOnClickListener(this);
        
        activity_login_qq_login_btn.setOnClickListener(this);
        activity_login_weixin_login_btn.setOnClickListener(this);
    }
    
    @Override
    public void initData()
    {
//        QQInit();
    }
    
    @Override
    protected void initLogic()
    {
        logic = (ILoginLogic)getLogicByInterfaceClass(ILoginLogic.class);
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.activity_login_btn:
                String name = activity_login_name_edit.getText().toString();
                String password = activity_login_password_edit.getText().toString();
                if (CheckUtils.isUserNameCheck(name, this) && CheckUtils.isPasswordCheck(password, this))
                {
                    password = MD5.GetMD5Code(password);
                    logic.Login(name, password);
                }
                break;
            case R.id.activity_login_register_btn:
                Intent intent1 = new Intent();
                intent1.setAction(ConstantAction.REGISTER_ACTION);
                startActivity(intent1);
                finish();
                break;
            case R.id.activity_login_cancel:
                finish();
                break;
            case R.id.activity_login_qq_login_btn:
            //    QQLogin();
                break;
            case R.id.activity_login_weixin_login_btn:
                
                break;
            default:
                break;
        }
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
            super.onActivityResult(requestCode, resultCode, data);  
            mTencent.onActivityResult(requestCode, resultCode, data);
    }
    
    @Override
    protected void onDestroy()
    {
        if (mTencent != null)
        {
            // 注销登录
            mTencent.logout(this);
        }
        super.onDestroy();
    }
    
    private void QQInit()
    {
        // 这里的APP_ID请换成你应用申请的APP_ID，我这里使用的是DEMO中官方提供的测试APP_ID 222222
        mAppid = AppConstant.QQ_APP_ID;
        // 第一个参数就是上面所说的申请的APPID，第二个是全局的Context上下文，这句话实现了调用QQ登录
        mTencent = Tencent.createInstance(mAppid, getApplicationContext());
        //登录监听
        loginListener = new IUiListener()
        {
            
            @Override
            public void onError(UiError arg0)
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onComplete(Object arg0)
            {
                // TODO Auto-generated method stub
                if (arg0 == null)
                {  MyLogUtils.i(TAG, "-11111111111111111---------:");
                    return;
                }
                try
                {
                    JSONObject jo = (JSONObject)arg0;
                    int ret = jo.getInt("ret");
                    System.out.println("json=" + String.valueOf(jo));
                    String nickName = jo.getString("nickname");
                    String gender = jo.getString("gender");
                    MyLogUtils.i(TAG, "------------------------:"+nickName);
                    Toast.makeText(LoginActivity.this, "你好，" + nickName, Toast.LENGTH_LONG).show();
                    
                    
                }
                catch (Exception e)
                {
                    // TODO: handle exception
                }
                
            }
            
            @Override
            public void onCancel()
            {
                // TODO Auto-generated method stub
                
            }
        };
        
        userInfoListener = new IUiListener() {  
            
            @Override  
            public void onError(UiError arg0) {  
                // TODO Auto-generated method stub  
                  
            }  
             
            @Override  
            public void onComplete(Object arg0) {  
                // TODO Auto-generated method stub  
                if(arg0 == null){  
                    return;  
                }  
                try {  
                    JSONObject jo = (JSONObject) arg0;  
                    int ret = jo.getInt("ret");  
                    System.out.println("json=" + String.valueOf(jo));  
                    String nickName = jo.getString("nickname");  
                    String gender = jo.getString("gender");  
  
                    Toast.makeText(LoginActivity.this, "你好，" + nickName,  
                            Toast.LENGTH_LONG).show();  
  
                } catch (Exception e) {  
                    // TODO: handle exception  
                }  
                  
                  
            }  
              
            @Override  
            public void onCancel() {  
                // TODO Auto-generated method stub  
                  
            }  
        };  
    }
    
    private void QQLogin()
    {
        if (!mTencent.isSessionValid()) {  
            //开始qq授权登录  
            mTencent.login(LoginActivity.this, scope, loginListener);  
        }  
    }
    
   
    @Override
    protected void handleStateMessage(Message msg)
    {
        switch (msg.what)
        {
            case ConstantMessage.HTTP_ERR_MSG:
                showToast(msg.obj.toString(), Toast.LENGTH_LONG);
                break;
            case ConstantMessage.IAdv.LOGIN_ERR_MSG:
                showToast(getString(R.string.err_login), Toast.LENGTH_LONG);
                break;
            case ConstantMessage.IAdv.LOGIN_OK_MSG:
                showToast(getString(R.string.ok_login), Toast.LENGTH_LONG);
                finish();
            default:
                break;
        }
    }
}
