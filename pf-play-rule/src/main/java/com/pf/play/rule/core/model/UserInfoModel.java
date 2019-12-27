package com.pf.play.rule.core.model;

import java.util.Date;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/8 17:12
 * @Version 1.0
 */
public class UserInfoModel {
    /**
     *
     *
     * @mbggenerated
     */
    private Integer memberId;
    /**
     * 微信openId
     */
    private String  wxOpenid;

    /**
     * 所属用户id
     *
     * @mbggenerated
     */
    private Integer ownerMemberId;

    /**
     * 会员头像
     *
     * @mbggenerated
     */
    private String memberAdd;

    /**
     * 会员昵称
     *
     * @mbggenerated
     */
    private String nickname;

    /**
     * 密码
     *
     * @mbggenerated
     */
    private String password;

    /**
     * 会员编号
     *
     * @mbggenerated
     */
    private String memberCode;

    /**
     * 会员类型 1、归属 2、邀请制
     *
     * @mbggenerated
     */
    private Integer memberType;

    /**
     * 当前经验等级:1、1
     *
     * @mbggenerated
     */
    private Integer empiricalLevel;

    /**
     * 达人等级
     *
     * @mbggenerated
     */
    private Integer darenLevel;

    /**
     * 是否重新计算活力值： 1、否  2 、是
     *
     * @mbggenerated
     */
    private Integer isVitalityValue;

    /**
     * 邀请码
     *
     * @mbggenerated
     */
    private String inviteCode;

    /**
     * 交易所地址
     *
     * @mbggenerated
     */
    private String tradingAddress;

    /**
     * 是否实名 1、未实名  2、已实名
     *
     * @mbggenerated
     */
    private Integer isCertification;

    /**
     * 用户注册时间
     *
     * @mbggenerated
     */
    private Integer createTime;

    /**
     * 用户当前状态：1、正常用户 2、黑名单
     *
     * @mbggenerated
     */
    private Integer isActive;

    /**
     * 用户登录时间
     *
     * @mbggenerated
     */
    private Integer loginTime;

    /***
     * 机器id
     */
    private String  deviceId;

    /**
     * 当天是否玩法结算  1、未结算、 2、结算完成
     *
     * @mbggenerated
     */
    private Integer isSett;

    /**
     * 推广人id
     *
     * @mbggenerated
     */
    private String extensionMemberId;

    /**
     * 登陆token
     */
    private String token;


    /***
     * 推广人id
     */
    private Integer superiorId;


    /**
     * 自产砖石总数
     *
     * @mbggenerated
     */
    private Float produceMasonryCount;

    /**
     * 推广砖石总数
     *
     * @mbggenerated
     */
    private Float extensionMasonryCount;

    /**
     * 当前总砖石
     *
     * @mbggenerated
     */
    private Float dayMasonry;

    /**
     * 当天推广砖石
     *
     * @mbggenerated
     */
    private Float dayExtMasonry;

    /**
     * 砖石冻结数量
     *
     * @mbggenerated
     */
    private Float frozenMasonry;

    /**
     * 经验值
     *
     * @mbggenerated
     */
    private Float empiricalValue;

    /**
     * 活跃值
     *
     * @mbggenerated
     */
    private Float activeValue;

    /**
     * 直推总人数
     *
     * @mbggenerated
     */
    private Integer pushPeople;


    /**
     * 新用户赠送
     *
     * @mbggenerated
     */
    private Integer isLevel0;

    /**
     * 是否领取1级达人 1、未领取 2、已领取
     *
     * @mbggenerated
     */
    private Integer isLevel1;

    /**
     * 是否领取2级达人 1、未领取 2、已领取
     *
     * @mbggenerated
     */
    private Integer isLevel2;

    /**
     * 是否领取3级达人1、未领取 2、已领取
     *
     * @mbggenerated
     */
    private Integer isLevel3;

    /**
     * 是否领取4级达人1、未领取 2、已领取
     *
     * @mbggenerated
     */
    private Integer isLevel4;

    /**
     * 是否领取5级达人1、未领取 2、已领取
     *
     * @mbggenerated
     */
    private Integer isLevel5;
    /**
     * 是否支付了  1、未是否 2 是已支付
     *
     * @mbggenerated
     */
    private Integer isPay;

