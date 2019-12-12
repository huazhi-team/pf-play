package com.pf.play.common.push.common;

import com.pf.play.common.push.config.AppPushConfig;
import com.pf.play.common.push.convert.IAppPushParamConverter;
import com.pf.play.common.push.impl.JiGuangPushImpl;
import com.pf.play.common.push.param.AppPushParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAppPush implements IAppPush{
	
	private static Logger log = LoggerFactory.getLogger(JiGuangPushImpl.class);
	
    protected IAppPushParamConverter appPushParamConverter;
    protected AppPushConfig appPushConfig;
    
	/**
	 * @param appPushParam
	 * @return
	 */
	public boolean push(AppPushParam appPushParam){
		
		String pushParmJsonStr = convertAppPushParam(appPushParam);
    	String authorization = getAuthorization(appPushConfig);
    	
    	String returnJson = post(appPushConfig,authorization,pushParmJsonStr);
    	if(returnJson!=null){
    		log.info("app push sucess:"+returnJson);
    		return true;
    	}
    	log.info("app push fail!");
    	return false;
	}
	
	
	/**
	 * 认证信息获取
	 * @param appPushConfig
	 * @return
	 */
	public abstract String getAuthorization(AppPushConfig appPushConfig);
	/**
	 *  参数转换
	 *  @param appPushParam
	 */
	public abstract String convertAppPushParam(AppPushParam appPushParam);
	/**
	 *  调用推送第三放服务
	 * @param appPushConfig
	 * @param authorization
	 * @param pushParmJsonStr
	 * @return
	 */
	public abstract String post(AppPushConfig appPushConfig, String authorization, String pushParmJsonStr);
}
