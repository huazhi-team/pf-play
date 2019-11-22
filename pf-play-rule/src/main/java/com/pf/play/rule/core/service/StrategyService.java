package com.pf.play.rule.core.service;

import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.strategy.StrategyModel;

/**
 * @Description 策略的Service层
 * @Author yoko
 * @Date 2019/11/21 21:06
 * @Version 1.0
 */
public interface StrategyService<T> extends BaseService<T> {

    /**
     * @Description: 根据条件查询策略数据
     * @param model - 查询条件
     * @param isCache - 是否通过缓存查询：0需要通过缓存查询，1直接查询数据库
     * @return
     * @author yoko
     * @date 2019/11/21 19:26
     */
    public StrategyModel getStrategyModel(StrategyModel model, int isCache) throws Exception;
}
