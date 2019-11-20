package com.pf.play.rule.core.service;

import com.pf.play.model.protocol.request.uesr.RegisterReq;
import com.pf.play.model.protocol.response.task.uesr.RegisterResp;
import com.pf.play.model.protocol.response.task.uesr.UserInfoResp;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.VcMember;
import com.pf.play.rule.core.model.redis.RegisterModel;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/8 14:04
 * @Version 1.0
 */
public interface RegisterService <T> extends BaseService<T> {
      public UserInfoResp savaRegisterInfo(RegisterReq registerReq) throws  Exception;
      void initDate() throws  Exception;
      boolean  checkRegister(RegisterReq registerReq)throws  Exception;
      VcMember checkInviteCode(RegisterReq registerReq)throws  Exception;
      String   createInviteCode()throws  Exception;
      boolean  addUserInfo(RegisterReq registerReq,String[] inviteCode,
                                    Integer superiorId,String extensionMemberId,String token)throws  Exception;
      void     addRedis(RegisterReq registerReq,String [] InviteAdd)throws  Exception;
      public  RegisterResp  getSmsVerification(String phone) throws  Exception;

      boolean   userRegisterReward(Integer memberId)throws  Exception;
}
