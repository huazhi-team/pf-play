package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.UdailyTaskStat;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UdailyTaskStatMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insert(UdailyTaskStat record);

    int insertSelective(UdailyTaskStat record);

    UdailyTaskStat selectByPrimaryKey(UdailyTaskStat record);

    int updateByPrimaryKeySelective(UdailyTaskStat record);

    int updateByPrimaryKey(UdailyTaskStat record);

    int updateByMemberId(UdailyTaskStat record);

    UdailyTaskStat selectByMemberIdDay(UdailyTaskStat record);
}