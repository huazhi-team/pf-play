package com.pf.play.model.protocol.request.appeal;

import com.pf.play.model.protocol.request.base.BaseRequest;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2019/12/5 14:00
 * @Version 1.0
 */
public class RequestAppeal extends BaseRequest implements Serializable {
    private static final long   serialVersionUID = 1233223332140L;

    public Long id;
    public String orderNo;
    public String appealDescribe;
    public String appealReplenish;
    public String pictureAds;
    public String refuteDescribe;
    public String refuteReplenish;
    public String refutePictureAds;

    public RequestAppeal(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppealDescribe() {
        return appealDescribe;
    }

    public void setAppealDescribe(String appealDescribe) {
        this.appealDescribe = appealDescribe;
    }

    public String getAppealReplenish() {
        return appealReplenish;
    }

    public void setAppealReplenish(String appealReplenish) {
        this.appealReplenish = appealReplenish;
    }

    public String getPictureAds() {
        return pictureAds;
    }

    public void setPictureAds(String pictureAds) {
        this.pictureAds = pictureAds;
    }

    public String getRefuteDescribe() {
        return refuteDescribe;
    }

    public void setRefuteDescribe(String refuteDescribe) {
        this.refuteDescribe = refuteDescribe;
    }

    public String getRefuteReplenish() {
        return refuteReplenish;
    }

    public void setRefuteReplenish(String refuteReplenish) {
        this.refuteReplenish = refuteReplenish;
    }

    public String getRefutePictureAds() {
        return refutePictureAds;
    }

    public void setRefutePictureAds(String refutePictureAds) {
        this.refutePictureAds = refutePictureAds;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
