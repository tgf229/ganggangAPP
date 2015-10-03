package com.ganggang.frame.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ganggang.ganggangapp.bean.Business;
import com.ganggang.ganggangapp.bean.Enumeration;
import com.ganggang.ganggangapp.constant.ConstantEnum;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import android.util.Log;

public class JsonUtil
{
    public static Gson GSON = new Gson();
    
    private static final String TAG = "JsonUtil";
    
    private static final String RESULT = "result";
    
    
    /**
     * 提取自定义标题的首页商家信息
     * <功能详细描述>
     * @param json
     * @param cls
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static HashMap<String, List<Business>> jsonForHomeBusinessTitle(String json,Class cls)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray data_jsonArray = (JSONArray)jsonObject.get("data");
            HashMap<String, List<Business>> map = new HashMap<String, List<Business>>();
            for(int i = 0 ;data_jsonArray!=null && i<data_jsonArray.length();i++)
            {
                JSONObject data_jsonObject =  (JSONObject)data_jsonArray.get(i);
                String key =  (String)data_jsonObject.get("titleId");
                 if(map.get(key)==null)
                 {
                     List<Business> list = JsonArrayForList(data_jsonObject.getString("business_list"),Business.class);
                     map.put(key, list);
                 }
            }
            return map.size()==0?null:map;
        }
        catch (JSONException e)
        {
           MyLogUtils.e(TAG, "jsonForHomeBusinessTitle is err",e);
           return null;
        }
    }
    
    public static HashMap<String, List<Business>> jsonForHomeBusiness(String json, Class cls)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject data_jsonObject = (JSONObject)jsonObject.get("data");
            HashMap<String, List<Business>> map = new HashMap<String, List<Business>>();
            if (data_jsonObject.has(ConstantEnum.IHomeBusinessType.TYPE_ONE))
            {
                map.put(ConstantEnum.IHomeBusinessType.TYPE_ONE, new ArrayList<Business>());
                JSONArray array_one = (JSONArray)data_jsonObject.get(ConstantEnum.IHomeBusinessType.TYPE_ONE);
                List<Business> list_type_one = JsonArrayForList(array_one.toString(), cls);
                map.get(ConstantEnum.IHomeBusinessType.TYPE_ONE).addAll(list_type_one);
            }
            if (data_jsonObject.has(ConstantEnum.IHomeBusinessType.TYPE_TWO))
            {
                map.put(ConstantEnum.IHomeBusinessType.TYPE_TWO, new ArrayList<Business>());
                JSONArray array_two = (JSONArray)data_jsonObject.get(ConstantEnum.IHomeBusinessType.TYPE_TWO);
                List<Business> list_type_two = JsonArrayForList(array_two.toString(), cls);
                map.get(ConstantEnum.IHomeBusinessType.TYPE_TWO).addAll(list_type_two);
            }
            if (data_jsonObject.has(ConstantEnum.IHomeBusinessType.TYPE_THREE))
            {
                map.put(ConstantEnum.IHomeBusinessType.TYPE_THREE, new ArrayList<Business>());
                JSONArray array_three = (JSONArray)data_jsonObject.get(ConstantEnum.IHomeBusinessType.TYPE_THREE);
                List<Business> list_type_three = JsonArrayForList(array_three.toString(), cls);
                map.get(ConstantEnum.IHomeBusinessType.TYPE_THREE).addAll(list_type_three);
            }
            if (data_jsonObject.has(ConstantEnum.IHomeBusinessType.TYPE_FOUR))
            {
                map.put(ConstantEnum.IHomeBusinessType.TYPE_FOUR, new ArrayList<Business>());
                JSONArray array_four = (JSONArray)data_jsonObject.get(ConstantEnum.IHomeBusinessType.TYPE_FOUR);
                List<Business> list_type_four = JsonArrayForList(array_four.toString(), cls);
                map.get(ConstantEnum.IHomeBusinessType.TYPE_FOUR).addAll(list_type_four);
            }
            if (data_jsonObject.has(ConstantEnum.IHomeBusinessType.TYPE_FIVE))
            {
                map.put(ConstantEnum.IHomeBusinessType.TYPE_FIVE, new ArrayList<Business>());
                JSONArray array_five = (JSONArray)data_jsonObject.get(ConstantEnum.IHomeBusinessType.TYPE_FIVE);
                List<Business> list_type_five = JsonArrayForList(array_five.toString(), cls);
                map.get(ConstantEnum.IHomeBusinessType.TYPE_FIVE).addAll(list_type_five);
                
            }
            if (data_jsonObject.has(ConstantEnum.IHomeBusinessType.TYPE_SIX))
            {
                map.put(ConstantEnum.IHomeBusinessType.TYPE_SIX, new ArrayList<Business>());
                JSONArray array_six = (JSONArray)data_jsonObject.get(ConstantEnum.IHomeBusinessType.TYPE_SIX);
                List<Business> list_type_six = JsonArrayForList(array_six.toString(), cls);
                map.get(ConstantEnum.IHomeBusinessType.TYPE_SIX).addAll(list_type_six);
            }
            if (data_jsonObject.has(ConstantEnum.IHomeBusinessType.TYPE_SEVEN))
            {
                map.put(ConstantEnum.IHomeBusinessType.TYPE_SEVEN, new ArrayList<Business>());
                JSONArray array_seven = (JSONArray)data_jsonObject.get(ConstantEnum.IHomeBusinessType.TYPE_SEVEN);
                List<Business> list_type_seven = JsonArrayForList(array_seven.toString(), cls);
                map.get(ConstantEnum.IHomeBusinessType.TYPE_SEVEN).addAll(list_type_seven);
            }
            if (data_jsonObject.has(ConstantEnum.IHomeBusinessType.TYPE_EIGHT))
            {
                map.put(ConstantEnum.IHomeBusinessType.TYPE_EIGHT, new ArrayList<Business>());
                JSONArray array_eight = (JSONArray)data_jsonObject.get(ConstantEnum.IHomeBusinessType.TYPE_EIGHT);
                List<Business> list_type_eight = JsonArrayForList(array_eight.toString(), cls);
                map.get(ConstantEnum.IHomeBusinessType.TYPE_EIGHT).addAll(list_type_eight);
            }
            return map.size() == 0 ? null : map;
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    public static List<Enumeration> jsonForEnumeration(String json, Class cls)
    {
        if (json == null)
            return null;
            
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray data_json = (JSONArray)jsonObject.get("data");
            for (int i = 0; data_json != null && i < data_json.length(); i++)
            {
                JSONObject jsonObject2 = (JSONObject)data_json.get(i);
                String str_json = jsonObject2.get("enumeration_list").toString();
                List<Enumeration> mlist = JsonArrayForList(str_json, cls);
                return mlist;
            }
            
        }
        catch (JSONException e)
        {
            MyLogUtils.e(TAG, "jsonForEnumeration is err", e);
        }
        
        return null;
    }
    
    /**
     * jsonArray 转list
     * <功能详细描述>
     * 
     * @param json
     * @param cls
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static <T> List<T> JsonArrayForList(String json, Class cls)
    {
        try
        {
            JSONArray array = new JSONArray(json);
            List<T> list = new ArrayList<T>();
            for (int i = 0; array != null && i < array.length(); i++)
            {
                list.add((T)GSON.fromJson(array.getString(i), cls));
            }
            return list.size() == 0 ? null : list;
        }
        catch (JSONException e)
        {
            MyLogUtils.e(TAG, "JsonForList IS ERR" + json, e);
            return null;
        }
    }
    
    public static <T> List<T> JsonForList(String json, Class cls)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            
            JSONArray array = (JSONArray)jsonObject.get("data");
            List<T> list = new ArrayList<T>();
            for (int i = 0; array != null && i < array.length(); i++)
            {
                list.add((T)GSON.fromJson(array.getString(i), cls));
            }
            return list.size() == 0 ? null : list;
        }
        catch (JSONException e)
        {
            MyLogUtils.e(TAG, "JsonForList IS ERR" + json, e);
            return null;
        }
    }
    
    public static boolean isJsonOk(String json)
    {
        try
        {
            if (json == null || json.intern().isEmpty())
                return false;
                
            JSONObject jsonObject = new JSONObject(json);
            String value = jsonObject.get(RESULT).toString();
            if (value.equals("success"))
            {
                return true;
            }
            else
                return false;
        }
        catch (JSONException e)
        {
            MyLogUtils.e(TAG, "isJsonOk json :" + json, e);
            return false;
        }
        
    }
    
    public static String objectToJson(Object obj)
    {
        String json = null;
        json = GSON.toJson(obj, obj.getClass());
      //  Log.i("INFO", "json:" + json);
        return json;
    }
    
    /**
     * 解析json
     * <功能详细描述>
     * 
     * @param json
     * @param beanCls
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static <T> T JsonToObject(String json, Class<T> beanCls)
    {
        T t = GSON.fromJson(json, beanCls);
        return t;
    }
    
    public static <T> List<T> jsonToList(String json, String key, Class<T> beanCls)
    {
        List<T> list = new ArrayList<T>();
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = (JSONArray)jsonObject.get(key);
            for (int i = 0; array != null && i < array.length(); i++)
            {
                list.add(GSON.fromJson(array.getString(i), beanCls));
            }
            
        }
        catch (JSONException e)
        {
            MyLogUtils.e(TAG, "jsonToList is err", e);
            return null;
        }
        return list.size() == 0 ? null : list;
    }
    
    public static String[] getJsonforKey(String json, String... keys)
    {
        if (keys == null || keys.length == 0)
            return null;
            
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            String[] values = new String[keys.length];
            for (int i = 0; i < keys.length; i++)
            {
                String value = "";
                Object obj = jsonObject.get(keys[i]);
                if (obj == null)
                {
                    value = "";
                }
                else
                    value = obj + "";
                    
                values[i] = value;
            }
            
            return values;
        }
        catch (JSONException e)
        {
            MyLogUtils.e(TAG, "getJsonforKey is err", e);
            return null;
        }
    }
    
    public static String getJsonforMsg(String json)
    {
        JSONObject jsonObject;
        try
        {
            jsonObject = new JSONObject(json);
            String value = jsonObject.get("message").toString();
            return value;
        }
        catch (JSONException e)
        {
            MyLogUtils.e(TAG, "getJsonforMsg IS ERR:", e);
            return null;
        }
    }
    
    public static boolean getJsonforResult(String json)
    {
        try
        {
            if (json == null || json.intern().isEmpty())
                return false;
            JSONObject jsonObject = new JSONObject(json);
            String value = jsonObject.get("result").toString();
            if (value.equals("success"))
            {
                return true;
            }
            else
                return false;
        }
        catch (JSONException e)
        {
            MyLogUtils.e(TAG, "getJsonforResult IS ERR:", e);
            return false;
        }
    }
    
}
