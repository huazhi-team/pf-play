package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.DisPlayConsume;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DisPlayConsumeMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insert(DisPlayConsume record);

    int insertSelective(DisPlayConsume record);

    DisPlayConsume selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DisPlayConsume record);

    int updateByPrimaryKey(DisPlayConsume record);
}