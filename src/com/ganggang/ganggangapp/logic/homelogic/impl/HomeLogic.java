package com.ganggang.ganggangapp.logic.homelogic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ganggang.frame.freamwork.http.HttpTask;
import com.ganggang.frame.freamwork.http.IHttpResponseListener;
import com.ganggang.frame.freamwork.http.RequestObject;
import com.ganggang.frame.freamwork.logic.BaseLogic;
import com.ganggang.frame.util.BitmapUtils;
import com.ganggang.frame.util.CheckUtils;
import com.ganggang.frame.util.ImageUtils;
import com.ganggang.frame.util.JsonUtil;
import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Adv;
import com.ganggang.ganggangapp.bean.Business;
import com.ganggang.ganggangapp.bean.Celebrity;
import com.ganggang.ganggangapp.constant.ConstantAction;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.constant.ConstantFile;
import com.ganggang.ganggangapp.constant.ConstantHttp;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.homelogic.IHomeLogic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class HomeLogic extends BaseLogic implements IHomeLogic
{
    private Context mContext;
    
    private final String TAG = HomeLogic.class.getSimpleName();
    
    private final String HomeBusinessIp = ConstantHttp.IP + ConstantHttp.GETBUSINESSBYTITLE;
    /**
     * 附近的商家信息
     */
    private final String Scope_http = ConstantHttp.IP + ConstantHttp.GETBUSINESSBYSCOPE;
    
    private final int top[] = new int[] {R.drawable.adv_top0, R.drawable.adv_top1, R.drawable.adv_top2, R.drawable.adv_top3, R.drawable.adv_top4};
    
    private final int bottom[] = new int[] {R.drawable.adv_bottom0, R.drawable.adv_bottom1, R.drawable.adv_bottom2, R.drawable.adv_bottom3};
    
    /**
     * 名人名博地址
     */
    private final String mingrenmingbo_http = ConstantHttp.IP + ConstantHttp.GETCELEBRITY;
    private  boolean flag_fist;
    
    public HomeLogic(Context context)
    {
        super();
        this.mContext = context;
        flag_fist =  (Boolean)MyDataUtils.getData(mContext, ConstantFile.IFileConfig.FileName, "fist_app",Boolean.class);
    }
    
    @Override
    public void getTopAdv()
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
//            mlist = setDefaultImageView();
            mlist = ImageUtils.setDefaultImageView(mContext, top);
        }
        else
        {
            if(!flag_fist)
            {
                mlist = ImageUtils.setImageView(mlist_adv);
            }
            else
            {
//            mlist = setImageView(mlist_adv);
                mlist = ImageUtils.setImageView(mlist_adv);
            }
        }
        sendMessage(ConstantMessage.IHome.HOME_TOP_OK_MSG, new Object[]{mlist,mlist_adv});
    }
    
    private List<Bitmap> setImageBottomView(List<Adv> list)
    {
        List<Bitmap> mlist = new ArrayList<Bitmap>();
        for (int i = 0; list != null && i < list.size(); i++)
        {
            final Adv adv = list.get(i);
            String path = BitmapUtils.getSDPath(ConstantEnum.IFileType.TYPE_ADV);
            Bitmap bitmap =  ImageUtils.showImage(path + "/" + adv.getAdvName());
        /*    Bitmap bitmap = BitmapFactory.decodeFile(path + "/" + adv.getAdvName());*/
         /*   ImageView img = new ImageView(mContext);
            img.setImageBitmap(bitmap);*/
             MyLogUtils.i(TAG, "底部广告获取的广告名称："+adv.getAdvName());
          /*  DisplayMetrics metrics = ViewUtils.getscreen(mContext);
            int width = metrics.widthPixels /2;
            MyLogUtils.i(TAG, "width:" + width);*/
        /*    MarginLayoutParams params = new MarginLayoutParams(width, bitmap.getHeight());
            img.setLayoutParams(params);
            img.setScaleType(ScaleType.FIT_XY);
            img.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (!CheckUtils.isEmpty(adv.getAdvUrl()))
                    {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(adv.getAdvUrl()));
                        intent = Intent.createChooser(intent, null);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                }
            });*/
            mlist.add(bitmap);
        }
        return mlist.size() == 0 ? null : mlist;
    }
    
   /* private List<Bitmap> setImageView(List<Adv> list)
    {
        List<Bitmap> mlist = new ArrayList<Bitmap>();
        for (int i = 0; list != null && i < list.size(); i++)
        {
            final Adv adv = list.get(i);
            String path = BitmapUtils.getSDPath(ConstantEnum.IFileType.TYPE_ADV);
            Bitmap bitmap = BitmapFactory.decodeFile(path + "/" + adv.getAdvName());
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
    }*/
    
    @Override
    public void getBottomAdv()
    {
        int num = (Integer)MyDataUtils.getData(mContext, ConstantFile.IFileConfig.FileName, ConstantFile.IFileConfig.HOMEBOTTOM_ADV_NUM, Integer.class);
        List<Adv> mlist_adv = new ArrayList<Adv>();
        for (int i = 0; i < num; i++)
        {
            String fileName = ConstantFile.IAdv.FileName + ConstantEnum.IAdv.HOMEBOTTOM_ADV_LOCATION + i;
            String name = (String)MyDataUtils.getData(mContext, fileName, ConstantFile.IAdv.AdvName, String.class);
            String picture = (String)MyDataUtils.getData(mContext, fileName, ConstantFile.IAdv.AdvPicture, String.class);
            String url = (String)MyDataUtils.getData(mContext, fileName, ConstantFile.IAdv.AdvUrl, String.class);
            Adv adv = new Adv(null, name, picture, null, url, null);
            mlist_adv.add(adv);
        }
        List<Bitmap> mlist = new ArrayList<Bitmap>();
        if (mlist_adv.size() == 0)
        {
            mlist =ImageUtils.setDefaultImageView(mContext, bottom);;
        }
        else
        {
            if(!flag_fist)
            {
                setImageBottomView(mlist_adv);
                mlist =  ImageUtils.setDefaultImageView(mContext, bottom);
            }
            else
            {
                mlist = setImageBottomView(mlist_adv);
            }
        }
        
        sendMessage(ConstantMessage.IHome.HOME_BOTTOM_ADV_OK_MSG, new Object[]{mlist,mlist_adv});
        
    }
    
    /*private List<ImageView> setDefaultImageBottom()
    {
        List<ImageView> list = new ArrayList<ImageView>();
        for (int i = 0; i < bottom.length; i++)
        {
            Options opts = new Options();
            ImageUtils.setDefaultImageView(mContext, imgs)
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), bottom[i], opts)
           
            ImageView img = new ImageView(mContext);
            img.setImageResource(bottom[i]);
            DisplayMetrics metrics = ViewUtils.getscreen(mContext);
            int width = metrics.widthPixels / 2;
            
            MarginLayoutParams params = new MarginLayoutParams(width, img.getHeight());
            img.setLayoutParams(params);
            img.setScaleType(ScaleType.FIT_XY);
            list.add(img);
        }
        return list;
    }*/
    
    @Override
    public void getHomeBusiness()
    {
        HashMap<String, String> map = new HashMap<String, String>();
        RequestObject requestObject = new RequestObject(mContext, HomeBusinessIp, map);
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
                if (JsonUtil.getJsonforResult(json))
                {
                    Log.i(TAG, json);
                    HashMap<String, List<Business>> map = JsonUtil.jsonForHomeBusinessTitle(json, Business.class);
                    if (map == null)
                    {
                        sendEmptyMessage(ConstantMessage.IHome.HOME_BUSINESS_ERR_MSG);
                    }
                    else
                    {
                        sendMessage(ConstantMessage.IHome.HOME_BUSINESS_OK_MSG, map);
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
            MyLogUtils.e(TAG, "HTTP ISERR", e);
            sendMessage(ConstantMessage.HTTP_ERR_MSG, mContext.getResources().getString(R.string.err_network_http));
        }
    }
    
    @Override
    public void getMingrenmingbo()
    {
        HashMap<String, String> map = new HashMap<String, String>();
        RequestObject requestObject = new RequestObject(mContext, mingrenmingbo_http, map);
        IHttpResponseListener listener = new IHttpResponseListener()
        {
            @Override
            public void onError(String errStr)
            {
                sendMessage(ConstantMessage.IHome.CELEBRITY_ERR_MSG, errStr);
            }
            
            @Override
            public void doHttpResponse(String json)
            {
                if (JsonUtil.isJsonOk(json))
                {
                    List<Celebrity> list = JsonUtil.JsonForList(json, Celebrity.class);
                    sendMessage(ConstantMessage.IHome.CELEBRITY_OK_MSG, list);
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.IHome.CELEBRITY_ERR_MSG);
                }
                
            }
        };
        
        try
        {
            new HttpTask().start(requestObject, HttpTask.REQUEST_TYPE_POST, listener, mContext);
        }
        catch (Exception e)
        {
            MyLogUtils.e(TAG, "getMingrenmingbo", e);
            sendMessage(ConstantMessage.HTTP_ERR_MSG, mContext.getString(R.string.err_network_http));
        }
        
    }

    @Override
    public void getMapBusiness(double userLongitude,double userLatitude,int startSize,int pageSize)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.USERLONGITUDE, userLongitude+"");
        map.put(ConstantHttp.USERLATITUDE, userLatitude+"");
        map.put(ConstantHttp.STARTSIZE, startSize+"");
        map.put(ConstantHttp.PAGESIZE, pageSize+"");
      //  MyLogUtils.i(TAG, "ip:"+Scope_http+"USERLONGITUDE:"+userLongitude+" USERLATITUDE:"+userLatitude+" STARTSIZE:"+startSize+" PAGESIZE:"+pageSize);
       /* http://120.24.100.32:8080/ganggang/getBusinessByScope.do?
            userLongitude=114.13863716&userLatitude=22.27736634&startSize=0&pageSize=100*/
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
              //  MyLogUtils.i(TAG, "home_map json:"+json);
                if(JsonUtil.isJsonOk(json))
                {
                    List<Business> mlist = JsonUtil.JsonForList(json, Business.class);
                    if(mlist!=null)
                    {
                        sendMessage(ConstantMessage.IHome.HOME_MAP_OK_MSG, mlist);
                    }
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.IHome.HOME_MAP_ERR_MSG);
                }
                
            }
        };
        
        try
        {
            new HttpTask().start(requestObject, HttpTask.REQUEST_TYPE_POST, listener, mContext);
        }
        catch (Exception e)
        {
            MyLogUtils.e(TAG, "getBusinessByScope is err",e);
            sendMessage(ConstantMessage.HTTP_ERR_MSG, mContext.getResources().getString(R.string.err_network_http));
        }
        
    }
    
}
