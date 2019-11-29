package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.DisEmpiricalVitalityAttribute;

public interface DisEmpiricalVitalityAttributeMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(DisEmpiricalVitalityAttribute record);

    DisEmpiricalVitalityAttribute selectByPrimaryKey(DisEmpiricalVitalityAttribute record);

    int updateByPrimaryKeySelective(DisEmpiricalVitalityAttribute record);

}