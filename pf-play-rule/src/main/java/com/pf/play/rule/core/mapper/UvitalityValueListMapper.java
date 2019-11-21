package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.UvitalityValueList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UvitalityValueListMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insert(UvitalityValueList record);

    int insertSelective(UvitalityValueList record);

    UvitalityValueList selectByPrimaryKey(UvitalityValueList record);

    List<UvitalityValueList> selectNeedHandle();

    int updateByPrimaryKeySelective(UvitalityValueList record);

    int updateByPrimaryKey(UvitalityValueList record);
}