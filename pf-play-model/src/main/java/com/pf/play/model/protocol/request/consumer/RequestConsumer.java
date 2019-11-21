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
    public String fullName;
    public String idCard;
    public Integer fixedType;
    public String fixedNum;

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getFixedType() {
        return fixedType;
    }

    public void setFixedType(Integer fixedType) {
        this.fixedType = fixedType;
    }

    public String getFixedNum() {
        return fixedNum;
    }

    public void setFixedNum(String fixedNum) {
        this.fixedNum = fixedNum;
    }
}
