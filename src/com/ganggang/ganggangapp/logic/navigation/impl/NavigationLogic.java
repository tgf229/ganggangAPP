package com.ganggang.ganggangapp.logic.navigation.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.ganggang.frame.freamwork.http.HttpTask;
import com.ganggang.frame.freamwork.http.IHttpResponseListener;
import com.ganggang.frame.freamwork.http.RequestObject;
import com.ganggang.frame.freamwork.logic.BaseLogic;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.StringUtils;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.navigation.INavigationLogic;

import android.content.Context;
import android.os.Message;
import android.webkit.ConsoleMessage;

public class NavigationLogic extends BaseLogic implements INavigationLogic
{
    private Context mContext;
    
    private final String TAG = NavigationLogic.class.getSimpleName();
    
    private final String map_http = "http://m.amap.com/navi/?";
    
    /**
     * <默认构造函数>
     */
    public NavigationLogic(Context mContext)
    {
        super();
        this.mContext = mContext;
    }
    
    @Override
    public void sendNavigation(final String start, final String end, final String destName)
    {
        
        new Thread()
        {
            public void run()
            {
                String uri = map_http + "start=" + start + "&" + "end=" + end + "&destName=" + destName + "&key=16d0416871ad4bb405d484906b76ceb4";
                InputStream is = null;
                try
                {
                    HttpGet httpRequest = new HttpGet(uri);
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpResponse response = (HttpResponse)httpclient.execute(httpRequest);
                    HttpEntity entity = response.getEntity();
                    BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(entity);
                    is = bufferedHttpEntity.getContent();
                    if (response.getStatusLine().getStatusCode() == 200)
                    {
                        String content = StringUtils.Inputstr2Str_Reader(is, null);
                        sendMessage(ConstantMessage.IMap.NAVIGATION_OK_MSG, content);
//                        MyLogUtils.i(TAG, content);
                    }
                }
                catch (ClientProtocolException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
            };
        }.start();
        ;
        
        /*
         * HashMap<String, String> map = new HashMap<String, String>();
         * map.put("start", start);
         * map.put("dest", end);
         * map.put("destName", destName);
         * map.put("key","16d0416871ad4bb405d484906b76ceb4");
         * RequestObject requestObject = new RequestObject(mContext, uri, map);
         * IHttpResponseListener listener = new IHttpResponseListener()
         * {
         * 
         * @Override
         * public void onError(String errStr)
         * {
         * 
         * }
         * 
         * @Override
         * public void doHttpResponse(String json)
         * {
         * MyLogUtils.i(TAG, json);
         * sendMessage(ConstantMessage.IMap.NAVIGATION_OK_MSG, json);
         * }
         * };
         * 
         * new HttpTask().start(requestObject, HttpTask.REQUEST_TYPE_POST, listener, mContext);
         */
        
    }
    
}
