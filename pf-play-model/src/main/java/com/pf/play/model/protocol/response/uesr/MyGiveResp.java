package com.pf.play.model.protocol.response.uesr;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/12/30 15:14
 * @Version 1.0
 */
public class MyGiveResp {
    private  String   token;
    private  String   wxOpenId;
    private String   phone;
    private Double    masonryCount;
    private  String   payPw;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Double getMasonryCount() {
        return masonryCount;
    }

    public void setMasonryCount(Double masonryCount) {
        this.masonryCount = masonryCount;
    }

    public String getPayPw() {
        return payPw;
    }

    public void setPayPw(String payPw) {
        this.payPw = payPw;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
