package com.pf.play.rule.core.service.impl;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.mapper.AppealMapper;
import com.pf.play.rule.core.mapper.OrderMapper;
import com.pf.play.rule.core.mapper.TradeMapper;
import com.pf.play.rule.core.model.appeal.AppealModel;
import com.pf.play.rule.core.model.order.OrderModel;
import com.pf.play.rule.core.model.trade.TradeModel;
import com.pf.play.rule.core.service.AppealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2019/12/5 10:25
 * @Version 1.0
 */
@Service
public class AppealServiceImpl<T> extends BaseServiceImpl<T> implements AppealService<T> {

    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    public long TWO_HOUR = 2;

    @Autowired
    private AppealMapper appealMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private TradeMapper tradeMapper;

    public BaseDao<T> getDao() {
        return appealMapper;
    }

    @Override
    public void updateActive(AppealModel model) {
        appealMapper.updateActive(model);
    }

    @Override
    public void updatePassive(AppealModel model) {
        appealMapper.updatePassive(model);
    }

    @Override
    public void addAppeal(AppealModel appealModel, OrderModel orderModel, TradeModel tradeModel) throws Exception {
        handleAddAppeal(appealModel, orderModel, tradeModel);
    }

    /**
     * @Description: 添加事物处理数据
     * @param appealModel
     * @param orderModel
     * @param tradeModel
     * @return void
     * @author yoko
     * @date 2019/12/6 17:43
    */
    @Transactional
    public void handleAddAppeal(AppealModel appealModel, OrderModel orderModel, TradeModel tradeModel){
        appealMapper.add(appealModel);
        orderMapper.updateOrderAppealStatus(orderModel);
        tradeMapper.updateTradeAppealStatus(tradeModel);
    }
}
