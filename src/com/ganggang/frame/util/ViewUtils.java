package com.ganggang.frame.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.ganggang.ganggangapp.R;
import com.ganggang.ganggangapp.R.id;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

/**
 * 
 * View Content 用到的工具类
 * 
 * @author 曾宝
 * @version [V1.00, 2015-4-27]
 * @see [相关类/方法]
 * @since V1.00
 */
public class ViewUtils
{
    /**
     *展开 
     * <功能详细描述>
     * @param adapter
     * @param listView
     * @see [类、类#方法、类#成员]
     */
    public static void expandGroup(BaseExpandableListAdapter adapter,ExpandableListView listView)
    {
        for(int i = 0 ;adapter!=null && i<adapter.getGroupTypeCount();i++)
        {
            listView.expandGroup(i);
        }
    }
    
    /**
     *设置ExpandablelistView 在scrollView 适配大小 
     * <功能详细描述>
     * @param fragment_home_business_info_layout2
     * @see [类、类#方法、类#成员]
     */
    public static  void setListViewHeightBasedOnChildren(ExpandableListView fragment_home_business_info_layout2)
    {
        ListAdapter adapter = fragment_home_business_info_layout2.getAdapter();
        if (adapter == null)
            return;
            
        int totalHeight = 0;
        
        for (int i = 0, len = adapter.getCount(); i < len; i++)
        {
            // listAdapter.getCount()返回数据项的数目
            View listItem = adapter.getView(i, null, fragment_home_business_info_layout2);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
            
        }
        ViewGroup.LayoutParams params = fragment_home_business_info_layout2.getLayoutParams();
        params.height = totalHeight + (fragment_home_business_info_layout2.getDividerHeight() * (fragment_home_business_info_layout2.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        fragment_home_business_info_layout2.setLayoutParams(params);
        
    }
    
    
    public static int getImage(String pic)
    {
        if (pic == null || pic.trim().equals(""))
        {
            return 0;
        }
        Class draw = R.drawable.class;
        try
        {
            Field field = draw.getDeclaredField(pic);
            return field.getInt(pic);
        }
        catch (SecurityException e)
        {
            return 0;
        }
        catch (NoSuchFieldException e)
        {
            return 0;
        }
        catch (IllegalArgumentException e)
        {
            return 0;
        }
        catch (IllegalAccessException e)
        {
            return 0;
        }
    }
    
    /**
     * 根据缩放参数，调整图片大小
     * <功能详细描述>
     * 
     * @param bitmap
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Bitmap AdjustBitmap(Bitmap bitmap, float scaleX, float scaleY)
    {
        if(scaleX<=0 || scaleY<=0)
            return bitmap;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY); // 长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }
    
    /**
     * 获取屏幕的宽度
     * <功能详细描述>
     * 
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static DisplayMetrics getscreen(Context context)
    {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        return dm;
    }
    
    public static Typeface getHYQIHE(Context context)
    {
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/HYQiHe.ttf");
        return typeFace;
    }
    
    
/*    public static PopupWindow getPopWindow(Context context, List<String> list, int width, int height)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_district, null, false);
       
        ((ListView)view).setAdapter(new SimpleAdapter(context, list, R.layout.listview_popwindow_district, 
                                        from, to);
//            <String>(context,android.R.layout.simple_list_item_1,list));
        final PopupWindow popupWindow = new PopupWindow(listview, width, height, true);
        popupWindow.setTouchInterceptor(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (popupWindow != null && popupWindow.isShowing())
                {
                    popupWindow.dismiss();
                }
                return false;
            }
        });
        if (popupWindow != null)
        {
            popupWindow.dismiss();
            return popupWindow;
        }
        return null;
    }*/
    
    public static PopupWindow getPopWindow(Context context, int xmlID, int width, int height)
    {
        View view = LayoutInflater.from(context).inflate(xmlID, null, false);
        final PopupWindow popupWindow = new PopupWindow(view, width, height, true);
        popupWindow.setTouchInterceptor(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (popupWindow != null && popupWindow.isShowing())
                {
                    popupWindow.dismiss();
                }
                return false;
            }
        });
        if (popupWindow != null)
        {
            popupWindow.dismiss();
            return popupWindow;
        }
        return null;
    }
    
