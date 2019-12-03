package com.pf.play.rule.core.model.consumer;

import java.io.Serializable;

/**
 * @Description 用户信息
 * @Author yoko
 * @Date 2019/11/28 17:38
 * @Version 1.0
 */
public class ConsumerModel implements Serializable {
    private static final long   serialVersionUID = 1233223331143L;

    /**
     * 用户ID
     */
    private Long memberId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户手机号
     */
    private String phoneNum;

    /**
     * 是否实名 1、未实名  2、已实名
     */
    private Integer isCertification;

    /**
     * 用户支付密码
     */
    private String payPassword;

    /**
     * 用户当前状态：1、正常用户 2、黑名单
     */
    private Integer isActive;

    /**
     * 用户拥有的钻石个数
     */
    private String dayMasonry;

    /**
     * 用户冻结的钻石个数
     */
    private String frozenMasonry;

    /**
     * 当前经验等级
     */
    private Integer empiricalLevel;

    /**
     * 手续费
     */
    private String ratio;

    /**
     * 是否有效; 1有效 2无效
     */
    private Integer isValid;

    /**
     * 姓名
     */
    private String fullName;

    /**
     * 用户身份证
     */
    private String idCard;

    /**
     * 用户账号类型：1微信号，2支付宝，3银行卡
     */
    private Integer fixedType;

    /**
     * 具体账号:微信号，2支付宝号，银行卡账号
     */
    private String fixedNum;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 是否有效：0有效，1无效/删除
     */
    private Integer yn;

    /**
     * 加减钻石的数量
     */
    private String addReduceNum;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getDayMasonry() {
        return dayMasonry;
    }

    public void setDayMasonry(String dayMasonry) {
        this.dayMasonry = dayMasonry;
    }

    public String getFrozenMasonry() {
        return frozenMasonry;
    }

    public void setFrozenMasonry(String frozenMasonry) {
        this.frozenMasonry = frozenMasonry;
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

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public String getAddReduceNum() {
        return addReduceNum;
    }

    public void setAddReduceNum(String addReduceNum) {
        this.addReduceNum = addReduceNum;
    }
}