    /**
     * 支付密码
     */
    private String payPassword;


    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getOwnerMemberId() {
        return ownerMemberId;
    }

    public void setOwnerMemberId(Integer ownerMemberId) {
        this.ownerMemberId = ownerMemberId;
    }

    public String getMemberAdd() {
        return memberAdd;
    }

    public void setMemberAdd(String memberAdd) {
        this.memberAdd = memberAdd;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public Integer getEmpiricalLevel() {
        return empiricalLevel;
    }

    public void setEmpiricalLevel(Integer empiricalLevel) {
        this.empiricalLevel = empiricalLevel;
    }

    public Integer getDarenLevel() {
        return darenLevel;
    }

    public void setDarenLevel(Integer darenLevel) {
        this.darenLevel = darenLevel;
    }

    public Integer getIsVitalityValue() {
        return isVitalityValue;
    }

    public void setIsVitalityValue(Integer isVitalityValue) {
        this.isVitalityValue = isVitalityValue;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getTradingAddress() {
        return tradingAddress;
    }

    public void setTradingAddress(String tradingAddress) {
        this.tradingAddress = tradingAddress;
    }

    public Integer getIsCertification() {
        return isCertification;
    }

    public void setIsCertification(Integer isCertification) {
        this.isCertification = isCertification;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Integer loginTime) {
        this.loginTime = loginTime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getIsSett() {
        return isSett;
    }

    public void setIsSett(Integer isSett) {
        this.isSett = isSett;
    }

    public String getExtensionMemberId() {
        return extensionMemberId;
    }

    public void setExtensionMemberId(String extensionMemberId) {
        this.extensionMemberId = extensionMemberId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(Integer superiorId) {
        this.superiorId = superiorId;
    }

    public Float getProduceMasonryCount() {
        return produceMasonryCount;
    }

    public void setProduceMasonryCount(Float produceMasonryCount) {
        this.produceMasonryCount = produceMasonryCount;
    }

    public Float getExtensionMasonryCount() {
        return extensionMasonryCount;
    }

    public void setExtensionMasonryCount(Float extensionMasonryCount) {
        this.extensionMasonryCount = extensionMasonryCount;
    }

    public Float getDayMasonry() {
        return dayMasonry;
    }

    public void setDayMasonry(Float dayMasonry) {
        this.dayMasonry = dayMasonry;
    }

    public Float getDayExtMasonry() {
        return dayExtMasonry;
    }

    public void setDayExtMasonry(Float dayExtMasonry) {
        this.dayExtMasonry = dayExtMasonry;
    }

    public Float getFrozenMasonry() {
        return frozenMasonry;
    }

    public void setFrozenMasonry(Float frozenMasonry) {
        this.frozenMasonry = frozenMasonry;
    }

    public Float getEmpiricalValue() {
        return empiricalValue;
    }

    public void setEmpiricalValue(Float empiricalValue) {
        this.empiricalValue = empiricalValue;
    }

    public Float getActiveValue() {
        return activeValue;
    }

    public void setActiveValue(Float activeValue) {
        this.activeValue = activeValue;
    }

    public Integer getPushPeople() {
        return pushPeople;
    }

    public void setPushPeople(Integer pushPeople) {
        this.pushPeople = pushPeople;
    }

    public Integer getIsLevel0() {
        return isLevel0;
    }

    public void setIsLevel0(Integer isLevel0) {
        this.isLevel0 = isLevel0;
    }

    public Integer getIsLevel1() {
        return isLevel1;
    }

    public void setIsLevel1(Integer isLevel1) {
        this.isLevel1 = isLevel1;
    }

    public Integer getIsLevel2() {
        return isLevel2;
    }

    public void setIsLevel2(Integer isLevel2) {
        this.isLevel2 = isLevel2;
    }

    public Integer getIsLevel3() {
        return isLevel3;
    }

    public void setIsLevel3(Integer isLevel3) {
        this.isLevel3 = isLevel3;
    }

    public Integer getIsLevel4() {
        return isLevel4;
    }

    public void setIsLevel4(Integer isLevel4) {
        this.isLevel4 = isLevel4;
    }

    public Integer getIsLevel5() {
        return isLevel5;
    }

    public void setIsLevel5(Integer isLevel5) {
        this.isLevel5 = isLevel5;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }
}
