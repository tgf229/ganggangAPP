package com.ganggang.ganggangapp.logic.modifylogic.impl;

import java.util.HashMap;
import java.util.List;

import com.ganggang.frame.freamwork.http.HttpTask;
import com.ganggang.frame.freamwork.http.IHttpResponseListener;
import com.ganggang.frame.freamwork.http.RequestObject;
import com.ganggang.frame.freamwork.logic.BaseLogic;
import com.ganggang.frame.freamwork.ui.MCActivityManager;
import com.ganggang.frame.util.JsonUtil;
import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Business;
import com.ganggang.ganggangapp.bean.UserInfo;
import com.ganggang.ganggangapp.constant.ConstantFile;
import com.ganggang.ganggangapp.constant.ConstantHttp;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.modifylogic.IModifyLogic;

import android.content.Context;

public class ModifyLogic extends BaseLogic implements IModifyLogic
{
    private Context mContext;
    
    private final String http = ConstantHttp.IP + ConstantHttp.MODIFYPASSWORD;
    
    private final String TAG = ModifyLogic.class.getSimpleName();
    
    /**
     * <默认构造函数>
     */
    public ModifyLogic(Context mContext)
    {
        super();
        this.mContext = mContext;
    }
    
    @Override
    public void modify(String loginname, final String passwrod)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        
        map.put(ConstantHttp.USERLOGINNAME, loginname);
        map.put(ConstantHttp.USERPASSWORD, passwrod);
        
        RequestObject requestObject = new RequestObject(mContext, http, map);
        
        IHttpResponseListener listener = new IHttpResponseListener()
        {
            @Override
            public void onError(String errStr)
            {
                sendMessage(ConstantMessage.HTTP_ERR_MSG, errStr);
            }
            
            @Override
            public void doHttpResponse(String json)
            {
                if (JsonUtil.isJsonOk(json))
                {
                    UserInfo info = JsonUtil.JsonToObject(json, UserInfo.class);
                    if (info != null)
                    {
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put(ConstantFile.IUserFile.LOGINPASSWORD, passwrod);
                        MyDataUtils.putData(mContext, ConstantFile.IUserFile.FileName, map);
                        sendEmptyMessage(ConstantMessage.IUserinfo.USERINFO_MODIFY_OK_MSG);
                    }
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.IUserinfo.USERINFO_MODIFY_ERR_MSG);
                }
                
            }
        };
        
        try
        {
            new HttpTask().start(requestObject, HttpTask.REQUEST_TYPE_POST, listener, mContext);
        }
        catch (Exception e)
        {
            MyLogUtils.e(TAG, "getBusinessByScope is err", e);
            sendMessage(ConstantMessage.HTTP_ERR_MSG, mContext.getResources().getString(R.string.err_network_http));
        }
        
    }
    
}
