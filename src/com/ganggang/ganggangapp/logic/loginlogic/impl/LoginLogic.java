package com.ganggang.ganggangapp.logic.loginlogic.impl;

import java.util.HashMap;

import com.ganggang.frame.freamwork.http.HttpTask;
import com.ganggang.frame.freamwork.http.IHttpResponseListener;
import com.ganggang.frame.freamwork.http.RequestObject;
import com.ganggang.frame.freamwork.logic.BaseLogic;
import com.ganggang.frame.freamwork.ui.MCActivityManager;
import com.ganggang.frame.util.JsonUtil;
import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.UserInfo;
import com.ganggang.ganggangapp.constant.ConstantFile;
import com.ganggang.ganggangapp.constant.ConstantHttp;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.loginlogic.ILoginLogic;

import android.content.Context;

public class LoginLogic extends BaseLogic implements ILoginLogic
{
    private Context mContext;
    
    private final String TAG=LoginLogic.class.getSimpleName();
    
    private final String login_http = ConstantHttp.IP+ConstantHttp.LOGIN;
    /** 
     * <默认构造函数>
     */
    public LoginLogic(Context mContext)
    {
        super();
        this.mContext = mContext;
    }



    @Override
    public void Login(String name, String password)
    {
         HashMap<String, String> map = new HashMap<String, String>();
         map.put(ConstantHttp.USERLOGINNAME, name);
         map.put(ConstantHttp.USERPASSWORD, password);
         RequestObject requestObject = new RequestObject(mContext, login_http, map);
         
         IHttpResponseListener listener = new IHttpResponseListener()
        {
            @Override
            public void onError(String errStr)
            {
                sendMessage(ConstantMessage.IAdv.LOGIN_ERR_MSG, errStr);
            }
            
            @Override
            public void doHttpResponse(String json)
            {
                if(JsonUtil.isJsonOk(json))
                {
                    UserInfo info =  JsonUtil.JsonToObject(json, UserInfo.class);
                    if(info!=null)
                    {
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put(ConstantFile.IUserFile.USERID, info.getUserId());
                        map.put(ConstantFile.IUserFile.LOGINNAME,info.getUserLoginname());
                        map.put(ConstantFile.IUserFile.LOGINPASSWORD, info.getUserPassword());
                        MyDataUtils.putData(mContext, ConstantFile.IUserFile.FileName, map);
                        sendMessage(ConstantMessage.IAdv.LOGIN_OK_MSG, info);
                    }
                }
                else
                {
                     sendEmptyMessage(ConstantMessage.IAdv.LOGIN_ERR_MSG);
                }
                
            }
        };
        
        try
        {
            new HttpTask().start(requestObject, HttpTask.REQUEST_TYPE_POST, listener, mContext);
        }
        catch (Exception e)
        {
           MyLogUtils.e(TAG, "http err", e);
           sendMessage(ConstantMessage.HTTP_ERR_MSG, mContext.getString(R.string.err_network_http));
        }
         
    }

}
