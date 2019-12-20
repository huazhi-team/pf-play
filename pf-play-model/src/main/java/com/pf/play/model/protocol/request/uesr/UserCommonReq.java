package com.pf.play.model.protocol.request.uesr;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/26 13:44
 * @Version 1.0
 */
public class UserCommonReq {
    private Integer memberId;
    private String  wxOpenId;
    private String  token;

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
}
