package com.pf.play.rule;

import com.pf.play.common.utils.BeanUtils;
import com.pf.play.common.utils.StringUtil;
import com.pf.play.model.protocol.request.uesr.UpdateUserReq;
import com.pf.play.model.protocol.request.uesr.UserCommonReq;
import com.pf.play.model.protocol.response.my.Empirical;
import com.pf.play.model.protocol.response.my.InviteMy;
import com.pf.play.model.protocol.response.my.Vitality;
import com.pf.play.model.protocol.response.uesr.*;
import com.pf.play.rule.core.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/27 14:10
 * @Version 1.0
 */
public class MyMethod {
    /**
     * @Description: 数据转换成我的好友要求出去
     * @param vcMember
    * @param vcMemberResource
    * @param list
     * @return java.util.List<com.pf.play.model.protocol.response.uesr.MyFriendsResp>
     * @author long
     * @date 2019/11/27 15:05
     */
     public  static MyFriendsResp   toMyFriendsResp(VcMember vcMember, VcMemberResource vcMemberResource ,List<VcMember> list){
         MyFriendsResp   myFriendsResp  =  new MyFriendsResp();
         BeanUtils.copy(vcMemberResource,myFriendsResp);
         InviteMy inviteMy = new InviteMy();
         inviteMy.setCreateTime(vcMember.getCreateTimeStr());
         inviteMy.setDarenLevel(vcMember.getDarenLevel());
         inviteMy.setNickname(vcMember.getNickname());
         inviteMy.setPhone(vcMember.getInviteCode());
         inviteMy.setEmpiricalLevel(vcMember.getEmpiricalLevel());
         inviteMy.setIsCertification(vcMember.getIsCertification());
         inviteMy.setTeamPeople(vcMember.getTeamPeople());

         myFriendsResp.setInviteMyList(inviteMy);
         myFriendsResp.setMyInvinteCount(list.size());

         List<InviteMy>  list1 = new ArrayList<>();
         for(VcMember vcMember1:list){
             InviteMy inviteMy1 = new InviteMy();
             inviteMy1.setCreateTime(vcMember1.getCreateTimeStr());
             inviteMy1.setDarenLevel(vcMember1.getDarenLevel());
             inviteMy1.setNickname(vcMember1.getNickname());
             inviteMy1.setPhone(vcMember1.getInviteCode());
             inviteMy1.setEmpiricalLevel(vcMember1.getEmpiricalLevel());
             inviteMy1.setIsCertification(vcMember1.getIsCertification());
             inviteMy1.setTeamPeople(vcMember1.getTeamPeople());
             list1.add(inviteMy1);
         }
         myFriendsResp.setMyInviteList(list1);
         return myFriendsResp;
     }

    /**
     * @Description: List DisEmpiricalValueLevel 转换为 List Empirical
     * @param list
     * @return java.util.List<com.pf.play.model.protocol.response.my.Empirical>
     * @author long
     * @date 2019/11/27 21:15
     */
     public static List<Empirical>   toDisEmpiricalValue(List<DisEmpiricalValueLevel>  list){
         List<Empirical>  empiricals  =   new ArrayList<>();
         for(DisEmpiricalValueLevel disEmpiricalValueLevel:list){
             Empirical empirical = new Empirical();
             empirical.setEmpiricalName(disEmpiricalValueLevel.getEmpiricalName());
             empirical.setRemarks(disEmpiricalValueLevel.getRemarks());
             if(disEmpiricalValueLevel.getTransactionNumber()==0){
                 empirical.setTransactionValue("无限制");
             }else{
                 empirical.setTransactionValue(disEmpiricalValueLevel.getTransactionNumber()+"");
             }
             empirical.setTransactionFee(disEmpiricalValueLevel.getTransactionFee()+"%");
             empiricals.add(empirical);
         }
         return empiricals;
     }

    /**
     * @Description: List DisVitalityValue 转换为 List  Vitality
     * @param list
     * @return java.util.List<com.pf.play.model.protocol.response.my.Vitality>
     * @author long
     * @date 2019/11/27 22:31
     */
    public static List<Vitality>   toDisVitalityValue(List<DisVitalityValue>  list){
        List<Vitality>  empiricals  =   new ArrayList<>();
        for(DisVitalityValue disVitalityValue:list){
            Vitality vitality = new Vitality();
            if(disVitalityValue.getPushNumber()==0){
                vitality.setPushNumber("实名制");
            }else{
                vitality.setPushNumber(disVitalityValue.getPushNumber()+"");
            }
            if(disVitalityValue.getPushNumber()==0){
                vitality.setTeamVitalitNum("实名制");
            }else{
                vitality.setTeamVitalitNum(disVitalityValue.getTeamVitalitNum()+"");
            }
            if(disVitalityValue.getPushNumber()==0){
                vitality.setTeamVitalitNum("实名制");
            }else{
                vitality.setTeamVitalitNum(disVitalityValue.getAllianceVitalitNum()+"");
            }
            vitality.setRewardNum(disVitalityValue.getRewardNum()+"");
            vitality.setRemarks(disVitalityValue.getRemarks());
            empiricals.add(vitality);
        }
        return empiricals;
    }



