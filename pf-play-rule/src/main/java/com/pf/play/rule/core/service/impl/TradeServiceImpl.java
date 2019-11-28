package com.pf.play.rule.core.service.impl;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.mapper.TradeMapper;
import com.pf.play.rule.core.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 交易的Service层的实现层
 * @Author yoko
 * @Date 2019/11/26 22:43
 * @Version 1.0
 */
@Service
public class TradeServiceImpl<T> extends BaseServiceImpl<T> implements TradeService<T> {
    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    public long TWO_HOUR = 2;

    @Autowired
    private TradeMapper tradeMapper;

    public BaseDao<T> getDao() {
        return tradeMapper;
    }
}
