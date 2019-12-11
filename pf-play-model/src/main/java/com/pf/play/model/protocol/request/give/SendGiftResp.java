package com.pf.play.model.protocol.request.give;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/12/10 17:54
 * @Version 1.0
 */
public class SendGiftResp {
    private Integer   sendMemberId;
    private Integer   receiptMemberId;
    private Double    masonryCount;
    private  String   payPw;

    public Integer getSendMemberId() {
        return sendMemberId;
    }

    public void setSendMemberId(Integer sendMemberId) {
        this.sendMemberId = sendMemberId;
    }

    public Integer getReceiptMemberId() {
        return receiptMemberId;
    }

    public void setReceiptMemberId(Integer receiptMemberId) {
        this.receiptMemberId = receiptMemberId;
    }

    public Double getMasonryCount() {
        return masonryCount;
    }

    public void setMasonryCount(Double masonryCount) {
        this.masonryCount = masonryCount;
    }

    public String getPayPw() {
        return payPw;
    }

    public void setPayPw(String payPw) {
        this.payPw = payPw;
    }
}
