package com.pf.play.rule.core.service.impl;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.mapper.OrderViolateMapper;
import com.pf.play.rule.core.service.OrderViolateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 订单违约纪录的Service层的实现层
 * @Author yoko
 * @Date 2019/12/9 17:30
 * @Version 1.0
 */
@Service
public class OrderViolateServiceImpl<T> extends BaseServiceImpl<T> implements OrderViolateService<T> {
    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    public long TWO_HOUR = 2;

    @Autowired
    private OrderViolateMapper orderViolateMapper;

    public BaseDao<T> getDao() {
        return orderViolateMapper;
    }
}
