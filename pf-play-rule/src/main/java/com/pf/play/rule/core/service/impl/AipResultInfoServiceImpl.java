package com.pf.play.rule.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.common.utils.constant.CacheKey;
import com.pf.play.rule.core.common.utils.constant.CachedKeyUtils;
import com.pf.play.rule.core.model.AipResultInfoModel;
import com.pf.play.rule.core.service.AipResultInfoService;
import com.pf.play.rule.util.ComponentUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author df
 * @Description:结果同步的Service层的实现层
 * @create 2019-05-29 14:48
 **/
@Service
public class AipResultInfoServiceImpl<T> extends BaseServiceImpl<T> implements AipResultInfoService<T> {
    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    @Autowired



    public BaseDao<T> getDao() {
        return null;
    }

    @Override
    public void addResultInfoChannel(AipResultInfoModel model) {
//        aipResultInfoMapper.addResultInfoChannel(model);
    }

    @Override
    public AipResultInfoModel countDayMoney(AipResultInfoModel model) {
//        return aipResultInfoMapper.countDayMoney(model);
        return null;
    }

    @Override
    public AipResultInfoModel getAipResultInfo(AipResultInfoModel model) {
        AipResultInfoModel dataModel = null;
//        String strKeyCache = CachedKeyUtils.getCacheKey(CacheKey.AIP_RESULT_INFO_SUC, model.getClExtData());
//        String strCache = (String) ComponentUtil.redisService.get(strKeyCache);
//        if (!StringUtils.isBlank(strCache)){
//            //从缓存里面获取数据
//            dataModel = JSON.parseObject(strCache, AipResultInfoModel.class);
//        }else {
//            //查询数据库
//            dataModel = (AipResultInfoModel) aipResultInfoMapper.findByObject(model);
//            if (dataModel != null && !StringUtils.isBlank(dataModel.getClExtData())){
//                //把数据存入缓存
//                ComponentUtil.redisService.set(strKeyCache, JSON.toJSONString(dataModel, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty), FIVE_MIN);
//            }
//        }
        return dataModel;
    }
}
