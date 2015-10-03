package com.ganggang.ganggangapp.ui.fragment.adapter;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;

import com.ganggang.frame.freamwork.imgload.ImageLoader;
import com.ganggang.frame.freamwork.imgload.Imgloader_new;
import com.ganggang.frame.util.BitmapUtils;
import com.ganggang.frame.util.ImageUtils;
import com.ganggang.frame.util.Utils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Celebrity;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.constant.ConstantHttp;
import com.ganggang.ganggangapp.utils.ImageCallBack;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MingrenMingBo_ExpandableList_adapter extends BaseExpandableListAdapter
{
    private List<Celebrity> mlist_Celebrity;
    
    private Context mContext;
    private static HashMap<String,Bitmap> bitmaps = new HashMap<String, Bitmap>();
    /**
     * <默认构造函数>
     */
    public MingrenMingBo_ExpandableList_adapter(List<Celebrity> mlist_Celebrity, Context mContext)
    {
        super();
        this.mlist_Celebrity = mlist_Celebrity;
        this.mContext = mContext;
    }
    
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer)
    {
        if (observer != null)
            super.unregisterDataSetObserver(observer);
    }
    
    @Override
    public int getGroupCount()
    {
        return mlist_Celebrity == null ? 0 : 1;
    }
    
    @Override
    public int getChildrenCount(int groupPosition)
    {
        return mlist_Celebrity == null ? 0 : mlist_Celebrity.size();
    }
    
    @Override
    public Object getGroup(int groupPosition)
    {
        return groupPosition;
    }
    
    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return mlist_Celebrity.get(childPosition);
    }
    
    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }
    
    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        // TODO Auto-generated method stub
        return childPosition;
    }
    
    @Override
    public boolean hasStableIds()
    {
        return true;
    }
    
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        // TODO Auto-generated method stub
        return true;
    }
    
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_mingrenmingbo_title_item, null);
        ImageView img = (ImageView)convertView.findViewById(R.id.img_module_home_feilei_mingrenmingbo);
        if (isExpanded)
        {
            img.setImageResource(R.drawable.xiajiantou_icon);
        }
        else
        {
            img.setImageResource(R.drawable.youjiantou_icon);
        }
        
        return convertView;
    }
    
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        Holder holder = null;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_mingrenmingbo_item, null);
            holder = new Holder();
            ViewUtils.initHolderView(holder, convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder)convertView.getTag();
        }
        
        final Celebrity celebrity = mlist_Celebrity.get(childPosition);
        convertView.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (celebrity.getCelebrityUrl() != null)
                {
                    Utils.intentUrl(celebrity.getCelebrityUrl(), mContext);
                }
            }
        });
        if(celebrity.getCelebrityPicture()!=null)
        {
            if(bitmaps.containsKey(celebrity.getCelebrityPicture()))
            {
                holder.module_home_feilei_mingrenmingbo_img.setImageBitmap(bitmaps.get(celebrity.getCelebrityPicture()));
            }
            else
            {
                Imgloader_new imgloader_new = new Imgloader_new();
                imgloader_new.loadDrawable(celebrity.getCelebrityPicture(), ConstantEnum.IFileType.TYPE_MINGRENMINGBO,new ImageCallBack(holder.module_home_feilei_mingrenmingbo_img,bitmaps));
            }
            
        }
        
        holder.module_home_feilei_mingrenmingbo_name.setText(celebrity.getCelebrityUserName()[0]);
        holder.module_home_feilei_mingrenmingbo_title.setText(celebrity.getCelebrityTitle());
        
        return convertView;
    }
    
    class Holder
    {
        ImageView module_home_feilei_mingrenmingbo_img;
        
        TextView module_home_feilei_mingrenmingbo_name, module_home_feilei_mingrenmingbo_title;
    }
    
}
