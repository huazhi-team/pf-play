package com.pf.play.model.protocol.response.consumer;

import com.pf.play.model.protocol.request.base.BaseResponse;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2019/11/13 21:15
 * @Version 1.0
 */
public class ResponseConsumer extends BaseResponse implements Serializable {
    private static final long   serialVersionUID = 1233223331149L;

    public String fullName;
    public String idCard;
    public Integer fixedType;
    public String fixedNum;

    public Integer empiricalLevel;
    public String ratio;

    public String nickname;
    public String phoneNum;
    public Integer isCertification;
    public Integer isActive;

    public ResponseConsumer(){

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

    public Integer getEmpiricalLevel() {
        return empiricalLevel;
    }

    public void setEmpiricalLevel(Integer empiricalLevel) {
        this.empiricalLevel = empiricalLevel;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getIsCertification() {
        return isCertification;
    }

    public void setIsCertification(Integer isCertification) {
        this.isCertification = isCertification;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
}
