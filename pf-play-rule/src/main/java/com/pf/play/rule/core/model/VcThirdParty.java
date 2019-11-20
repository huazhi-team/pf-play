package com.pf.play.rule.core.model;

public class VcThirdParty {
    /**
     * 自增主键ID
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 用户ID:对应vc_member表的member_id
     *
     * @mbggenerated
     */
    private Integer memberId;

    /**
     * 登录token
     *
     * @mbggenerated
     */
    private String token;

    /**
     * token过期时间
     *
     * @mbggenerated
     */
    private Integer tokenExpire;

    /**
     * 极光推送token
     *
     * @mbggenerated
     */
    private String jpushToken;

    /**
     * 微信的access
     *
     * @mbggenerated
     */
    private String wxAccess;

    /**
     * 微信的refresh
     *
     * @mbggenerated
     */
    private String wxRefresh;

    /**
     * 微信的openid
     *
     * @mbggenerated
     */
    private String wxOpenid;

    /**
     * 微信unionid
     *
     * @mbggenerated
     */
    private String wxUnionid;

    /**
     * 是否有效; 1有效 2无效
     *
     * @mbggenerated
     */
    private Boolean isValid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getTokenExpire() {
        return tokenExpire;
    }

    public void setTokenExpire(Integer tokenExpire) {
        this.tokenExpire = tokenExpire;
    }

    public String getJpushToken() {
        return jpushToken;
    }

    public void setJpushToken(String jpushToken) {
        this.jpushToken = jpushToken;
    }

    public String getWxAccess() {
        return wxAccess;
    }

    public void setWxAccess(String wxAccess) {
        this.wxAccess = wxAccess;
    }

    public String getWxRefresh() {
        return wxRefresh;
    }

    public void setWxRefresh(String wxRefresh) {
        this.wxRefresh = wxRefresh;
    }

    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }

    public String getWxUnionid() {
        return wxUnionid;
    }

    public void setWxUnionid(String wxUnionid) {
        this.wxUnionid = wxUnionid;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
}