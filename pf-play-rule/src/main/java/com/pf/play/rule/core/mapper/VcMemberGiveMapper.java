package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.VcMemberGive;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VcMemberGiveMapper<T> extends BaseDao<T> {

    VcMemberGive  selectByPrimaryKey();

    int insert(VcMemberGive record);

    int insertSelective(VcMemberGive record);
}