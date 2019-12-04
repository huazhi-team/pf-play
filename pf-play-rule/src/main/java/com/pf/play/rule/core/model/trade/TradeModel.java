package com.pf.play.rule.core.model.trade;

import com.pf.play.model.protocol.page.BasePage;

import java.io.Serializable;

/**
 * @Description 交易的实体属性Bean
 * @Author yoko
 * @Date 2019/11/26 22:39
 * @Version 1.0
 */
public class TradeModel extends BasePage implements Serializable {
    private static final long   serialVersionUID = 1233223301144L;

    /**
     * 自增主键ID
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 交易数量：买卖的数量
     */
    private String tradeNum;

    /**
     * 交易价格：求购价格、卖的价格
     */
    private String tradePrice;

    /**
     * 订单交易状态：0初始化，1锁定，2确认付款，3完成
     */
    private Integer orderTradeStatus;

    /**
     * 订单状态：1正常，2取消，3完成交易
     */
    private Integer orderStatus;

    /**
     * 订单申诉状态：1无申诉，2申诉，3已处理
     */
    private Integer appealStatus;

    /**
     * 创建日期：存的日期格式20160530
     */
    private Integer curday;

    /**
     * 创建所属小时：24小时制
     */
    private Integer curhour;

    /**
     * 创建所属分钟：60分钟制
     */
    private Integer curminute;

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
     * 排序类型：1按照时间降序排，2按照时间升序，3按照交易数量降序，4按照数量升序，5按照单价降序，6按照单价升序
     * 默认按照时间降序排列
     */
    private Integer sortType;

    /**
     * 自己订单号是否需要包含在内：不为空则自己的订单不做显示
     */
    private Integer ownType;

    private Integer curdayStart;

    private Integer curdayEnd;

    /**
     * 总价：单价X数量
     */
    private String totalPrice;


    /**
     * ##################################
     * 一下是订单交易流水的属性
     * ##################################
     */


    /**
     * 订单流水的主键ID
     */
    private Long orderTradeId;

    /**
     * 订单号的主键ID
     */
    private Long orderId;

    /**
     * 卖家会员ID
     */
    private Long sellMemberId;

    /**
     * 买家会员ID
     */
    private Long buyMemberId;

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
     * 订单流水的申诉状态
     * 订单申诉状态：1无申诉，2申诉，3已处理
     */
    private Integer sellAppealStatus;


    /**
     * 交易所：当前买量
     */
    private String buyTradeNum;

    /**
     * 交易所：今日成交量
     */
    private String sucTradeNum;

    /**
     * 交易所：当前买量、今日成交量
     * 这两个字段值都用此字段
     */
    private String tradeDataNum;

    /**
     * 老的状态：类似于SQL如下
     * update table set status = 1 where status = 0
     */
    private Integer oldStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getOrderTradeStatus() {
        return orderTradeStatus;
    }

    public void setOrderTradeStatus(Integer orderTradeStatus) {
        this.orderTradeStatus = orderTradeStatus;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getAppealStatus() {
        return appealStatus;
    }

    public void setAppealStatus(Integer appealStatus) {
        this.appealStatus = appealStatus;
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

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    public Integer getOwnType() {
        return ownType;
    }

    public void setOwnType(Integer ownType) {
        this.ownType = ownType;
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

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getOrderTradeId() {
        return orderTradeId;
    }

    public void setOrderTradeId(Long orderTradeId) {
        this.orderTradeId = orderTradeId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public Integer getSellAppealStatus() {
        return sellAppealStatus;
    }

    public void setSellAppealStatus(Integer sellAppealStatus) {
        this.sellAppealStatus = sellAppealStatus;
    }


    public String getBuyTradeNum() {
        return buyTradeNum;
    }

    public void setBuyTradeNum(String buyTradeNum) {
        this.buyTradeNum = buyTradeNum;
    }

    public String getSucTradeNum() {
        return sucTradeNum;
    }

    public void setSucTradeNum(String sucTradeNum) {
        this.sucTradeNum = sucTradeNum;
    }

    public String getTradeDataNum() {
        return tradeDataNum;
    }

    public void setTradeDataNum(String tradeDataNum) {
        this.tradeDataNum = tradeDataNum;
    }

    public Integer getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(Integer oldStatus) {
        this.oldStatus = oldStatus;
    }
}
