package com.ganggang.ganggangapp.ui.sreach;

import java.util.ArrayList;
import java.util.List;

import com.ganggang.frame.common.FlowLayout;
import com.ganggang.frame.freamwork.ui.BasicActivity;
import com.ganggang.frame.util.MyDataUtils;
import com.ganggang.frame.util.MyLogUtils;
import com.ganggang.frame.util.ViewUtils;
import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.bean.Business;
import com.ganggang.ganggangapp.bean.HotWord;
import com.ganggang.ganggangapp.constant.Constant;
import com.ganggang.ganggangapp.constant.ConstantAction;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.ganggang.ganggangapp.constant.ConstantFile;
import com.ganggang.ganggangapp.constant.ConstantMessage;
import com.ganggang.ganggangapp.logic.sreachlogic.ISreachLogic;
import com.ganggang.ganggangapp.ui.fragment.adapter.Business_ListView_adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

public class SreachActivity extends BasicActivity implements OnClickListener
{
    private final String TAG = SreachActivity.class.getSimpleName();
    
    private ISreachLogic logic;
    
    private FlowLayout sreach_hotlayout;
    
    private ImageView layout_top_sreach_return;
    
    private SearchView layout_top_sreach_edit;
    
    private ListView sreach_listview;
    
    private ScrollView sreach_scrollview;
    
    private boolean flag;

    private String mBack;
    
    private int startSize = 0;
    
    private int pageSize = Constant.BUSINESSLISTSIZE;
    
    private Business_ListView_adapter adapter;
    
    private List<Business> mlist_business;

    protected boolean mFlag_sreach;

