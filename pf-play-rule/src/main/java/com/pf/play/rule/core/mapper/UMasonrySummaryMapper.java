package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.UMasonrySummary;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UMasonrySummaryMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UMasonrySummary record);

    UMasonrySummary selectByPrimaryKey(UMasonrySummary record);

    int updateByPrimaryKeySelective(UMasonrySummary record);
}