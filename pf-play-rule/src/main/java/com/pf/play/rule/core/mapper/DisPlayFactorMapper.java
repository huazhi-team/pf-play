package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.DisPlayFactor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DisPlayFactorMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insert(DisPlayFactor record);

    int insertSelective(DisPlayFactor record);

    DisPlayFactor selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DisPlayFactor record);

    int updateByPrimaryKey(DisPlayFactor record);
}