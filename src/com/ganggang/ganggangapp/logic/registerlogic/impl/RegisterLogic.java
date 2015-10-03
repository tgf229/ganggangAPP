package com.ganggang.ganggangapp.logic.registerlogic.impl;

import java.util.HashMap;

import com.ganggang.frame.freamwork.http.HttpTask;
import com.ganggang.frame.freamwork.http.IHttpResponseListener;
import com.ganggang.frame.freamwork.http.RequestObject;
import com.ganggang.frame.freamwork.logic.BaseLogic;
import com.ganggang.frame.util.JsonUtil;
import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.UserInfo;
import com.ganggang.ganggangapp.constant.ConstantFile;
import com.ganggang.ganggangapp.constant.ConstantHttp;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.registerlogic.IRegisterLogic;

import android.content.Context;

public class RegisterLogic extends BaseLogic implements IRegisterLogic
{
    private final String TAG=RegisterLogic.class.getSimpleName();
    
    private Context mContext;
    
    private final String register_http = ConstantHttp.IP+ConstantHttp.REGISTE;
    
    /** 
     * <默认构造函数>
     */
    public RegisterLogic(Context mContext)
    {
        super();
        this.mContext = mContext;
    }


    @Override
    public void Register(String userLoginname, String userPassword)
    {
           HashMap<String, String> map = new HashMap<String, String>();
           map.put(ConstantHttp.USERLOGINNAME, userLoginname);
           map.put(ConstantHttp.USERPASSWORD, userPassword);
           RequestObject requestObject = new RequestObject(mContext, register_http, map);
           
           IHttpResponseListener listener = new IHttpResponseListener()
        {
            @Override
            public void onError(String errStr)
            {
                sendMessage(ConstantMessage.IAdv.REGISTER_ERR_MSG, errStr);
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
                        MyDataUtils.putData(mContext, ConstantFile.IUserFile.FileName, map);
                        sendMessage(ConstantMessage.IAdv.REGISTER_OK_MSG, info);
                    }
                }
                else
                    sendEmptyMessage(ConstantMessage.IAdv.REGISTER_ERR_MSG);
                
            }
        };
        
        try
        {
            new HttpTask().start(requestObject, HttpTask.REQUEST_TYPE_POST, listener, mContext);
        }
        catch (Exception e)
        {
           MyLogUtils.e(TAG, "ERR",e);
           sendMessage(ConstantMessage.HTTP_ERR_MSG, mContext.getString(R.string.err_network_http));
        }
        
    }

}
