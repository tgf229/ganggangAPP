package com.ganggang.ganggangapp.logic.maplogic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ganggang.frame.freamwork.http.HttpTask;
import com.ganggang.frame.freamwork.http.IHttpResponseListener;
import com.ganggang.frame.freamwork.http.RequestObject;
import com.ganggang.frame.freamwork.logic.BaseLogic;
import com.ganggang.frame.freamwork.ui.MCActivityManager;
import com.ganggang.frame.util.JsonUtil;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Business;
import com.ganggang.ganggangapp.constant.ConstantAction;
import com.ganggang.ganggangapp.constant.ConstantHttp;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.maplogic.IMapLogic;

import android.content.Context;

public class MapLogic extends BaseLogic implements IMapLogic
{
    private Context mContext;
    
    private final String Scope_http = ConstantHttp.IP + ConstantHttp.GETBUSINESSBYSCOPE;
    
    private final String ByScopeAndBrand_http = ConstantHttp.IP + ConstantHttp.GETSUBBUSINESSBYSCOPEANDBRAND;
    
    private final String ByScopeAndType_http = ConstantHttp.IP + ConstantHttp.getBusinessByScopeAndType;
    
    private final String TAG = MapLogic.class.getSimpleName();
    
    public MapLogic(Context context)
    {
        super();
        this.mContext = context;
    }
    
    @Override
    public void getBusinessByScope(double userLongitude, double userLatitude, int startSize, int pageSize)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.USERLONGITUDE, userLongitude + "");
        map.put(ConstantHttp.USERLATITUDE, userLatitude + "");
        map.put(ConstantHttp.STARTSIZE, startSize + "");
        map.put(ConstantHttp.PAGESIZE, pageSize + "");
        
        RequestObject requestObject = new RequestObject(mContext, Scope_http, map);
        
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
                    List<Business> mlist = JsonUtil.JsonForList(json, Business.class);
                    if (mlist != null)
                    {
                        sendMessage(ConstantMessage.IMap.BUSINESSBYSCOPE_OK_MSG, mlist);
                    }
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.IMap.BUSINESSBYSCOPE_ERR_MSG);
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
    
    @Override
    public void getSubBusinessByScopeAndBrand(int businessBrandId, double userLongitude, double userLatitude, int startSize, int pageSize)
    {
        MyLogUtils.i(TAG, "businessBrandId:" + businessBrandId + "userLongitude :" + userLongitude + "userLatitude:" + userLatitude);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.BUSINESSBRANDID, businessBrandId + "");
        map.put(ConstantHttp.USERLONGITUDE, userLongitude + "");
        map.put(ConstantHttp.USERLATITUDE, userLatitude + "");
        map.put(ConstantHttp.STARTSIZE, startSize + "");
        map.put(ConstantHttp.PAGESIZE, pageSize + "");
        
        RequestObject requestObject = new RequestObject(mContext, ByScopeAndBrand_http, map);
        
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
                    List<Business> mlist = JsonUtil.JsonForList(json, Business.class);
                    if (mlist != null)
                    {
                        sendMessage(ConstantMessage.IMap.BUSINESSBYSCOPEANDBRAND_OK_MSG, mlist);
                    }
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.IMap.BUSINESSBYSCOPEANDBRAND_ERR_MSG);
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
    
    @Override
    public void getBusinessByScopeAndType(String businessTypeCode, double userLongitude, double userLatitude, int startSize, int pageSize)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.IGetBusinessType.BUSINESSTYPECODE, businessTypeCode);
        map.put(ConstantHttp.USERLONGITUDE, userLongitude + "");
        map.put(ConstantHttp.USERLATITUDE, userLatitude + "");
        map.put(ConstantHttp.STARTSIZE, startSize + "");
        map.put(ConstantHttp.PAGESIZE, pageSize + "");
        
        RequestObject requestObject = new RequestObject(mContext, ByScopeAndType_http, map);
        
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
                    List<Business> mlist = JsonUtil.JsonForList(json, Business.class);
                    if (mlist != null)
                    {
                        sendMessage(ConstantMessage.IMap.BUSINESSBYSCOPEANDTYPE_OK_MSG, mlist);
                    }
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.IMap.BUSINESSBYSCOPEANDTYPE_ERR_MSG);
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
