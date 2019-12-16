package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.VcMember;
import com.pf.play.rule.core.model.VcMemberResource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VcMemberResourceMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Long id);

    int insert(VcMemberResource record);

    int insertSelective(VcMemberResource record);

    VcMemberResource selectByPrimaryKey(VcMemberResource record);

    List<VcMemberResource> selectByMemberId(Integer memberId);

    int updateByPrimaryKeySelective(VcMemberResource record);

    int updateByPrimaryKey(VcMemberResource record);

    int updateAddmemberResource(VcMemberResource record);

    List<VcMemberResource> selectMemberId(VcMemberResource record);

    int updateHeroActive(VcMember vcMember);

    int updateAllianceActive(VcMember vcMember);

    int updateTeamActive(VcMemberResource record);

    int updateAddMasonry(VcMemberResource record);

    int updateCutMasonry(VcMemberResource record);

    int updateByActiveValue(VcMemberResource record);

    List<VcMemberResource>  selectByTeamActive(VcMemberResource record);

    int updateByMasonry(VcMemberResource record);




}