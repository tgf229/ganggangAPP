package com.ganggang.ganggangapp.logic.comment.impl;

import java.util.HashMap;
import java.util.List;

import com.ganggang.frame.freamwork.http.HttpTask;
import com.ganggang.frame.freamwork.http.IHttpResponseListener;
import com.ganggang.frame.freamwork.http.RequestObject;
import com.ganggang.frame.freamwork.logic.BaseLogic;
import com.ganggang.frame.util.JsonUtil;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Comment;
import com.ganggang.ganggangapp.constant.ConstantHttp;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.comment.ICommentLogic;

import android.content.Context;
import android.util.Log;

public class CommentLogic extends BaseLogic implements ICommentLogic
{
    
    private final String TAG = CommentLogic.class.getSimpleName();
    
    private Context mContext;
    
    private final String COMMENT_HTTP = ConstantHttp.IP + ConstantHttp.ADDCOMMENT;
    
    // 个人评论
    private final String comment_user_http = ConstantHttp.IP + ConstantHttp.COMMENT_USER;
    
    /**
     * <默认构造函数>
     */
    public CommentLogic(Context mContext)
    {
        super();
        this.mContext = mContext;
    }
    
    @Override
    public void sendCommnet(String comment, int businessId, int createUserId)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.IComment.COMMENTCONTENT, comment);
        map.put(ConstantHttp.IComment.BUSINESSID, businessId + "");
        map.put(ConstantHttp.IComment.CREATEUSERID, createUserId + "");
        map.put("isAndroid", "isAndroid");
        RequestObject requestObject = new RequestObject(mContext, COMMENT_HTTP, map);
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
                    sendEmptyMessage(ConstantMessage.IComment.COMMENT_OK_MSG);
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.IComment.COMMENT_ERR_MSG);
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
    public void getComment(int userid)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ConstantHttp.IComment.USERID, userid + "");
        RequestObject requestObject = new RequestObject(mContext, comment_user_http, map);
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
                Log.e(TAG, json);
                if (JsonUtil.isJsonOk(json))
                {
                   List<Comment> list =  JsonUtil.jsonToList(json, "data", Comment.class);
                    sendMessage(ConstantMessage.IComment.COMMENT_USER_OK_MSG,list);
                }
                else
                {
                    sendEmptyMessage(ConstantMessage.IComment.COMMENT_USER_ERR_MSG);
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
