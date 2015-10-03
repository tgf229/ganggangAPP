package com.ganggang.ganggangapp.ui.comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ganggang.frame.freamwork.ui.BasicActivity;
import com.ganggang.frame.util.CheckUtils;
import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Business;
import com.ganggang.ganggangapp.bean.Comment;
import com.ganggang.ganggangapp.constant.ConstantAction;
import com.ganggang.ganggangapp.constant.ConstantBundle;
import com.ganggang.ganggangapp.constant.ConstantFile;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.comment.ICommentLogic;
import com.ganggang.ganggangapp.ui.comment.adapter.Comment_ListView_adapter;
import com.tencent.mm.sdk.platformtools.Util;

/**
 * 
 * 评论页面
 * 
 * @author 曾宝
 * @version [V1.00, 2015年8月7日]
 * @see [相关类/方法]
 * @since V1.00
 */
public class CommentActivity extends BasicActivity implements OnClickListener
{
    private final String TAG = CommentActivity.class.getSimpleName();
    
    /**
     * 左侧返回
     */
    private ImageView layout_top_comment_return;
    
    /**
     * 商品 名, 评论数
     */
    private TextView layout_top_comment_business_name, activity_comment_title_num;
    
    /**
     * 编辑
     */
    private EditText comment_comment_editText;
    
    /**
     * 提交按钮
     */
    private Button activity_comment_sub;
    
    private LinearLayout activity_comment_sub_layout;
    /**
     * listview
     */
    private ListView activity_comment_listview;
    
    private List<Comment> mlist_Comment;
    
    private Comment_ListView_adapter adapter_comment;
    
    private Business mBusiness;
    
    private ICommentLogic logic;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.activity_comment_sub:
                
                Object id = (Integer)MyDataUtils.getData(this, ConstantFile.IUserFile.FileName, ConstantFile.IUserFile.USERID, Integer.class);
                
                if (id==null || id.toString().isEmpty())
                {
                    showToast(R.string.comment_show_not_sub, Toast.LENGTH_LONG);
                    return;
                }
                String comment = comment_comment_editText.getText().toString();
                if (CheckUtils.isEmpty(comment))
                {
                    showToast(R.string.comment_show_not_comment, Toast.LENGTH_LONG);
                    return;
                }
                if (mBusiness == null)
                {
                    showToast(R.string.comment_show_not_business, Toast.LENGTH_LONG);
                    return;
                }
                logic.sendCommnet(comment, mBusiness.getBusinessId(), Integer.parseInt(id.toString()));
                break;
            case R.id.layout_top_comment_return:
                /*
                 * Intent intent =new Intent ();
                 * intent.setAction(ConstantAction.BUSINESS_DETAIL_ACTION);
                 * startActivity(intent);
                 */
                finish();
                break;
                
            default:
                break;
        }
    }
    
    @Override
    protected void handleStateMessage(Message msg)
    {
        switch (msg.what)
        {
            case ConstantMessage.IComment.COMMENT_USER_OK_MSG:
                if (msg.obj != null)
                {
                    mlist_Comment.clear();
                    mlist_Comment.addAll((List<Comment>)msg.obj);
                    adapter_comment.notifyDataSetChanged();
                }
                break;
            case ConstantMessage.IComment.COMMENT_OK_MSG:
                String text = comment_comment_editText.getText().toString();
                Comment comment = new Comment();
                comment.setComment(text);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String date = format.format(new Date());
                comment.setCreateTime(date);
                String name = (String)MyDataUtils.getData(this, ConstantFile.IUserFile.FileName, ConstantFile.IUserFile.LOGINNAME, String.class);
                comment.setUser(new String[] {name});
                mlist_Comment.add(0,comment);
                comment_comment_editText.setText("");
                adapter_comment.notifyDataSetChanged();
                break;
            case ConstantMessage.IComment.COMMENT_ERR_MSG:
                
                break;
            default:
                break;
        }
    }
    
    @Override
    public void setContentView()
    {
        setContentView(R.layout.activity_comment);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_top_comment);
    }
    
    @Override
    public void initViews()
    {
        try
        {
            ViewUtils.initView(this);
        }
        catch (Exception e)
        {
            MyLogUtils.e(TAG, "init is err", e);
        }
    }
    
    @Override
    public void initListeners()
    {
        activity_comment_sub.setOnClickListener(this);
        // ---------------------------------------------------
        layout_top_comment_return.setOnClickListener(this);
        
    }
    
    @Override
    public void initData()
    {
        Object object = getIntent().getSerializableExtra(ConstantBundle.BUSINESS_COMMENT);
        String name = (String)MyDataUtils.getData(this, ConstantFile.IUserFile.FileName, ConstantFile.IUserFile.LOGINNAME, String.class);
        int userid = -1;
        Object user = MyDataUtils.getData(this, ConstantFile.IUserFile.FileName, ConstantFile.IUserFile.USERID, Integer.class);
        if (user != null&&!user.toString().isEmpty())
        {
            userid = Integer.parseInt(user.toString());
        }
        // int userid =
        if (object != null)
        {
            mBusiness = (Business)object;
            comment_comment_editText.setVisibility(View.VISIBLE);
            activity_comment_sub_layout.setVisibility(View.VISIBLE);
            layout_top_comment_business_name.setVisibility(View.VISIBLE);
            layout_top_comment_business_name.setText(mBusiness.getBusinessName());
            
        }
        else
        {
            activity_comment_sub_layout.setVisibility(View.GONE);
            comment_comment_editText.setVisibility(View.GONE);
            // layout_top_comment_business_name.setVisibility(View.GONE);
            layout_top_comment_business_name.setText(name);
        }
        Object object2 = getIntent().getSerializableExtra(ConstantBundle.BUSINESS_COMMENT_LIST);
        if (object2 != null)
        {
            mlist_Comment = (List<Comment>)object2;
        }
        else
        {
            mlist_Comment = new ArrayList<Comment>();
            if (userid > 0)
                logic.getComment(userid);
                
        }
        
        adapter_comment = new Comment_ListView_adapter(this, mlist_Comment);
        
        activity_comment_listview.setAdapter(adapter_comment);
        
        adapter_comment.notifyDataSetChanged();
    }
    
    @Override
    protected void initLogic()
    {
        logic = (ICommentLogic)getLogicByInterfaceClass(ICommentLogic.class);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            ViewUtils.HideSoftKeyboard(this);
        }
        return super.onTouchEvent(event);
    }
}
