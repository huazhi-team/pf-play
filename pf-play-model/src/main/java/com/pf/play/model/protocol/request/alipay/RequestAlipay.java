package com.pf.play.model.protocol.request.alipay;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2019/12/19 20:06
 * @Version 1.0
 */
public class RequestAlipay {
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
}
