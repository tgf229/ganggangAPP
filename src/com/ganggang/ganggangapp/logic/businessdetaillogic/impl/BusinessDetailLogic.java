package com.ganggang.ganggangapp.logic.businessdetaillogic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ganggang.frame.freamwork.http.HttpTask;
import com.ganggang.frame.freamwork.http.IHttpResponseListener;
import com.ganggang.frame.freamwork.http.RequestObject;
import com.ganggang.frame.freamwork.logic.BaseLogic;
import com.ganggang.frame.util.JsonUtil;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.Utils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Business;
import com.ganggang.ganggangapp.bean.BusinessBrand;
import com.ganggang.ganggangapp.bean.Comment;
import com.ganggang.ganggangapp.bean.Commodity;
import com.ganggang.ganggangapp.bean.HotWord;
import com.ganggang.ganggangapp.constant.Constant;
import com.ganggang.ganggangapp.constant.ConstantHttp;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.businessdetaillogic.IBusinessDetailLogic;
import com.ganggang.ganggangapp.logic.businesslogic.IBusinessLogic;
import com.ganggang.ganggangapp.ui.fragment.HomeFragment;
import com.tencent.mm.sdk.platformtools.Log;

import android.content.Context;

/**
 * 
 * 获取商家详情
 * 
 * @author 曾宝
 * @version [V1.00, 2015年8月6日]
 * @see [相关类/方法]
 * @since V1.00
 */
public class BusinessDetailLogic extends BaseLogic implements IBusinessDetailLogic
{
    private Context mContext;
    
    private final String TAG = BusinessDetailLogic.class.getSimpleName();
    
    private final String DETAIL_HTTP = ConstantHttp.IP + ConstantHttp.BUSINESSDETAIL;
    
    /**
     * <默认构造函数>
     */
    public BusinessDetailLogic(Context mContext)
    {
        super();
        this.mContext = mContext;
    }
    
    @Override
    public void getBusinessDetail(int businessID)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        
        map.put(ConstantHttp.IBusinessDetail.BUSINESSID, businessID + "");
        
        RequestObject requestObject = new RequestObject(mContext, DETAIL_HTTP, map);
        
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
                     Log.i(TAG, json);
                    // 品牌信息
                    BusinessBrand businessBrand = JsonUtil.JsonToObject(json, BusinessBrand.class);
                    
                    // 进入店信息
                    Business business = JsonUtil.JsonToObject(json, Business.class);
                    business.setBusinessBrand(new String[] {businessBrand.getBusinessBrandName(), businessBrand.getBusinessBrandNameComplex()});
                    // 商品信息
                    List<Commodity> mlist_Commodity = JsonUtil.jsonToList(json, "commodity_list", Commodity.class);
                    // 评论
                    List<Comment> mlist_comment = JsonUtil.jsonToList(json, "comment_list", Comment.class);
                    
                    List<Business> mlist_sub = JsonUtil.jsonToList(json, "sub_list", Business.class);
                    Log.e(TAG,"所有的分店数量:"+mlist_sub.size());
                    List<HotWord> mlist_hotword = JsonUtil.jsonToList(json, "hotwrods_list", HotWord.class);
                    
                    List<Business> mlist_sub_fujin = new ArrayList<Business>();
                    if (mlist_sub != null)
                    {
                        for (int i = 0; i < mlist_sub.size(); i++)
                        {
                            Business business_sub = mlist_sub.get(i);
                            float meas = Utils.meauseLocation(business_sub.getBusinessLatitude(), business_sub.getBusinessLongitude(), HomeFragment.MAPLOCATION);
                            MyLogUtils.e(TAG, "MEAS:" + meas);
                            if (meas != -1 && meas <= Constant.NEARBYBUSINESS)
                            {
                                mlist_sub_fujin.add(business_sub);
                            }
                        }
                    }
                    
                    HashMap<String, Object> detail = new HashMap<String, Object>();
                    detail.put(Constant.IBusinessDetail.BUSINESS_DETAIL, business);
                    detail.put(Constant.IBusinessDetail.BUSINESS_BRAND, businessBrand);
                    detail.put(Constant.IBusinessDetail.MLIST_COMMODITY, mlist_Commodity);
                    detail.put(Constant.IBusinessDetail.MLIST_COMMENT, mlist_comment);
                    detail.put(Constant.IBusinessDetail.MLIST_SUB_ALL, mlist_sub);
                    detail.put(Constant.IBusinessDetail.MLIST_SUB_FUJIN, mlist_sub_fujin);
                    detail.put(Constant.IBusinessDetail.MLIST_HOTWORD, mlist_hotword);
                    sendMessage(ConstantMessage.IBusinessDetail.BUSINESSDETAIL_OK_MSG, detail);
                    
                    MyLogUtils.i(TAG, "---------------");
                    
                }
                
            }
        };
        
        try
        {
            new HttpTask().start(requestObject, HttpTask.REQUEST_TYPE_POST, listener, mContext);
        }
        catch (Exception e)
        {
            MyLogUtils.e(TAG, "getBusinessDetail is err", e);
            sendMessage(ConstantMessage.HTTP_ERR_MSG, mContext.getResources().getString(R.string.err_network_http));
        }
    }
    
}
