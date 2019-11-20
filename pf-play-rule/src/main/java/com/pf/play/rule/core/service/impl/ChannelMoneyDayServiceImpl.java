package com.pf.play.rule.core.service.impl;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.model.ChannelMoneyDayModel;
import com.pf.play.rule.core.service.ChannelMoneyDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author df
 * @Description:渠道每日金额分配的Service层的实现层
 * @create 2019-05-22 10:07
 **/
@Service
public class ChannelMoneyDayServiceImpl<T> extends BaseServiceImpl<T> implements ChannelMoneyDayService<T> {
    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;


    public BaseDao<T> getDao() {
        return null;
    }

    @Override
    public ChannelMoneyDayModel randomChannelMoneyDay(ChannelMoneyDayModel model) {
        return null;
    }
}
