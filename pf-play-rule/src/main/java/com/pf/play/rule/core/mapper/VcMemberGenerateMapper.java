package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.VcMemberGenerateModel;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/13 19:20
 * @Version 1.0
 */
public interface VcMemberGenerateMapper<T> extends BaseDao<T> {
    void insertSelective(VcMemberGenerateModel vcMemberGenerateModel);
}
