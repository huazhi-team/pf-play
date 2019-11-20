package com.pf.play.rule.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.common.utils.constant.CacheKey;
import com.pf.play.rule.core.common.utils.constant.CachedKeyUtils;
import com.pf.play.rule.core.common.utils.constant.ServerConstant;
import com.pf.play.rule.core.model.ChannelInfoModel;
import com.pf.play.rule.core.service.ChannelInfoService;
import com.pf.play.rule.util.ComponentUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author df
 * @Description:渠道信息的Service层的实现层
 * @create 2019-05-27 16:46
 **/
@Service
public class ChannelInfoServiceImpl<T> extends BaseServiceImpl<T> implements ChannelInfoService<T> {

    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    public long ONE_HOUR = 1;



    public BaseDao<T> getDao() {
        return null;
    }

    @Override
    public ChannelInfoModel getChannelInfo(ChannelInfoModel model) {
        ChannelInfoModel dataModel = null;
//        String strKeyCache = CachedKeyUtils.getCacheKey(CacheKey.CHANNEL_INFO, model.getId());
//        String strCache = (String) ComponentUtil.redisService.get(strKeyCache);
//        if (!StringUtils.isBlank(strCache)){
//            //从缓存里面获取数据
//            dataModel = JSON.parseObject(strCache, ChannelInfoModel.class);
//        }else {
//            //查询数据库
//            dataModel = (ChannelInfoModel) channelInfoMapper.findByObject(model);
//            if (dataModel != null && dataModel.getId() != ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
//                //把数据存入缓存
//                ComponentUtil.redisService.set(strKeyCache, JSON.toJSONString(dataModel, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty), ONE_HOUR, TimeUnit.HOURS);
//            }
//        }
        return dataModel;
    }
}
