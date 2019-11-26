package com.pf.play.rule.core.model;

import java.util.Date;

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
    private Float teamActive;
    /**
     * 英雄活力值
     *
     * @mbggenerated
     */
    private Float heroActive;
    /**
     *联盟活力值
     *
     * @mbggenerated
     */
    private Float allianceActive;
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

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
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

    public Integer getTeamPeople() {
        return teamPeople;
    }

    public void setTeamPeople(Integer teamPeople) {
        this.teamPeople = teamPeople;
    }

    public Float getTeamActive() {
        return teamActive;
    }

    public void setTeamActive(Float teamActive) {
        this.teamActive = teamActive;
    }

    public Float getHeroActive() {
        return heroActive;
    }

    public void setHeroActive(Float heroActive) {
        this.heroActive = heroActive;
    }

    public Float getAllianceActive() {
        return allianceActive;
    }

    public void setAllianceActive(Float allianceActive) {
        this.allianceActive = allianceActive;
    }
}