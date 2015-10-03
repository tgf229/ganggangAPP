package com.ganggang.ganggangapp.logic.sreachlogic.impl;

import java.util.HashMap;
import java.util.List;

import com.ganggang.frame.freamwork.http.HttpTask;
import com.ganggang.frame.freamwork.http.IHttpResponseListener;
import com.ganggang.frame.freamwork.http.RequestObject;
import com.ganggang.frame.freamwork.logic.BaseLogic;
import com.ganggang.frame.util.JsonUtil;
import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Business;
import com.ganggang.ganggangapp.bean.HotWord;
import com.ganggang.ganggangapp.constant.ConstantFile;
import com.ganggang.ganggangapp.constant.ConstantHttp;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.sreachlogic.ISreachLogic;

import android.content.Context;

public class SreachLogic extends BaseLogic implements ISreachLogic
{
    private Context mContext;
    
    private final String gethotword_http = ConstantHttp.IP + ConstantHttp.GETHOTWORDSLIST;
    
    /**
     * 根据热词获取商家信息
     */
    private final String hotword_http = ConstantHttp.IP+ConstantHttp.GETBUSINESSBYHOTWORDS;
    
    
    /**
     * 根据模糊查询
     */
   private final String getBusinessByBrandName_http = ConstantHttp.IP+ConstantHttp.GETBUSINESSBYBRANDNAME;
   
    private final String TAG = SreachLogic.class.getSimpleName();
    /**
     * <默认构造函数>
     */
    public SreachLogic(Context mContext)
    {
        super();
        this.mContext = mContext;
    }
    
    @Override
    public void getHotWord()
    {
        HashMap<String, String> map = new HashMap<String, String>();
        RequestObject requestObject = new RequestObject(mContext, gethotword_http, map);
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
                    List<HotWord> list = JsonUtil.JsonForList(json, HotWord.class);
                    if(list!=null)
                    {
                        for(int i = 0 ;i<list.size();i++)
                        {
                            HotWord hotWord = list.get(i);
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put(ConstantFile.IHotWord.hotWordsId, hotWord.getHotWordsId());
                            map.put(ConstantFile.IHotWord.hotWordsName, hotWord.getHotWordsName());
                            map.put(ConstantFile.IHotWord.hotWordsNameComplex, hotWord.getHotWordsNameComplex());
                            MyDataUtils.putData(mContext, ConstantFile.IHotWord.FileName+i, map);
                        }
                        HashMap<String, Object> map1 = new HashMap<String, Object>();
                        map1.put(ConstantFile.IFileConfig.HOTWORD_NUM, list.size());
                        MyDataUtils.putData(mContext, ConstantFile.IFileConfig.HOTWORD_NUM, map1);
                        sendMessage(ConstantMessage.ISreach.HOTWORD_OK_MSG, list);
                        return;
                    }
                    sendEmptyMessage(ConstantMessage.ISreach.HOTWORD_ERR_MSG);
                }
                
            }
        };
        
        try
        {
            new HttpTask().start(requestObject, HttpTask.REQUEST_TYPE_POST, listener, mContext);
        }
        catch (Exception e)
        {
             MyLogUtils.e(TAG, "err",e);
             sendMessage(ConstantMessage.HTTP_ERR_MSG, mContext.getString(R.string.err_network_http));
        }
        
    }

    @Override
    public void getBusinessForHotWorld(String hotworld, int startSize, int pageSize)
    {
        MyLogUtils.i(TAG,  " 热词搜索 startsize:" + startSize + "  pagesize:" + pageSize);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.HOTWORDSNAME, hotworld + "");
        map.put(ConstantHttp.STARTSIZE, startSize + "");
        map.put(ConstantHttp.PAGESIZE, pageSize + "");
        map.put("isAndroid", "isAndroid");
        
        RequestObject requestObject = new RequestObject(mContext, hotword_http, map);
        
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
                MyLogUtils.d(TAG, json);
                if (JsonUtil.isJsonOk(json))
                {
                    List<Business> list = JsonUtil.JsonForList(json, Business.class);
                    sendMessage(ConstantMessage.ISreach.HOTWORD_SREACH_OK_MSG, list);
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.ISreach.HOTWORD_SREACH_ERR_MSG);
                }
            }
        };
        try
        {
            new HttpTask().start(requestObject, HttpTask.REQUEST_TYPE_POST, listener, mContext);
        }
        catch (Exception e)
        {
            MyLogUtils.e(TAG, "TYPE IS ERR", e);
            sendEmptyMessage(ConstantMessage.HTTP_ERR_MSG);
        }
        
    }
    
    
    
    @Override
    public void getBusinessForSreach(String sreach, int startSize, int pageSize)
    {
        MyLogUtils.e(TAG," 品牌名称查询:"+sreach+"  startsize:" + startSize + "pagesize:" + pageSize+"  "+getBusinessByBrandName_http);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.BUSINESSBRANDNAME, sreach + "");
        map.put(ConstantHttp.STARTSIZE, startSize + "");
        map.put(ConstantHttp.PAGESIZE, pageSize + "");
        map.put("isAndroid", "isAndroid");
        RequestObject requestObject = new RequestObject(mContext, getBusinessByBrandName_http, map);
        
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
                MyLogUtils.e(TAG, json);
                if (JsonUtil.isJsonOk(json))
                {
                    List<Business> list = JsonUtil.JsonForList(json, Business.class);
                    sendMessage(ConstantMessage.ISreach.SREACH_OK_MSG, list);
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.ISreach.SREACH_ERR_MSG);
                }
            }
        };
        try
        {
            new HttpTask().start(requestObject, HttpTask.REQUEST_TYPE_POST, listener, mContext);
        }
        catch (Exception e)
        {
            MyLogUtils.e(TAG, "TYPE IS ERR", e);
            sendEmptyMessage(ConstantMessage.HTTP_ERR_MSG);
        }
        
    }

   

    
}
