package com.ganggang.ganggangapp.logic.mainlogic.impl;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.ganggang.frame.constant.Constanct_Db;
import com.ganggang.frame.freamwork.http.HttpTask;
import com.ganggang.frame.freamwork.http.IHttpResponseListener;
import com.ganggang.frame.freamwork.http.RequestObject;
import com.ganggang.frame.freamwork.logic.BaseLogic;
import com.ganggang.frame.util.BitmapUtils;
import com.ganggang.frame.util.JsonUtil;
import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Adv;
import com.ganggang.ganggangapp.bean.BusinessTitle;
import com.ganggang.ganggangapp.bean.Enumeration;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.constant.ConstantFile;
import com.ganggang.ganggangapp.constant.ConstantHttp;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.mainlogic.IMainLogic;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MainLogic extends BaseLogic implements IMainLogic
{
    private Context mContext;
    
    private final String TAG = MainLogic.class.getSimpleName();
    
    private final String ADV_HTTP = ConstantHttp.IP + ConstantHttp.ADV_HTTP;
    
    private final String ADV_ALL_HTTP = ConstantHttp.IP + ConstantHttp.ADV_ALL_HTTP;
    
    private final String BUSSINESSTYPE_HTTP = ConstantHttp.IP + ConstantHttp.BUSINESSTYPE_HTTP;
    
    private final String BUSINESSTITLE_HTTP = ConstantHttp.IP+ConstantHttp.GETTITLELIST;
    public MainLogic(Context context)
    {
        super();
        this.mContext = context;
        
    }
    
    @Override
    public void getAdv()
    {
        HashMap<String, String> map = new HashMap<String, String>();
        
        RequestObject requestObject = new RequestObject(mContext, ADV_ALL_HTTP, map);
        
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
                    List<Adv> list = JsonUtil.JsonForList(json, Adv.class);
                    
                    if (list == null)
                    {
                      sendMessage(ConstantMessage.HTTP_ERR_MSG, mContext.getResources().getString(R.string.err_network_null));
                    }
                   else
                    {
                        int g_adv_num = 0;
                        int top_adv_num = 0;
                        int bottom_adv_num = 0;
                        String fileName ="";
                        for (int i = 0; i < list.size(); i++)
                        {
                            Adv adv = list.get(i);
                            //下载到sd卡
                            BitmapUtils.downIMG_SaveSD(adv.getAdvPicture(), adv.getAdvName(), ConstantEnum.IFileType.TYPE_ADV);
                            if (adv.getAdvLocationCode().equals(ConstantEnum.IAdv.GUIDE_ADV_LOCATION))
                            {
                                fileName = ConstantFile.IAdv.FileName + ConstantEnum.IAdv.GUIDE_ADV_LOCATION+g_adv_num;
                                g_adv_num++;
                            }
                            else if (adv.getAdvLocationCode().equals(ConstantEnum.IAdv.HOMEBANNER_ADV_LOCATION))
                            {
                                fileName = ConstantFile.IAdv.FileName + ConstantEnum.IAdv.HOMEBANNER_ADV_LOCATION +top_adv_num;
                                top_adv_num++;
                            }
                            else if (adv.getAdvLocationCode().equals(ConstantEnum.IAdv.HOMEBOTTOM_ADV_LOCATION))
                            {
                                fileName = ConstantFile.IAdv.FileName + ConstantEnum.IAdv.HOMEBOTTOM_ADV_LOCATION + bottom_adv_num;
                                bottom_adv_num++;
                            }
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put(ConstantFile.IAdv.AdvID,adv.getAdvId());
                            map.put(ConstantFile.IAdv.AdvName,adv.getAdvName());
                            map.put(ConstantFile.IAdv.AdvLocationCode, adv.getAdvLocationCode());
                            map.put(ConstantFile.IAdv.AdvUrl, adv.getAdvUrl());
                            map.put(ConstantFile.IAdv.AdvPicture, adv.getAdvPicture());
                            map.put(ConstantFile.IAdv.AdvNumber, adv.getAdvNumber());
                            MyDataUtils.putData(mContext, fileName, map);
                        }
                        HashMap<String, Object> adv_num_map = new HashMap<String, Object>();
                        adv_num_map.put(ConstantFile.IFileConfig.GUIDE_ADV_NUM, g_adv_num);
                        adv_num_map.put(ConstantFile.IFileConfig.HOMEBANNER_ADV_NUM, top_adv_num);
                        adv_num_map.put(ConstantFile.IFileConfig.HOMEBOTTOM_ADV_NUM, bottom_adv_num);
                        MyDataUtils.putData(mContext, ConstantFile.IFileConfig.FileName,adv_num_map);
                        
                    }
                }
            }
            
        };
        
        try
        {
            new HttpTask().start(requestObject, HttpTask.REQUEST_TYPE_POST, listener, mContext);
        }
        catch (Exception e)
        {
            MyLogUtils.e(TAG, "httpTask", e);
            sendMessage(ConstantMessage.HTTP_ERR_MSG, mContext.getResources().getString(R.string.err_network_null));
        }
        
    }
    
    @Override
    public void getBusinessType()
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.IBusinessType.ENUMERATIONTYPEID, ConstantEnum.IHomeBusinessType.TYPE_MAN);
        RequestObject requestObject = new RequestObject(mContext, BUSSINESSTYPE_HTTP, map);
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
                    List<Enumeration> list = JsonUtil.jsonForEnumeration(json, Enumeration.class);
                    if (list != null)
                    {
                        for (int i = 0; i < list.size(); i++)
                        {
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put(ConstantFile.IBusinessType.CODE, list.get(i).getEnumerationCode());
                            map.put(ConstantFile.IBusinessType.NAME, list.get(i).getEnumerationName());
                            map.put(ConstantFile.IBusinessType.NAMECOMPLEX, list.get(i).getEnumerationNameComplex());
                            
                            MyDataUtils.putData(mContext, list.get(i).getEnumerationCode(), map);
                            
                        }
                    }
                }
            }
        };
        
        try
        {
            new HttpTask().start(requestObject, HttpTask.REQUEST_TYPE_POST, listener, mContext);
        }
        catch (Exception e)
        {
            sendMessage(ConstantMessage.HTTP_ERR_MSG, mContext.getResources().getString(R.string.err_network_http));
        }
    }

    @Override
    public void getHomeBusinessTilte()
    {
        HashMap<String, String> map = new HashMap<String, String>();
        RequestObject requestObject = new RequestObject(mContext, BUSINESSTITLE_HTTP, map);
        
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
                   List<BusinessTitle> list =   JsonUtil.JsonForList(json, BusinessTitle.class);
                   if(list!=null)
                   {
                       for(int i = 0 ;list!=null && i<list.size();i++)
                       {
                           HashMap<String, Object> data_map = new HashMap<String, Object>();
                           data_map.put(ConstantFile.IBusinessTitle.ID, list.get(i).getTitleId());
                           data_map.put(ConstantFile.IBusinessTitle.NAME, list.get(i).getTitleName());
                           data_map.put(ConstantFile.IBusinessTitle.NAMECOMPLEX, list.get(i).getTitleNameComplex());
                         MyDataUtils.putData(mContext, ConstantFile.IBusinessTitle.FileName+list.get(i).getTitleId(),data_map);
                       }
                       HashMap<String, Object> config_map = new HashMap<String, Object>();
                       config_map.put(ConstantFile.IFileConfig.BUSINESSTITLE_NUM, list.size());
                       MyDataUtils.putData(mContext, ConstantFile.IFileConfig.FileName, config_map);
                   }
                }
                else
                {
                }
                
            }
        };
        
        
        try
        {
            new HttpTask().start(requestObject, HttpTask.REQUEST_TYPE_POST, listener, mContext);
        }
        catch (Exception e)
        {
            sendMessage(ConstantMessage.HTTP_ERR_MSG, mContext.getResources().getString(R.string.err_network_http));
        }
    }
    
}
