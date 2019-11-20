package com.pf.play.rule.core.model;

public class UTaskHaveModel {
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
     * 玩法id
     *
     * @mbggenerated
     */
    private String playId;

    /**
     * 玩法名称
     *
     * @mbggenerated
     */
    private String playName;

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
    private Integer startTime;

    /**
     * 结束时间
     *
     * @mbggenerated
     */
    private Integer endTime;

    /**
     * 已经生产总量
     *
     * @mbggenerated
     */
    private Float alreadyNum;

    /**
     * 剩余生产量
     *
     * @mbggenerated
     */
    private Float surplusNum;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Integer updateTime;

    /**
     * 当前状态 : 1、进行中 2、已完成 3、已过期
     *
     * @mbggenerated
     */
    private Boolean currentState;

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

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public Integer getSurplusDay() {
        return surplusDay;
    }

    public void setSurplusDay(Integer surplusDay) {
        this.surplusDay = surplusDay;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Float getAlreadyNum() {
        return alreadyNum;
    }

    public void setAlreadyNum(Float alreadyNum) {
        this.alreadyNum = alreadyNum;
    }

    public Float getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(Float surplusNum) {
        this.surplusNum = surplusNum;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Boolean currentState) {
        this.currentState = currentState;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
}