package com.ganggang.ganggangapp.logic.businesslogic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ganggang.frame.freamwork.http.HttpTask;
import com.ganggang.frame.freamwork.http.IHttpResponseListener;
import com.ganggang.frame.freamwork.http.RequestObject;
import com.ganggang.frame.freamwork.logic.BaseLogic;
import com.ganggang.frame.util.BitmapUtils;
import com.ganggang.frame.util.ImageUtils;
import com.ganggang.frame.util.JsonUtil;
import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.NetWorkUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Adv;
import com.ganggang.ganggangapp.bean.Business;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.constant.ConstantFile;
import com.ganggang.ganggangapp.constant.ConstantHttp;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.businesslogic.IBusinessLogic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract.FullNameStyle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;

/**
 * 
 * 逻辑层
 * 
 * @author 曾宝
 * @version [V1.00, 2015年7月31日]
 * @see [相关类/方法]
 * @since V1.00
 */
public class BusinessLogic extends BaseLogic implements IBusinessLogic
{
    private Context mContext;
    
    /**
     * 好评优先
     */
    private final String default_http = ConstantHttp.IP + ConstantHttp.BUSINESSBYDEFAULT;
    
    /**
     * 按分类搜索商家
     */
    private final String type_http = ConstantHttp.IP + ConstantHttp.GETBUSINESSBYTYPE_HTTP;
    
    /**
     * 好评搜索商家
     */
    private final String remark_http = ConstantHttp.IP + ConstantHttp.GETBUSINESSBYREMARK;
    
    /**
     * 按点击数 搜索
     */
    private final String count_http = ConstantHttp.IP + ConstantHttp.GETBUSINESSBYCOUNTS;
    
    /**
     * 按距离搜索商家
     */
    private final String distance_http = ConstantHttp.IP + ConstantHttp.GETBUSINESSBYDISTANCE;
    
    /**
     * 按照品牌ID查询商家
     */
    private final String brandbyID_http = ConstantHttp.IP+ConstantHttp.GETBUSINESSBYBRAND;
    /**
     * 根据热词获取商家信息
     */
    private final String hotword_http = ConstantHttp.IP+ConstantHttp.GETBUSINESSBYHOTWORDS;
    
     /**
      * 根据模糊查询
      */
    private final String getBusinessByBrandName_http = ConstantHttp.IP+ConstantHttp.GETBUSINESSBYBRANDNAME;
    /**
     * 按距离搜索商家 + 商家分类
     */
    private final String getBusinessByDistanceAndType_http = ConstantHttp.IP+ConstantHttp.GETBUSINESSBYDISTANCEANDTYPE;
    /**
     * 按点击数搜索商家（人气最旺排序）+商家分类
     */
    private final String getBusinessByCountsandType_http = ConstantHttp.IP+ConstantHttp.GETBUSINESSBYCOUNTSANDTYPE;
    /**
     * 按好评搜索商家 + 商家条件
     */
    private final String getBusinessByRemarkAndType_http = ConstantHttp.IP+ConstantHttp.GETBUSINESSBYREMARKANDTYPE;
    /**
     * 按默认搜索商家+商家分类
     */
    private final String getBusinessBydefaultandType_http=ConstantHttp.IP+ConstantHttp.GETBUSINESSBYDEFAULTANDTYPE;
    
    private final int top[] = new int[] {R.drawable.adv_top0, R.drawable.adv_top1, R.drawable.adv_top2, R.drawable.adv_top3, R.drawable.adv_top4};
    
    private final int bottom[] = new int[] {R.drawable.adv_bottom0, R.drawable.adv_bottom1, R.drawable.adv_bottom2, R.drawable.adv_bottom3};
    
    private final String TAG = BusinessLogic.class.getSimpleName();
    
