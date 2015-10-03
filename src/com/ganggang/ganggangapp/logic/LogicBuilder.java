package com.ganggang.ganggangapp.logic;


import com.ganggang.frame.freamwork.logic.BaseLogicBuilder;
import com.ganggang.ganggangapp.bean.BusinessBrand;
import com.ganggang.ganggangapp.constant.ConstantMessage.IMap;
import com.ganggang.ganggangapp.constant.ConstantMessage.ISreach;
import com.ganggang.ganggangapp.logic.advlogic.IAdvLogic;
import com.ganggang.ganggangapp.logic.advlogic.impl.AdvLogic;
import com.ganggang.ganggangapp.logic.businessbrandlogic.IBusinessBrandLogic;
import com.ganggang.ganggangapp.logic.businessbrandlogic.impl.BusinessBrandLogic;
import com.ganggang.ganggangapp.logic.businessdetaillogic.IBusinessDetailLogic;
import com.ganggang.ganggangapp.logic.businessdetaillogic.impl.BusinessDetailLogic;
import com.ganggang.ganggangapp.logic.businesslogic.IBusinessLogic;
import com.ganggang.ganggangapp.logic.businesslogic.impl.BusinessLogic;
import com.ganggang.ganggangapp.logic.comment.ICommentLogic;
import com.ganggang.ganggangapp.logic.comment.impl.CommentLogic;
import com.ganggang.ganggangapp.logic.homelogic.IHomeLogic;
import com.ganggang.ganggangapp.logic.homelogic.impl.HomeLogic;
import com.ganggang.ganggangapp.logic.loginlogic.ILoginLogic;
import com.ganggang.ganggangapp.logic.loginlogic.impl.LoginLogic;
import com.ganggang.ganggangapp.logic.mainlogic.IMainLogic;
import com.ganggang.ganggangapp.logic.mainlogic.impl.MainLogic;
import com.ganggang.ganggangapp.logic.maplogic.IMapLogic;
import com.ganggang.ganggangapp.logic.maplogic.impl.MapLogic;
import com.ganggang.ganggangapp.logic.modifylogic.IModifyLogic;
import com.ganggang.ganggangapp.logic.modifylogic.impl.ModifyLogic;
import com.ganggang.ganggangapp.logic.navigation.INavigationLogic;
import com.ganggang.ganggangapp.logic.navigation.impl.NavigationLogic;
import com.ganggang.ganggangapp.logic.registerlogic.IRegisterLogic;
import com.ganggang.ganggangapp.logic.registerlogic.impl.RegisterLogic;
import com.ganggang.ganggangapp.logic.sreachlogic.ISreachLogic;
import com.ganggang.ganggangapp.logic.sreachlogic.impl.SreachLogic;
import com.ganggang.ganggangapp.logic.userinfologic.IUserInfoLogic;
import com.ganggang.ganggangapp.logic.userinfologic.impl.UserInfoLogic;
import com.ganggang.ganggangapp.ui.userinfo.ModifyActivity;

import android.content.Context;


/**
 * logicBuilder 注册
 * @author 曾宝
 * @version  [V1.00, 2015年7月14日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class LogicBuilder extends BaseLogicBuilder
{
    /**
     * DEBUG_TAG
     */
    private static final String TAG = "LogicBuilder";
    
    /**
     * 单例对象
     */
    private static LogicBuilder sInstance;
    
    /**
     * 单例模式 
     * <功能详细描述>
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static LogicBuilder getInstance(Context context)
    {
        if (null == sInstance)
        {
            sInstance = new LogicBuilder(context);
        }
        return sInstance;
    }
    
    /**
     * DEFAULT_CONSTRUCTOR
     * 
     * @param context
     *            Context
     */
    private LogicBuilder(Context context)
    {
        super(context);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void initLogic(Context context)
    {
        registAllLogics(context);
    }
    
    /**
     * 
     * @param context
     *            Context
     */
    private void registAllLogics(Context context)
    {
        MainLogic mainLogic = new MainLogic(context);
        registerLogic(IMainLogic.class, mainLogic);
        
        AdvLogic advLogic = new AdvLogic(context);
        registerLogic(IAdvLogic.class, advLogic);
        
        HomeLogic homeLogic = new HomeLogic(context);
        registerLogic(IHomeLogic.class, homeLogic);
        
        MapLogic mapLogic = new MapLogic(context);
        registerLogic(IMapLogic.class, mapLogic);
        
        BusinessLogic businessLogic = new BusinessLogic(context);
        registerLogic(IBusinessLogic.class, businessLogic);
        
        BusinessDetailLogic businessDetailLogic = new BusinessDetailLogic(context);
        registerLogic(IBusinessDetailLogic.class, businessDetailLogic);
        
        CommentLogic commentLogic = new CommentLogic(context);
        registerLogic(ICommentLogic.class, commentLogic);
        
        BusinessBrandLogic brandLogic = new BusinessBrandLogic(context);
        registerLogic(IBusinessBrandLogic.class, brandLogic);
        
        SreachLogic sreachlogic = new SreachLogic(context);
        registerLogic(ISreachLogic.class, sreachlogic);
    
        LoginLogic loginLogic = new LoginLogic(context);
        registerLogic(ILoginLogic.class, loginLogic);
        
        RegisterLogic registerLogic = new RegisterLogic(context);
        registerLogic(IRegisterLogic.class,registerLogic);
        
        NavigationLogic navigationLogic = new NavigationLogic(context);
        registerLogic(INavigationLogic.class, navigationLogic);
        
        UserInfoLogic userInfoLogic = new UserInfoLogic(context);
        registerLogic(IUserInfoLogic.class, userInfoLogic);
        
        ModifyLogic modifyLogic = new ModifyLogic(context);
        registerLogic(IModifyLogic.class, modifyLogic);
    }
    
}
