package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.UserInfoModel;
import com.pf.play.rule.core.model.VcMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VcMemberMapper<T> extends BaseDao<T> {
    int deleteByPrimaryKey(Integer memberId);

    int insert(VcMember record);

    int insertSelective(VcMember record);

    VcMember selectByPrimaryKey(VcMember record);

    VcMember selectByCodeOrAddress(VcMember record);

    List<VcMember> selectByPid(VcMember record);

    VcMember selectByMemberId(VcMember record);


    int updateByPrimaryKeySelective(VcMember record);

    int updateByPrimaryKey(VcMember record);

    /**
     * @Description: 更新用户的支付密码
     * @param model
     * @return void
     * @author yoko
     * @date 2019/11/21 16:47
     */
    public void updatePayPassword(UserInfoModel model);

    VcMember  selectByIsCertificationNum(VcMember vcMember) ;

    VcMember  selectIsLevel(VcMember vcMember) ;

    List<VcMember> selectByisSynchro(VcMember vcMember);


    void updateMemberIdList(VcMember vcMember);

}