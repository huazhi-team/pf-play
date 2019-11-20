package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.VcRewardReceive;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VcRewardReceiveMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Integer memberId);

    int insert(VcRewardReceive record);

    int insertSelective(VcRewardReceive record);

    VcRewardReceive selectByPrimaryKey(Integer memberId);

    int updateByPrimaryKeySelective(VcRewardReceive record);

    int updateByPrimaryKey(VcRewardReceive record);
}