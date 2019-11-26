package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.DisWisemanAttribute;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DisWisemanAttributeMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(DisWisemanAttribute record);

    DisWisemanAttribute selectByPrimaryKey(DisWisemanAttribute record);

    int updateByPrimaryKeySelective(DisWisemanAttribute record);

}