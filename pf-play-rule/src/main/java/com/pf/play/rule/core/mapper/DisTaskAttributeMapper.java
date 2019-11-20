package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.DisTaskAttribute;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DisTaskAttributeMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insert(DisTaskAttribute record);

    int insertSelective(DisTaskAttribute record);

    List<DisTaskAttribute> selectByPrimaryKey(DisTaskAttribute record);

    int updateByPrimaryKeySelective(DisTaskAttribute record);

    int updateByPrimaryKey(DisTaskAttribute record);
}