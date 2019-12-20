package com.pf.play.model.protocol.request.uesr;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/8 11:59
 * @Version 1.0
 */
public class RegisterReq{

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 协议版本号
     */
    private  String  version;
    /**
     * 短信验证码
     */
    private  String  smsVerification;
    /**
     * 手机号
     */
    private  String  phone;
    /**
     * 微信名
     */
    private  String  wxName;

    /**
     * 微信头像
     */
    private  String  memberAdd;

    /**
     * 会员类型  1、归属 2、邀请制
     */
    private  Integer memberType;

    /**
     * 微信长期使用获取信息 30 天内。
     */
    private  String  wxRefresh;

    /**
     * 注册类型 1 、手机号  2 、邮箱  3、微信
     */
    private  Integer registerType;


    /***
     * wx unionid 信息
     */
    private  String  wxUnionid;
    /**
     * 邀请码
     */
    private  String  inviteCode;
    /**
     * 注册类型
     */
    private  String  type;

    /***
     * 微信openid
     */
    private  String  wxOpenid;

    /**
     * 归属
     */
    private  Integer  owner;

    /**
     * 时间戳
     */
    private  String  timeStamp;

    /**
     * 是否启用预判端
     */
    private  Integer  isLogin;

    public Integer getRegisterType() {
        return registerType;
    }

    public void setRegisterType(Integer registerType) {
        this.registerType = registerType;
    }

    public String getWxUnionid() {
        return wxUnionid;
    }

    public void setWxUnionid(String wxUnionid) {
        this.wxUnionid = wxUnionid;
    }

    public String getMemberAdd() {
        return memberAdd;
    }

    public void setMemberAdd(String memberAdd) {
        this.memberAdd = memberAdd;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }
    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSmsVerification() {
        return smsVerification;
    }

    public void setSmsVerification(String smsVerification) {
        this.smsVerification = smsVerification;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getWxRefresh() {
        return wxRefresh;
    }

    public void setWxRefresh(String wxRefresh) {
        this.wxRefresh = wxRefresh;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Integer isLogin) {
        this.isLogin = isLogin;
    }
}
