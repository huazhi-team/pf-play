package com.pf.play.rule.core.model.appeal;

import com.pf.play.model.protocol.page.BasePage;

import java.io.Serializable;

/**
 * @Description 申诉的实体属性Bean
 * @Author yoko
 * @Date 2019/12/5 10:02
 * @Version 1.0
 */
public class AppealModel extends BasePage implements Serializable {
    private static final long   serialVersionUID = 1233223301144L;

    /**
     * 自增主键ID
     */
    private Long id;

    /**
     * 订单号主键ID
     */
    private Long orderId;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 申诉原因
     */
    private String appealDescribe;

    /**
     * 申诉补充
     */
    private String appealReplenish;

    /**
     * 申诉图片上传,以json存储，多个图片上传所以以json存储
     */
    private String pictureAds;

    /**
     * 身份类别：是买家还是卖家；1买家，2卖家
     */
    private Integer identityType;

    /**
     * 涉及的会员ID：被申诉的会员ID
     */
    private Long involveMemberId;

    /**
     * 反驳原因：被申诉人进行反驳描述
     */
    private String refuteDescribe;

    /**
     * 反驳补充
     */
    private String refuteReplenish;

    /**
     * 反驳截图上传,以json存储，多个图片上传所以以json存储
     */
    private String refutePictureAds;

    /**
     * 申诉结果：0初始化，1申诉失败，2申诉成功
     */
    private Integer appealResult;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建日期：存的日期格式20160530
     */
    private Integer curday;

    /**
     *运行计算次数
     */
    private Integer runNum;

    /**
     * 运行计算状态：：0初始化，1锁定，2计算失败，3计算成功
     */
    private Integer runStatus;


    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 是否有效：0有效，1无效/删除
     */
    private Integer yn;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     *
     * #################################################
     * 订单交易流水
     * #################################################
     */


    /**
     * 订单交易流水的主键ID
     */
    private Long orderTradeId;


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
     * 订单交易流水的买家支付凭证
     */
    private String tradePictureAds;

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
     * 订单流水的申诉状态
     * 订单申诉状态：1无申诉，2申诉，3已处理
     */
    private Integer appealStatus;

    /**
     * 总价：单价X数量
     */
    private String totalPrice;

    /**
     * 订单交易流水的创建时间
     */
    private String orderTradeTime;




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

    public String getAppealDescribe() {
        return appealDescribe;
    }

    public void setAppealDescribe(String appealDescribe) {
        this.appealDescribe = appealDescribe;
    }

    public String getAppealReplenish() {
        return appealReplenish;
    }

    public void setAppealReplenish(String appealReplenish) {
        this.appealReplenish = appealReplenish;
    }

    public String getPictureAds() {
        return pictureAds;
    }

    public void setPictureAds(String pictureAds) {
        this.pictureAds = pictureAds;
    }

    public Integer getIdentityType() {
        return identityType;
    }

    public void setIdentityType(Integer identityType) {
        this.identityType = identityType;
    }

    public Long getInvolveMemberId() {
        return involveMemberId;
    }

    public void setInvolveMemberId(Long involveMemberId) {
        this.involveMemberId = involveMemberId;
    }

    public String getRefuteDescribe() {
        return refuteDescribe;
    }

    public void setRefuteDescribe(String refuteDescribe) {
        this.refuteDescribe = refuteDescribe;
    }

    public String getRefuteReplenish() {
        return refuteReplenish;
    }

    public void setRefuteReplenish(String refuteReplenish) {
        this.refuteReplenish = refuteReplenish;
    }

    public String getRefutePictureAds() {
        return refutePictureAds;
    }

    public void setRefutePictureAds(String refutePictureAds) {
        this.refutePictureAds = refutePictureAds;
    }

    public Integer getAppealResult() {
        return appealResult;
    }

    public void setAppealResult(Integer appealResult) {
        this.appealResult = appealResult;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCurday() {
        return curday;
    }

    public void setCurday(Integer curday) {
        this.curday = curday;
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

    public Long getOrderTradeId() {
        return orderTradeId;
    }

    public void setOrderTradeId(Long orderTradeId) {
        this.orderTradeId = orderTradeId;
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

    public String getTradePictureAds() {
        return tradePictureAds;
    }

    public void setTradePictureAds(String tradePictureAds) {
        this.tradePictureAds = tradePictureAds;
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

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderTradeTime() {
        return orderTradeTime;
    }

    public void setOrderTradeTime(String orderTradeTime) {
        this.orderTradeTime = orderTradeTime;
    }
}
