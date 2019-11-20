package com.pf.play.rule.core.service.impl;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.service.AipRequestInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author df
 * @Description:请求纪录的Service层的实现层
 * @create 2019-05-27 17:51
 **/
@Service
public class AipRequestInfoServiceImpl<T> extends BaseServiceImpl<T> implements AipRequestInfoService<T> {
    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;


    public BaseDao<T> getDao() {
        return null;
    }
}
