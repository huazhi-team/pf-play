package com.pf.play.model.protocol.request.trade;

import com.pf.play.model.protocol.request.base.BaseRequest;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2019/11/27 15:50
 * @Version 1.0
 */
public class RequestTrade extends BaseRequest implements Serializable {
    private static final long   serialVersionUID = 1233223331149L;

    public String orderNo;
    public String payPw;
    public String pictureAds;

    public RequestTrade(){
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayPw() {
        return payPw;
    }

    public void setPayPw(String payPw) {
        this.payPw = payPw;
    }

    public String getPictureAds() {
        return pictureAds;
    }

    public void setPictureAds(String pictureAds) {
        this.pictureAds = pictureAds;
    }
}
