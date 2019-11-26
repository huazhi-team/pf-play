package com.pf.play.model.protocol.request.uesr;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/26 11:19
 * @Version 1.0
 */
public class PhoneVerificationReq {

    private  String  phone;
    private  String  picVerification;
    private  String  picTimeStamp;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPicVerification() {
        return picVerification;
    }

    public void setPicVerification(String picVerification) {
        this.picVerification = picVerification;
    }

    public String getPicTimeStamp() {
        return picTimeStamp;
    }

    public void setPicTimeStamp(String picTimeStamp) {
        this.picTimeStamp = picTimeStamp;
    }
}
