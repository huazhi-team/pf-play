package com.pf.play.rule.core.model.consumer;

import java.io.Serializable;

/**
 * @Description 用户固定账号
 * @Author yoko
 * @Date 2019/11/21 17:25
 * @Version 1.0
 */
public class ConsumerFixedModel implements Serializable {
    private static final long   serialVersionUID = 1233223331143L;

    /**
     * 自增主键ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long memberId;

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
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 是否有效：0有效，1无效/删除
     */
    private Integer yn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }
}
