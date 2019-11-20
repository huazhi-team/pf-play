package com.pf.play.rule.core.model;

import java.util.Date;

public class VcRealNameStayHandle {
    /**
     * 自增主键ID
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 产生用户id
     *
     * @mbggenerated
     */
    private Integer createMemberId;

    /**
     * 收益用户id
     *
     * @mbggenerated
     */
    private String profitMemberId;

    /**
     * 处理过用户id
     *
     * @mbggenerated
     */
    private String handleMemberId;

    private String handleTeamNumber;


    /**
     * 处理状态：1、未处理 2、处理中 3、处理完成
     *
     * @mbggenerated
     */
    private Integer handleType;

    /**
     * 创建日期：存的日期格式20160530
     *
     * @mbggenerated
     */
    private Integer curday;

    /**
     * 创建所属小时：24小时制
     *
     * @mbggenerated
     */
    private Integer curhour;

    /**
     * 创建所属分钟：60分钟制
     *
     * @mbggenerated
     */
    private Integer curminute;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCreateMemberId() {
        return createMemberId;
    }

    public void setCreateMemberId(Integer createMemberId) {
        this.createMemberId = createMemberId;
    }

    public String getProfitMemberId() {
        return profitMemberId;
    }

    public void setProfitMemberId(String profitMemberId) {
        this.profitMemberId = profitMemberId;
    }

    public String getHandleMemberId() {
        return handleMemberId;
    }

    public void setHandleMemberId(String handleMemberId) {
        this.handleMemberId = handleMemberId;
    }

    public Integer getCurday() {
        return curday;
    }

    public void setCurday(Integer curday) {
        this.curday = curday;
    }

    public Integer getHandleType() {
        return handleType;
    }

    public void setHandleType(Integer handleType) {
        this.handleType = handleType;
    }

    public Integer getCurhour() {
        return curhour;
    }

    public void setCurhour(Integer curhour) {
        this.curhour = curhour;
    }

    public Integer getCurminute() {
        return curminute;
    }

    public void setCurminute(Integer curminute) {
        this.curminute = curminute;
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

    public String getHandleTeamNumber() {
        return handleTeamNumber;
    }

    public void setHandleTeamNumber(String handleTeamNumber) {
        this.handleTeamNumber = handleTeamNumber;
    }

}