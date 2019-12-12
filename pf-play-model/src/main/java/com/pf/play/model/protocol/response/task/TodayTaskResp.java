package com.pf.play.model.protocol.response.task;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/12/12 16:30
 * @Version 1.0
 */
public class TodayTaskResp {
    /**
     * 点赞总数
     */
    private  Integer  acceptNumberCount;
    /**
     * 查看商品总数
     */
    private  Integer  lookCommodityNumCount;
    /**
     * 今天点赞
     */
    private  Integer  acceptNumber;
    /**
     * 今天商品总数
     */
    private  Integer  lookCommodityNum;
    /**
     * 是否完成了
     */
    private  Integer  isComplete;

    /**
     * 剩余奖励
     */
    private  Double  surplusNum;

    /**
     * 已领奖励
     */
    private  Double  alreadyNum;

    /**
     * 上次奖励
     */
    private  Double  upReward;

    /**
     * 当次产生砖石奖励
     */
    private  Double  masonry;

    public Integer getAcceptNumberCount() {
        return acceptNumberCount;
    }

    public void setAcceptNumberCount(Integer acceptNumberCount) {
        this.acceptNumberCount = acceptNumberCount;
    }

    public Integer getLookCommodityNumCount() {
        return lookCommodityNumCount;
    }

    public void setLookCommodityNumCount(Integer lookCommodityNumCount) {
        this.lookCommodityNumCount = lookCommodityNumCount;
    }

    public Integer getAcceptNumber() {
        return acceptNumber;
    }

    public void setAcceptNumber(Integer acceptNumber) {
        this.acceptNumber = acceptNumber;
    }

    public Integer getLookCommodityNum() {
        return lookCommodityNum;
    }

    public void setLookCommodityNum(Integer lookCommodityNum) {
        this.lookCommodityNum = lookCommodityNum;
    }

    public Integer getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Integer isComplete) {
        this.isComplete = isComplete;
    }

    public Double getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(Double surplusNum) {
        this.surplusNum = surplusNum;
    }

    public Double getAlreadyNum() {
        return alreadyNum;
    }

    public void setAlreadyNum(Double alreadyNum) {
        this.alreadyNum = alreadyNum;
    }

    public Double getUpReward() {
        return upReward;
    }

    public void setUpReward(Double upReward) {
        this.upReward = upReward;
    }

    public Double getMasonry() {
        return masonry;
    }

    public void setMasonry(Double masonry) {
        this.masonry = masonry;
    }
}
