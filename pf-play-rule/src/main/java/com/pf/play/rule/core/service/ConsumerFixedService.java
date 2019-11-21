package com.pf.play.rule.core.service;


import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.consumer.ConsumerFixedModel;

/**
 * @Description 用户固定账号的Service层
 * @Author yoko
 * @Date 2019/11/21 17:25
 * @Version 1.0
 */
public interface ConsumerFixedService<T> extends BaseService<T> {

    /**
     * @Description: 根据条件查询用户固定账号信息
     * @param model - 查询条件
     * @param isCache - 是否通过缓存查询：0需要通过缓存查询，1直接查询数据库
     * @return
     * @author yoko
     * @date 2019/11/21 19:26
    */
    public ConsumerFixedModel getConsumerFixed(ConsumerFixedModel model, int isCache) throws Exception;
}
