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


}
