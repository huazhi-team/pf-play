package com.pf.play.model.protocol.response.trade;

import com.pf.play.model.protocol.request.base.BaseResponse;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2019/12/16 11:43
 * @Version 1.0
 */
public class ResponseTradeRule extends BaseResponse implements Serializable {
    private static final long   serialVersionUID = 1233023331141L;

    public Integer isTrade;
    public String tradeTime;

    public String buyTradeNum;
    public String sucTradeNum;

    public String t_exchangePrice;
    public String y_exchangePrice;
    public String t_tallestPrice;
    public String y_tallestPrice;
    public String growthRate;
    public String growthRateType;
    public String maxPrice;
    public String minPrice;

    public ResponseTradeRule(){

    }


    public Integer getIsTrade() {
        return isTrade;
    }

    public void setIsTrade(Integer isTrade) {
        this.isTrade = isTrade;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
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

    public String getGrowthRateType() {
        return growthRateType;
    }

    public void setGrowthRateType(String growthRateType) {
        this.growthRateType = growthRateType;
    }
}
