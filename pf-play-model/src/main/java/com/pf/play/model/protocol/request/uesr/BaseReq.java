package com.pf.play.model.protocol.request.uesr;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/13 20:36
 * @Version 1.0
 */
public class BaseReq {
    public int agtVer;
    public String androidVer;
    public int clientVer;
    public long ctime;
    public long cctime;
    public String sign;
    public String token;

    public int getAgtVer() {
        return agtVer;
    }

    public void setAgtVer(int agtVer) {
        this.agtVer = agtVer;
    }

    public String getAndroidVer() {
        return androidVer;
    }

    public void setAndroidVer(String androidVer) {
        this.androidVer = androidVer;
    }

    public int getClientVer() {
        return clientVer;
    }

    public void setClientVer(int clientVer) {
        this.clientVer = clientVer;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public long getCctime() {
        return cctime;
    }

    public void setCctime(long cctime) {
        this.cctime = cctime;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
