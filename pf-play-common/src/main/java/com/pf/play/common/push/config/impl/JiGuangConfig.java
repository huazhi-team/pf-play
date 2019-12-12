package com.pf.play.common.push.config.impl;


import com.pf.play.common.push.config.AppPushConfig;

/**
 * 极光推送配置信息
 * @author lijunkui
 *
 */
public class JiGuangConfig implements AppPushConfig {
//   private  String masterSecret = "";
//   private  String appKey = "";
private  String masterSecret = "b1bcae8402ceaeb1e7928bd0";
	private  String appKey = "9e8c23f670efe7dba280b6a6";
   private  String pushUrl = "https://api.jpush.cn/v3/push";
	@Override
	public String getAppKey() {
		return appKey;
	}
	@Override
	public String getPushUrl() {
		return pushUrl;
	}
	@Override
	public String getMasterSecret() {
		return masterSecret;
	}
	   
}
