package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.VcMemberResource;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VcMemberResourceMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insert(VcMemberResource record);

    int insertSelective(VcMemberResource record);

    VcMemberResource selectByPrimaryKey(VcMemberResource record);

    int updateByPrimaryKeySelective(VcMemberResource record);

    int updateByPrimaryKey(VcMemberResource record);
}