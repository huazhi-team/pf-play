package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.appeal.AppealModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 申诉订单的Dao层
 * @Author yoko
 * @Date 2019/12/5 10:25
 * @Version 1.0
 */
@Mapper
public interface AppealMapper<T> extends BaseDao<T> {

    /**
     * @Description: 申诉人更新资料原因-积极的
     * @param model
     * @return void
     * @author yoko
     * @date 2019/12/5 19:45
     */
    public void updateActive(AppealModel model);

    /**
     * @Description: 被申诉人更新资料原因-被动的反驳方
     * @param model
     * @return void
     * @author yoko
     * @date 2019/12/5 19:45
     */
    public void updatePassive(AppealModel model);
}
