package com.ganggang.ganggangapp.bean;

import java.io.Serializable;

public class Comment implements Serializable
{
     private Integer commentId;
     
     private String comment;
     
     private String[] user;
     
     private String createTime;
     
     private String createUserId;
     

    public String getCreateUserId()
    {
        return createUserId;
    }

    public void setCreateUserId(String createUserId)
    {
        this.createUserId = createUserId;
    }

    public Integer getCommentId()
    {
        return commentId;
    }

    public void setCommentId(Integer commentId)
    {
        this.commentId = commentId;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String[] getUser()
    {
        return user;
    }

    public void setUser(String[] user)
    {
        this.user = user;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
     
     
}
