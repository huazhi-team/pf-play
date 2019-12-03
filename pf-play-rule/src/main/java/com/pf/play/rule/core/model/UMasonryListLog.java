package com.pf.play.rule.core.model;

import java.util.Date;

public class UMasonryListLog {
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
     * 类型: 1、完成任务 2、活力值奖励 3、赠送给Ta  4、Ta人赠送 5、转出手续费 6、转入手续费 7、交易所转入 8、转出砖石 9、购买任务消耗
     *
     * @mbggenerated
     */
    private Integer type;
    /**
     * 类型值: 1、完成任务 2、活力值奖励 3、赠送给Ta  4、Ta人赠送 5、转出手续费 6、转入手续费 7、交易所转入 8、转出砖石 9、购买任务消耗
     *
     * @mbggenerated
     */
    private String  typeValue;



    /**
     * 符号类型:1、加  2 减
     *
     * @mbggenerated
     */
    private Integer symbolType;
    /**
     * 任务id:
     *
     * @mbggenerated
     */
    private Integer taskId;

    /**
     * 砖石数
     *
     * @mbggenerated
     */
    private Float masonryNum;

    /**
     * 赠送人
     *
     * @mbggenerated
     */
    private Integer giveMemberId;

    /**
     * 给予人
     *
     * @mbggenerated
     */
    private Integer collectMemberId;

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
     * 订单号
     *
     * @mbggenerated
     */
    private String orderNum;

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
     * 生成时间
     *
     * @mbggenerated
     */
    private String createTimeStr;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSymbolType() {
        return symbolType;
    }

    public void setSymbolType(Integer symbolType) {
        this.symbolType = symbolType;
    }

    public Float getMasonryNum() {
        return masonryNum;
    }

    public void setMasonryNum(Float masonryNum) {
        this.masonryNum = masonryNum;
    }

    public Integer getGiveMemberId() {
        return giveMemberId;
    }

    public void setGiveMemberId(Integer giveMemberId) {
        this.giveMemberId = giveMemberId;
    }

    public Integer getCollectMemberId() {
        return collectMemberId;
    }

    public void setCollectMemberId(Integer collectMemberId) {
        this.collectMemberId = collectMemberId;
    }

    public Integer getCurday() {
        return curday;
    }

    public void setCurday(Integer curday) {
        this.curday = curday;
    }

    public Integer getCurhour() {
        return curhour;
    }

    public void setCurhour(Integer curhour) {
        this.curhour = curhour;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getCurminute() {
        return curminute;
    }

    public void setCurminute(Integer curminute) {
        this.curminute = curminute;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
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

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}