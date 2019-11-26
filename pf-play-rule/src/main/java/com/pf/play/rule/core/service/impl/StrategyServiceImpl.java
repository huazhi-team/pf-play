package com.pf.play.rule.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.common.utils.constant.CachedKeyUtils;
import com.pf.play.rule.core.common.utils.constant.PfCacheKey;
import com.pf.play.rule.core.common.utils.constant.ServerConstant;
import com.pf.play.rule.core.mapper.StrategyMapper;
import com.pf.play.rule.core.model.strategy.StrategyModel;
import com.pf.play.rule.core.service.StrategyService;
import com.pf.play.rule.util.ComponentUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 策略的Service层的实现层
 * @Author yoko
 * @Date 2019/11/21 21:07
 * @Version 1.0
 */
@Service
public class StrategyServiceImpl<T> extends BaseServiceImpl<T> implements StrategyService<T> {
    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    public long TWO_HOUR = 2;

    @Autowired
    private StrategyMapper strategyMapper;

    public BaseDao<T> getDao() {
        return strategyMapper;
    }

    @Override
    public StrategyModel getStrategyModel(StrategyModel model, int isCache) throws Exception {
        StrategyModel dataModel = null;
        if (isCache == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            String strKeyCache = CachedKeyUtils.getPfCacheKey(PfCacheKey.STRATEGY, model.getStgType());
            String strCache = (String) ComponentUtil.redisService.get(strKeyCache);
            if (!StringUtils.isBlank(strCache)) {
                // 从缓存里面获取数据
                dataModel = JSON.parseObject(strCache, StrategyModel.class);
            } else {
                //查询数据库
                dataModel = (StrategyModel) strategyMapper.findByObject(model);
                if (dataModel != null && dataModel.getId() != ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO) {
                    // 把数据存入缓存
                    ComponentUtil.redisService.set(strKeyCache, JSON.toJSONString(dataModel, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty), FIVE_MIN);
                }
            }
        }else {
            // 直接查数据库
            // 查询数据库
            dataModel = (StrategyModel) strategyMapper.findByObject(model);
        }
        return dataModel;
    }
}