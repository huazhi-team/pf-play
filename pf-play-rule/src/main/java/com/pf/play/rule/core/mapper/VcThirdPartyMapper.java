package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.VcThirdParty;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VcThirdPartyMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insert(VcThirdParty record);

    int insertSelective(VcThirdParty record);

    VcThirdParty selectByPrimaryKey(VcThirdParty record);

    int updateByPrimaryKeySelective(VcThirdParty record);

    int updateByWxOpenId(VcThirdParty record);

    int updateByPrimaryKey(VcThirdParty record);
}