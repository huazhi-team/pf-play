package com.pf.play.rule.core.model;

import java.util.Date;

public class DisEmpiricalValueLevel {
    /**
     * 自增id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 等级id
     *
     * @mbggenerated
     */
    private Integer empiricalId;

    /**
     * 经验值名称
     *
     * @mbggenerated
     */
    private String empiricalName;

    /**
     * 可交易数
     *
     * @mbggenerated
     */
    private Integer transactionNumber;

    /**
     * 手续费
     *
     * @mbggenerated
     */
    private Float transactionFee;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 修改时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 是否有效; 1有效 2无效
     *
     * @mbggenerated
     */
    private Integer isValid;


    /**
     * 是否有效; 1有效 2无效
     *
     * @mbggenerated
     */
    private String remarks;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEmpiricalId() {
        return empiricalId;
    }

    public void setEmpiricalId(Integer empiricalId) {
        this.empiricalId = empiricalId;
    }

    public String getEmpiricalName() {
        return empiricalName;
    }

    public void setEmpiricalName(String empiricalName) {
        this.empiricalName = empiricalName;
    }

    public Integer getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(Integer transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public Float getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(Float transactionFee) {
        this.transactionFee = transactionFee;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}