package com.pf.play.rule;

import com.pf.play.common.utils.BeanUtils;
import com.pf.play.common.utils.DateUtil;
import com.pf.play.model.protocol.request.register.PhoneRegister;
import com.pf.play.model.protocol.request.uesr.RegisterReq;
import com.pf.play.rule.core.common.utils.constant.Constant;
import com.pf.play.rule.core.model.*;
import com.pf.play.rule.core.singleton.RegisterSingleton;
import com.pf.play.rule.core.singleton.TaskSingleton;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @Description 注册相关的
 * @Author long
 * @Date 2019/11/24 11:29
 * @Version 1.0
 */
public class RegisterMethod {

    /**
     * @Description: 生成用户砖石汇总表的信息
     * @param memberId
     * @return com.pf.play.rule.core.model.UMasonrySummary
     * @author long
     * @date 2019/11/24 14:41
     */
    public  static UMasonrySummary  insertUMasonrySummary(Integer memberId){
        Date   date  = new  Date();
        UMasonrySummary  uMasonrySummary = new UMasonrySummary();
        uMasonrySummary.setCreateTime(date);
        uMasonrySummary.setUpdateTime(date);
        uMasonrySummary.setMemberId(memberId);
        return   uMasonrySummary;
    }
    /**
     * @Description: TODO
     * @param memberId
    * @param registerReq
    * @param SecureUUID
    * @param superiorId
    * @param extensionMemberId
     * @return com.pf.play.rule.core.model.VcMember
     * @author long
     * @date 2019/11/24 16:14
     */
    public  static VcMember insertVcMember(Integer memberId, RegisterReq registerReq,String [] SecureUUID,Integer superiorId,String extensionMemberId){
        VcMember  memberModel = new VcMember();
        Integer  createTime  =  Integer.parseInt(DateUtil.timeStamp());
        Integer  loginTime  =  createTime;
        memberModel.setMemberId(memberId);
        memberModel.setDeviceId(registerReq.getDeviceId());
        memberModel.setOwnerMemberId(registerReq.getOwner());
        memberModel.setMemberAdd(registerReq.getMemberAdd());
        memberModel.setNickname(registerReq.getWxName());
        memberModel.setMemberCode("C"+registerReq.getPhone());
        if(registerReq.getOwner()==0){
            memberModel.setMemberType(2);
        }else{
            memberModel.setMemberType(1);
        }
        memberModel.setInviteCode(SecureUUID[0]);
        memberModel.setTradingAddress(SecureUUID[1]);
        memberModel.setCreateTime(createTime);
        memberModel.setLoginTime(loginTime);
        memberModel.setSuperiorId(superiorId);
        memberModel.setExtensionMemberId(extensionMemberId+","+memberId);
        return   memberModel;
    }

    /**
     * @Description: 账户信息
     * @param memberId
    * @param phone
    * @param accountType
     * @return com.pf.play.rule.core.model.VcAccountRelation
     * @author long
     * @date 2019/11/24 16:30
     */
    public  static VcAccountRelation insertVcAccountRelation(Integer memberId,String phone,Integer accountType){
        Integer  createTime  =  Integer.parseInt(DateUtil.timeStamp());
        Integer  loginTime  =  createTime;
        VcAccountRelation  accountRelationModel = new VcAccountRelation();//账户信息
        accountRelationModel.setMemberId(memberId);
        accountRelationModel.setAccountNum(phone);
        accountRelationModel.setCreateTime(createTime);
        accountRelationModel.setUpdateTime(loginTime);
        accountRelationModel.setAccountType(accountType);
        return   accountRelationModel;
    }

    /**
     * @Description: 根据用户添加初始奖励
     * @param memberId
     * @return com.pf.play.rule.core.model.UTaskHave
     * @author long
     * @date 2019/11/25 10:07
     */
    public  static UTaskHave  insertUTaskHave(Integer memberId ){
        List<DisTaskType> taskList  = TaskSingleton.getInstance().getDisTaskTypeList();
        UTaskHave   uTaskHave =new  UTaskHave();
        if(taskList.size()==0){
            return uTaskHave;
        }
        Date   date  = new Date();
        Date endTime = DateUtil.getDateBetween(date,taskList.get(0).getTaskValidityDay());
        uTaskHave.setMemberId(memberId);
        uTaskHave.setTaskId(1);
        uTaskHave.setSurplusDay(taskList.get(0).getTaskValidityDay());
        uTaskHave.setStartTime(date);
        uTaskHave.setEndTime(endTime);
        uTaskHave.setSurplusNum(taskList.get(0).getTotalNum());
        uTaskHave.setCurday(DateUtil.getDayNumber(date));
        uTaskHave.setCurhour(DateUtil.getHour(date));
        uTaskHave.setCurminute(DateUtil.getCurminute(date));
        uTaskHave.setGiveType(1);
        uTaskHave.setTaskLevel(1);
        uTaskHave.setCreateTime(date);
        uTaskHave.setUpdateTime(date);
        return  uTaskHave ;
    }

