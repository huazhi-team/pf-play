package com.pf.play.rule.core.model;

import java.util.Date;

public class DisVitalityValue {
    /**
     * 自增id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 活力值id
     *
     * @mbggenerated
     */
    private Integer vitalityId;

    /**
     * 等级名称
     *
     * @mbggenerated
     */
    private String vitalityName;

    /**
     * 直推要求
     *
     * @mbggenerated
     */
    private Integer pushNumber;

    /**
     * 团队活力值
     *
     * @mbggenerated
     */
    private Integer teamVitalitNum;

    /**
     * 联盟联盟值
     *
     * @mbggenerated
     */
    private Integer allianceVitalitNum;

    /**
     * 升级需要数量
     *
     * @mbggenerated
     */
    private Integer upgradeNum  ;
    /**
     * 等级
     */
    private Integer darenLevel  ;

    /**
     * 分红
     *
     * @mbggenerated
     */
    private Double rewardNum;



    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 修改时间
     *
     * @mbggenerated
     */
    private Date updateTime;
    /**
     * 备注
     *
     * @mbggenerated
     */
    private String  remarks;

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

    public Integer getVitalityId() {
        return vitalityId;
    }

    public void setVitalityId(Integer vitalityId) {
        this.vitalityId = vitalityId;
    }

    public String getVitalityName() {
        return vitalityName;
    }

    public void setVitalityName(String vitalityName) {
        this.vitalityName = vitalityName;
    }

    public Integer getPushNumber() {
        return pushNumber;
    }

    public void setPushNumber(Integer pushNumber) {
        this.pushNumber = pushNumber;
    }

    public Integer getTeamVitalitNum() {
        return teamVitalitNum;
    }

    public void setTeamVitalitNum(Integer teamVitalitNum) {
        this.teamVitalitNum = teamVitalitNum;
    }

    public Integer getAllianceVitalitNum() {
        return allianceVitalitNum;
    }

    public void setAllianceVitalitNum(Integer allianceVitalitNum) {
        this.allianceVitalitNum = allianceVitalitNum;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getUpgradeNum() {
        return upgradeNum;
    }

    public void setUpgradeNum(Integer upgradeNum) {
        this.upgradeNum = upgradeNum;
    }

    public Integer getDarenLevel() {
        return darenLevel;
    }

    public void setDarenLevel(Integer darenLevel) {
        this.darenLevel = darenLevel;
    }

    public Double getRewardNum() {
        return rewardNum;
    }

    public void setRewardNum(Double rewardNum) {
        this.rewardNum = rewardNum;
    }
}