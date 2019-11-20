package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.VcRealNameStayHandle;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VcRealNameStayHandleMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insert(VcRealNameStayHandle record);

    int insertSelective(VcRealNameStayHandle record);

    VcRealNameStayHandle select(VcRealNameStayHandle vcRealNameStayHandle);

    int updateByPrimaryKeySelective(VcRealNameStayHandle record);

    int updateByPrimaryKey(VcRealNameStayHandle record);
}