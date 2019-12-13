package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.UMasonryListLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UMasonryListLogMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insert(UMasonryListLog record);

    int insertSelective(UMasonryListLog record);

    UMasonryListLog selectByPrimaryKey(Long id);

    List<UMasonryListLog> selectByInfo(UMasonryListLog record);

    int updateByPrimaryKeySelective(UMasonryListLog record);

    int updateByPrimaryKey(UMasonryListLog record);

    UMasonryListLog selectByInfoMax(UMasonryListLog record);

}