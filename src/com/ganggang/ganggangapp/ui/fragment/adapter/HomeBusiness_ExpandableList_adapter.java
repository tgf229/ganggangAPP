package com.ganggang.ganggangapp.ui.fragment.adapter;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;

import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.ganggang.frame.common.MyTextView;
import com.ganggang.frame.freamwork.imgload.Imgloader_new;
import com.ganggang.frame.freamwork.imgload.Imgloader_new.ImageCallback;
import com.ganggang.frame.util.BitmapUtils;
import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.Utils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Business;
import com.ganggang.ganggangapp.constant.ConstantAction;
import com.ganggang.ganggangapp.constant.ConstantBundle;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.constant.ConstantFile;
import com.ganggang.ganggangapp.ui.fragment.HomeFragment;
import com.ganggang.ganggangapp.ui.module.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.text.BoringLayout.Metrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeBusiness_ExpandableList_adapter extends BaseExpandableListAdapter
{
    private HashMap<String, List<Business>> map;
    
    private Context mContext;
    
    public static HashMap<String,Bitmap> bitmaps = new HashMap<String, Bitmap>();
    private boolean flag;
    // 准备加载图片的异步任务
    
    private final String TAG = HomeBusiness_ExpandableList_adapter.class.getSimpleName();
    
    /**
     * <默认构造函数>
     */
    public HomeBusiness_ExpandableList_adapter(HashMap<String, List<Business>> map, Context mContext)
    {
        super();
        this.map = map;
        this.mContext = mContext;
        flag = (Boolean)MyDataUtils.getData(mContext, ConstantFile.IMore.FileName, ConstantFile.IMore.MORE_TRANSLATION, Boolean.class);
    }
    
    /**
     * 头部数量
     * 重写方法
     * 
     * @return
     * @see android.widget.ExpandableListAdapter#getGroupCount()
     */
    @Override
    public int getGroupCount()
    {
        if (map.size() == 0)
            return 0;
            
        // MyLogUtils.i(TAG, "getGroupCount:" + map.keySet().size());
        return map.size();
    }
    
    
    
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer)
    {
        if (observer != null)
            super.unregisterDataSetObserver(observer);
    }
    
    @Override
    public int getChildrenCount(int groupPosition)
    {
        if (map.size() == 0)
            return 0;
            
        Object key = map.keySet().toArray()[groupPosition];
        int length = ((List<Business>)map.get(key)).size();
        // MyLogUtils.i(TAG, "getChildrenCount:" + length);
        return length;
    }
    
    @Override
    public Object getGroup(int groupPosition)
    {
        if (map.size() == 0)
            return null;
            
        return map.keySet().toArray()[groupPosition];
    }
    
    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        if (map.size() == 0)
            return null;
            
        Object key = map.keySet().toArray()[groupPosition];
        return ((List<Business>)map.get(key)).get(childPosition);
        
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
    public int getGroupTypeCount()
    {
        return map.size();
    }
    
    @Override
    public boolean hasStableIds()
    {
        // TODO Auto-generated method stub
        return true;
    }
    
    /**
     * 标题
     * 
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     * @see [类、类#方法、类#成员]
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        TopHolder topHolder = null;
        convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_homebusinesstype, null);
        topHolder = new TopHolder();
        ViewUtils.initHolderView(topHolder, convertView);
        convertView.setTag(topHolder);
        
        String title_type = map.keySet().toArray()[groupPosition].toString();
        String title = getTile(title_type, flag);
        if (isExpanded)
        {
            topHolder.img_module_home_feilei_yule.setImageResource(R.drawable.xiajiantou_icon);
        }
        else
        {
            topHolder.img_module_home_feilei_yule.setImageResource(R.drawable.youjiantou_icon);
        }
        topHolder.layout_homebusinesstype_titleText.setText(title);
        
        return convertView;
    }
    
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        ChildHolder childHolder = new ChildHolder();
        
        convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_homebusinessinfo, null);
      ViewUtils.initHolderView(childHolder, convertView);

    /*    // 店 图
        childHolder.layout_homebusiness_img =  (ImageView)convertView.findViewById(R.id.layout_homebusiness_img);
        // 店名
        childHolder.layout_homebusiness_name =  (MyTextView)convertView.findViewById(R.id.layout_homebusiness_name);
        // 位置
        childHolder.layout_homebusiness_location =  (MyTextView)convertView.findViewById(R.id.layout_homebusiness_location);
        // 距离
        childHolder.layout_homebusiness_location_juli =  (MyTextView)convertView.findViewById(R.id.layout_homebusiness_location_juli);
        // 号码
        childHolder.layout_homebusiness_phone =  (MyTextView)convertView.findViewById(R.id.layout_homebusiness_phone);
        // 位置
        childHolder.layout_homebusiness_location_layout =  (LinearLayout)convertView.findViewById(R.id.layout_homebusiness_location_layout);*/
       
        convertView.setTag(childHolder);
        
        String value = map.keySet().toArray()[groupPosition].toString();
        List<Business> mlist = map.get(value);
        final Business business = mlist.get(childPosition);
        
        if (business.getPreviewPicture() != null)
        {
            if(bitmaps.containsKey(business.getPreviewPicture()))
            {
                childHolder.layout_homebusiness_img.setImageBitmap(bitmaps.get(business.getPreviewPicture()));
            }
            else
            {
                Imgloader_new imgloader_new = new Imgloader_new();
                imgloader_new.loadDrawable(business.getPreviewPicture(), ConstantEnum.IFileType.TYPE_BUSINESS,new MyCallBack(childHolder.layout_homebusiness_img));
                
            }
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
             /*   ((HomeActivity)mContext).finish();*/
            }
        });
        childHolder.layout_homebusiness_name
            .setText(flag ? business.getBusinessBrandNameComplex() + "" + business.getBusinessNameComplex() : business.getBusinessBrandName() + "" + business.getBusinessName());
        childHolder.layout_homebusiness_location.setText(flag ? business.getBusinessAddressComplex() : business.getBusinessAddress());
        childHolder.layout_homebusiness_location_layout.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Utils.intentNavigation(business, mContext);
            }
        });
        
        String juli = "未知";
        if (HomeFragment.MAPLOCATION != null)
        {
            LatLng startLng = new LatLng(HomeFragment.MAPLOCATION.getLatitude(), HomeFragment.MAPLOCATION.getLongitude());
            LatLng endLng = new LatLng(business.getBusinessLatitude(), business.getBusinessLongitude());
            float juli_lng = AMapUtils.calculateLineDistance(startLng, endLng);
            juli = Utils.meauseCompany(juli_lng);
            // = juli_lng+"米";
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
       MyLogUtils.i(TAG, "界面可见么："+convertView.isShown());
        return convertView;
    }
    
    class MyCallBack implements ImageCallback
    {
        private ImageView img;
        
        /**
         * <默认构造函数>
         */
        public MyCallBack(ImageView img)
        {
            super();
            this.img = img;
        }
        
        @Override
        public void imageLoaded(Bitmap imageDrawable, String imageUrl)
        {
            if(imageDrawable==null)
            {
               this.img.setImageResource(R.drawable.moren_smail);
            }
            else
            {
                this.img.setImageBitmap(imageDrawable);
                bitmaps.put(imageUrl, imageDrawable);
            }
        }
        
    }
    
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        // TODO Auto-generated method stub
        return true;
    }
    
    /**
     * 从数据库获取类型名称
     * <功能详细描述>
     * 
     * @param title
     * @param flag2
     * @see [类、类#方法、类#成员]
     */
    private String getTile(String title, boolean flag)
    {
        return (String)MyDataUtils.getData(mContext, ConstantFile.IBusinessTitle.FileName + title, flag ? ConstantFile.IBusinessTitle.NAMECOMPLEX : ConstantFile.IBusinessTitle.NAME, String.class);
    }
    
    class TopHolder
    {
        TextView layout_homebusinesstype_titleText;
        
        ImageView img_module_home_feilei_yule;
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
