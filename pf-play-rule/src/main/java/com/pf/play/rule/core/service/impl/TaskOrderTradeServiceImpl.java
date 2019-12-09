package com.pf.play.rule.core.service.impl;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.mapper.TaskOrderTradeMapper;
import com.pf.play.rule.core.service.TaskOrderTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 订单交易流水的Task的Service层的实现层
 * @Author yoko
 * @Date 2019/12/6 21:41
 * @Version 1.0
 */
@Service
public class TaskOrderTradeServiceImpl<T> extends BaseServiceImpl<T> implements TaskOrderTradeService<T> {
    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    public long TWO_HOUR = 2;

    @Autowired
    private TaskOrderTradeMapper taskOrderTradeMapper;

    public BaseDao<T> getDao() {
        return taskOrderTradeMapper;
    }
}
