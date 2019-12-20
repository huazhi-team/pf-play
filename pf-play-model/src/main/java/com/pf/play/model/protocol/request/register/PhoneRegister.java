package com.pf.play.model.protocol.request.register;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/12/20 10:59
 * @Version 1.0
 */
public class PhoneRegister {
    /**
     * 手机号码
     */
    private String  phone;
    /**
     * 邀请码
     */
    private String  inviteCode;
    /**
     * 时间戳
     */
    private  String  timeStamp;
    /**
     * 短信验证码
     */
    private  String  smsVerification;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSmsVerification() {
        return smsVerification;
    }

    public void setSmsVerification(String smsVerification) {
        this.smsVerification = smsVerification;
    }
}
