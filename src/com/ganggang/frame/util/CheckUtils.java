package com.ganggang.frame.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ganggang.ganggangapp.R;

import android.content.Context;
import android.widget.Toast;
/**
 * 
 *公共检查 工具类  
 * @author 曾宝
 * @version  [V1.00, 2015年7月14日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class CheckUtils
{
    
    private Context context;
    
    public CheckUtils(Context context)
    {
        this.context = context;
    }
    
    /**
     * 检查是否为空 
     * <功能详细描述>
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isEmpty(String str)
    {
        if (str == null || str.equals(""))
        {
            return true;
        }
        return false;
    }
    
    public static boolean isValidateEmailCheck(String validateMsg,Context context)
    {
        if (!isEmpty(validateMsg))
        {
            Pattern p = Pattern.compile("^[0-9a-z][a-z0-9\\._-]{1,}@[a-z0-9-]{1,}[a-z0-9]\\.[a-z\\.]{1,}[a-z]$");
            Matcher m = p.matcher(validateMsg);
            if(!m.matches())
            {
                Toast.makeText(context, R.string.err_validateemail, Toast.LENGTH_LONG);
            }
           return m.matches();
        }
        else
        {
         /*   Toast.makeText(context, R.string.err_validatemsg_null, Toast.LENGTH_LONG);*/
            return true;
        }
    }
    
    
    
    public static boolean isValidateMsgCheck(String validateMsg,Context context)
    {
        if (!isEmpty(validateMsg))
        {
            Pattern p = Pattern.compile("\\d{6}");
            Matcher m = p.matcher(validateMsg);
            if(!m.matches())
            {
                Toast.makeText(context, R.string.err_validatemsg, Toast.LENGTH_LONG);
            }
           return m.matches();
        }
        else
        {
            Toast.makeText(context, R.string.err_validatemsg_null, Toast.LENGTH_LONG);
        }
        return false;
    }
    
    /**
     * 手机号码检查 
     * <功能详细描述>
     * @param mobileNumber
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isMobileCheck(String mobileNumber, Context context)
    {
        if (!isEmpty(mobileNumber))
        {
            Pattern p = Pattern.compile("^1[0-9]{10}$");
            Matcher m = p.matcher(mobileNumber);
            if (!m.matches())
            {
                Toast.makeText(context, R.string.err_mobile,1000).show();
            }
            return m.matches();
        }
        else
        {
            Toast.makeText(context, R.string.err_mobile_null, 1000).show();
        }
        return false;
    }
    
    /**
     * password 检查
     * <功能详细描述>
     * @param password
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isPasswordCheck(String password, Context context)
    {
        if (!isEmpty(password))
        {
            if (password.length() >= 6)
            {
                return true;
            }
            else
            {
                Toast.makeText(context, R.string.err_password_len, 1000).show();
            }
        }
        else
        {
            Toast.makeText(context, R.string.err_password_null, 1000).show();
        }
        return false;
    }
    
    /**
     * 检查用户名
     * <功能详细描述>
     * @param userName
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isUserNameCheck(String userName, Context context)
    {
        if (!isEmpty(userName))
        {
            if (userName.length() > 30)
            {
                Toast.makeText(context, R.string.err_username_len_max, 1000).show();
            }
            else if (userName.length() <= 2)
            {
                Toast.makeText(context, R.string.err_username_len_min, 1000).show();
            }
            else
            {
                return true;
            }
        }
        else
        {
            Toast.makeText(context, R.string.err_username_null, 1000).show();
        }
        return false;
    }
    
    /**
     * 年龄检查 
     * <功能详细描述>
     * @param age
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isAgeCheck(String age, Context context)
    {
        if (!isEmpty(age))
        {
            Pattern p = Pattern.compile("^([0-9]|[0-9]{2}|100)$");
            Matcher m = p.matcher(age);
            if (!m.matches())
            {
                Toast.makeText(context, R.string.err_age, 1000).show();
            }
            else
            {
                return true;
            }
        }
        else
        {
            Toast.makeText(context, R.string.err_age_null, 1000).show();
        }
        return false;
    }
    
   
}
