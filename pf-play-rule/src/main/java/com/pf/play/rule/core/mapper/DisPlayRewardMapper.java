package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.DisPlayReward;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DisPlayRewardMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insert(DisPlayReward record);

    int insertSelective(DisPlayReward record);

    DisPlayReward selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DisPlayReward record);

    int updateByPrimaryKey(DisPlayReward record);
}