    public BusinessLogic(Context context)
    {
        super();
        this.mContext = context;
    }
    /**
     * 广告
     * 重写方法
     * @see com.ganggang.ganggangapp.logic.businesslogic.getBusinessTopAdv()
     */
    @Override
    public void getBusinessTopAdv()
    {
        int num = (Integer)MyDataUtils.getData(mContext, ConstantFile.IFileConfig.FileName, ConstantFile.IFileConfig.HOMEBANNER_ADV_NUM, Integer.class);
        List<Adv> mlist_adv = new ArrayList<Adv>();
        for (int i = 0; i < num; i++)
        {
            String fileName = ConstantFile.IAdv.FileName + ConstantEnum.IAdv.HOMEBANNER_ADV_LOCATION + i;
            String name = (String)MyDataUtils.getData(mContext, fileName, ConstantFile.IAdv.AdvName, String.class);
            String picture = (String)MyDataUtils.getData(mContext, fileName, ConstantFile.IAdv.AdvPicture, String.class);
            String url = (String)MyDataUtils.getData(mContext, fileName, ConstantFile.IAdv.AdvUrl, String.class);
            Adv adv = new Adv(null, name, picture, null, url, null);
            mlist_adv.add(adv);
        }
        List<Bitmap> mlist = new ArrayList<Bitmap>();
        if (mlist_adv.size() == 0)
        {// 获取默认广告
            mlist = setDefaultImageView();
        }
        else
        {
            mlist = setImageView(mlist_adv);
        }
        sendMessage(ConstantMessage.IBusiness.BUSINESS_ADV_OK_MSG, new Object[]{mlist,mlist_adv});
    }
    
    private List<Bitmap> setImageView(List<Adv> list)
    {
        List<Bitmap> mlist = new ArrayList<Bitmap>();
        for (int i = 0; list != null && i < list.size(); i++)
        {
            final Adv adv = list.get(i);
            String path = BitmapUtils.getSDPath(ConstantEnum.IAdv.TYPE);
           Bitmap bitmap =  ImageUtils.showImage(path + "/" + adv.getAdvName());
//            Bitmap bitmap = BitmapFactory.decodeFile(path + "/" + adv.getAdvName());
            mlist.add(bitmap);
         
        }
        return mlist.size() == 0 ? null : mlist;
    }
    
