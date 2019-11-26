package com.pf.play.model.protocol.request.uesr;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/26 13:44
 * @Version 1.0
 */
public class UserCommonReq {

    private String  wxOpenid;
    private String  token;

    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
