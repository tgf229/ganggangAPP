package com.ganggang.ganggangapp.ui.userinfo;

import com.ganggang.frame.freamwork.ui.BasicActivity;
import com.ganggang.frame.util.CheckUtils;
import com.ganggang.frame.util.MD5;
import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.constant.ConstantFile;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.modifylogic.IModifyLogic;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.ConsoleMessage;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ModifyActivity extends BasicActivity implements OnClickListener
{
    
    private EditText userinfo_xinmima, userinfo_jiumima;
    
    private TextView layout_top_modify_ok;
    
    private ImageView layout_top_modify_return;
    
    private IModifyLogic logic;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void setContentView()
    {
        setContentView(R.layout.activity_modify);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_top_xiugaimima);
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
        layout_top_modify_return.setOnClickListener(this);
        layout_top_modify_ok.setOnClickListener(this);
    }
    
    @Override
    public void initData()
    {
    
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.layout_top_modify_return:
                finish();
                break;
            case R.id.layout_top_modify_ok:
                String jiu = userinfo_jiumima.getText().toString().intern();
                String xin = userinfo_xinmima.getText().toString().intern();
                if (CheckUtils.isEmpty(jiu) && CheckUtils.isEmpty(xin))
                {
                    showToast(R.string.err_password_null, Toast.LENGTH_LONG);
                }
                else
                {
                    String oldPassword = (String)MyDataUtils.getData(this, ConstantFile.IUserFile.FileName, ConstantFile.IUserFile.LOGINPASSWORD, String.class);
                    String jiu_md5 = MD5.GetMD5Code(jiu);
                    if (jiu_md5.equals(oldPassword))
                    {
                        String xin_md5 = MD5.GetMD5Code(xin);
                        String loginname = (String)MyDataUtils.getData(this, ConstantFile.IUserFile.FileName, ConstantFile.IUserFile.LOGINNAME, String.class);
                        logic.modify(loginname, xin_md5);
                    }
                    else
                    {
                        showToast(R.string.err_passwrod_old_not, Toast.LENGTH_LONG);
                    }
                }
                
                break;
            default:
                break;
        }
        
    }
    
    @Override
    protected void handleStateMessage(Message msg)
    {
        switch (msg.what)
        {
            case ConstantMessage.IUserinfo.USERINFO_MODIFY_ERR_MSG:
                showToast(R.string.err_passwrod_old_not,Toast.LENGTH_LONG);
                break;
            case ConstantMessage.IUserinfo.USERINFO_MODIFY_OK_MSG:
                showToast(R.string.err_passwrod_old_ok,Toast.LENGTH_LONG);
                finish();
                break;
            default:
                break;
        }
    }
    
    @Override
    protected void initLogic()
    {
        logic = (IModifyLogic)getLogicByInterfaceClass(IModifyLogic.class);
    }
}
