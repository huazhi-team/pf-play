package com.pf.play.model.protocol.request.alipay;

import com.pf.play.model.protocol.request.base.BaseRequest;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2019/12/19 20:06
 * @Version 1.0
 */
public class RequestAlipay extends BaseRequest implements Serializable {
    private static final long   serialVersionUID = 1233223332241L;

    public String body;
    public String subject;
    public String outTradeNo;
    public String timeoutExpress;
    public String totalAmount;
    public String productCode;
    public String notifyUrl;

    public RequestAlipay(){

    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTimeoutExpress() {
        return timeoutExpress;
    }

    public void setTimeoutExpress(String timeoutExpress) {
        this.timeoutExpress = timeoutExpress;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
