package com.pf.play.rule.core.model;

import java.util.Date;

public class UMasonrySummary {
    /**
     * 自增id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 会员id
     *
     * @mbggenerated
     */
    private Integer memberId;

    /**
     * 收入砖石
     *
     * @mbggenerated
     */
    private Double inMasonry;

    /**
     * 支出砖石
     *
     * @mbggenerated
     */
    private Double outMasonry;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Double getInMasonry() {
        return inMasonry;
    }

    public void setInMasonry(Double inMasonry) {
        this.inMasonry = inMasonry;
    }

    public Double getOutMasonry() {
        return outMasonry;
    }

    public void setOutMasonry(Double outMasonry) {
        this.outMasonry = outMasonry;
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
}