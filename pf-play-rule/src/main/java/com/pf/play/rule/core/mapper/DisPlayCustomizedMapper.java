package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.DisPlayCustomized;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DisPlayCustomizedMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insert(DisPlayCustomized record);

    int insertSelective(DisPlayCustomized record);

    DisPlayCustomized selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DisPlayCustomized record);

    int updateByPrimaryKey(DisPlayCustomized record);
}