package com.pf.play.rule;

import com.alibaba.fastjson.JSON;
import com.pf.play.common.utils.BeanUtils;
import com.pf.play.common.utils.StringUtil;
import com.pf.play.model.protocol.request.uesr.UpdateUserReq;
import com.pf.play.model.protocol.request.uesr.UserCommonReq;
import com.pf.play.model.protocol.response.my.Empirical;
import com.pf.play.model.protocol.response.my.InviteMy;
import com.pf.play.model.protocol.response.my.Vitality;
import com.pf.play.model.protocol.response.uesr.*;
import com.pf.play.rule.core.model.*;
import com.pf.play.rule.core.singleton.EmpiricalVitalitySingleton;
import com.pf.play.rule.core.singleton.TaskSingleton;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
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
         inviteMy.setUpdateTime(vcMember.getUpdateTimeStr());
         inviteMy.setDarenLevel(vcMember.getDarenLevel());
         inviteMy.setNickname(vcMember.getNickname());
         inviteMy.setPhone(vcMember.getInviteCode());
         inviteMy.setEmpiricalLevel(vcMember.getEmpiricalLevel());
         inviteMy.setIsCertification(vcMember.getIsCertification());
         inviteMy.setTeamPeople(vcMember.getTeamPeople());
         myFriendsResp.setTeamActive(vcMemberResource.getTeamActive());
         myFriendsResp.setHeroActive(vcMemberResource.getHeroActive());
         myFriendsResp.setAllianceActive(vcMemberResource.getAllianceActive());
         myFriendsResp.setInviteMyList(inviteMy);
         myFriendsResp.setMyInvinteCount(list.size());

         List<InviteMy>  list1 = new ArrayList<>();
         for(VcMember vcMember1:list){
             InviteMy inviteMy1 = new InviteMy();
             inviteMy1.setCreateTime(vcMember1.getCreateTimeStr());
             inviteMy1.setUpdateTime(vcMember1.getUpdateTimeStr());
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
             empirical.setLevel(disEmpiricalValueLevel.getEmpiricalLevel());
             empirical.setUpgradeNum(disEmpiricalValueLevel.getUpgradeNum());
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
            BeanUtils.copy(disVitalityValue,vitality);
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
            vitality.setAllianceVitalitNum(disVitalityValue.getAllianceVitalitNum()+"");
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
    public static MyEmpiricalResp toMyEmpiricalResp(Double EmpiricalValue,List<Empirical>  empiricalList,VcMemberResource vcMemberResource1){
        MyEmpiricalResp  myEmpiricalResp  =   new MyEmpiricalResp();
        if(vcMemberResource1==null){
            myEmpiricalResp.setLevel(0);
            myEmpiricalResp.setNeedVitalityValue(0D);
            myEmpiricalResp.setEmpiricalValue(0D);
        }else{
            for(Empirical empirical:empiricalList){
                if(empirical.getLevel()==vcMemberResource1.getEmpiricalLevel()){
                    myEmpiricalResp.setNeedVitalityValue(Double.valueOf(empirical.getUpgradeNum()));
                    break;
                }
            }
            myEmpiricalResp.setLevel(vcMemberResource1.getEmpiricalLevel());
            myEmpiricalResp.setEmpiricalValue(vcMemberResource1.getEmpiricalValue());
        }
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
    public static MyVitalityResp toMyVitalityListResp(Double VitalityValue,List<Vitality>  vitalityList,VcMemberResource vcMemberResource){
        MyVitalityResp myVitalityResp  =   new MyVitalityResp();
        myVitalityResp.setLevel(vcMemberResource.getDarenLevel());
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
        userCommonReq.setWxOpenId(updateUserReq.getWxOpenId());
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
        if(updateUserReq.getSex()==0){
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
        if(null != updateUserReq.getSex()){
            vcMember1.setSex(updateUserReq.getSex());
        }

        if(null != updateUserReq.getBirthday()){
            vcMember1.setBirthday(updateUserReq.getBirthday());
        }

        if(null != updateUserReq.getProvince()){
            vcMember1.setProvince(updateUserReq.getProvince());
        }

        if(null != updateUserReq.getCity()){
            vcMember1.setCity(updateUserReq.getCity());
        }
        return vcMember1;
    }

    /**
     * @Description: vcMember 转  MyUserInfoResp
     * @param vcMember
     * @return com.pf.play.model.protocol.response.uesr.MyUserInfoResp
     * @author long
     * @date 2019/11/28 16:36
     */
    public static MyUserInfoResp toMyUserInfoResp(VcMember vcMember,VcMemberResource vcMemberResource){
        MyUserInfoResp  myUserInfoResp  = new MyUserInfoResp();
        myUserInfoResp.setNickname(vcMember.getNickname());
        myUserInfoResp.setMemberAdd(vcMember.getMemberAdd());
        myUserInfoResp.setBirthday(vcMember.getBirthday());
        myUserInfoResp.setProvince(vcMember.getProvince());
        myUserInfoResp.setCity(vcMember.getCity());
        myUserInfoResp.setSex(vcMember.getSex());
        myUserInfoResp.setDarenLevel(vcMemberResource.getDarenLevel());
        myUserInfoResp.setEmpiricalLevel(vcMemberResource.getEmpiricalLevel());
        myUserInfoResp.setMasonry(vcMemberResource.getDayMasonry());
        myUserInfoResp.setActiveValue(vcMemberResource.getActiveValue());
        myUserInfoResp.setEmpiricalValue(vcMemberResource.getEmpiricalValue());
        myUserInfoResp.setIsCertif(vcMember.getIsCertification());
        return myUserInfoResp;
    }

    /**
     * @Description: 转换出我的资产
     * @param uMasonryList
     * @return java.util.List<com.pf.play.model.protocol.response.uesr.MyMasonryResp>
     * @author long
     * @date 2019/12/2 21:54
     */
    public static MyMasonryResp toMyMasonryResp(List<UMasonryListLog> uMasonryList,VcMemberResource  vcMemberResource1,UMasonrySummary uMasonrySummary){
        List<MasonryList>  list  = new ArrayList<>();
        MyMasonryResp  myMasonryResp  =new  MyMasonryResp();
        myMasonryResp.setBalance(vcMemberResource1.getDayMasonry());
        myMasonryResp.setInMasonry(uMasonrySummary.getInMasonry());
        myMasonryResp.setOutMasonry(uMasonrySummary.getOutMasonry());
        for (UMasonryListLog uMasonryListLog:uMasonryList){
            MasonryList   masonryList = new MasonryList();
            BeanUtils.copy(uMasonryListLog,masonryList);
            masonryList.setCreateTime(uMasonryListLog.getCreateTimeStr());
            masonryList.setMasonryNum(uMasonryListLog.getMasonryNum());
            list.add(masonryList);
        }
        myMasonryResp.setMasonrylist(list);
        return myMasonryResp;
    }

    /**
     * @Description: 同步出去的用户数据
     * @param vcMember
     * @return String
     * @author long
     * @date 2019/12/5 10:12
     */
    public static  String    toSynchronousResp(VcMember  vcMember,String token){
        SynchronousResp  synchronousResp = new SynchronousResp();
        synchronousResp.setToken(token);
        synchronousResp.setMember_id(vcMember.getMemberId());
        synchronousResp.setMember_add(vcMember.getMemberAdd());
        synchronousResp.setNickname(vcMember.getNickname());
        synchronousResp.setMember_code(vcMember.getMemberCode());
        synchronousResp.setIs_certification(vcMember.getIsCertification());
        synchronousResp.setCreate_time(vcMember.getCreateTime());
        synchronousResp.setIs_active(vcMember.getIsActive());
        synchronousResp.setSex(vcMember.getSex());
        synchronousResp.setBirthday(vcMember.getBirthday());
        synchronousResp.setProvince(vcMember.getProvince());
        synchronousResp.setCity(vcMember.getCity());
        return JSON.toJSONString(synchronousResp);
    }

    /**
     * @Description: 查询组装信息
     * @param memberId
     * @return com.pf.play.rule.core.model.UMasonrySummary
     * @author long
     * @date 2019/12/13 10:06
     */
    public  static UMasonrySummary   toUMasonrySummary(Integer memberId){
        UMasonrySummary  uMasonrySummary  = new UMasonrySummary();
        uMasonrySummary.setMemberId(memberId);
        return  uMasonrySummary;
    }

    /**
     * @Description: 转换成MyVitalityResp
     * @param pushPeople 直推人数
    * @param vcMemberResource
     * @return com.pf.play.model.protocol.response.uesr.MyVitalityResp
     * @author long
     * @date 2019/12/16 0:12
     */
    public  static  MyVitalityResp   toMyVitalityResp(Integer pushPeople ,VcMemberResource vcMemberResource,List<Vitality>  vitalityList){
        MyVitalityResp   myVitalityResp = new MyVitalityResp();
        myVitalityResp.setPushPeople(Double.parseDouble(pushPeople+""));
        myVitalityResp.setLevel(vcMemberResource.getDarenLevel());
        myVitalityResp.setAllianceActive(vcMemberResource.getAllianceActive());
        myVitalityResp.setTeamActive(vcMemberResource.getTeamActive());

        List<DisEmpiricalVitalityAttribute>  list1 = TaskSingleton.getInstance().getDisVitalityAttribute1();
        boolean  flag = false;
        Integer  taskId= 1;
        List<DisVitalityValue>  list =EmpiricalVitalitySingleton.getInstance().getDisVitalityValue();
        for(int  i = 0;i<list.size();i++){
            if(null!=myVitalityResp.getAllianceActiveNum()){
                break;
            }
            if(flag||i==list.size()-1){
                for(DisEmpiricalVitalityAttribute dis:list1){
                   if(taskId==dis.getTypeId()){
                       myVitalityResp.setAllianceActiveNum(Double.valueOf(dis.getKey3()));
                       myVitalityResp.setPushPeopleNum(Double.valueOf(dis.getKey1()));
                       myVitalityResp.setTeamActiveNum(Double.valueOf(dis.getKey2()));
                       break;
                   }
                }
            }
            if(list.get(i).getDarenLevel()==vcMemberResource.getDarenLevel()){
                flag=true;
            }
            taskId++;

        }
        myVitalityResp.setList(vitalityList);

        return   myVitalityResp ;
    }


}
