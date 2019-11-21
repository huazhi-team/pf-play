package com.pf.play.model.protocol.request.consumer;

import com.pf.play.model.protocol.request.base.BaseRequest;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2019/11/6 17:23
 * @Version 1.0
 */
public class RequestConsumer extends BaseRequest implements Serializable {
    private static final long   serialVersionUID = 1233223331149L;

    public String payPw;
    public Integer upPayCode;

    public RequestConsumer(){
    }

    public String getPayPw() {
        return payPw;
    }

    public void setPayPw(String payPw) {
        this.payPw = payPw;
    }

    public Integer getUpPayCode() {
        return upPayCode;
    }

    public void setUpPayCode(Integer upPayCode) {
        this.upPayCode = upPayCode;
    }
}
