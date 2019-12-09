package com.pf.play.rule.core.model.violate;

import java.io.Serializable;

/**
 * @Description 订单违约纪录的实体属性Bean
 * @Author yoko
 * @Date 2019/12/9 17:16
 * @Version 1.0
 */
public class OrderViolateModel implements Serializable {
    private static final long   serialVersionUID = 1233223301143L;

    /**
     * 自在主键ID
     */
    private Long id;

    /**
     * 订单主键ID
     */
    private Long orderId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 用户ID
     */
   private Long memberId;

    /**
     * 违约类型：1买家未付款（未在规定时间内），2卖家未确认收款（未在规定时间内），3被人投诉成功，4投诉失败
     */
    private Integer violateType;

    /**
     * 违约处罚类型:1不做处罚（也就统计纪录一下违约的数据），2扣减钻石，3账号封号
     */
    private Integer punishType;

    /**
     * 要扣减的钻石个数
     */
    private String masonryNum;

    /**
     * 数据来源：1系统运算录入，2人工录入
     */
    private Integer dataFrom;

    /**
     * 备注
     */
    private String remark;

    /**
     * 运行次数
     */
    private Integer runNum;

    /**
     * 运行计算状态：：0初始化，1锁定，2计算失败，3计算成功
     */
    private Integer runStatus;

    private Integer curday;

    private Integer curdayStart;

    private Integer curdayEnd;

    private Integer curhour;

    private Integer curminute;

    private String createTime;
    private String updateTime;

    /**
     * 是否有效：0有效，1无效/删除
     */
    private Integer yn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getViolateType() {
        return violateType;
    }

    public void setViolateType(Integer violateType) {
        this.violateType = violateType;
    }

    public Integer getPunishType() {
        return punishType;
    }

    public void setPunishType(Integer punishType) {
        this.punishType = punishType;
    }

    public String getMasonryNum() {
        return masonryNum;
    }

    public void setMasonryNum(String masonryNum) {
        this.masonryNum = masonryNum;
    }

    public Integer getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(Integer dataFrom) {
        this.dataFrom = dataFrom;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getRunNum() {
        return runNum;
    }

    public void setRunNum(Integer runNum) {
        this.runNum = runNum;
    }

    public Integer getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(Integer runStatus) {
        this.runStatus = runStatus;
    }

    public Integer getCurday() {
        return curday;
    }

    public void setCurday(Integer curday) {
        this.curday = curday;
    }

    public Integer getCurdayStart() {
        return curdayStart;
    }

    public void setCurdayStart(Integer curdayStart) {
        this.curdayStart = curdayStart;
    }

    public Integer getCurdayEnd() {
        return curdayEnd;
    }

    public void setCurdayEnd(Integer curdayEnd) {
        this.curdayEnd = curdayEnd;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }
}
