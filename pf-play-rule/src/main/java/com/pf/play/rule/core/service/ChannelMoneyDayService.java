package com.pf.play.rule.core.service;


import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.ChannelMoneyDayModel;

/**
 * @author df
 * @Description:渠道每日金额分配
 * @create 2019-05-22 9:58
 **/
public interface ChannelMoneyDayService<T> extends BaseService<T> {

    /**
     * 根具日期随机获取一条未完成的数据
     * @param model
     * @return
     */
    public ChannelMoneyDayModel randomChannelMoneyDay(ChannelMoneyDayModel model);
}
