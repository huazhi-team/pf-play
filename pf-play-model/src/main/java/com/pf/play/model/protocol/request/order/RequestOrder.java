package com.pf.play.model.protocol.request.order;

import com.pf.play.model.protocol.request.base.BaseRequest;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2019/11/25 14:09
 * @Version 1.0
 */
public class RequestOrder extends BaseRequest implements Serializable {
    private static final long   serialVersionUID = 1233223331149L;
    public String orderNo;
    public String tradeNum;
    public String tradePrice;
    public Integer sortType;

    public RequestOrder(){
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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
}
