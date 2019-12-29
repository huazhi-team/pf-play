package com.pf.play.rule.core.model.order;

import com.pf.play.model.protocol.page.BasePage;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @Description 订单流水的属性
 * @Author yoko
 * @Date 2019/11/23 16:51
 * @Version 1.0
 */
public class OrderModel extends BasePage implements Serializable {
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
     * 是否是初始化：大于零则表示属于初始化，初始化数据就是在查询订单状态orderTradeStatus时，默认值=0
     */
    private Integer isInit;

    /**
     * 订单状态：1正常，2取消，3完成交易
     */
    private Integer orderStatus;

    /**
     * 订单流水-订单申诉状态：1无申诉，2申诉，3已处理
     */
    private Integer appealStatus;

    /**
     * 订单交易流水-订单申诉状态：1无申诉，2申诉，3已处理
     */
    private Integer tradeAppealStatus;

    /**
     * 订单是否超时（用户未及时支付金额给卖家）：1未超时，2超时
     */
    private Integer isOvertime;

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
     * 推广码：也可以当做手机号
     */
    private String inviteCode;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 交易时间：可以理解为卖家卖给买家的时间
     */
    private String tradeCreateTime;

    /**
     * 老的状态：类似于SQL如下
     * update table set status = 1 where status = 0
     */
    private Integer oldStatus;

    /**
     * 订单流水的状态集合
     */
    private List<Integer> orderTradeStatusList;

    /**
     * 订单交易流水的状态集合
     */
    private List<Integer> tradeStatusList;

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
     *卖家昵称
     */
    private String sellNickname;

    /**
     * 买家昵称
     */
    private String buyNickname;

    /**
     * 买家的支付时间
     */
    private String payTime;

    /**
     * 卖家的确认收款时间
     */
    private String receiveTime;

    /**
     * 订单类型：1求购订单，2卖出订单
     * 让客户端好判断订单；如用户求购完成的订单为1，用户卖出完成的订单为2
     */
    private Integer orderType;

    /**
     * 支付图片上传
     */
    private String pictureAds;

    /**
     * 卖家的固定账号
     */
    private String sellFixedNum;

    /**
     * 买家的固定账号
     */
    private String buyFixedNum;

    /**
     * 查询所有数据
     * 针对SQL：为空则SQL条件默认加上is_overtime = 1
     * 不为空则SQL条件不需要加上is_overtime = 1
     */
    private Integer isAll;

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
        if (!StringUtils.isBlank(this.tradeNum) && !StringUtils.isBlank(this.tradePrice)){
            BigDecimal resDoble;
            BigDecimal x = new BigDecimal(this.tradeNum);
            BigDecimal y = new BigDecimal(this.tradePrice);
            resDoble = x.multiply(y);
            DecimalFormat sb = new DecimalFormat("###.##");
            String str = sb.format(resDoble);
            return str;
        }else{
            return totalPrice;
        }
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }


    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getIsOvertime() {
        return isOvertime;
    }

    public void setIsOvertime(Integer isOvertime) {
        this.isOvertime = isOvertime;
    }

    public String getTradeCreateTime() {
        return tradeCreateTime;
    }

    public void setTradeCreateTime(String tradeCreateTime) {
        this.tradeCreateTime = tradeCreateTime;
    }


    public Integer getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(Integer oldStatus) {
        this.oldStatus = oldStatus;
    }

    public List<Integer> getOrderTradeStatusList() {
        return orderTradeStatusList;
    }

    public void setOrderTradeStatusList(List<Integer> orderTradeStatusList) {
        this.orderTradeStatusList = orderTradeStatusList;
    }

    public List<Integer> getTradeStatusList() {
        return tradeStatusList;
    }

    public void setTradeStatusList(List<Integer> tradeStatusList) {
        this.tradeStatusList = tradeStatusList;
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

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getTradeAppealStatus() {
        return tradeAppealStatus;
    }

    public void setTradeAppealStatus(Integer tradeAppealStatus) {
        this.tradeAppealStatus = tradeAppealStatus;
    }

    public String getPictureAds() {
        return pictureAds;
    }

    public void setPictureAds(String pictureAds) {
        this.pictureAds = pictureAds;
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

    public Integer getIsInit() {
        return isInit;
    }

    public void setIsInit(Integer isInit) {
        this.isInit = isInit;
    }

    public Integer getIsAll() {
        return isAll;
    }

    public void setIsAll(Integer isAll) {
        this.isAll = isAll;
    }
}
