package com.ganggang.ganggangapp.logic.userinfologic.impl;

import java.util.HashMap;

import com.ganggang.frame.freamwork.http.HttpTask;
import com.ganggang.frame.freamwork.http.IHttpResponseListener;
import com.ganggang.frame.freamwork.http.RequestObject;
import com.ganggang.frame.freamwork.logic.BaseLogic;
import com.ganggang.frame.util.JsonUtil;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.UserInfo;
import com.ganggang.ganggangapp.constant.Constant;
import com.ganggang.ganggangapp.constant.ConstantHttp;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.userinfologic.IUserInfoLogic;

import android.content.Context;

public class UserInfoLogic extends BaseLogic implements IUserInfoLogic 
{
    private Context mContext;
    
    private final String TAG= UserInfoLogic.class.getSimpleName();
    
    private final String GETUSERINFO_HTTP = ConstantHttp.IP+ConstantHttp.GETUSERINFO;
    
    private final String ADDUSERINFO = ConstantHttp.IP+ConstantHttp.ADDUSERINFO;
    /** 
     * <默认构造函数>
     */
    public UserInfoLogic(Context mContext)
    {
        super();
        this.mContext = mContext;
    }

    @Override
    public void getUserInfo(int userid)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.IComment.USERID, userid+"");
        RequestObject object = new RequestObject(mContext, GETUSERINFO_HTTP, map);
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
                if(JsonUtil.isJsonOk(json))
                {
                    UserInfo info =  JsonUtil.JsonToObject(json, UserInfo.class);
                    sendMessage(ConstantMessage.IUserinfo.USERINFO_OK_MSG, info);
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.IUserinfo.USERINFO_ERR_MSG);
                }
            }
        };
        
        try
        {
            new HttpTask().start(object, HttpTask.REQUEST_TYPE_POST, listener, mContext);
        }
        catch (Exception e)
        {
            MyLogUtils.e(TAG, "err",e);
            sendMessage(ConstantMessage.HTTP_ERR_MSG, mContext.getString(R.string.err_network_http));
        }
    }

    @Override
    public void setUserInfo(UserInfo info)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.IComment.USERID, info.getUserId()+"");
        map.put(ConstantHttp.USERNAME, info.getUserName());
        map.put(ConstantHttp.USERGENDER, info.getUserGender());
        map.put(ConstantHttp.EMAIL, info.getEmail());
        map.put(ConstantHttp.MOBILE, info.getMobile());
        map.put("isAndroid", "isAndroid");
        
        RequestObject object = new RequestObject(mContext, ADDUSERINFO, map);
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
                if(JsonUtil.isJsonOk(json))
                {
                    UserInfo info =  JsonUtil.JsonToObject(json, UserInfo.class);
                    sendMessage(ConstantMessage.IUserinfo.USERINFO_ADD_OK_MSG, info);
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.IUserinfo.USERINFO_ADD_ERR_MSG);
                }
            }
        };
        
        try
        {
            new HttpTask().start(object, HttpTask.REQUEST_TYPE_POST, listener, mContext);
        }
        catch (Exception e)
        {
            MyLogUtils.e(TAG, "err",e);
            sendMessage(ConstantMessage.HTTP_ERR_MSG, mContext.getString(R.string.err_network_http));
        }
        
    }

}
