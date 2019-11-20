package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.SysTypeDictionary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysTypeDictionaryMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insert(SysTypeDictionary record);

    int insertSelective(SysTypeDictionary record);

    List<SysTypeDictionary> selectByPrimaryKey();

    int updateByPrimaryKeySelective(SysTypeDictionary record);

    int updateByPrimaryKey(SysTypeDictionary record);
}