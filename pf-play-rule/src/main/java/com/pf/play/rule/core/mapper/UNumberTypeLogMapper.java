package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.model.UNumberTypeLog;

public interface UNumberTypeLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UNumberTypeLog record);

    int insertSelective(UNumberTypeLog record);

    UNumberTypeLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UNumberTypeLog record);

    int updateByPrimaryKey(UNumberTypeLog record);
}