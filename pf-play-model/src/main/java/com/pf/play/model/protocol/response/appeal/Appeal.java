package com.pf.play.model.protocol.response.appeal;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2019/12/5 20:48
 * @Version 1.0
 */
public class Appeal implements Serializable {
    private static final long   serialVersionUID = 1233023391141L;

    public Long id;
    public String orderNo;
    public String appealDescribe;
    public String appealReplenish;
    public String pictureAds;
    public Integer identityType;
    public String refuteDescribe;
    public String refuteReplenish;
    public String refutePictureAds;
    public Integer appealResult;
    public String createTime;
    public String tradeNum;
    public String tradePrice;
    public String serviceCharge;
    public String totalPrice;
    public String sellNickname;
    public String buyNickname;
    public String orderTradeTime;

    public Appeal(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public Integer getIdentityType() {
        return identityType;
    }

    public void setIdentityType(Integer identityType) {
        this.identityType = identityType;
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

    public Integer getAppealResult() {
        return appealResult;
    }

    public void setAppealResult(Integer appealResult) {
        this.appealResult = appealResult;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(String tradeNum) {
        this.tradeNum = tradeNum;
    }

    public String getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(String tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSellNickname() {
        return sellNickname;
    }

    public void setSellNickname(String sellNickname) {
        this.sellNickname = sellNickname;
    }

    public String getBuyNickname() {
        return buyNickname;
    }

    public void setBuyNickname(String buyNickname) {
        this.buyNickname = buyNickname;
    }

    public String getOrderTradeTime() {
        return orderTradeTime;
    }

    public void setOrderTradeTime(String orderTradeTime) {
        this.orderTradeTime = orderTradeTime;
    }
}
