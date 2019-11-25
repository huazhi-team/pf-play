package com.pf.play.rule.core.service;

import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.price.VirtualCoinPriceModel;

import java.util.List;

/**
 * @Description 虚拟币每天兑换的价格的Service层
 * @Author yoko
 * @Date 2019/11/21 22:16
 * @Version 1.0
 */
public interface VirtualCoinPriceService<T> extends BaseService<T> {

    /**
     * @Description: 根据条件查询虚拟币每天兑换的价格：今天、昨天
     * @param model - 查询条件
     * @param isCache - 是否通过缓存查询：0需要通过缓存查询，1直接查询数据库
     * @return
     * @author yoko
     * @date 2019/11/21 19:26
     */
    public List<VirtualCoinPriceModel> getVirtualCoinPriceList(VirtualCoinPriceModel model, int isCache) throws Exception;


    /**
     * @Description: 根据条件查询虚拟币每天兑换的价格：今天
     * @param model - 查询条件
     * @param isCache - 是否通过缓存查询：0需要通过缓存查询，1直接查询数据库
     * @return
     * @author yoko
     * @date 2019/11/21 19:26
     */
    public VirtualCoinPriceModel getVirtualCoinPrice(VirtualCoinPriceModel model, int isCache) throws Exception;

}
