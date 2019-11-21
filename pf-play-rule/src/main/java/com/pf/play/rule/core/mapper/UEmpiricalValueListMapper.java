package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.UEmpiricalValueList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UEmpiricalValueListMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insert(UEmpiricalValueList record);

    int insertSelective(UEmpiricalValueList record);

    UEmpiricalValueList selectByPrimaryKey(Long id);




    int updateByPrimaryKeySelective(UEmpiricalValueList record);

    int updateByPrimaryKey(UEmpiricalValueList record);
}