    private List<Bitmap> setDefaultImageView()
    {
        List<Bitmap> mlist = new ArrayList<Bitmap>();
        for (int i = 0; i < top.length; i++)
        {
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), top[i]);
            mlist.add(bitmap);
        }
        
        return mlist.size() == 0 ? null : mlist;
    }
    
    /**
     * 默认
     * 重写方法
     * @param startSize
     * @param pageSize
     * @see com.ganggang.ganggangapp.logic.businesslogic.IBusinessLogic#getBusinessDefault(int, int)
     */
    @Override
    public void getBusinessDefault(int startSize, int pageSize)
    {
        if (!NetWorkUtils.checkNetwork(mContext))
        {// 无网络状态
            return;
        }
        MyLogUtils.i(TAG, "默认搜索：翻页：" + startSize + "  : " + pageSize);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.IBusinessList.STARTSIZE, startSize + "");
        map.put(ConstantHttp.IBusinessList.PAGESIZE, pageSize + "");
        RequestObject requestObject = new RequestObject(mContext, default_http, map);
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
                    List<Business> mlist_business = JsonUtil.JsonForList(json, Business.class);
                    sendMessage(ConstantMessage.IBusiness.BUSINESS_TYPE_DEFAULT_OK_MSG, mlist_business);
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.IBusiness.BUSINESS_TYPE_DEFAULT_ERR_MSG);
                }
            }
        };
        try
        {
            new HttpTask().start(requestObject, HttpTask.REQUEST_TYPE_POST, listener, mContext);
        }
        catch (Exception e)
        {
            MyLogUtils.e(TAG, "getBusinessDefault iserr", e);
            sendMessage(ConstantMessage.HTTP_ERR_MSG, mContext.getResources().getString(R.string.err_network_http));
        }
        
    }
    
    /**
     * 商家类型搜索
     * 重写方法
     * @param type
     * @param startsize
     * @param pagesize
     * @see com.ganggang.ganggangapp.logic.businesslogic.IBusinessLogic#getBusinessType(java.lang.String, int, int)
     */
    @Override
    public void getBusinessType(String type, int startsize, int pagesize)
    {
        MyLogUtils.i(TAG, "商家类型 搜索type:" + type + "  startsize:" + startsize + "  pagesize:" + pagesize);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.IGetBusinessType.BUSINESSTYPECODE, type);
        map.put(ConstantHttp.IGetBusinessType.STARTSIZE, startsize + "");
        map.put(ConstantHttp.IGetBusinessType.PAGESIZE, pagesize + "");
        
        RequestObject requestObject = new RequestObject(mContext, type_http, map);
        
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
                    MyLogUtils.d(TAG, "list:"+list.size());
                    sendMessage(ConstantMessage.IBusiness.BUSINESS_TYPE_OK_MSG, list);
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.IBusiness.BUSINESS_TYPE_ERR_MSG);
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
        }
        
    }
    
    /**
     * 好评搜索
     * 重写方法
     * @param startsize
     * @param pagesize
     * @see com.ganggang.ganggangapp.logic.businesslogic.IBusinessLogic#getBusinessByRemark(int, int)
     */
    @Override
    public void getBusinessByRemark(int startsize, int pagesize)
    {
        MyLogUtils.i(TAG,  "  startsize:" + startsize + "  pagesize:" + pagesize);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.STARTSIZE, startsize + "");
        map.put(ConstantHttp.PAGESIZE, pagesize + "");
        
        RequestObject requestObject = new RequestObject(mContext, remark_http, map);
        
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
                    sendMessage(ConstantMessage.IBusiness.BUSINESS_REMARK_OK_MSG, list);
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.IBusiness.BUSINESS_REMARK_ERR_MSG);
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
            sendEmptyMessage(ConstantMessage.IBusiness.BUSINESS_REMARK_ERR_MSG);
        }
        
    }
    
    /**
     * 人气
     * 重写方法
     * @param startsize
     * @param pagesize
     * @see com.ganggang.ganggangapp.logic.businesslogic.IBusinessLogic#getBusinessByCounts(int, int)
     */
    @Override
    public void getBusinessByCounts(int startsize, int pagesize)
    {
        MyLogUtils.i(TAG,  " 人气查询 startsize:" + startsize + "  pagesize:" + pagesize);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.STARTSIZE, startsize + "");
        map.put(ConstantHttp.PAGESIZE, pagesize + "");
        
        RequestObject requestObject = new RequestObject(mContext, count_http, map);
        
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
                    sendMessage(ConstantMessage.IBusiness.BUSINESS_COUNT_OK_MSG, list);
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.IBusiness.BUSINESS_COUNT_ERR_MSG);
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
            sendEmptyMessage(ConstantMessage.IBusiness.BUSINESS_COUNT_ERR_MSG);
        }
        
    }
    
   /**
    * 按距离搜索
    * 重写方法
    * @param businessLongitude
    * @param businessLatitude
    * @param startSize
    * @param pageSize
    * @see com.ganggang.ganggangapp.logic.businesslogic.IBusinessLogic#getBusinessByDistance(double, double, int, int)
    */
    @Override
    public void getBusinessByDistance(double businessLongitude, double businessLatitude, int startSize, int pageSize)
    {
        MyLogUtils.i(TAG,  " 按距离搜索： startsize:" + startSize + "  pagesize:" + pageSize);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.BUSINESSLONGITUDE, businessLongitude + "");
        map.put(ConstantHttp.BUSINESSLATITUDE, businessLatitude + "");
        map.put(ConstantHttp.STARTSIZE, startSize + "");
        map.put(ConstantHttp.PAGESIZE, pageSize + "");
        
        RequestObject requestObject = new RequestObject(mContext, distance_http, map);
        
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
                    sendMessage(ConstantMessage.IBusiness.BUSINESS_DISTANCE_OK_MSG,list);
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.IBusiness.BUSINESS_DISTANCE_ERR_MSG);
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
            sendEmptyMessage(ConstantMessage.IBusiness.BUSINESS_DISTANCE_ERR_MSG);
        }
        
    }
    
    /**
     * 按品牌ID
     * 重写方法
     * @param businessBrandId
     * @param startSize
     * @param pageSize
     * @see com.ganggang.ganggangapp.logic.businesslogic.IBusinessLogic#getBusinessByBrand(int, int, int)
     */
    @Override
    public void getBusinessByBrand(int businessBrandId, int startSize, int pageSize)
    {
        MyLogUtils.i(TAG,  " 品牌ID搜索"+businessBrandId+"startsize:" + startSize + "  pagesize:" + pageSize);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.BUSINESSBRANDID, businessBrandId + "");
        map.put(ConstantHttp.STARTSIZE, startSize + "");
        map.put(ConstantHttp.PAGESIZE, pageSize + "");
        
        RequestObject requestObject = new RequestObject(mContext, brandbyID_http, map);
        
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
                    sendMessage(ConstantMessage.IBusiness.BUSINESS_BRANDID_OK_MSG, list);
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.IBusiness.BUSINESS_BRANDID_ERR_MSG);
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
            sendEmptyMessage(ConstantMessage.IBusiness.BUSINESS_BRANDID_ERR_MSG);
        }
    }
    
    /**
     * 热词
     * 重写方法
     * @param hotWordsName
     * @param startSize
     * @param pageSize
     * @see com.ganggang.ganggangapp.logic.businesslogic.IBusinessLogic#getBusinessByHotWord(java.lang.String, int, int)
     */
    @Override
    public void getBusinessByHotWord(String hotWordsName, int startSize, int pageSize)
    {
        MyLogUtils.i(TAG,  " 热词搜索 startsize:" + startSize + "  pagesize:" + pageSize);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.HOTWORDSNAME, hotWordsName + "");
        map.put(ConstantHttp.STARTSIZE, startSize + "");
        map.put(ConstantHttp.PAGESIZE, pageSize + "");
        
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
                    sendMessage(ConstantMessage.IBusiness.BUSINESS_TYPE_HOTWORD_OK_MSG, list);
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.IBusiness.BUSINESS_TYPE_HOTWORD_ERR_MSG);
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
            sendEmptyMessage(ConstantMessage.IBusiness.BUSINESS_TYPE_HOTWORD_ERR_MSG);
        }
        
    }
    
    /**
     * 品牌名称查询
     * 重写方法
     * @param businessBrandName
     * @param startSize
     * @param pageSize
     * @see com.ganggang.ganggangapp.logic.businesslogic.IBusinessLogic#getBusinessByBrandName(java.lang.String, int, int)
     */
    @Override
    public void getBusinessByBrandName(String businessBrandName, int startSize, int pageSize)
    {
        MyLogUtils.i(TAG," 品牌名称查询:"+businessBrandName+"  startsize:" + startSize + "pagesize:" + pageSize);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.BUSINESSBRANDNAME, businessBrandName + "");
        map.put(ConstantHttp.STARTSIZE, startSize + "");
        map.put(ConstantHttp.PAGESIZE, pageSize + "");
        
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
                MyLogUtils.d(TAG, json);
                if (JsonUtil.isJsonOk(json))
                {
                    List<Business> list = JsonUtil.JsonForList(json, Business.class);
                    sendMessage(ConstantMessage.IBusiness.BUSINESS_BRANDNAME_OK_MSG, list);
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.IBusiness.BUSINESS_BRANDNAME_ERR_MSG);
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
            sendEmptyMessage(ConstantMessage.IBusiness.BUSINESS_BRANDNAME_ERR_MSG);
        }
    }
    /**
     * 按距离搜索商家 + 商家分类
     * 重写方法
     * @param businessTypeCode
     * @param businessLongitude
     * @param businessLatitude
     * @param startSize
     * @param pageSize
     * @see com.ganggang.ganggangapp.logic.businesslogic.IBusinessLogic#getBusinessByDistanceAndType(java.lang.String, java.lang.String, java.lang.String, int, int)
     */
    @Override
    public void getBusinessByDistanceAndType(String businessTypeCode, double businessLongitude, double businessLatitude, int startSize, int pageSize)
    {
        MyLogUtils.e(TAG," 距离搜索商家 + 商家分类查询:"+businessTypeCode+"  startsize:" + startSize + "pagesize:" + pageSize+" businessLongitude:"+businessLongitude+" businessLatitude:"+businessLatitude);
       MyLogUtils.e(TAG, "请求地址："+getBusinessByDistanceAndType_http);
        
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.IGetBusinessType.BUSINESSTYPECODE, businessTypeCode + "");
        map.put(ConstantHttp.BUSINESSLONGITUDE, businessLongitude+"");
        map.put(ConstantHttp.BUSINESSLATITUDE, businessLatitude+"");
        map.put(ConstantHttp.STARTSIZE, startSize + "");
        map.put(ConstantHttp.PAGESIZE, pageSize + "");
        
        RequestObject requestObject = new RequestObject(mContext, getBusinessByDistanceAndType_http, map);
        
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
                    MyLogUtils.i(TAG, "距离搜索商家 + 商家分类查询:"+list.size());
                    sendMessage(ConstantMessage.IBusiness.BUSINESS_DISTANCEANDTYPE_OK_MSG, list);
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.IBusiness.BUSINESS_DISTANCEANDTYPE_ERR_MSG);
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
            sendEmptyMessage(ConstantMessage.IBusiness.BUSINESS_DISTANCEANDTYPE_ERR_MSG);
        }
        
    }
    
    /**
     * 按点击数搜索商家（人气最旺排序）+商家分类
     * 重写方法
     * @param businessTypeCode
     * @param startSize
     * @param pageSize
     * @see com.ganggang.ganggangapp.logic.businesslogic.IBusinessLogic#getBusinessByCountsAndType(java.lang.String, int, int)
     */
    @Override
    public void getBusinessByCountsAndType(String businessTypeCode, int startSize, int pageSize)
    {
        MyLogUtils.i(TAG," Counts搜索商家 + 商家分类查询:"+businessTypeCode+"  startsize:" + startSize + "pagesize:" + pageSize);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.IGetBusinessType.BUSINESSTYPECODE, businessTypeCode + "");
        map.put(ConstantHttp.STARTSIZE, startSize + "");
        map.put(ConstantHttp.PAGESIZE, pageSize + "");
        
        RequestObject requestObject = new RequestObject(mContext, getBusinessByCountsandType_http, map);
        
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
                    MyLogUtils.i(TAG, "----------------------------");
                    List<Business> list = JsonUtil.JsonForList(json, Business.class);
                    sendMessage(ConstantMessage.IBusiness.BUSINESS_COUNTSANDTYPE_OK_MSG, list);
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.IBusiness.BUSINESS_COUNTSANDTYPE_ERR_MSG);
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
            sendEmptyMessage(ConstantMessage.IBusiness.BUSINESS_COUNTSANDTYPE_ERR_MSG);
        }
        
    }
    
    /**
     * 按好评搜索商家 + 商家条件
     * 重写方法
     * @param businessTypeCode
     * @param startSize
     * @param pageSize
     * @see com.ganggang.ganggangapp.logic.businesslogic.IBusinessLogic#getBusinessByRemarkAndType(java.lang.String, int, int)
     */
    @Override
    public void getBusinessByRemarkAndType(String businessTypeCode, int startSize, int pageSize)
    {
        MyLogUtils.i(TAG," 按好评搜索商家 + 商家条查询:"+businessTypeCode+"  startsize:" + startSize + "pagesize:" + pageSize);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.IGetBusinessType.BUSINESSTYPECODE, businessTypeCode + "");
        map.put(ConstantHttp.STARTSIZE, startSize + "");
        map.put(ConstantHttp.PAGESIZE, pageSize + "");
        
        RequestObject requestObject = new RequestObject(mContext, getBusinessByRemarkAndType_http, map);
        
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
                    sendMessage(ConstantMessage.IBusiness.BUSINESS_REMARKANDTYPE_OK_MSG, list);
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.IBusiness.BUSINESS_REMARKANDTYPE_ERR_MSG);
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
            sendEmptyMessage(ConstantMessage.IBusiness.BUSINESS_REMARKANDTYPE_ERR_MSG);
        }
    }
    
    /**
     * 按默认搜索商家+商家分类
     * 重写方法
     * @param businessTypeCode
     * @param startSize
     * @param pageSize
     * @see com.ganggang.ganggangapp.logic.businesslogic.IBusinessLogic#getBusinessByDefaultAndType(java.lang.String, int, int)
     */
    @Override
    public void getBusinessByDefaultAndType(String businessTypeCode, int startSize, int pageSize)
    {
        MyLogUtils.e(TAG," 按默认搜索商家+商家分类查询:"+businessTypeCode+"  startsize:" + startSize + "pagesize:" + pageSize+":"+getBusinessBydefaultandType_http);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.IGetBusinessType.BUSINESSTYPECODE, businessTypeCode + "");
        map.put(ConstantHttp.STARTSIZE, startSize + "");
        map.put(ConstantHttp.PAGESIZE, pageSize + "");
        
        RequestObject requestObject = new RequestObject(mContext, getBusinessBydefaultandType_http, map);
        
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
                    sendMessage(ConstantMessage.IBusiness.BUSINESS_DEFAULTANDTYPE_OK_MSG, list);
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.IBusiness.BUSINESS_DEFAULTANDTYPE_ERR_MSG);
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
            sendEmptyMessage(ConstantMessage.IBusiness.BUSINESS_DEFAULTANDTYPE_ERR_MSG);
        }
        
        
    }
    
}
