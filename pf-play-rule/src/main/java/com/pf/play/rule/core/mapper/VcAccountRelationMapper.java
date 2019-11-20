package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.VcAccountRelation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VcAccountRelationMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insert(VcAccountRelation record);

    int insertSelective(VcAccountRelation record);

    VcAccountRelation selectByPrimaryKey(VcAccountRelation record);

    int updateByPrimaryKeySelective(VcAccountRelation record);

    int updateByPrimaryKey(VcAccountRelation record);
}