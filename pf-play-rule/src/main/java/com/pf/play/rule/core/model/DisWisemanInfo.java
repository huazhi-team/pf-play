package com.pf.play.rule.core.model;

import java.util.Date;

public class DisWisemanInfo {
    /**
     * 自增id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 达人id
     *
     * @mbggenerated
     */
    private Integer wisemanId;

    /**
     * 达人名称
     *
     * @mbggenerated
     */
    private String wisemanName;

    /**
     * 显示要求：显示给列表信息的要求
     *
     * @mbggenerated
     */
    private String remarks;

    /**
     * 对应等级,对应用户表的等级
     *
     * @mbggenerated
     */
    private Integer relativeLevel;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 是否有效; 1有效 2无效
     *
     * @mbggenerated
     */
    private Integer isValid;
    /**
     * 是否显示; 1显示 2不显示
     *
     * @mbggenerated
     */
    private  Integer isDisplay;
    /**
     * 是否能领取; 1 已领取 2、可以领取 3、不能领取
     *
     * @mbggenerated
     */
    private  Integer isReceive;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWisemanId() {
        return wisemanId;
    }

    public void setWisemanId(Integer wisemanId) {
        this.wisemanId = wisemanId;
    }

    public String getWisemanName() {
        return wisemanName;
    }

    public void setWisemanName(String wisemanName) {
        this.wisemanName = wisemanName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getRelativeLevel() {
        return relativeLevel;
    }

    public void setRelativeLevel(Integer relativeLevel) {
        this.relativeLevel = relativeLevel;
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

    public Integer getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(Integer isDisplay) {
        this.isDisplay = isDisplay;
    }

    public Integer getIsReceive() {
        return isReceive;
    }

    public void setIsReceive(Integer isReceive) {
        this.isReceive = isReceive;
    }
}