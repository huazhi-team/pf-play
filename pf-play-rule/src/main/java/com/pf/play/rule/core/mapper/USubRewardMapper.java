package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.USubReward;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface USubRewardMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insert(USubReward record);

    int insertSelective(USubReward record);

    USubReward selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(USubReward record);

    int updateByPrimaryKey(USubReward record);
}