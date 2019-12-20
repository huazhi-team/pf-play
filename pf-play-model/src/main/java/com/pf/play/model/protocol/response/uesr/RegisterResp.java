package com.pf.play.model.protocol.response.uesr;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/8 14:38
 * @Version 1.0
 */
public class RegisterResp {
    private  String  token ;
    private  Integer stateCode;
    private  Integer isLogin;
    private  String timeStamp;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getStateCode() {
        return stateCode;
    }

    public void setStateCode(Integer stateCode) {
        this.stateCode = stateCode;
    }

    public Integer getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Integer isLogin) {
        this.isLogin = isLogin;
    }
}
