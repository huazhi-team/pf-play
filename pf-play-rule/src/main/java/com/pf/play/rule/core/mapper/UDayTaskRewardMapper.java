package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.UDayTaskReward;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UDayTaskRewardMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insert(UDayTaskReward record);

    int insertSelective(UDayTaskReward record);

    UDayTaskReward selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UDayTaskReward record);

    int updateByPrimaryKey(UDayTaskReward record);
}