    public static int[] getMeasureSpec(View view)
    {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        int width = view.getMeasuredWidth();
        return new int[] {width, height};
    }
    
    /**
     * 获取对应的属性
     * 
     * @param attr
     * @see [类、类#方法、类#成员]
     */
    public static float getAttribute(AttributeSet attrs, String name)
    {
        for (int i = 0; i < attrs.getAttributeCount(); i++)
        {
            String attriName = attrs.getAttributeName(i);
            MyLogUtils.d("tag", "attriName:" + attriName + " name" + name);
            if (attriName.equals(name))
            {
                String value = attrs.getAttributeValue(i);
                value = value.substring(0, value.indexOf("dip"));
                float va = Float.parseFloat(value);
                MyLogUtils.d("tag", "va:" + va);
                return va;
            }
        }
        return 0;
    }
    
    /**
     * 更换 头部信息
     * <功能详细描述>
     * 
     * @param activity
     * @param flag
     * @see [类、类#方法、类#成员]
     */
    public static void setTitleBarState(Activity activity, boolean flag)
    {
        if (flag)
        {
            // 隐藏titlebar
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            
        }
        else
        {
            // 显示titlebar
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        flag = !flag;
    }
    
    /**
     * DIP 转PX
     * <功能详细描述>
     * 
     * @param context
     * @param dipValue
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int dip2px(Context context, float dipValue)
    {
        WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        int w = manager.getDefaultDisplay().getWidth();
        int h = manager.getDefaultDisplay().getHeight();
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
        float densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
        float xdpi = dm.xdpi;
        float ydpi = dm.ydpi;
        MyLogUtils.d("window", "w=" + w + " h=" + h + " density=" + density + " densityDPI=" + densityDPI + " xdpi=" + xdpi + " ydpi=" + ydpi);
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }
    
    /**
     * px 转 dip
     * <功能详细描述>
     * 
     * @param context
     * @param pxValue
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int px2dip(Context context, float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }
    
    /**
     * 隐藏键盘
     * <功能详细描述>
     * 
     * @param context
     * @see [类、类#方法、类#成员]
     */
    public static void HideSoftKeyboard(Context context)
    {
        InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
        {
            View localView = ((Activity)context).getCurrentFocus();
            if (localView != null && localView.getWindowToken() != null)
            {
                IBinder windowToken = localView.getWindowToken();
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
            }
        }
    }
    
    /**
     * 初始化 list 加载页面上的控件
     * <功能详细描述>
     * 
     * @param object --holder
     * @param view ---list 对应的view
     * @see [类、类#方法、类#成员]
     */
    public static void initHolderView(Object holder, View view)
    {
        // 获取holder 的class
        Class holderClass = holder.getClass();
        // 获取holder定义的属性
        Field[] objFields = holderClass.getDeclaredFields();
        // R.ID
        Class ridClass = R.id.class;
        
        Class viewClass = view.getClass();
        
        Method findViewByIdMethod = null;
        try
        {
            findViewByIdMethod = viewClass.getMethod("findViewById", int.class);
        }
        catch (SecurityException e)
        {// 权限
            e.printStackTrace();
        }
        catch (NoSuchMethodException e)
        {// 没有取到对应的方法
            e.printStackTrace();
        }
        
        for (Field objField : objFields)
        {
            Class fieldType = objField.getType();
            // 控件必须是view的子类
            if (View.class.isAssignableFrom(fieldType))
            {
                try
                {
                    // 获取到R.ID 对应的属性 并获取ID
                    Field ridField = ridClass.getField(objField.getName());
                    int id = ridField.getInt("");
                    Object obj = findViewByIdMethod.invoke(view, id);
                    objField.setAccessible(true);
                    objField.set(holder, obj);
                }
                catch (SecurityException e)
                {// 权限
                    e.printStackTrace();
                }
                catch (NoSuchFieldException e)
                {// 没有获取到属性
                    e.printStackTrace();
                }
                catch (IllegalArgumentException e)
                {// 参数
                    e.printStackTrace();
                }
                catch (IllegalAccessException e)
                {// 权限
                    e.printStackTrace();
                }
                catch (InvocationTargetException e)
                {// 实例化的类不存在
                    e.printStackTrace();
                }
            }
        }
        
    }
    
    public static void initView(Fragment fragment, View view)
        throws Exception
    {
        Class<? extends Fragment> fragmentClass = fragment.getClass();
        
        Class<id> rClass = R.id.class;
        
        Class viewClass = view.getClass();
        
        Method findViewId = null;
        
        try
        {
            findViewId = viewClass.getMethod("findViewById", int.class);
        }
        catch (NoSuchMethodException e)
        {
            throw new Exception("INITVIEW-findViewByIdMethod is err:" + e);
        }
        
        Field[] fragmentFields = fragmentClass.getDeclaredFields();
        
        for (int i = 0; fragmentFields != null && i < fragmentFields.length; i++)
        {
            // 获取类型
            Class fragmentType = fragmentFields[i].getType();
            if (View.class.isAssignableFrom(fragmentType))
            {
                try
                {
                    // 根据属性名称 查找R.id 里面的属性
                    Field idField = rClass.getDeclaredField(fragmentFields[i].getName());
//                    MyLogUtils.i("TAG", fragmentFields[i].getName());
                    // 获取属性的值
                    int id = idField.getInt("");
                    // 将找到到的值 与activity的findViewById 方法绑定
                    Object obj = findViewId.invoke(view, id);
                    // 打开私有化赋值
                    fragmentFields[i].setAccessible(true);
                    // 绑定后的对象 跟对应的属性 绑定
                    fragmentFields[i].set(fragment, obj);
                    // 绑定后将私有化赋值关闭
                    fragmentFields[i].setAccessible(false);
                }
                catch (NoSuchFieldException e)
                {
                    continue;
                }
                catch (Exception e)
                {// 权限问题
                    throw new Exception("INITVIEW-fragmentFields is err:" + e);
                }
            }
        }
        
    }
    
    /**
     * 初始化界面Activity
     * 
     * @param activity
     * @see [类、类#方法、类#成员]
     */
    public static void initView(Activity activity)
        throws Exception
    {
        // 获取Activity 的类
        Class<? extends Activity> activityClass = activity.getClass();
        // r.id 类
        Class<id> idClass = R.id.class;
        
        Method findViewByIdMethod = null;
        try
        {
            findViewByIdMethod = activityClass.getMethod("findViewById", int.class);
        }
        catch (Exception e)
        {
            throw new Exception("INITVIEW-findViewByIdMethod is err:" + e);
        }
        
        Field[] activityFields = activityClass.getDeclaredFields();
        
        for (Field activityField : activityFields)
        {
            // 获取类型
            Class activityType = activityField.getType();
            // 继承View的类
            if (View.class.isAssignableFrom(activityType))
            {
                try
                {
                    // 根据属性名称 查找R.id 里面的属性
                    Field idField = idClass.getDeclaredField(activityField.getName());
                    // 获取属性的值
                    int id = idField.getInt("");
                    // 将找到到的值 与activity的findViewById 方法绑定
                    Object obj = findViewByIdMethod.invoke(activity, id);
                    // 打开私有化赋值
                    activityField.setAccessible(true);
                    // 绑定后的对象 跟对应的属性 绑定
                    activityField.set(activity, obj);
                    // 绑定后将私有化赋值关闭
                    activityField.setAccessible(false);
                }
                catch (NoSuchFieldException e)
                {
                    continue;
                }
                catch (Exception e)
                {// 权限问题
                    throw new Exception("INITVIEW-activityFields is err:" + e);
                }
                
            }
            
        }
        
    }
}
