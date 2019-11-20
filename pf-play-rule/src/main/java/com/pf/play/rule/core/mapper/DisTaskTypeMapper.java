package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.DisTaskType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DisTaskTypeMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insert(DisTaskType record);

    int insertSelective(DisTaskType record);

    List<DisTaskType> selectByPrimaryKey(DisTaskType disTaskType);

    int updateByPrimaryKeySelective(DisTaskType record);

    int updateByPrimaryKey(DisTaskType record);
}