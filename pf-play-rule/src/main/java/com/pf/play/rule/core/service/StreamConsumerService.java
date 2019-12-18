package com.pf.play.rule.core.service;

import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.stream.StreamConsumerModel;

/**
 * @Description 用户访问、异常的流水的Service层
 * @Author yoko
 * @Date 2019/12/18 14:19
 * @Version 1.0
 */
public interface StreamConsumerService<T> extends BaseService<T> {

    /**
     * @Description: 添加用户访问正常的流水数据
     * @param model
     * @return int
     * @author yoko
     * @date 2019/12/18 14:23
    */
    public int addVisit(StreamConsumerModel model);


    /**
     * @Description: 添加用户访问异常的流水数据
     * @param model
     * @return int
     * @author yoko
     * @date 2019/12/18 14:23
     */
    public int addError(StreamConsumerModel model);

}
