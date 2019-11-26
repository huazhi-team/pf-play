package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.DisWisemanInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DisWisemanInfoMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(DisWisemanInfo record);

    List<DisWisemanInfo> selectByPrimaryKey(DisWisemanInfo record);

    int updateByPrimaryKeySelective(DisWisemanInfo record);

}