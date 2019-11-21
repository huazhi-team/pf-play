package com.pf.play.model.protocol.response;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/7 14:01
 * @Version 1.0
 */
public class TaskResp {
    /**
     * 任务名字
     */
    private String  taskName;

    /**
     * 到期时间
     */
    private String  expireTime;
    /**
     * 点赞次数
     */
    private Integer  acceptNumber;

    /**
     * 查看广告次数
     */
    private Integer  lookAdNum;

    /**
     * 查看商品次数
     */
    private Integer  lookCommodityNum;
    /**
     * 次数
     */
    private Integer  everyNum;

    /**
     * 剩余次数
     */
    private Integer  haveNum;

    /**
     * 有效天数
     */
    private Integer  taskValidityDay;

    /**
     * 用户拥有任务数
     */
    private Integer  userTaskNumber;

    /***
     *总产量
     */
    private Double    totalNum;

    /***
     * 活跃的
     */
    private Double    activeValue;

    /***
     * 已获取砖石
     */
    private Double    alreadyMasonryCount;

    /**
     * 购买需要砖石
     */
    private Double    needResourceMasonry;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getAcceptNumber() {
        return acceptNumber;
    }

    public void setAcceptNumber(Integer acceptNumber) {
        this.acceptNumber = acceptNumber;
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

    public Integer getEveryNum() {
        return everyNum;
    }

    public void setEveryNum(Integer everyNum) {
        this.everyNum = everyNum;
    }

    public Integer getHaveNum() {
        return haveNum;
    }

    public void setHaveNum(Integer haveNum) {
        this.haveNum = haveNum;
    }

    public Integer getTaskValidityDay() {
        return taskValidityDay;
    }

    public void setTaskValidityDay(Integer taskValidityDay) {
        this.taskValidityDay = taskValidityDay;
    }

    public Integer getUserTaskNumber() {
        return userTaskNumber;
    }

    public void setUserTaskNumber(Integer userTaskNumber) {
        this.userTaskNumber = userTaskNumber;
    }

    public Double getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Double totalNum) {
        this.totalNum = totalNum;
    }

    public Double getActiveValue() {
        return activeValue;
    }

    public void setActiveValue(Double activeValue) {
        this.activeValue = activeValue;
    }

    public Double getAlreadyMasonryCount() {
        return alreadyMasonryCount;
    }

    public void setAlreadyMasonryCount(Double alreadyMasonryCount) {
        this.alreadyMasonryCount = alreadyMasonryCount;
    }

    public Double getNeedResourceMasonry() {
        return needResourceMasonry;
    }

    public void setNeedResourceMasonry(Double needResourceMasonry) {
        this.needResourceMasonry = needResourceMasonry;
    }
}
