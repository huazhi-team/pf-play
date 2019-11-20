package com.pf.play.rule.core.service;

import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.VcMember;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/14 17:43
 * @Version 1.0
 */
public interface RewardService<T> extends BaseService<T> {
    /****** 新用户注册奖励 ********/
    void  newUserSuperiorReward(Integer member);
    /****** 实名制奖励 ********/
    void  realNameReward(Integer member);//
    /**********用户交易奖励**********/
    void  userExchangeReward();
    /*******下级领取任务奖励******/
    void  downLevelReceiveTask();

    boolean  insertStayHanld(Integer memberId, VcMember info);

    boolean  updateEmpiricalValue(Integer type,Integer memberId,String proMemberId,Float empiricvValue);

}
