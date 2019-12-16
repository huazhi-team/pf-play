package com.pf.play.model.protocol.response.order;

import com.pf.play.model.protocol.request.base.BaseResponse;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2019/11/25 21:40
 * @Version 1.0
 */
public class Order implements Serializable {
    private static final long   serialVersionUID = 1233023331141L;

    public String nickname;
    public String orderNo;
    public String tradeNum;
    public String tradePrice;
    public String totalPrice;
    /**
     * 存在疑问：建议不给用户看
     */
    public String createTime;


    public Order(){

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

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
