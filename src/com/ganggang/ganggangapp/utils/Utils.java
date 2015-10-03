package com.ganggang.ganggangapp.utils;

import java.util.HashMap;

import com.ganggang.frame.util.CheckUtils;
import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.ganggangapp.constant.ConstantAction;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.constant.ConstantFile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Utils
{   
    /*public static void setBackInfo(Context context,String tag,String back_args,Integer page,String [] keys,Object[] params)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(tag, back_args);
        if(page!=null)
        {
            map.put(ConstantEnum.IBack.BACK_PAGE, page);
        }
        if(keys!=null && params!=null && keys.length==params.length)
        {
            for(int i = 0;i<keys.length;i++)
            {
                map.put(keys[i],params[i]);
            }
        }
        MyDataUtils.putData(context, ConstantEnum.IBack.BACK, map);
    } 
    
    public static Intent getBackInfo(Context context,String tag)
    {
        String args = (String)MyDataUtils.getData(context, ConstantEnum.IBack.BACK, tag, String.class);
        if(arg)
        
    }*/
    
    public static boolean getTranslation(Context mContext)
    {
        return  (Boolean)MyDataUtils.getData(mContext, ConstantFile.IMore.FileName,ConstantFile.IMore.MORE_TRANSLATION, boolean.class);
    }
    
/*    public static Intent getBackActivity(String back)
    {
        if(CheckUtils.isEmpty(back))
        {
            return null;
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        if(back.equals(ConstantEnum.IBack.BACK_HOME))
        {
            intent.setAction(ConstantAction.HOME_ACTION);
        }
        else if(back.equals(ConstantEnum.IBack.BACK_BUSINESS))
        {
            intent.putExtras(bundle);
            intent.setAction(ConstantAction.HOME_ACTION);
        }
        return null;
    }*/
}
