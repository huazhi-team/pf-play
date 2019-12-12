package com.pf.play.common.push.common;

import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.pf.play.common.push.param.AppPushParam;


public interface IAppPush {
	
	
	/**
	 * app 推送服务
	 * @param appPushParam
	 * @return
	 * @throws HttpProcessException 
	 */
	public boolean push(AppPushParam appPushParam);
}
