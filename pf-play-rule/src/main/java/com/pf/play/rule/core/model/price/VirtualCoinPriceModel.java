package com.pf.play.rule.core.model.price;

import java.io.Serializable;

/**
 * @Description 虚拟币每天兑换的价格的实体Bean
 * @Author yoko
 * @Date 2019/11/21 22:11
 * @Version 1.0
 */
public class VirtualCoinPriceModel implements Serializable {
    private static final long   serialVersionUID = 1233223031140L;

    /**
     * 自增主键ID
     */
    private Long id;

    /**
     * 兑换的价格名称
     */
    private String aliasName;

    /**
     * 兑换说明：例如1钻石=x CNY
     */
    private String exchangeExplain;

    /**
     * 兑换价格：这里的价格表示1钻石等于多少CNY，反之多少CNY等于1钻石
     */
    private String exchangePrice;

    /**
     * 最高价：交易时的最高价；展现给用户看的
     */
    private String tallestPrice;

    /**
     * 当日的推荐的最高价
     */
    private String maxPrice;

    /**
     * 当日的推荐的最低价
     */
    private String minPrice;

    /**
     * 兑换日期：每天兑换虚拟币的价格不一样，根据日期来显示兑换的价格
     */
    private Integer curday;


    /**
     * 审核状态：1初始化，2审核完毕
     */
    private Integer checkStatus;

    /**
     * 备注
     */
    private String remark;

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
     * 开始时间
     */
    private Integer curdayStart;

    /**
     * 结束时间
     */
    private Integer curdayEnd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getExchangeExplain() {
        return exchangeExplain;
    }

    public void setExchangeExplain(String exchangeExplain) {
        this.exchangeExplain = exchangeExplain;
    }

    public String getExchangePrice() {
        return exchangePrice;
    }

    public void setExchangePrice(String exchangePrice) {
        this.exchangePrice = exchangePrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getCurday() {
        return curday;
    }

    public void setCurday(Integer curday) {
        this.curday = curday;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getTallestPrice() {
        return tallestPrice;
    }

    public void setTallestPrice(String tallestPrice) {
        this.tallestPrice = tallestPrice;
    }
}
