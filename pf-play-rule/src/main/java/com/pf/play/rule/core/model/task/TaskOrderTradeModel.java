package com.pf.play.rule.core.model.task;

import java.io.Serializable;

/**
 * @Description 订单交易流水的Task的实体Bean
 * @Author yoko
 * @Date 2019/12/6 21:09
 * @Version 1.0
 */
public class TaskOrderTradeModel implements Serializable {
    private static final long   serialVersionUID = 1233223301142L;

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
     * 卖家会员ID
     */
    private Long sellMemberId;

    /**
     * 买家会员ID
     */
    private Long buyMemberId;

    /**
     * 交易数量：买卖的数量
     */
    private String tradeNum;

    /**
     * 交易价格：求购价格、卖的价格
     */
    private String tradePrice;

    /**
     * 订单总价格:交易数量X单价=买家要支付的订单总价
     */
    private String totalPrice;

    /**
     * 手续费
     */
    private String serviceCharge;

    /**
     * 卖家昵称
     */
    private String sellNickname;

    /**
     *买家昵称
     */
    private String buyNickname;

    /**
     * 卖家手机号
     */
    private String sellPhone;

    /**
     * 买家手机号
     */
    private String buyPhone;

    /**
     * 卖家的支付宝
     */
    private String sellFixedNum;

    /**
     * 买家的支付宝
     */
    private String buyFixedNum;

    /**
     * 交易状态：1超时，2正常进行中，3问题申诉，4确认已付款（买家等待），5确认已收款（卖家确认）
     */
    private Integer tradeStatus;

    /**
     * 订单转账截图的图片地址
     */
    private String pictureAds;

    /**
     * 具体手续费比例
     */
    private String ratio;

    /**
     * 买家确认付款时间：如果卖家在1个小时之内点击确认收款，则程序根据这个时间推迟一个小时自动确认已收款
     */
    private String payTime;

    /**
     * 卖家确认已收款的时间：如果程序给订单进行自动确认，则这个字段值为空
     */
    private String receiveTime;

    /**
     * 订单申诉状态：1无申诉，2申诉，3已处理
     */
    private Integer appealStatus;

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

    public Long getSellMemberId() {
        return sellMemberId;
    }

    public void setSellMemberId(Long sellMemberId) {
        this.sellMemberId = sellMemberId;
    }

    public Long getBuyMemberId() {
        return buyMemberId;
    }

    public void setBuyMemberId(Long buyMemberId) {
        this.buyMemberId = buyMemberId;
    }

    public String getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(String tradeNum) {
        this.tradeNum = tradeNum;
    }

    public String getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(String tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getSellNickname() {
        return sellNickname;
    }

    public void setSellNickname(String sellNickname) {
        this.sellNickname = sellNickname;
    }

    public String getBuyNickname() {
        return buyNickname;
    }

    public void setBuyNickname(String buyNickname) {
        this.buyNickname = buyNickname;
    }

    public String getSellPhone() {
        return sellPhone;
    }

    public void setSellPhone(String sellPhone) {
        this.sellPhone = sellPhone;
    }

    public String getBuyPhone() {
        return buyPhone;
    }

    public void setBuyPhone(String buyPhone) {
        this.buyPhone = buyPhone;
    }

    public String getSellFixedNum() {
        return sellFixedNum;
    }

    public void setSellFixedNum(String sellFixedNum) {
        this.sellFixedNum = sellFixedNum;
    }

    public String getBuyFixedNum() {
        return buyFixedNum;
    }

    public void setBuyFixedNum(String buyFixedNum) {
        this.buyFixedNum = buyFixedNum;
    }

    public Integer getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getPictureAds() {
        return pictureAds;
    }

    public void setPictureAds(String pictureAds) {
        this.pictureAds = pictureAds;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Integer getAppealStatus() {
        return appealStatus;
    }

    public void setAppealStatus(Integer appealStatus) {
        this.appealStatus = appealStatus;
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

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
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
}
