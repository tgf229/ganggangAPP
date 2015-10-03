package com.ganggang.ganggangapp.ui.business.adapter;

import java.util.HashMap;
import java.util.List;

import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.ganggang.frame.freamwork.imgload.ImageLoader;
import com.ganggang.frame.freamwork.imgload.Imgloader_new;
import com.ganggang.frame.util.MapUtils;
import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.frame.util.Utils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Business;
import com.ganggang.ganggangapp.constant.ConstantAction;
import com.ganggang.ganggangapp.constant.ConstantBundle;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.constant.ConstantFile;
import com.ganggang.ganggangapp.constant.ConstantHttp;
import com.ganggang.ganggangapp.ui.business.BusinessDetailActivity;
import com.ganggang.ganggangapp.ui.fragment.HomeFragment;
import com.ganggang.ganggangapp.utils.ImageCallBack;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BusinessDetail_ExpandableListView_adapter extends BaseExpandableListAdapter
{
    private Context mContext;
    
    private HashMap<String, List<Business>> map;
    
    private boolean flag;

    private HashMap<String, Bitmap> bitmaps = new HashMap<String, Bitmap>();
    
    /**
     * <默认构造函数>
     */
    public BusinessDetail_ExpandableListView_adapter(Context mContext, HashMap<String, List<Business>> map)
    {
        super();
        this.mContext = mContext;
        this.map = map;
        flag = (Boolean)MyDataUtils.getData(mContext, ConstantFile.IMore.FileName, ConstantFile.IMore.MORE_TRANSLATION, Boolean.class);
    }
    
    @Override
    public int getGroupCount()
    {
        return map == null ? 0 : map.size();
    }
    
    @Override
    public int getChildrenCount(int groupPosition)
    {
        if (map == null || map.size() == 0)
            return 0;
        else
        {
            String key = map.keySet().toArray()[groupPosition].toString();
            return map.get(key) == null ? 0 : map.get(key).size();
        }
    }
    
    @Override
    public Object getGroup(int groupPosition)
    {
        if (map == null || map.size() == 0)
            return null;
        else
        {
            return map.keySet().toArray()[groupPosition];
        }
    }
    
    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        if (map == null || map.size() == 0)
            return null;
        else
        {
            if (getGroup(groupPosition) == null)
                return null;
            else
            {
                String key = getGroup(groupPosition).toString();
                if (map.get(key) == null || map.get(key).size() == 0)
                    return null;
                else
                {
                    return map.get(key).get(childPosition);
                }
            }
        }
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
        // TODO Auto-generated method stub
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
        GroupHead groupHead = null;
        
        convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_homebusinesstype, null);
        groupHead = new GroupHead();
        ViewUtils.initHolderView(groupHead, convertView);
        convertView.setTag(groupHead);
        
        ImageView img = (ImageView)convertView.findViewById(R.id.img_module_home_feilei_yule);
        if (isExpanded)
        {
            img.setImageResource(R.drawable.xiajiantou_icon);
        }
        else
        {
            img.setImageResource(R.drawable.youjiantou_icon);
        }
        String title = map.keySet().toArray()[groupPosition].toString();
        groupHead.layout_homebusinesstype_titleText.setText(title);
        return convertView;
    }
    
    @Override
    public int getGroupTypeCount()
    {
        // TODO Auto-generated method stub
        return map==null?0:map.size();
    }
    
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        ChildHolder childHolder = null;
        
        convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_homebusinessinfo, null);
        childHolder = new ChildHolder();
        ViewUtils.initHolderView(childHolder, convertView);
        convertView.setTag(childHolder);
        
        String value = map.keySet().toArray()[groupPosition].toString();
        List<Business> mlist = map.get(value);
        final Business business = mlist.get(childPosition);
        if (business.getPreviewPicture() != null)
        {
           /* if(business.getbusinesspicture_list()!=null &&business.getbusinesspicture_list().size()>=1 )
            {*/
            if(bitmaps.containsKey(business.getPreviewPicture()))
            {
                childHolder.layout_homebusiness_img.setImageBitmap(bitmaps.get(business.getPreviewPicture()));
            }
            else
            {
                Imgloader_new imgloader_new = new Imgloader_new();
                imgloader_new.loadDrawable(business.getPreviewPicture(), ConstantEnum.IFileType.TYPE_BUSINESS, new ImageCallBack(childHolder.layout_homebusiness_img, bitmaps));
                /*     ImageLoader imageLoader = new ImageLoader();
                imageLoader.execute(null, childHolder.layout_homebusiness_img, ConstantHttp.IP + business.getBusinessPicture_list().get(0),ConstantEnum.IFileType.TYPE_BUSINESS);
                 */
            }
        
//            }
        }
        
        childHolder.layout_homebusiness_img.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setAction(ConstantAction.BUSINESS_DETAIL_ACTION);
                intent.putExtra(ConstantBundle.BUSINESSDEATILID, business.getBusinessId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                ((BusinessDetailActivity)mContext).finish();
            }
        });
        childHolder.layout_homebusiness_name.setText(flag ?business.getBusinessBrandNameComplex()+business.getBusinessNameComplex() :business.getBusinessBrandName()+business.getBusinessName());
        childHolder.layout_homebusiness_location.setText(flag ? business.getBusinessAddressComplex() : business.getBusinessAddress());
        
        childHolder.layout_homebusiness_location_layout.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("点击地图", business.getBusinessBrandName()+"");
                Utils.intentMap(business, mContext);
            }
        });
        
        String juli = "未知";
        if (HomeFragment.MAPLOCATION != null)
        {
            LatLng startLng = new LatLng(HomeFragment.MAPLOCATION.getLatitude(), HomeFragment.MAPLOCATION.getLongitude());
            LatLng endLng = new LatLng(business.getBusinessLatitude(), business.getBusinessLongitude());
            float juli_lng = AMapUtils.calculateLineDistance(startLng, endLng);
            juli = Utils.meauseCompany(juli_lng);
        }
        childHolder.layout_homebusiness_location_juli.setText(juli);
        childHolder.layout_homebusiness_phone.setText(business.getBusinessPhone());
        childHolder.layout_homebusiness_phone.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Utils.sysPhone(business.getBusinessPhone(), mContext);
            }
        });
        
        return convertView;
    }
    
    class GroupHead
    {
        TextView layout_homebusinesstype_titleText;
    }
    
    class ChildHolder
    {
        // 店 图
        ImageView layout_homebusiness_img;
        
        // 店名
        TextView layout_homebusiness_name;
        
        // 位置
        TextView layout_homebusiness_location;
        
        // 距离
        TextView layout_homebusiness_location_juli;
        
        // 号码
        TextView layout_homebusiness_phone;
        
        // 位置
        LinearLayout layout_homebusiness_location_layout;
    }
}
