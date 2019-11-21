package com.pf.play.rule.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.common.utils.constant.CachedKeyUtils;
import com.pf.play.rule.core.common.utils.constant.PfCacheKey;
import com.pf.play.rule.core.common.utils.constant.ServerConstant;
import com.pf.play.rule.core.mapper.ConsumerFixedMapper;
import com.pf.play.rule.core.model.consumer.ConsumerFixedModel;
import com.pf.play.rule.core.service.ConsumerFixedService;
import com.pf.play.rule.util.ComponentUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Description 用户固定账号的Service层的实现层
 * @Author yoko
 * @Date 2019/11/21 17:25
 * @Version 1.0
 */
@Service
public class ConsumerFixedServiceImpl<T> extends BaseServiceImpl<T> implements ConsumerFixedService<T> {
    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    public long TWO_HOUR = 2;

    @Autowired
    private ConsumerFixedMapper consumerFixedMapper;

    public BaseDao<T> getDao() {
        return consumerFixedMapper;
    }

    @Override
    public ConsumerFixedModel getConsumerFixed(ConsumerFixedModel model, int isCache) throws Exception {
        ConsumerFixedModel dataModel = null;
        if (isCache == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            String strKeyCache = CachedKeyUtils.getPfCacheKey(PfCacheKey.CONSUMER_FIXED, model.getMemberId());
            String strCache = (String) ComponentUtil.redisService.get(strKeyCache);
            if (!StringUtils.isBlank(strCache)) {
                // 从缓存里面获取数据
                dataModel = JSON.parseObject(strCache, ConsumerFixedModel.class);
            } else {
                //查询数据库
                dataModel = (ConsumerFixedModel) consumerFixedMapper.findByObject(model);
                if (dataModel != null && dataModel.getId() != ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO) {
                    // 把数据存入缓存
                    ComponentUtil.redisService.set(strKeyCache, JSON.toJSONString(dataModel, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty), FIVE_MIN);
                }
            }
        }else {
            // 直接查数据库
            // 查询数据库
            dataModel = (ConsumerFixedModel) consumerFixedMapper.findByObject(model);
        }
        return dataModel;
    }
}