    protected String str_sreach;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        
    }
    
    @Override
    public void setContentView()
    {
        setContentView(R.layout.activity_sreach);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_top_sreach);
        
    }
    
    @Override
    public void initViews()
    {
        try
        {
            ViewUtils.initView(this);
            sreach_listview.setVisibility(View.GONE);
            sreach_scrollview.setVisibility(View.VISIBLE);
        }
        catch (Exception e)
        {
            MyLogUtils.e(TAG, "init err", e);
        }
    }
    
    @Override
    public void initListeners()
    {
        layout_top_sreach_return.setOnClickListener(this);
        
        
        sreach_listview.setOnScrollListener(new OnScrollListener()
        {
            
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState)
            {
                
            }
            
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                  int lastitem = firstVisibleItem+visibleItemCount-1;
                  if(totalItemCount==firstVisibleItem+visibleItemCount)
                  {
                      startSize+=Constant.BUSINESSLISTSIZE;
                      pageSize+=Constant.BUSINESSLISTSIZE;
                      if(mFlag_sreach)
                      {
                          logic.getBusinessForHotWorld(str_sreach, startSize, pageSize);
                      }
                      else
                      {
                          logic.getBusinessForSreach(str_sreach, startSize, pageSize);
                      }
                  }
                  MyLogUtils.i(TAG, "last:"+lastitem+"  :"+totalItemCount);
            }
        });
        
        layout_top_sreach_edit.setOnQueryTextListener(new OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                MyLogUtils.i(TAG, "QUERY:"+query);
                startSize = 0;
                pageSize = Constant.BUSINESSLISTSIZE;
                if(mlist_business!=null)
                {
                    mlist_business.clear();
                }
                else
                {
                    mlist_business=new ArrayList<Business>();
                }
                mFlag_sreach = false;
                str_sreach = query;
                logic.getBusinessForSreach(query, startSize, pageSize);
          /*      Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt(ConstantEnum.IBusinessFragment.TYPE, ConstantEnum.IBusinessFragment.TYPE_BRANDNAME_BUSINESS);
                bundle.putString(ConstantEnum.IBusinessFragment.TYPE_ARGS, query);
                intent.putExtras(bundle);
                intent.setAction(ConstantAction.HOME_ACTION);
                SreachActivity.this.startActivity(intent);
                finish();*/
                ViewUtils.HideSoftKeyboard(SreachActivity.this);
                return true;
            }
            
            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });
    }
    
    @Override
    public void initData()
    {
        flag = (Boolean)MyDataUtils.getData(this, ConstantFile.IMore.FileName, ConstantFile.IMore.MORE_TRANSLATION, Boolean.class);
        
        mlist_business = new ArrayList<Business>();
        
        adapter = new Business_ListView_adapter(mlist_business, this);
        
        sreach_listview .setAdapter(adapter);
        
        mBack =  getIntent().getStringExtra(ConstantEnum.IBack.BACK_ARGS);
        logic.getHotWord();
        
    }
    
    @Override
    protected void handleStateMessage(Message msg)
    {
        switch (msg.what)
        {
            case ConstantMessage.HTTP_ERR_MSG:
                showToast(msg.obj.toString(), Toast.LENGTH_LONG);
                break;
            case ConstantMessage.ISreach.HOTWORD_OK_MSG:
                List<HotWord> list = (List<HotWord>)msg.obj;
                setSreachHotWord(list);
                break;
            case ConstantMessage.ISreach.HOTWORD_SREACH_OK_MSG:
                sreach_listview.setVisibility(View.VISIBLE);
                sreach_scrollview.setVisibility(View.GONE);
                if(msg.obj!=null)
                {
                    mlist_business.addAll((List<Business>)msg.obj);
                    adapter.notifyDataSetChanged();
                }
                break;
            case ConstantMessage.ISreach.SREACH_OK_MSG:
                sreach_listview.setVisibility(View.VISIBLE);
                sreach_scrollview.setVisibility(View.GONE);
                if(msg.obj!=null)
                {
                    mlist_business.addAll((List<Business>)msg.obj);
                    adapter.notifyDataSetChanged();
                }
               break;
            case ConstantMessage.ISreach.SREACH_ERR_MSG:
              /*  sreach_listview.setVisibility(View.VISIBLE);
                sreach_scrollview.setVisibility(View.GONE);*/
                break;
            case ConstantMessage.ISreach.HOTWORD_SREACH_ERR_MSG:
  /*              sreach_listview.setVisibility(View.VISIBLE);
                sreach_scrollview.setVisibility(View.GONE);*/
                break;
            case ConstantMessage.ISreach.HOTWORD_ERR_MSG:
                break;
            default:
                break;
        }
    }
    
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(mBack==null || mBack.equals(ConstantEnum.IBack.IBackArgs.BACK_HOME))
            {
                Intent intent = new Intent();
                intent.setAction(ConstantAction.HOME_ACTION);
                startActivity(intent);
                finish();
            }
            else
            {
                Intent intent = new Intent();
                intent.setAction(ConstantAction.HOME_ACTION);
                Bundle bundle = new Bundle();
                bundle.putInt(ConstantEnum.IBusinessFragment.TYPE, ConstantEnum.IBusinessFragment.TYPE_BRANDNAME_BUSINESS);
                bundle.putString(ConstantEnum.IBusinessFragment.TYPE_ARGS, "");
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    @SuppressLint({"NewApi", "ResourceAsColor"})
    private void setSreachHotWord(List<HotWord> list)
    {
        sreach_hotlayout.removeAllViews();
        for (int i = 0; list != null & i < list.size(); i++)
        {
            HotWord hotWord = list.get(i);
            // TextView text = (TextView)LayoutInflater.from(this).inflate(R.layout.layout_sreach_hotword_item,
            // null,false);
            TextView text = new TextView(this);
            MarginLayoutParams params = new MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT);
//            text.setBackground(this.getResources().getDrawable(R.drawable.drawable_business_detail_hot_text));
            
            text.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.drawable_business_detail_hot_text));
            text.setTextColor(R.color.home_fenlei_font);
            text.setTextSize(14);
            text.setPadding(20, 20, 20, 20);
            params.leftMargin = 20;
            params.rightMargin = 20;
            params.topMargin = 20;
            params.bottomMargin = 20;
            text.setLayoutParams(params);
            text.setText(flag ? hotWord.getHotWordsNameComplex() : hotWord.getHotWordsName());
            text.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                   String text = ((TextView)v).getText().toString();
                    MyLogUtils.i(TAG, "点击搜素:" +text );
                    /*  Intent intent = new Intent();
                    intent.setAction(ConstantAction.HOME_ACTION);
                    Bundle bundle = new Bundle();
                    bundle.putInt(ConstantEnum.IBusinessFragment.TYPE, ConstantEnum.IBusinessFragment.TYPE_HOTWORD_BUSINESS);
                    bundle.putString(ConstantEnum.IBusinessFragment.TYPE_ARGS, text);
                    intent.putExtras(bundle);
                    SreachActivity.this.startActivity(intent);
                    SreachActivity.this.finish();*/
                    if(mlist_business!=null)
                    {
                        mlist_business.clear();
                    }
                    else
                    {
                        mlist_business=new ArrayList<Business>();
                    }
                    startSize = 0;
                    pageSize = Constant.BUSINESSLISTSIZE;
                    SreachActivity.this.mFlag_sreach = true;
                    SreachActivity.this.str_sreach = text;
                    logic.getBusinessForHotWorld(text, startSize, pageSize);
                }
            });
            sreach_hotlayout.addView(text);
        }
        
    }
    
    @Override
    protected void initLogic()
    {
        logic = (ISreachLogic)getLogicByInterfaceClass(ISreachLogic.class);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.layout_top_sreach_return:
                if(mBack==null || mBack.equals(ConstantEnum.IBack.IBackArgs.BACK_HOME))
                {
                    Intent intent = new Intent();
                    intent.setAction(ConstantAction.HOME_ACTION);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent();
                    intent.setAction(ConstantAction.HOME_ACTION);
                    Bundle bundle = new Bundle();
                    bundle.putInt(ConstantEnum.IBusinessFragment.TYPE, ConstantEnum.IBusinessFragment.TYPE_BRANDNAME_BUSINESS);
                    bundle.putString(ConstantEnum.IBusinessFragment.TYPE_ARGS, "");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
//                finish();
                break;
            case R.id.layout_top_sreach_edit:
               
                break;
            default:
                break;
        }
        
    }
    
}
