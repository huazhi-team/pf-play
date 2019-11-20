package com.pf.play.model.protocol.request.uesr;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/13 20:36
 * @Version 1.0
 */
public class LoginReq extends BaseReq {
    /****
     * 微信openid
     */
    private String wxOpenId;
    /**
     * 登陆类型  1、是微信 2、手机号+密码
     */
    private Integer loginType;
    /**
     * 手机号
     */
    private String phone;
    /***
     * 密码
     */
    private String password;

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
