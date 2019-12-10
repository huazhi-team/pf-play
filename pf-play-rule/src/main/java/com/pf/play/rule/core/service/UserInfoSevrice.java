package com.pf.play.rule.core.service;

import com.pf.play.model.protocol.response.my.Empirical;
import com.pf.play.model.protocol.response.my.Vitality;
import com.pf.play.model.protocol.response.uesr.MyFriendsResp;
import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.DisVitalityValue;
import com.pf.play.rule.core.model.UserInfoModel;
import com.pf.play.rule.core.model.VcMember;
import com.pf.play.rule.core.model.VcMemberResource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/11 14:34
 * @Version 1.0
 */

public interface UserInfoSevrice  <T> extends BaseService<T> {
    public UserInfoModel getUserInfo(Integer type,String wxOpenid,String phone,String passWord);
    boolean  receiveTaskReward(Integer type,String wxOpenid,String phone,String passWord)throws  Exception;

    /**
     * @Description: 更新用户的支付密码
     * @param model
     * @return void
     * @author yoko
     * @date 2019/11/21 16:47
    */
    public void updatePayPassword(UserInfoModel model);

    VcMember getMySuperiorInfo(Integer memberId);
    List<VcMember> getMyUpInfo(Integer memberId);
    VcMemberResource getMyTeamResourceInfo(Integer memberId);

    List<Empirical> getMyEmpirical(Integer memberId);

    List<Vitality> getMyDisVitalityValue(Integer memberId);

    VcMember   getMemeber(Integer memberId);

    boolean   updateMemeber(VcMember vcMember);

    boolean   isToken(String  token);

    MyFriendsResp toMyFriensResp(Integer memberId, Integer superiorId)throws  Exception;

    Boolean    userSynchronousQhr(Integer memberId,String token);

    void       superiorSynchronousQhr(List<VcMember> list);

    void       executeSuperior();

}
