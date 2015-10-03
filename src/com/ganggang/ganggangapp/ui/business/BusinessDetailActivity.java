package com.ganggang.ganggangapp.ui.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ganggang.frame.common.FlowLayout;
import com.ganggang.frame.freamwork.imgload.ImageLoader;
import com.ganggang.frame.freamwork.imgload.Imgloader_new;
import com.ganggang.frame.freamwork.imgload.Imgloader_new.ImageCallback;
import com.ganggang.frame.freamwork.ui.BasicActivity;
import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.Utils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Business;
import com.ganggang.ganggangapp.bean.BusinessBrand;
import com.ganggang.ganggangapp.bean.Comment;
import com.ganggang.ganggangapp.bean.Commodity;
import com.ganggang.ganggangapp.bean.HotWord;
import com.ganggang.ganggangapp.constant.Constant;
import com.ganggang.ganggangapp.constant.ConstantAction;
import com.ganggang.ganggangapp.constant.ConstantBundle;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.constant.ConstantFile;
import com.ganggang.ganggangapp.constant.ConstantHttp;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.businessdetaillogic.IBusinessDetailLogic;
import com.ganggang.ganggangapp.ui.business.adapter.BusinessDetail_ExpandableListView_adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 *商家详情  
 * @author 曾宝
 * @version  [V1.00, 2015年8月16日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class BusinessDetailActivity extends BasicActivity implements OnClickListener
{
    private final String TAG = BusinessDetailActivity.class.getSimpleName();
    /**
     * 刚进入时的加载框
     */
    private ProgressBar business_detail_progress;
    
    /**
     * 主页面
     */
    private ScrollView business_detail_main;
    
    
    /**
     * 附近和分享,头部返回
     */
    private ImageView business_detail_top_fujin, bWusiness_detail_top_return;
    
    /**
     * ,头部店家图片,手机拨号,收藏箭头img，更换箭头
     */
    private ImageView business_detail_begin_picture,business_detail_phone,business_detail_youjiantou,business_detail_top_return;
    
    /**
     * 地址 布局，点击进入地图显示位置
     */
    private LinearLayout activity_business_detail_location_layout;
    /**
     * 商家详情的layout
     */
    private RelativeLayout business_detail_relativelayout;

    /**
     * 店家名称，好评信息,店家地址,店家介绍-取品牌的信息,店家营业时间
     */
    private TextView business_detail_name,business_detail_highOpinion,business_detail_address,business_detail_content,business_detail_time_content,
    /**
     * 店家路线信息,评论数量,评论 人名,评论内容
     */
    business_detail_traffic_content,business_detail_comment_num,business_detail_comment_content_user,business_detail_comment_content;
    
    
    /**
     * 推荐商品 存放的位置
     */
    private FlowLayout business_detail_hot_layout,business_detail_commodity_img_layout;
    
    /**
     * 商家详情隐藏/显示的 部分,评论点击跳转评论,评论显示的layout-如果没有评论 隐藏掉
     */
    private LinearLayout business_detail_linearlayout,business_detail_comment_layout,business_detail_comment_content_layout;
    
    /**
     * 附近店铺
     */
    private ExpandableListView business_detail_expandablelistview;
    /**
     * 分店信息
     */
    private BusinessDetail_ExpandableListView_adapter adapter_businessDetail_sub;
    /**
     * 分店信息
     */
    private HashMap<String, List<Business>> map_business_sub;
    /**
     * 评论
     */
    private List<Comment> mlist_comment;
    /**
     * 商品
     */
    private List<Commodity> mlist_commodity;

    private List<Business> mlist_sub_fujin;
    /**
     * 所有附近分店
     */
    private List<Business> mlist_sub_all;
    
    
    /**
     * 简繁体中文
     */
    private static boolean FLAG_TRANSLATION;
    /**
     * 详情逻辑
     */
    private IBusinessDetailLogic logic;
    
    private Business mBusiness;
    /**
     * 返回来的商家所有信息
     */
    private HashMap<String, Object> map = null;
    private BusinessBrand business_brand = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);

    }
    
    @Override
    public void setContentView()
    {
        setContentView(R.layout.activity_business_detail);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_top_business_detail);
        
    }
    
    @Override
    public void initViews()
    {
        try
        {
            ViewUtils.initView(this);
            business_detail_progress.setVisibility(View.VISIBLE);
            business_detail_main.setVisibility(View.GONE);
        }
        catch (Exception e)
        {
            MyLogUtils.e(TAG, "initViews is err", e);
        }
        
    }
    
    @Override
    public void initListeners()
    {
        
        activity_business_detail_location_layout.setOnClickListener(this);
        
        //点击全部的品牌的位置
        business_detail_top_fujin.setOnClickListener(this);
        
        
    	business_detail_top_return.setOnClickListener(this);
    	
        business_detail_relativelayout.setOnClickListener(this);
        //商家评论，点击跳转评论
        business_detail_comment_layout.setOnClickListener(this);
        
        business_detail_phone.setOnClickListener(this);
        //展开监听
        business_detail_expandablelistview.setOnGroupExpandListener(new OnGroupExpandListener()
        {
            @Override
            public void onGroupExpand(int groupPosition)
            {
                ViewUtils.setListViewHeightBasedOnChildren(business_detail_expandablelistview);
            }
        });
        
        business_detail_expandablelistview.setOnGroupCollapseListener(new OnGroupCollapseListener()
        {
            @Override
            public void onGroupCollapse(int groupPosition)
            {
                ViewUtils.setListViewHeightBasedOnChildren(business_detail_expandablelistview);
            }
        });
        
    }
    
    
    
    @Override
    public void initData()
    {
        FLAG_TRANSLATION = (Boolean)MyDataUtils.getData(this, ConstantFile.IMore.FileName, ConstantFile.IMore.MORE_TRANSLATION, Boolean.class);
        int id = getIntent().getIntExtra(ConstantBundle.BUSINESSDEATILID, 0);
        if (id <= 0)
        {
            showToast("未找到商家详情", Toast.LENGTH_LONG);
            finish();
        }
        mlist_comment = new ArrayList<Comment>();
        
        mlist_commodity = new ArrayList<Commodity>();
        
        mlist_sub_fujin = new ArrayList<Business>();
        
        mlist_sub_all = new ArrayList<Business>();
        
        map_business_sub = new HashMap<String, List<Business>>();
        
        adapter_businessDetail_sub = new BusinessDetail_ExpandableListView_adapter(this, map_business_sub);
        
        business_detail_expandablelistview.setAdapter(adapter_businessDetail_sub);
        
        adapter_businessDetail_sub.notifyDataSetChanged();
        
        ViewUtils.setListViewHeightBasedOnChildren(business_detail_expandablelistview);
        
        logic.getBusinessDetail(id);
        
    }
    
    @Override
    protected void initLogic()
    {
        logic = (IBusinessDetailLogic)getLogicByInterfaceClass(IBusinessDetailLogic.class);
    }
    
    @SuppressLint({"NewApi", "ResourceAsColor"})
    @Override
    protected void handleStateMessage(Message msg)
    {
        switch (msg.what)
        {
            case ConstantMessage.IBusinessDetail.BUSINESSDETAIL_OK_MSG:
                map  = (HashMap<String, Object>)msg.obj;
                mBusiness = (Business)map.get(Constant.IBusinessDetail.BUSINESS_DETAIL);
                business_brand = (BusinessBrand)map.get(Constant.IBusinessDetail.BUSINESS_BRAND);
                List<Comment> comment_list = (List<Comment>)map.get(Constant.IBusinessDetail.MLIST_COMMENT);
                List<Commodity> commodity_list = (List<Commodity>)map.get(Constant.IBusinessDetail.MLIST_COMMODITY);
                List<Business> sub_all_list = (List<Business>)map.get(Constant.IBusinessDetail.MLIST_SUB_ALL);
                Log.d(TAG,"总点数："+sub_all_list.size());
                mlist_sub_all.clear();
                mlist_sub_all.addAll(sub_all_list);
                List<Business> sub_fujin_list = (List<Business>)map.get(Constant.IBusinessDetail.MLIST_SUB_FUJIN);
                List<HotWord> hotword_list = (List<HotWord>)map.get(Constant.IBusinessDetail.MLIST_HOTWORD);
//                MyLogUtils.i(TAG, "商品："+comment_list.size());
                for(int i = 0 ;commodity_list!=null && i<commodity_list.size();i++)
                {
                    MyLogUtils.i(TAG, "commodity_list:"+commodity_list.get(i).toString());
                }
                
                mlist_comment = comment_list;
                //business_brand.getBusinessBrandPicture()!=null
                business_detail_begin_picture.setImageResource(R.drawable.moren_img);
                if(mBusiness.getBusinessPicture_list()!=null && mBusiness.getBusinessPicture_list().size()>0)
                {
                    Imgloader_new imgloader_new = new Imgloader_new();
                    imgloader_new.loadDrawable(mBusiness.getBusinessPicture_list().get(0), ConstantEnum.IFileType.TYPE_BUSINESSBRANNDER, new ImageCallback()
                    {
                        @Override
                        public void imageLoaded(Bitmap imageDrawable, String imageUrl)
                        {
                            business_detail_begin_picture.setImageBitmap(imageDrawable);
                        }
                    });
                  /*  //商家图片 加载
                    ImageLoader imageLoader = new ImageLoader();
                    imageLoader.execute(null,business_detail_begin_picture,ConstantHttp.IP+mBusiness.getBusinessPicture_list().get(0),ConstantEnum.IFileType.TYPE_BUSINESS);*/
                }
                //商家名称
                business_detail_name.setText(FLAG_TRANSLATION?business_brand.getBusinessBrandNameComplex()+mBusiness.getBusinessNameComplex():business_brand.getBusinessBrandName()+mBusiness.getBusinessName());
                //商家热度
                business_detail_highOpinion.setText("好评:"+mBusiness.getHighOpinion()+"分");
                //商家地址
                business_detail_address.setText(FLAG_TRANSLATION?mBusiness.getBusinessAddressComplex():mBusiness.getBusinessAddress());
                //热词
                if(hotword_list!=null)
                {
                    for(int i = 0 ;i<hotword_list.size();i++)
                    {
                        TextView text = new TextView(this);
                        MarginLayoutParams layoutParams = new MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT);
//                        text.setBackground(this.getResources().getDrawable(R.drawable.drawable_business_detail_hot_text));
                        text.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.drawable_business_detail_hot_text));
                        text.setText(hotword_list.get(i).getHotWordsName());
                        text.setTextColor(R.color.home_fenlei_font);
                        text.setTextSize(14);
                        text.setPadding(20, 20, 20, 20);
                        layoutParams.leftMargin = 20;
                        layoutParams.rightMargin = 20;
                        layoutParams.topMargin = 20;
                        layoutParams.bottomMargin = 20;
                        text.setLayoutParams(layoutParams);
                        business_detail_hot_layout.addView(text);
                    }
                }
                
                if(commodity_list!=null)
                {
                    for(int i = 0;commodity_list!=null && i<commodity_list.size();i++)
                    {
                         final ImageView commodity_img = new ImageView(this);
                         MarginLayoutParams commodity_params = new MarginLayoutParams(150,150);
                         commodity_params.leftMargin = 10;
//                         commodity_params.rightMargin = 10;
                         commodity_params.topMargin = 10;
                         commodity_params.bottomMargin = 10;
                         commodity_img.setScaleType(ScaleType.FIT_XY);
                        commodity_img.setPadding(10, 10, 10, 10);
                        commodity_img.setImageResource(R.drawable.moren_img);
                        if(commodity_list.get(i).getCommodityPicture()!=null)
                        {
                            Imgloader_new imgloader_new = new Imgloader_new();
                            imgloader_new.loadDrawable(commodity_list.get(i).getCommodityPicture(), ConstantEnum.IFileType.TYPE_BUSINESS, new ImageCallback()
                            {
                                @Override
                                public void imageLoaded(Bitmap imageDrawable, String imageUrl)
                                {
                                    commodity_img.setImageBitmap(imageDrawable);
                                }
                            });
                           /* //商家图片 加载
                            ImageLoader commodity_img_loader = new ImageLoader();
                            commodity_img_loader.execute(null,commodity_img,ConstantHttp.IP+commodity_list.get(i).getCommodityPicture(),ConstantEnum.IFileType.TYPE_BUSINESS);*/
                        }
                         commodity_img.setLayoutParams(commodity_params);
                         business_detail_commodity_img_layout.addView(commodity_img);
                    }
                }
                
                
                //商家简介
                business_detail_content.setText(FLAG_TRANSLATION?business_brand.getBusinessBrandRemarkComplex():business_brand.getBusinessBrandRemark());
                //商家营业时间
                business_detail_time_content.setText(mBusiness.getBusinessHours());
                
                //商家路线
                business_detail_traffic_content.setText(FLAG_TRANSLATION?mBusiness.getBusinessBusLineComplex():mBusiness.getBusinessBusLine());
                
                //评论数量
                business_detail_comment_num.setText(comment_list==null?"(0)":"("+comment_list.size()+")");
                //显示评论布局
                if(comment_list==null || comment_list.size()==0)
                {
                    business_detail_comment_content_layout.setVisibility(View.GONE);
                }
                else
                {
                    business_detail_comment_content_layout.setVisibility(View.VISIBLE);
                    if(comment_list!=null && comment_list.size()>0)
                    {
                        if(comment_list.get(comment_list.size()-1).getUser()!=null && comment_list.get(comment_list.size()-1).getUser().length>0)
                        {
                            business_detail_comment_content_user.setText(comment_list.get(comment_list.size()-1).getUser()[0]);
                        }
                        business_detail_comment_content.setText(comment_list.get(comment_list.size()-1).getComment());
                    }
                }
                //分店信息
                if((sub_all_list==null || sub_all_list.size()==0)
                    &&(sub_fujin_list==null || sub_fujin_list.size()==0))
                {
                    business_detail_expandablelistview.setVisibility(View.GONE);
                }
                else
                {
                    if(sub_all_list!=null && sub_all_list.size()!=0)
                    {
                        map_business_sub.put(getResources().getString(R.string.allfendian),sub_all_list);
                    }
                    if(sub_fujin_list!=null && sub_fujin_list.size()!=0)
                    {
                        map_business_sub.put(getResources().getString(R.string.allfendian),sub_fujin_list);
                    }
                    adapter_businessDetail_sub.notifyDataSetChanged();
                    
                }
           
                ViewUtils.expandGroup(adapter_businessDetail_sub, business_detail_expandablelistview);
                ViewUtils.setListViewHeightBasedOnChildren(business_detail_expandablelistview);
                MyLogUtils.i(TAG, mBusiness.toString());
                
                break;
                
            default:
                break;
        }
        business_detail_progress.setVisibility(View.GONE);
        business_detail_main.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.business_detail_relativelayout:
                //收藏商家详情
                if(business_detail_linearlayout.getVisibility()==View.VISIBLE)
                {
                    business_detail_linearlayout.setVisibility(View.GONE);
                    business_detail_youjiantou.setImageResource(R.drawable.youjiantou_icon);;
                }
                else
                {
                    business_detail_linearlayout.setVisibility(View.VISIBLE);
                    business_detail_youjiantou.setImageResource(R.drawable.xiajiantou_icon);;
                }
                break;
            case R.id.business_detail_comment_layout:
                //跳转评论
                MyLogUtils.i(TAG, "跳转评论");
                if(mBusiness!=null)
                {
                    Intent intent = new Intent();
                    intent.setAction(ConstantAction.COMMENT_ACTION);
                    intent.putExtra(ConstantBundle.BUSINESS_COMMENT, mBusiness);
                    if(mlist_comment!=null)
                        intent.putExtra(ConstantBundle.BUSINESS_COMMENT_LIST, (Serializable)mlist_comment);
                    
                    startActivity(intent);
                    
                }
                break;
