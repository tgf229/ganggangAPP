package com.ganggang.ganggangapp.logic.businessbrandlogic.impl;

import java.util.HashMap;
import java.util.List;

import com.ganggang.frame.freamwork.http.HttpTask;
import com.ganggang.frame.freamwork.http.IHttpResponseListener;
import com.ganggang.frame.freamwork.http.RequestObject;
import com.ganggang.frame.freamwork.logic.BaseLogic;
import com.ganggang.frame.util.BitmapUtils;
import com.ganggang.frame.util.ImageUtils;
import com.ganggang.frame.util.JsonUtil;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.BusinessBrand;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.constant.ConstantFile;
import com.ganggang.ganggangapp.constant.ConstantHttp;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.businessbrandlogic.IBusinessBrandLogic;

import android.content.Context;

public class BusinessBrandLogic extends BaseLogic implements IBusinessBrandLogic 
{
    private Context mContext;
    
    private final String brand_http =ConstantHttp.IP+ConstantHttp.BRANDBYTYPE;
    /** 
     * <默认构造函数>
     */
    public BusinessBrandLogic(Context mContext)
    {
        super();
        this.mContext = mContext;
    }


    @Override
    public void getBusinessBrand(String businessBrandTypeCode, int startSize, int pageSize)
    {
         HashMap<String, String> map = new HashMap<String, String>();
         map.put(ConstantHttp.IBrandByType.BUSINESSBRANDTYPECODE, businessBrandTypeCode);
         map.put(ConstantHttp.IBrandByType.STARTSIZE,startSize+"");
         map.put(ConstantHttp.IBrandByType.PAGESIZE, pageSize+"");
         RequestObject requestObject = new RequestObject(mContext, brand_http, map);
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
                     List<BusinessBrand> list =  JsonUtil.JsonForList(json, BusinessBrand.class);
                     if(list!=null)
                     {/*
                         for(int i = 0 ;i<list.size();i++)
                         {
                             String name = list.get(i).getBusinessBrandPicture().substring(list.get(i).getBusinessBrandPicture().lastIndexOf("/") + 1);
                             BitmapUtils.downIMG_SaveSD(list.get(i).getBusinessBrandPicture(), name, ConstantEnum.IFileType.TYPE_BUSINESS);
                         }*/
                         sendMessage(ConstantMessage.IBusinessBrand.BRAND_OK_MSG, list);
                     }
                  }
                  else
                      sendEmptyMessage(ConstantMessage.IBusinessBrand.BRAND_ERR_MSG);
                
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
