package com.pf.play.rule.core.model;

import java.util.Date;

public class VcRewardReceive {
    /**
     * 会员id
     *
     * @mbggenerated
     */
    private Integer memberId;

    /**
     * 新用户赠送
     *
     * @mbggenerated
     */
    private Integer isLevel0;

    /**
     * 是否领取1级达人 1、未领取 2、已领取
     *
     * @mbggenerated
     */
    private Integer isLevel1;

    /**
     * 是否领取2级达人 1、未领取 2、已领取
     *
     * @mbggenerated
     */
    private Integer isLevel2;

    /**
     * 是否领取3级达人1、未领取 2、已领取
     *
     * @mbggenerated
     */
    private Integer isLevel3;

    /**
     * 是否领取4级达人1、未领取 2、已领取
     *
     * @mbggenerated
     */
    private Integer isLevel4;

    /**
     * 是否领取5级达人1、未领取 2、已领取
     *
     * @mbggenerated
     */
    private Integer isLevel5;

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

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getIsLevel0() {
        return isLevel0;
    }

    public void setIsLevel0(Integer isLevel0) {
        this.isLevel0 = isLevel0;
    }

    public Integer getIsLevel1() {
        return isLevel1;
    }

    public void setIsLevel1(Integer isLevel1) {
        this.isLevel1 = isLevel1;
    }

    public Integer getIsLevel2() {
        return isLevel2;
    }

    public void setIsLevel2(Integer isLevel2) {
        this.isLevel2 = isLevel2;
    }

    public Integer getIsLevel3() {
        return isLevel3;
    }

    public void setIsLevel3(Integer isLevel3) {
        this.isLevel3 = isLevel3;
    }

    public Integer getIsLevel4() {
        return isLevel4;
    }

    public void setIsLevel4(Integer isLevel4) {
        this.isLevel4 = isLevel4;
    }

    public Integer getIsLevel5() {
        return isLevel5;
    }

    public void setIsLevel5(Integer isLevel5) {
        this.isLevel5 = isLevel5;
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