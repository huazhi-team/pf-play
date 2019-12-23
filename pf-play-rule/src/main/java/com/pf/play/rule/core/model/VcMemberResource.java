package com.pf.play.rule.core.model;

import java.util.Date;
import java.util.List;

public class VcMemberResource {
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
     * 自产砖石总数
     *
     * @mbggenerated
     */
    private Double produceMasonryCount;

    /**
     * 推广砖石总数
     *
     * @mbggenerated
     */
    private Double extensionMasonryCount;

    /**
     * 当前总砖石
     *
     * @mbggenerated
     */
    private Double dayMasonry;

    /**
     * 当天推广砖石
     *
     * @mbggenerated
     */
    private Double dayExtMasonry;

    /**
     * 砖石冻结数量
     *
     * @mbggenerated
     */
    private Double frozenMasonry;
    /**
     * 魅力值
     *
     * @mbggenerated
     */
    private Double charmValue;

    /**
     * 经验值
     *
     * @mbggenerated
     */
    private Double empiricalValue;

    /**
     * 活跃值
     *
     * @mbggenerated
     */
    private Double activeValue;

    /**
     * 直推总人数
     *
     * @mbggenerated
     */
    private Integer pushPeople;

    /**
     * 用户注册时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 用户登录时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 经验值等级
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
     * 团队总人数
     *
     * @mbggenerated
     */
    private Integer teamPeople;
    /**
     * 团队总活力值
     *
     * @mbggenerated
     */
    private Double teamActive;
    /**
     * 英雄活力值
     *
     * @mbggenerated
     */
    private Double heroActive;
    /**
     *联盟活力值
     *
     * @mbggenerated
     */
    private Double allianceActive;
    /**
     * 是否重新计算活力值： 1、否  2 、是
     *
     * @mbggenerated
     */
    private Integer isVitalityValue;

    /**
     * 是否有效; 1有效 2无效
     *
     * @mbggenerated
     */
    private Integer isValid;
    /**
     * 登录时间
     *
     * @mbggenerated
     */
    private String uqdateTimeStr;
    /**
     * 注册时间
     *
     * @mbggenerated
     */
    private String createTimeStr;
    /**
     * 昵称
     *
     * @mbggenerated
     */
    private String nickName;
    /**
     * 昵称
     *
     * @mbggenerated
     */
    private String phone;

    /**
     * 上级id
     *
     * @mbggenerated
     */
    private Integer superiorId;


    private List<Integer> idList;
    /**
     * 减活跃值
     *
     * @mbggenerated
     */
    private Double activeValueCut;

    /**
     * 加活跃值
     *
     * @mbggenerated
     */
    private Double activeValueAdd;
    /**
     * 全部直推总人数
     *
     * @mbggenerated
     */
    private Double allPushPeople;
    /**
     * 全部直推总人数
     *
     * @mbggenerated
     */
    private Double allTeamPeople;

    /**
     * 是否实名制
     *
     * @mbggenerated
     */
    private Integer isCertification;

    /**
     * 头像地址
     *
     * @mbggenerated
     */
    private String memberAdd;

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

    public Double getProduceMasonryCount() {
        return produceMasonryCount;
    }

    public void setProduceMasonryCount(Double produceMasonryCount) {
        this.produceMasonryCount = produceMasonryCount;
    }

    public Double getExtensionMasonryCount() {
        return extensionMasonryCount;
    }

    public void setExtensionMasonryCount(Double extensionMasonryCount) {
        this.extensionMasonryCount = extensionMasonryCount;
    }

    public Double getDayMasonry() {
        return dayMasonry;
    }

    public void setDayMasonry(Double dayMasonry) {
        this.dayMasonry = dayMasonry;
    }

    public Double getDayExtMasonry() {
        return dayExtMasonry;
    }

    public void setDayExtMasonry(Double dayExtMasonry) {
        this.dayExtMasonry = dayExtMasonry;
    }

    public Double getFrozenMasonry() {
        return frozenMasonry;
    }

    public void setFrozenMasonry(Double frozenMasonry) {
        this.frozenMasonry = frozenMasonry;
    }



    public Integer getPushPeople() {
        return pushPeople;
    }

    public void setPushPeople(Integer pushPeople) {
        this.pushPeople = pushPeople;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public Integer getTeamPeople() {
        return teamPeople;
    }

    public void setTeamPeople(Integer teamPeople) {
        this.teamPeople = teamPeople;
    }



    public Double getHeroActive() {
        return heroActive;
    }

    public void setHeroActive(Double heroActive) {
        this.heroActive = heroActive;
    }

    public Double getAllianceActive() {
        return allianceActive;
    }

    public void setAllianceActive(Double allianceActive) {
        this.allianceActive = allianceActive;
    }

    public Integer getIsVitalityValue() {
        return isVitalityValue;
    }

    public void setIsVitalityValue(Integer isVitalityValue) {
        this.isVitalityValue = isVitalityValue;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getUqdateTimeStr() {
        return uqdateTimeStr;
    }

    public void setUqdateTimeStr(String uqdateTimeStr) {
        this.uqdateTimeStr = uqdateTimeStr;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

    public Integer getIsCertification() {
        return isCertification;
    }

    public void setIsCertification(Integer isCertification) {
        this.isCertification = isCertification;
    }

    public Double getActiveValueCut() {
        return activeValueCut;
    }

    public void setActiveValueCut(Double activeValueCut) {
        this.activeValueCut = activeValueCut;
    }

    public Double getActiveValueAdd() {
        return activeValueAdd;
    }

    public void setActiveValueAdd(Double activeValueAdd) {
        this.activeValueAdd = activeValueAdd;
    }

    public Double getEmpiricalValue() {
        return empiricalValue;
    }

    public void setEmpiricalValue(Double empiricalValue) {
        this.empiricalValue = empiricalValue;
    }

    public Double getActiveValue() {
        return activeValue;
    }

    public void setActiveValue(Double activeValue) {
        this.activeValue = activeValue;
    }

    public Double getTeamActive() {
        return teamActive;
    }

    public void setTeamActive(Double teamActive) {
        this.teamActive = teamActive;
    }

    public Double getCharmValue() {
        return charmValue;
    }

    public void setCharmValue(Double charmValue) {
        this.charmValue = charmValue;
    }

    public Integer getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(Integer superiorId) {
        this.superiorId = superiorId;
    }

    public Double getAllPushPeople() {
        return allPushPeople;
    }

    public void setAllPushPeople(Double allPushPeople) {
        this.allPushPeople = allPushPeople;
    }

    public Double getAllTeamPeople() {
        return allTeamPeople;
    }

    public void setAllTeamPeople(Double allTeamPeople) {
        this.allTeamPeople = allTeamPeople;
    }

    public String getMemberAdd() {
        return memberAdd;
    }

    public void setMemberAdd(String memberAdd) {
        this.memberAdd = memberAdd;
    }
}