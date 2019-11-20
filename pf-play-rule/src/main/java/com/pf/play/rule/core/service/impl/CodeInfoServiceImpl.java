package com.pf.play.rule.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.common.utils.constant.CacheKey;
import com.pf.play.rule.core.common.utils.constant.CachedKeyUtils;
import com.pf.play.rule.core.common.utils.constant.ServerConstant;
import com.pf.play.rule.core.mapper.CodeInfoMapper;
import com.pf.play.rule.core.model.CodeInfoModel;
import com.pf.play.rule.core.service.CodeInfoService;
import com.pf.play.rule.util.ComponentUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author df
 * @Description:代码信息的Service层的实现层
 * @create 2019-05-22 15:32
 **/
@Service
public class CodeInfoServiceImpl<T> extends BaseServiceImpl<T> implements CodeInfoService<T> {
    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    public long ONE_HOUR = 1;

    @Autowired
    private CodeInfoMapper codeInfoMapper;

    public BaseDao<T> getDao() {
        return codeInfoMapper;
    }

    @Override
    public CodeInfoModel getCodeInfo(CodeInfoModel model) {
        CodeInfoModel dataModel = null;
        String strKeyCache = CachedKeyUtils.getCacheKey("sss", model.getId());
        String strCache = (String) ComponentUtil.redisService.get(strKeyCache);
        if (!StringUtils.isBlank(strCache)){
            //从缓存里面获取数据
            dataModel = JSON.parseObject(strCache, CodeInfoModel.class);
        }else {
            //查询数据库
            dataModel = (CodeInfoModel) codeInfoMapper.findByObject(model);
            if (dataModel != null && dataModel.getId() != ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
                //把数据存入缓存
                ComponentUtil.redisService.set(strKeyCache, JSON.toJSONString(dataModel,
                            SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty), ONE_HOUR, TimeUnit.HOURS);
            }
        }
        return dataModel;
    }

}
