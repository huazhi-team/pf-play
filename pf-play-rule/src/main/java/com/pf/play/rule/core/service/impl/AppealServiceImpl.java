package com.pf.play.rule.core.service.impl;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.mapper.AppealMapper;
import com.pf.play.rule.core.model.appeal.AppealModel;
import com.pf.play.rule.core.service.AppealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2019/12/5 10:25
 * @Version 1.0
 */
@Service
public class AppealServiceImpl<T> extends BaseServiceImpl<T> implements AppealService<T> {

    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    public long TWO_HOUR = 2;

    @Autowired
    private AppealMapper appealMapper;

    public BaseDao<T> getDao() {
        return appealMapper;
    }

    @Override
    public void updateActive(AppealModel model) {
        appealMapper.updateActive(model);
    }

    @Override
    public void updatePassive(AppealModel model) {
        appealMapper.updatePassive(model);
    }
}