    /**
     * @Description: 注册用户会员奖励领取表初始化
     * @param memberId
     * @return com.pf.play.rule.core.model.VcRewardReceive
     * @author long
     * @date 2019/11/25 10:13
     */
    public static  VcRewardReceive  insertVcRewardReceive(Integer memberId){
        Date     createdate =  DateUtil.currentTimestamp();
        VcRewardReceive  vcRewardReceive  = new VcRewardReceive();
        vcRewardReceive.setMemberId(memberId);
        vcRewardReceive.setCreateTime(createdate);
        vcRewardReceive.setUpdateTime(createdate);
        vcRewardReceive.setIsLevel0(2);
        return vcRewardReceive;
    }
    /**
     * @Description: 注册用户三方资源信息
     * @param memberId
    * @param wxRefresh
    * @param wxOpenid
    * @param wxUnionid
    * @param token
     * @return com.pf.play.rule.core.model.VcThirdParty
     * @author long
     * @date 2019/11/25 10:18
     */
    public static  VcThirdParty  insertVcThirdParty(Integer memberId,String wxRefresh,String wxOpenid,String wxUnionid,String token){
        String   token_expire   =  DateUtil.sdfLongTimePlus.format(DateUtil.addDay(new Date(), Constant.TOKEN_EXPIRE));
        Long  token_expire_time = DateUtil.getSecordsFromString(token_expire)/1000;
        VcThirdParty   vcThirdPartyModel  = new VcThirdParty(); //三方资源信息
        vcThirdPartyModel.setMemberId(memberId);
        vcThirdPartyModel.setTokenExpire(token_expire_time.intValue());
        vcThirdPartyModel.setWxRefresh(wxRefresh);
        vcThirdPartyModel.setWxOpenid(wxOpenid);
        vcThirdPartyModel.setWxUnionid(wxUnionid);
        vcThirdPartyModel.setToken(token);
        return   vcThirdPartyModel;
    }

    /**
     * @Description: 注册用户会员资源初始化
     * @param memberId
     * @return com.pf.play.rule.core.model.VcMemberResource
     * @author long
     * @date 2019/11/25 10:27
     */
    public  static VcMemberResource  insertVcMemberResource(Integer memberId){
        Double  activeValue = Double.valueOf("0");
        Date     createdate =  DateUtil.currentTimestamp();
        VcMemberResource   vcMemberResourceModel  =  new  VcMemberResource();
        VcMemberGive   vcMemberGive  =  RegisterSingleton.getInstance().getVcMemberGive();
        vcMemberResourceModel.setMemberId(memberId);
//        vcMemberResourceModel.setActiveValue();
        vcMemberResourceModel.setCreateTime(createdate);
        vcMemberResourceModel.setDarenLevel(0);
        vcMemberResourceModel.setUpdateTime(new Date());
        if(vcMemberGive==null||vcMemberGive.getActiveValue()<=0){//没有获取到值
            vcMemberResourceModel.setActiveValue(activeValue);
        }else{
            vcMemberResourceModel.setActiveValue(Double.valueOf(vcMemberGive.getActiveValue()));
        }
        return   vcMemberResourceModel;
    }

    /**
     * @Description: 查询电话号码是否被注册
     * @param phone
     * @return com.pf.play.rule.core.model.VcPhoneDeploy
     * @author long
     * @date 2019/12/20 10:37
     */
    public  static VcPhoneDeploy  queryVcPhoneDeploy(String  phone){
        VcPhoneDeploy  vcPhoneDeploy = new  VcPhoneDeploy();
        vcPhoneDeploy.setPhone(phone);
        return   vcPhoneDeploy;
    }


    /**
     * @Description: 查询电话号码是否被注册
     * @param phone
     * @return com.pf.play.rule.core.model.VcPhoneDeploy
     * @author long
     * @date 2019/12/20 10:37
     */
    public  static VcMember  queryPhoneVcMember(String  phone){
        VcMember  vcMember = new  VcMember();
        vcMember.setMemberCode("C"+phone);
        return   vcMember;
    }

    /**
     * @Description: 添加 vcPhoneDeploy 信息
     * @param phone
    * @param inviteCode
     * @return com.pf.play.rule.core.model.VcPhoneDeploy
     * @author long
     * @date 2019/12/20 10:56
     */
    public  static VcPhoneDeploy  insertPhoneVcMember(String  phone,String inviteCode ){
        VcPhoneDeploy  vcPhoneDeploy = new  VcPhoneDeploy();
        DateModel dateModel =TaskMethod.getDate();
        BeanUtils.copy(dateModel,vcPhoneDeploy);
        vcPhoneDeploy.setPhone(phone);
        vcPhoneDeploy.setInviteCode(inviteCode);
        return   vcPhoneDeploy;
    }
    /**
     * @Description: 参数不正确
     * @param phoneRegister
     * @return boolean
     * @author long
     * @date 2019/12/20 11:38
     */
    public  static boolean  cheackPhoneDeployOk(PhoneRegister phoneRegister){
        boolean  flag = false ;
        if(StringUtils.isBlank(phoneRegister.getInviteCode())){
            return flag;
        }else  if(StringUtils.isBlank(phoneRegister.getPhone())){
            return flag;
        }else  if(StringUtils.isBlank(phoneRegister.getTimeStamp())){
            return flag;
        }else  if(StringUtils.isBlank(phoneRegister.getSmsVerification())){
            return flag;
        }
        return   true;
    }

    /**
     * @Description: 添加 vcPhoneDeploy 信息
     * @param phone
    * @param inviteCode
     * @return com.pf.play.rule.core.model.VcPhoneDeploy
     * @author long
     * @date 2019/12/20 10:56
     */
    public  static VcPhoneDeploy  queryPhoneDeploy(String  phone){
        VcPhoneDeploy  vcPhoneDeploy = new  VcPhoneDeploy();
        vcPhoneDeploy.setPhone(phone);
        vcPhoneDeploy.setIsValid(1);
        return   vcPhoneDeploy;
    }

}
