package com.ganggang.frame.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextViewUtil
{
    /**
     * 半角转换为全角
     * 
     * @param input
     * @return
     */
    public static String ToDBC(String input)
    {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++)
        {
            if (isChinese(c[i]))
            {
                if (c[i] == 12288)
                {
                    c[i] = (char)32;
                    continue;
                }
                if (c[i] > 65280 && c[i] < 65375)
                    c[i] = (char)(c[i] - 65248);
            }
        }
        return new String(c);
    }
    
    // 半角转化为全角的方法
    public static String ToSBC(String input)
    {
        // 半角转全角：
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++)
        {
            if (c[i] == 32)
            {
                c[i] = (char)12288;
                continue;
            }
            if (c[i] < 127 && c[i] > 32)
                c[i] = (char)(c[i] + 65248);
        }
        return new String(c);
    }
    
    private static boolean isChinese(char c)
    {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
            || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)
        {
            return true;
        }
        return false;
    }
    
    /**
     * 去除特殊字符或将所有中文标号替换为英文标号
     * 
     * @param str
     * @return
     */
    public static String stringFilter(String str)
    {
        str = str.replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}
