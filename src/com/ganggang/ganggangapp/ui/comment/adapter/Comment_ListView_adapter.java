package com.ganggang.ganggangapp.ui.comment.adapter;

import java.util.List;

import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Comment_ListView_adapter extends BaseAdapter
{
    private Context mContext;
    
    private List<Comment> mlist_comment;
    
    /** 
     * <默认构造函数>
     */
    public Comment_ListView_adapter(Context mContext, List<Comment> mlist_comment)
    {
        super();
        this.mContext = mContext;
        this.mlist_comment = mlist_comment;
    }

    @Override
    public int getCount()
    {
        return mlist_comment==null?0:mlist_comment.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mlist_comment==null?null:mlist_comment.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Holder holder = null;
        if(convertView==null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_comment_content, null);
            holder = new Holder();
            ViewUtils.initHolderView(holder, convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder)convertView.getTag();
        }
        Comment comment = mlist_comment.get(position);
        if(comment.getUser()!=null && comment.getUser().length>0)
        {
            holder.layout_comment_content_username.setText(comment.getUser()[0]);
        }
        else
        {
//            holder.layout_comment_content_username.setText(comment.getCreateUserId());
        }
        holder.layout_comment_content_content.setText(comment.getComment());
        holder.layout_comment_content_time.setText(comment.getCreateTime());
        return convertView;
    } 
    
    class Holder
    {
        //评论人
        TextView layout_comment_content_username;
        //评论内容
        TextView layout_comment_content_content;
        //评论时间
        TextView layout_comment_content_time;
    }
    
}
