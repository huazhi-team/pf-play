package com.pf.play.rule.core.model.price;

import java.io.Serializable;

/**
 * @Description 虚拟币每天兑换的价格的实体封装给客户端协议
 * @Author yoko
 * @Date 2019/11/22 18:28
 * @Version 1.0
 * t代表今天，y代表昨天
 */
public class VirtualCoinPriceDto implements Serializable {
    private static final long   serialVersionUID = 1233223031141L;

    /**
     * 今天-兑换价格：这里的价格表示1钻石等于多少CNY，反之多少CNY等于1钻石
     * 相当于今天的参考价
     */
    private String t_exchangePrice;

    /**
     * 昨天-兑换价格：这里的价格表示1钻石等于多少CNY，反之多少CNY等于1钻石
     * 相当于昨天的参考价
     */
    private String y_exchangePrice;

    /**
     * 今天-最高价：交易时的最高价；展现给用户看的
     */
    private String t_tallestPrice;

    /**
     * 昨天-最高价：交易时的最高价；展现给用户看的
     */
    private String y_tallestPrice;

    /**
     * 增长率：涨幅
     */
    private String growthRate;

    /**
     * 当日的推荐的最高价：允许用户填写的最高的价格
     */
    private String maxPrice;

    /**
     * 当日的推荐的最低价：允许用户填写的最低的价格
     */
    private String minPrice;


    public String getT_exchangePrice() {
        return t_exchangePrice;
    }

    public void setT_exchangePrice(String t_exchangePrice) {
        this.t_exchangePrice = t_exchangePrice;
    }

    public String getY_exchangePrice() {
        return y_exchangePrice;
    }

    public void setY_exchangePrice(String y_exchangePrice) {
        this.y_exchangePrice = y_exchangePrice;
    }

    public String getT_tallestPrice() {
        return t_tallestPrice;
    }

    public void setT_tallestPrice(String t_tallestPrice) {
        this.t_tallestPrice = t_tallestPrice;
    }

    public String getY_tallestPrice() {
        return y_tallestPrice;
    }

    public void setY_tallestPrice(String y_tallestPrice) {
        this.y_tallestPrice = y_tallestPrice;
    }

    public String getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(String growthRate) {
        this.growthRate = growthRate;
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
}
