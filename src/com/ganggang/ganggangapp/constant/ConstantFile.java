package com.ganggang.ganggangapp.constant;
/**
 * 
 *数据  
 * @author 曾宝
 * @version  [V1.00, 2015年7月30日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class ConstantFile
{
 
    public interface IHotWord
    {
        public static final String FileName = "Hotword";
        
        public static final String hotWordsId = "hotWordsId";
        
        public static final String hotWordsName="hotWordsName";
        
        public static final String hotWordsNameComplex="hotWordsNameComplex";
    }
    
    public interface IBusinessTitle
    {
        public static final  String FileName="BusinessTitle";
        
        public static final String ID = "titleId";
        
        public static final String NAME = "titleName";
        
        public static final String NAMECOMPLEX="titleNameComplex";
    }
    
    public interface IUserFile
    {
        public static final String FileName = "UserFile";
        
        public static final String USERID="userId";
        
        public static final String LOGINNAME = "userLoginName";
        
        public static final String LOGINPASSWORD = "userLoginPassword";
        
        public static final String USERNAME = "userName";
        
        public static final String USERSEX ="userSex";
        
        
        
    }
    
    
    public interface IFileConfig
    {
        public static final String FileName ="fileConfig";
        
        public static final String GUIDE_ADV_NUM = "guide_adv_num";
        
        public static final String HOMEBANNER_ADV_NUM="homebanner_adv_num";
        
        public static final String HOMEBOTTOM_ADV_NUM = "homebottom_adv_num";
        
        public static final String BUSINESSTITLE_NUM="businessTitle_num";
        
        public static final String HOTWORD_NUM="hotword_num";
        
    }
    
    public interface IAdv
    {
        public static final String FileName="adv";
            
         public static final String AdvID="advID";
         
         public static final String AdvName = "advName";
         
         public static final String AdvLocationCode="advLocationCode";
         
         public static final String AdvNumber="advNumber";
         
         public static final String AdvUrl = "advUrl";
         
         public static final String AdvPicture="advPicture";
    }
    
    
    public interface IMore
    {
        //文件夹
        public static final String FileName="more";
        //简体/繁体 翻译
        public static final String MORE_TRANSLATION = "flag_more_translation";
         //模式
        public static final String MORE_MODEL = "flag_more_model";
        //节省流量
        public static final String MODE_ECONOMIZE="flag_more_economize";
        
        public static final String FIRST_APP="first_app";
    }
    
    public interface IBusinessType
    {
        public static final String FileName = "businessTypel";
        
        public static final String CODE="enumerationCode";
        
        public static final String NAME="enumerationName";
            
        public static final String NAMECOMPLEX="enumerationNameComplex";
    }
    
    /**
     *back键 
     *  
     * @author 曾宝
     * @version  [V1.00, 2015年8月19日]
     * @see  [相关类/方法]
     * @since V1.00
     */
    public interface IBackFile
    {
        public static final String File_Back_sreach = "File_Back_sreach";
        
        
        
        //参数
        public static final String File_Args="File_Back_args";
    }
}
