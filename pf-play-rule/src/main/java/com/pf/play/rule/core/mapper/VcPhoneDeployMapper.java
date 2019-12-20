package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.VcPhoneDeploy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VcPhoneDeployMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insert(VcPhoneDeploy record);

    int insertSelective(VcPhoneDeploy record);

    List<VcPhoneDeploy> selectByPrimaryKey(VcPhoneDeploy record);

    int updateByPrimaryKeySelective(VcPhoneDeploy record);

    int updateByPrimaryKey(VcPhoneDeploy record);
}