// 详细界面返回箭头               
            case R.id.business_detail_top_return:
   /*         	Intent intent =new Intent();
            	intent.setAction(ConstantAction.HOME_ACTION);
            	startActivity(intent);*/
            	finish();
            	break;
            case R.id.business_detail_phone:
                Utils.sysPhone(mBusiness.getBusinessPhone(), this);
                break;
            case R.id.activity_business_detail_location_layout:
                //点击显示本分店的地图
                MyLogUtils.i(TAG, "点击显示本分店的地图");
              /*  end = mBundle.getString(ConstantEnum.IMap.TYPE_ARGS_NAVIGATION_DEST);
                destName = mBundle.getString(ConstantEnum.IMap.TYPE_ARGS_NAVIGATION_DESTNAME);*/
                Intent intent1 = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(ConstantEnum.IMap.TYPE_ARGS_NAVIGATION_DEST, mBusiness.getBusinessLongitude()+","+mBusiness.getBusinessLatitude());
                bundle.putString(ConstantEnum.IMap.TYPE_ARGS_NAVIGATION_DESTNAME, mBusiness.getBusinessAddressComplex());
                intent1.putExtras(bundle);
                intent1.setAction(ConstantAction.NAVIGATION_ACTION);
                startActivity(intent1);
                
              /*  Intent intent1 = new Intent();
                intent1.putExtra(ConstantBundle.IMapType.TO_MAP, ConstantEnum.IMap.TYPE_DETAIL_SINGLE);
                intent1.putExtra(ConstantBundle.IMapType.BUSINESS_DETAIL_MAP, mBusiness);
                intent1.setAction(ConstantAction.MAP_ACTION);
                startActivity(intent1);*/
                break;
            case R.id.business_detail_top_fujin:
                //点击显示品牌所有的分店
                MyLogUtils.i(TAG, "点击显示品牌所有的分店");
                MyLogUtils.i(TAG, "点击显示本分店的地图");
                Intent intent2 = new Intent();
                Bundle bundle2 = new Bundle();
                 bundle2.putInt(ConstantBundle.IMapType.TO_MAP,ConstantEnum.IMap.TYPE_DETAIL_BUSINESSBRAND);
                 bundle2.putSerializable(ConstantBundle.IMapType.BUSINESS_DETAIL_MAP, (Serializable)mlist_sub_all);
                /*ntent2.putExtra(ConstantBundle.IMapType.TO_MAP, ConstantEnum.IMap.TYPE_DETAIL_BUSINESSBRAND);
                intent2.putExtra(ConstantBundle.IMapType.BUSINESS_DETAIL_MAP, (Serializable)mlist_sub_all);*/
                intent2.setAction(ConstantAction.MAP_ACTION);
                intent2.putExtras(bundle2);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }
    
}
