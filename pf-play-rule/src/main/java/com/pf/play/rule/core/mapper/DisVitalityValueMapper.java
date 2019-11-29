package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.DisVitalityValue;

import java.util.List;

public interface DisVitalityValueMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(DisVitalityValue record);

    List<DisVitalityValue> selectByPrimaryKey();

    int updateByPrimaryKeySelective(DisVitalityValue record);

}