    /**
     * @Description: 拼接成我的经验值
     * @param EmpiricalValue
    * @param empiricalList
     * @return com.pf.play.model.protocol.response.uesr.MyEmpiricalResp
     * @author long
     * @date 2019/11/27 21:45
     */
    public static MyEmpiricalResp toMyEmpiricalResp(Float EmpiricalValue,List<Empirical>  empiricalList){
        MyEmpiricalResp  myEmpiricalResp  =   new MyEmpiricalResp();
        myEmpiricalResp.setEmpiricalValue(EmpiricalValue);
        myEmpiricalResp.setList(empiricalList);
        return myEmpiricalResp;
    }
    /**
     * @Description: 拼接成我的活跃值
     * @param VitalityValue
    * @param empiricalList
     * @return com.pf.play.model.protocol.response.uesr.MyEmpiricalResp
     * @author long
     * @date 2019/11/27 21:45
     */
    public static MyVitalityResp toMyVitalityListResp(Float VitalityValue,List<Vitality>  vitalityList){
        MyVitalityResp myVitalityResp  =   new MyVitalityResp();
        myVitalityResp.setVitalityValue(VitalityValue);
        myVitalityResp.setList(vitalityList);
        return myVitalityResp;
    }

    /**
     * @Description: TODO
     * @param updateUserReq
     * @return com.pf.play.model.protocol.request.uesr.UserCommonReq
     * @author long
     * @date 2019/11/28 15:28
     */
    public static UserCommonReq uqdateUserToUserCommon(UpdateUserReq updateUserReq){
        UserCommonReq userCommonReq = new UserCommonReq();
        userCommonReq.setToken(updateUserReq.getToken());
        userCommonReq.setWxOpenid(updateUserReq.getWxOpenId());
        return  userCommonReq;
    }

    /**
     * @Description: 是否是有效的请求
     * @param updateUserReq
     * @return boolean
     * @author long
     * @date 2019/11/28 15:47
     */
    public static boolean checkUqdateUser(UpdateUserReq updateUserReq){
        boolean  flag = true;
        if(updateUserReq.getSix()==0){
            flag = false;
        }else if(StringUtil.isEmpty(updateUserReq.getWxOpenId())||
                    StringUtil.isEmpty(updateUserReq.getToken())||
                        StringUtil.isEmpty(updateUserReq.getBirthday())||
                            StringUtil.isEmpty(updateUserReq.getProvince())||
                                StringUtil.isEmpty(updateUserReq.getCity())){
            flag =false;
        }
        return  flag;
    }

    /**
     * @Description: 修改用户信息的转换
     * @param memberId
    * @param updateUserReq
     * @return com.pf.play.rule.core.model.VcMember
     * @author long
     * @date 2019/11/28 16:04
     */
    public static VcMember toUqdateVcMember(Integer memberId,UpdateUserReq updateUserReq){
        VcMember  vcMember1  = new VcMember();
        vcMember1.setMemberId(memberId);
//        vcMember1.setMemberAdd(updateUserReq.getMemberAdd());
//        vcMember1.setNickname(updateUserReq.getNickname());
        vcMember1.setSex(updateUserReq.getSix());
        vcMember1.setBirthday(updateUserReq.getBirthday());
        vcMember1.setProvince(updateUserReq.getProvince());
        vcMember1.setCity(updateUserReq.getCity());
        return vcMember1;
    }

    /**
     * @Description: vcMember 转  MyUserInfoResp
     * @param vcMember
     * @return com.pf.play.model.protocol.response.uesr.MyUserInfoResp
     * @author long
     * @date 2019/11/28 16:36
     */
    public static MyUserInfoResp toMyUserInfoResp(VcMember vcMember){
        MyUserInfoResp  myUserInfoResp  = new MyUserInfoResp();
        myUserInfoResp.setNickname(vcMember.getNickname());
        myUserInfoResp.setMemberAdd(vcMember.getMemberAdd());
        myUserInfoResp.setBirthday(vcMember.getBirthday());
        myUserInfoResp.setProvince(vcMember.getProvince());
        myUserInfoResp.setCity(vcMember.getCity());
        myUserInfoResp.setSex(vcMember.getSex());
        return myUserInfoResp;
    }

    /**
     * @Description: 转换出我的资产
     * @param uMasonryList
     * @return java.util.List<com.pf.play.model.protocol.response.uesr.MyMasonryResp>
     * @author long
     * @date 2019/12/2 21:54
     */
    public static List<MyMasonryResp> toMyMasonryResp(List<UMasonryListLog> uMasonryList){
        List<MyMasonryResp>  list  = new ArrayList<>();
        for (UMasonryListLog uMasonryListLog:uMasonryList){
            MyMasonryResp   myMasonryResp = new MyMasonryResp();
            BeanUtils.copy(uMasonryListLog,myMasonryResp);
            myMasonryResp.setCreateTime(uMasonryListLog.getCreateTimeStr());
            list.add(myMasonryResp);
        }
        return list;
    }


}
