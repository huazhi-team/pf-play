package com.pf.play.common.push.common;


import com.pf.play.common.push.param.AppPushParam;

public class AppPushManger {
	
	private IAppPush appPush;
	
	public AppPushManger(IAppPush appPush) {
		this.appPush = appPush;
	}
	
	public boolean push(AppPushParam appPushParam) {
		return appPush.push(appPushParam);
	}
}
