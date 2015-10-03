package com.ganggang.ganggangapp.logic.advlogic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ganggang.frame.constant.Constanct_Db;
import com.ganggang.frame.freamwork.http.IHttpResponseListener;
import com.ganggang.frame.freamwork.http.RequestObject;
import com.ganggang.frame.freamwork.logic.BaseLogic;
import com.ganggang.frame.freamwork.ui.MCActivityManager;
import com.ganggang.frame.util.BitmapUtils;
import com.ganggang.frame.util.CheckUtils;
import com.ganggang.frame.util.ImageUtils;
import com.ganggang.frame.util.JsonUtil;
import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Adv;
import com.ganggang.ganggangapp.constant.ConstantAction;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.constant.ConstantFile;
import com.ganggang.ganggangapp.constant.ConstantHttp;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.advlogic.IAdvLogic;
import com.ganggang.ganggangapp.ui.login.AdvActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

/**
 * 
 * 广告逻辑
 * 
 * @author 曾宝
 * @version [V1.00, 2015年7月27日]
 * @see [相关类/方法]
 * @since V1.00
 */
public class AdvLogic extends BaseLogic implements IAdvLogic
{
    private Context mContext;
    
    private final String TAG = AdvLogic.class.getSimpleName();
    
    private final String ADV_HTTP = ConstantHttp.IP + ConstantHttp.ADV_HTTP;
    
    private int imgs[] = new int[]{R.drawable.adv_2,R.drawable.adv_1,R.drawable.adv_0};
    
    public AdvLogic(Context context)
    {
        super();
        this.mContext = context;
       
    }
    
    @Override
    public void getAdv()
    {
        int num = (Integer)MyDataUtils.getData(mContext, ConstantFile.IFileConfig.FileName, ConstantFile.IFileConfig.GUIDE_ADV_NUM, Integer.class);
        List<Adv> mlist_adv = new ArrayList<Adv>();
        for(int i = 0 ;i<num;i++)
        {
            String fileName =  ConstantFile.IAdv.FileName+ConstantEnum.IAdv.GUIDE_ADV_LOCATION+i;
           String name =   (String)MyDataUtils.getData(mContext, fileName, ConstantFile.IAdv.AdvName, String.class);
           String picture = (String)MyDataUtils.getData(mContext, fileName, ConstantFile.IAdv.AdvPicture, String.class);
           String url = (String)MyDataUtils.getData(mContext, fileName, ConstantFile.IAdv.AdvUrl, String.class);
           Adv adv = new Adv(null, name, picture, null, url, null);
           mlist_adv.add(adv);
        }
       boolean flag_fist =  (Boolean)MyDataUtils.getData(mContext, ConstantFile.IMore.FileName, ConstantFile.IMore.FIRST_APP,Boolean.class);
        List<Bitmap> mlist = new ArrayList<Bitmap>();
        if(mlist_adv.size()==0)
        {//获取默认广告
//            mlist = setDefaultImageView();
            mlist = ImageUtils.setDefaultImageView(mContext, imgs);
        }
        else
        {
            if(!flag_fist)
            {
                ImageUtils.setImageView(mlist_adv);
                mlist = ImageUtils.setDefaultImageView(mContext, imgs);
            }
            else
            {
                mlist = ImageUtils.setImageView(mlist_adv);
            }
        }
   
        sendMessage(ConstantMessage.IAdv.ADV_GET_MSG, mlist);
    }
    
    private List<ImageView> setImageView(List<Adv> list)
    {
        List<ImageView> mlist = new ArrayList<ImageView>();
        for (int i = 0; list != null && i < list.size(); i++)
        {
            final Adv adv = list.get(i);
            String path = BitmapUtils.getSDPath(ConstantEnum.IAdv.TYPE);
            Bitmap bitmap =  ImageUtils.showImage(path + "/" + adv.getAdvName());
          /*  Bitmap bitmap = BitmapFactory.decodeFile(path + "/" + adv.getAdvName());*/
            ImageView img = new ImageView(mContext);
            img.setImageBitmap(bitmap);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            img.setScaleType(ScaleType.FIT_XY);
            img.setLayoutParams(layoutParams);
            img.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(!CheckUtils.isEmpty(adv.getAdvUrl()))
                    {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(adv.getAdvUrl()));
                        intent = Intent.createChooser(intent, null);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                }
            });
            mlist.add(img);
        }
        return mlist.size() == 0 ? null : mlist;
    }
    
  
}
