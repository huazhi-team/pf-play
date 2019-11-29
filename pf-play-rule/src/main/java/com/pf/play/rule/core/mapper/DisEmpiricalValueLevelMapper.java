package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.DisEmpiricalValueLevel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DisEmpiricalValueLevelMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(DisEmpiricalValueLevel record);

    List<DisEmpiricalValueLevel> selectByPrimaryKey();

    int updateByPrimaryKeySelective(DisEmpiricalValueLevel record);

}