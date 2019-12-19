package com.pf.play.common.alipay.model;

/**
 * @Description 阿里支付的实体bean
 * @Author yoko
 * @Date 2019/12/19 17:53
 * @Version 1.0
 */
public class AlipayModel {
    /**
     * 订单内容；
     * 我是测试数据
     */
    public String body;

    /**
     *主题
     */
    public String subject;

    /**
     * 交易订单号
     */
    public String outTradeNo;

    /**
     * 超时时间
     * 30m（30分钟）
     */
    public String timeoutExpress;

    /**
     * 总金额
     */
    public String totalAmount;

    /**
     * 商品编码
     */
    public String productCode;


    public AlipayModel(){

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

}
