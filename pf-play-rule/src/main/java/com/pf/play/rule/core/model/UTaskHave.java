package com.pf.play.rule.core.model;

import java.util.Date;

public class UTaskHave {
    /**
     * 
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 会员id
     *
     * @mbggenerated
     */
    private Integer memberId;

    /**
     * 任务id
     *
     * @mbggenerated
     */
    private Integer taskId;

    /**
     * 剩余时间
     *
     * @mbggenerated
     */
    private Integer surplusTime;

    /**
     * 剩余天数
     *
     * @mbggenerated
     */
    private Integer surplusDay;

    /**
     * 开始时间
     *
     * @mbggenerated
     */
    private Date startTime;

    /**
     * 结束时间
     *
     * @mbggenerated
     */
    private Date endTime;
    private String  startTimeStr;
    private String  endTimeStr;


    /**
     * 已经生产总量
     *
     * @mbggenerated
     */
    private Double alreadyNum;

    /**
     * 剩余生产量
     *
     * @mbggenerated
     */
    private Double surplusNum;
    /**
     * 总产数
     *
     * @mbggenerated
     */
    private Double totalNum;
    /**
     * 赠送活跃度
     *
     * @mbggenerated
     */
    private Double giveActiveNum;

    /**
     * 创建日期：存的日期格式20160530
     *
     * @mbggenerated
     */
    private Integer curday;

    /**
     * 当前状态 : 1、进行中 2、已完成 3、已过期
     *
     * @mbggenerated
     */
    private Integer currentState;

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
     * 玩法等级
     *
     * @mbggenerated
     */
    private Integer taskLevel;

    /**
     * 赠送类型  0:购买   1:HLv0     2:HLv1     3:HLv2      4:HLv3      5:HLv4      6:HLv5
     *
     * @mbggenerated
     */
    private Integer giveType;

    /**
     * 任务的统计数
     *
     * @mbggenerated
     */
    private Integer taskCount;

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

    private DisTaskAttribute  disTaskAttribute;

    private Double alreadyNumCount;

    private Double surplusNumCount;

    public Integer getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(Integer taskCount) {
        this.taskCount = taskCount;
    }

    public DisTaskAttribute getDisTaskAttribute() {
        return disTaskAttribute;
    }

    public void setDisTaskAttribute(DisTaskAttribute disTaskAttribute) {
        this.disTaskAttribute = disTaskAttribute;
    }

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

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getSurplusTime() {
        return surplusTime;
    }

    public void setSurplusTime(Integer surplusTime) {
        this.surplusTime = surplusTime;
    }

    public Integer getSurplusDay() {
        return surplusDay;
    }

    public void setSurplusDay(Integer surplusDay) {
        this.surplusDay = surplusDay;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public Integer getCurday() {
        return curday;
    }

    public void setCurday(Integer curday) {
        this.curday = curday;
    }

    public Integer getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Integer currentState) {
        this.currentState = currentState;
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

    public Integer getTaskLevel() {
        return taskLevel;
    }

    public void setTaskLevel(Integer taskLevel) {
        this.taskLevel = taskLevel;
    }

    public Integer getGiveType() {
        return giveType;
    }

    public void setGiveType(Integer giveType) {
        this.giveType = giveType;
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


    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public Double getAlreadyNumCount() {
        return alreadyNumCount;
    }

    public void setAlreadyNumCount(Double alreadyNumCount) {
        this.alreadyNumCount = alreadyNumCount;
    }

    public Double getSurplusNumCount() {
        return surplusNumCount;
    }

    public void setSurplusNumCount(Double surplusNumCount) {
        this.surplusNumCount = surplusNumCount;
    }

    public Double getAlreadyNum() {
        return alreadyNum;
    }

    public void setAlreadyNum(Double alreadyNum) {
        this.alreadyNum = alreadyNum;
    }

    public Double getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(Double surplusNum) {
        this.surplusNum = surplusNum;
    }

    public Double getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Double totalNum) {
        this.totalNum = totalNum;
    }

    public Double getGiveActiveNum() {
        return giveActiveNum;
    }

    public void setGiveActiveNum(Double giveActiveNum) {
        this.giveActiveNum = giveActiveNum;
    }
}