package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.VcMember;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VcMemberMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Integer memberId);

    int insert(VcMember record);

    int insertSelective(VcMember record);

    VcMember selectByPrimaryKey(VcMember record);

    VcMember selectByCodeOrAddress(VcMember record);

    int updateByPrimaryKeySelective(VcMember record);

    int updateByPrimaryKey(VcMember record);
}