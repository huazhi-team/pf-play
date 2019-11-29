package com.pf.play.rule.core.model;

public class VcMember {
    /**
     * 
     *
     * @mbggenerated
     */
    private Integer memberId;

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
     * 用户注册时间
     *
     * @mbggenerated
     */
    private String  createTimeStr;
    /**
     * 用户登录时间
     *
     * @mbggenerated
     */
    private String  loginTimeStr;

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


    /**
     * 等级类型
     */
    private Integer dateType;

    /***
     * 推广人id
     */
    private Integer superiorId;
    /***
     * 团队总人数
     */
    private Integer teamPeople;
    /***
     * 性别 1 男 2女
     */
    private Integer sex;
    /***
     * 生日
     */
    private String birthday;
    /***
     * 省份
     */
    private String province;
    /***
     * 城市
     */
    private String city;


    public Integer getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(Integer superiorId) {
        this.superiorId = superiorId;
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

    public Integer getDateType() {
        return dateType;
    }

    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getLoginTimeStr() {
        return loginTimeStr;
    }

    public void setLoginTimeStr(String loginTimeStr) {
        this.loginTimeStr = loginTimeStr;
    }

    public Integer getTeamPeople() {
        return teamPeople;
    }

    public void setTeamPeople(Integer teamPeople) {
        this.teamPeople = teamPeople;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}