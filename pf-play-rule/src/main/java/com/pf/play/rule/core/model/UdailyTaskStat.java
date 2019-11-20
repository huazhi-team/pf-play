package com.pf.play.rule.core.model;

import java.util.Date;

public class UdailyTaskStat {
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
     * 点赞次数
     *
     * @mbggenerated
     */
    private Integer acceptNumber;

    /**
     * 阅读文章分钟
     *
     * @mbggenerated
     */
    private Float readArticleMinute;

    /**
     * 玩游戏
     *
     * @mbggenerated
     */
    private Float playGameMinute;

    /**
     * 查看广告次数
     *
     * @mbggenerated
     */
    private Integer lookAdNum;

    /**
     * 查看商品次数
     *
     * @mbggenerated
     */
    private Integer lookCommodityNum;

    /**
     * 是否完成  1、未完成  2、完成
     *
     * @mbggenerated
     */
    private Boolean isComplete;

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
    private Boolean curhour;

    /**
     * 创建所属分钟：60分钟制
     *
     * @mbggenerated
     */
    private Boolean curminute;

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

    public Integer getAcceptNumber() {
        return acceptNumber;
    }

    public void setAcceptNumber(Integer acceptNumber) {
        this.acceptNumber = acceptNumber;
    }

    public Float getReadArticleMinute() {
        return readArticleMinute;
    }

    public void setReadArticleMinute(Float readArticleMinute) {
        this.readArticleMinute = readArticleMinute;
    }

    public Float getPlayGameMinute() {
        return playGameMinute;
    }

    public void setPlayGameMinute(Float playGameMinute) {
        this.playGameMinute = playGameMinute;
    }

    public Integer getLookAdNum() {
        return lookAdNum;
    }

    public void setLookAdNum(Integer lookAdNum) {
        this.lookAdNum = lookAdNum;
    }

    public Integer getLookCommodityNum() {
        return lookCommodityNum;
    }

    public void setLookCommodityNum(Integer lookCommodityNum) {
        this.lookCommodityNum = lookCommodityNum;
    }

    public Boolean getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Boolean isComplete) {
        this.isComplete = isComplete;
    }

    public Integer getCurday() {
        return curday;
    }

    public void setCurday(Integer curday) {
        this.curday = curday;
    }

    public Boolean getCurhour() {
        return curhour;
    }

    public void setCurhour(Boolean curhour) {
        this.curhour = curhour;
    }

    public Boolean getCurminute() {
        return curminute;
    }

    public void setCurminute(Boolean curminute) {
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

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
}