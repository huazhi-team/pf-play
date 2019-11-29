package com.pf.play.rule;

import com.pf.play.common.utils.BeanUtils;
import com.pf.play.common.utils.DateUtil;
import com.pf.play.common.utils.PhoneUtil;
import com.pf.play.model.protocol.request.task.TaskReq;
import com.pf.play.model.protocol.request.uesr.PhoneVerificationReq;
import com.pf.play.model.protocol.request.uesr.RegisterReq;
import com.pf.play.model.protocol.request.uesr.UserCommonReq;
import com.pf.play.rule.core.model.*;
import com.pf.play.rule.core.singleton.TaskSingleton;
import com.pf.play.rule.util.ComponentUtil;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @Description 装换处理表
 * @Author long
 * @Date 2019/11/20 20:41
 * @Version 1.0
 */
public class TaskMethod {
    /**
     * @Description: TODO
     * @param memberId
    * @param type
    * @param symbo
    * @param activeValue
     * @return com.pf.play.rule.core.model.UvitalityValueList
     * @author long
     * @date 2019/11/20 20:33
     */
    public static UvitalityValueList pottingVitalityValue(Integer memberId, Integer type,
                                                          Integer symbo, Float activeValue){
        UvitalityValueList  uvitalityValueList = new  UvitalityValueList();
        DateModel  dateModel  = TaskMethod.getDate();
        BeanUtils.copy(dateModel,uvitalityValueList);
        uvitalityValueList.setMemberId(memberId);
        uvitalityValueList.setRewardType(type);
        uvitalityValueList.setSymbolType(symbo);
        uvitalityValueList.setActiveValue(activeValue);
        uvitalityValueList.setIsValid(1);
        return   uvitalityValueList;
    }

    /**
     * @Description: 获取当前系统时间
     * @param
     * @return com.pf.play.rule.core.model.DateModel
     * @author long
     * @date 2019/11/20 22:15
     */
    public  static DateModel  getDate(){
        Date   date  = new Date();
        DateModel dateModel = new DateModel();
        dateModel.setCurday(DateUtil.getDayNumber(date));
        dateModel.setCurhour(DateUtil.getHour(date));
        dateModel.setCurminute(DateUtil.getCurminute(date));
        dateModel.setCreateTime(date);
        dateModel.setUpdateTime(date);
        return dateModel;
    }

    /**
     * @Description: 活力值修改状态转换
     * @param uvitalityValue
     * @return com.pf.play.rule.core.model.UvitalityValueList
     * @author long
     * @date 2019/11/21 11:31
     */
    public  static  UvitalityValueList toUqdateInfo(UvitalityValueList uvitalityValue){
        UvitalityValueList uvitalityValueList = new UvitalityValueList();
        uvitalityValueList.setId(uvitalityValue.getId());
        uvitalityValueList.setIsCount(uvitalityValue.getIsCount());
        return  uvitalityValueList;
    }


    public static VitalityValueModel   vitalityValueToVitalityValueModel(List<UvitalityValueList> uvitalityValueList ){
        VitalityValueModel valueModel = new  VitalityValueModel();

        return valueModel;
    }


    /**
     * @Description: 根据taskId  获取单条用户信息
     * @param taskId
     * @return com.pf.play.rule.core.model.DisTaskType
     * @author long
     * @date 2019/11/21 17:06
     */
    public static DisTaskType   changeDisTaskType(Integer taskId){
        DisTaskType disTaskType = new DisTaskType();
        List<DisTaskType>  taskList  = TaskSingleton.getInstance().getDisTaskTypeList();
        if(taskList.size()==0){
            return null;
        }
        for (DisTaskType disTaskType1:taskList){
            if(disTaskType1.getTaskId()==taskId){
                BeanUtils.copy(disTaskType1,disTaskType);
                break;
            }
        }
        return  disTaskType;
    }


    /**
     * @Description: 生成用户拥有表
     * @param memberId  用户id
    * @param taskId    任务id
    * @param validityDay  剩余天数
    * @param totalNum     总产量
    * @param taskLevel    任务等级
     * @return com.pf.play.rule.core.model.UTaskHave
     * @author long
     * @date 2019/11/21 17:19
     */
    public static UTaskHave   changeAddUTaskHave(Integer memberId,Integer taskId,Integer validityDay,Float totalNum,Integer taskLevel){
        UTaskHave uTaskHave = new UTaskHave();
        DateModel dateModel= TaskMethod.getDate();
        Date endTime = DateUtil.getDateBetween(dateModel.getCreateTime(),validityDay);
        BeanUtils.copy(dateModel,uTaskHave);
        uTaskHave.setSurplusDay(validityDay);
        uTaskHave.setMemberId(memberId);
        uTaskHave.setTaskId(taskId);
        uTaskHave.setSurplusNum(totalNum);
        uTaskHave.setGiveType(0);
        uTaskHave.setTaskLevel(taskLevel);
        uTaskHave.setStartTime(dateModel.getCreateTime());
        uTaskHave.setEndTime(endTime);
        return  uTaskHave;
    }

    /**
     * @Description: 根据taskId 获取 条件列表的信息
     * @param taskId    任务id
     * @return com.pf.play.rule.core.model.DisTaskAttribute
     * @author long
     * @date 2019/11/21 17:30
     */
    public   static  DisTaskAttribute   taskIdChangeDisTaskAttribute(Integer taskId){
        DisTaskAttribute disTaskAttribute = new DisTaskAttribute();
        List<DisTaskAttribute>  buteList = TaskSingleton.getInstance().getAttributeTypeList2();
        for(DisTaskAttribute disTaskAttribute1:buteList){
            if(disTaskAttribute1.getTaskId()==taskId){
                BeanUtils.copy(disTaskAttribute1,disTaskAttribute);
                break;
            }
        }
        return disTaskAttribute;
    }

    /**
     * @Description: 用户砖石明细需要信息
     * @param memberId  用户id
    * @param taskId    任务id
    * @param type      明细类型
    * @param SymbolType 1 是加  2 是减
    * @param needResource 砖石数
     * @return com.pf.play.rule.core.model.UMasonryListLog
     * @author long
     * @date 2019/11/21 17:35
     */
    public   static  UMasonryListLog   changeUMasonryListLog(Integer memberId,Integer taskId,Integer type,Integer SymbolType,Float needResource){
        UMasonryListLog   uMasonryListLog  =  new UMasonryListLog();
        DateModel dateModel= TaskMethod.getDate();
        BeanUtils.copy(dateModel,uMasonryListLog);
        uMasonryListLog.setMemberId(memberId);
        uMasonryListLog.setTaskId(taskId);
        uMasonryListLog.setType(type);
        uMasonryListLog.setSymbolType(SymbolType);
        uMasonryListLog.setMasonryNum(needResource);
        return uMasonryListLog;
    }

    /**
     * @Description: 用户资源表需要修改的字段
     * @param memberId
    * @param masonry
     * @return com.pf.play.rule.core.model.VcMemberResource
     * @author long
     * @date 2019/11/21 18:01
     */
    public   static VcMemberResource  changeUpdateResource(Integer memberId,Float masonry){
        VcMemberResource vcMemberResource = new VcMemberResource();
        vcMemberResource.setMemberId(memberId);
        vcMemberResource.setUpdateTime(new Date());
        vcMemberResource.setDayMasonry(masonry);
        return vcMemberResource;
    }

    /**
     * @Description: 根据用户id 生成资源查询
     * @param memberId
     * @return com.pf.play.rule.core.model.VcMemberResource
     * @author long
     * @date 2019/11/21 19:30
     */
    public  static  VcMemberResource    changeQueryMemberResource(Integer memberId){
        VcMemberResource  vcMemberResource = new VcMemberResource();
        vcMemberResource.setMemberId(memberId);
        return  vcMemberResource;
    }

    /**
     * @Description: 执行日志表的状态
     * @param uvitalityValueList  总的结果
    * @param successObject       成功的结果
    * @param failObject          失败的结果
     * @return void
     * @author long
     * @date 2019/11/21 19:34
     */
    public static  void   exeUserVitalityValueType( List<UvitalityValueList>   uvitalityValueList,
                                                    List<UvitalityValueList>   successObject,
                                                    List<UvitalityValueList>   failObject){
        if(successObject.size()==uvitalityValueList.size()){
            for(UvitalityValueList uvitalityValue:successObject){
                ComponentUtil.taskService.updateUserVitalityValueType(uvitalityValue,4);
            }
        }else{
            for(UvitalityValueList uvitalityValue:successObject){
                ComponentUtil.taskService.updateUserVitalityValueType(uvitalityValue,4);
            }
            for(UvitalityValueList uvitalityValue:failObject){
                ComponentUtil.taskService.updateUserVitalityValueType(uvitalityValue,3);
            }
        }
    }



    /**
     * @Description: 修改用户资源表，需要的条件
     * @param memberId     会员id
    * @param activeValue  修改的资源
     * @return com.pf.play.rule.core.model.VcMemberResource
     * @author long
     * @date 2019/11/21 19:49
     */
    public static   VcMemberResource   changUpdateResourceTOActiveValue(Integer memberId,Float activeValue ){
        VcMemberResource vcMemberResource1  =  new VcMemberResource();
        vcMemberResource1.setMemberId(memberId);
        vcMemberResource1.setUpdateTime(new Date());
        vcMemberResource1.setActiveValue(activeValue);
        return vcMemberResource1;
    }



    /**
     * @Description: 没有登录达人奖励信息转换
     * @param list
     * @return java.util.List<com.pf.play.rule.core.model.DisWisemanInfo>
     * @author long
     * @date 2019/11/22 12:01
     */
    public static   List<DisWisemanInfo>   changNoLoginDisWisemanInfo(List<DisWisemanInfo> list){
        for(DisWisemanInfo disWisemanInfo :list){
            disWisemanInfo.setIsDisplay(2);
            disWisemanInfo.setIsReceive(3);
        }
        return list;
    }

    /**
     * @Description: 用户详细信息
     * @param memberId
     * @return com.pf.play.rule.core.model.VcMember
     * @author long
     * @date 2019/11/22 16:02
     */
    public static   VcMember   changvcMember(Integer memberId){
        VcMember  vcMember = new  VcMember();
        vcMember.setMemberId(memberId);
        return   vcMember;
    }

    /**
     * @Description: 用户详细信息
     * @param memberId
     * @return com.pf.play.rule.core.model.VcMember
     * @author long
     * @date 2019/11/22 16:02
     */
    public static   VcMemberResource   changvcMemberResource(Integer memberId){
        VcMemberResource  vcMemberResource = new  VcMemberResource();
        vcMemberResource.setMemberId(memberId);
        return   vcMemberResource;
    }


    /**
     * @Description: 查询最大的size
     * @param vcMemberResource
    * @param rs
    * @param vcMember
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author long
     * @date 2019/11/26 14:34
     */
    public  static Map<String,Object> changCurrentAndMaxMap(VcMemberResource vcMemberResource, VcRewardReceive  rs,VcMember vcMember ){


        Map<String,Object> map = new HashMap<>();
        if(vcMember.getIsCertification()==1){
            for(int i=0; i<6;i++){
                map.put("size"+i,3);
            }
        }else{
            for(int  i=0;i<6;i++){
                if(i<=vcMemberResource.getDarenLevel()){
                    if(i==0){
                        if(rs.getIsLevel0()==1){
                            map.put("size"+i,2);
                        }else{
                            map.put("size"+i,1);
                        }
                    }else if(i==1){
                        if(rs.getIsLevel1()==1){
                            map.put("size"+i,2);
                        }else{
                            map.put("size"+i,1);
                        }
                    }else if(i==2){
                        if(rs.getIsLevel2()==1){
                            map.put("size"+i,2);
                        }else{
                            map.put("size"+i,1);
                        }
                    }else if(i==3){
                        if(rs.getIsLevel3()==1){
                            map.put("size"+i,2);
                        }else{
                            map.put("size"+i,1);
                        }
                    }else if(i==4){
                        if(rs.getIsLevel4()==1){
                            map.put("size"+i,2);
                        }else{
                            map.put("size"+i,1);
                        }
                    }else if(i==5){
                        if(rs.getIsLevel5()==1){
                            map.put("size"+i,2);
                        }else{
                            map.put("size"+i,1);
                        }
                    }
                }else{
                    map.put("size"+i,3);
                }
            }
        }
        return  map;
    }




    /**
     * @Description: 组装任务大派送的详细信息
     * @param map
    * @param disWisemanInfoList
     * @return java.util.List<com.pf.play.rule.core.model.DisWisemanInfo>
     * @author long
     * @date 2019/11/22 18:07
     */
    public static  List<DisWisemanInfo>      changDisWisemanInfo(Map<String,Object> map,List<DisWisemanInfo>  disWisemanInfoList){
        int   count  =0 ;
        for(DisWisemanInfo disWisemanInfoList1:disWisemanInfoList  ){
            disWisemanInfoList1.setIsDisplay(1);
            disWisemanInfoList1.setIsReceive(Integer.parseInt(map.get("size"+count).toString()));
            count++;
        }
        return  disWisemanInfoList;
    }

    /**
     * @Description: 检查是否有效可以执行的taskId
     * @param vcMemberResource
    * @param rewardReceive
    * @param taskId
     * @return boolean
     * @author long
     * @date 2019/11/26 14:35
     */
    public static  boolean   cheakCondition(VcMemberResource   vcMemberResource,VcRewardReceive  rewardReceive,Integer  taskId){
        boolean  flag = true ;
        if(vcMemberResource.getDarenLevel()>=taskId){
            if(taskId==0){
                if(rewardReceive.getIsLevel0()==2){
                    return false;
                }
            }else if(taskId==1){
                if(rewardReceive.getIsLevel1()==2){
                    return false;
                }
            }else if(taskId==2){
                if(rewardReceive.getIsLevel2()==2){
                    return false;
                }
            }else if(taskId==3){
                if(rewardReceive.getIsLevel3()==2){
                    return false;
                }
            }else if(taskId==4){
                if(rewardReceive.getIsLevel4()==2){
                    return false;
                }
            }else if(taskId==5){
                if(rewardReceive.getIsLevel5()==2){
                    return false;
                }
            }
        }
        return  flag;
    }




    /**
     * @Description: 组装用户的奖励领取
     * @param memberId
    * @param level
     * @return com.pf.play.rule.core.model.VcRewardReceive
     * @author long
     * @date 2019/11/26 14:36
     */
    public   static VcRewardReceive  changeUpdateRewardReceive(Integer memberId,Integer level){
        VcRewardReceive vcRewardReceive = new VcRewardReceive();
        vcRewardReceive.setMemberId(memberId);
        vcRewardReceive.setUpdateTime(new Date());
        if(level==0){
            vcRewardReceive.setIsLevel0(2);
        }else if(level==1){
            vcRewardReceive.setIsLevel1(2);
        }else if(level==2){
            vcRewardReceive.setIsLevel2(2);
        }else if(level==3){
            vcRewardReceive.setIsLevel3(2);
        }else if(level==4){
            vcRewardReceive.setIsLevel4(2);
        }else if(level==5){
            vcRewardReceive.setIsLevel5(2);
        }
        return vcRewardReceive;
    }


    /**
     * @Description: 转换VcMember
     * @param memberId
     * @return com.pf.play.rule.core.model.VcMember
     * @author long
     * @date 2019/11/23 12:09
     */
    public  static  VcMember  getMember(Integer  memberId){
        VcMember  vcMember  = new VcMember();
        vcMember.setMemberId(memberId);
        return vcMember;
    }

    /**
     * @Description: 该用户是否实名制用户
     * @param vcMember
     * @return boolean
     * @author long
     * @date 2019/11/23 12:38
     */
    public  static  boolean   isCertification(VcMember vcMember){
        boolean  flag = true ;
        if(vcMember==null){
            return false;
        }else{
            if(vcMember.getIsCertification()==1){
                return false;
            }
        }
        return  flag;
    }

    /**
     * @Description: TODO
     * @param memberId
     * @return com.pf.play.rule.core.model.UTaskHave
     * @author long
     * @date 2019/11/23 12:48
     */
    public  static  UTaskHave  getuTaskHave(Integer  memberId){
        UTaskHave  uTaskHave  = new UTaskHave();
        uTaskHave.setMemberId(memberId);
        return uTaskHave;
    }

    /**
     * @Description: 更新砖石汇总表信息
     * @param memberId
    * @param type  1、是添加  2、是减
    * @param masonry 砖石数
     * @return com.pf.play.rule.core.model.UMasonrySummary
     * @author long
     * @date 2019/11/25 16:07
     */
    public  static  UMasonrySummary  updateUMasonrySummary(Integer memberId,Integer type,Float masonry ){
        UMasonrySummary  uMasonrySummary = new UMasonrySummary();
        uMasonrySummary.setMemberId(memberId);
        if(type==1){
            uMasonrySummary.setInMasonry(masonry);
        }else{
            uMasonrySummary.setOutMasonry(masonry);
        }
        uMasonrySummary.setUpdateTime(new Date());
        return  uMasonrySummary;
    }

    /**
     * @Description: 注册接口参数
     * @param registerReq
     * @return boolean
     * @author long
     * @date 2019/11/26 11:08
     */
    public static  boolean   checkRegisterParameter(RegisterReq registerReq){
        boolean flag  = true ;
        if(StringUtils.isBlank(registerReq.getDeviceId())){
            flag = false;
        }else if(StringUtils.isBlank(registerReq.getVersion())){
            flag = false;
        }else  if(StringUtils.isBlank(registerReq.getSmsVerification())){
            flag = false;
        }else if(StringUtils.isBlank(registerReq.getPhone())&& PhoneUtil.isMobile(registerReq.getPhone()) &&!PhoneUtil.isInvalidMobile(registerReq.getPhone())){
            flag = false;
        }else  if(StringUtils.isBlank(registerReq.getWxOpenid())){
            flag = false;
        }else if(StringUtils.isBlank(registerReq.getWxName())){
            flag = false;
        }else  if(StringUtils.isBlank(registerReq.getMemberAdd())){
            flag = false;
        }else if(StringUtils.isBlank(registerReq.getWxRefresh())){
            flag = false;
        }else  if(StringUtils.isBlank(registerReq.getInviteCode())){
            flag = false;
        }else  if(StringUtils.isBlank(registerReq.getTimeStamp())){
            flag = false;
        }
        return  flag;
    }

    /**
     * @Description: 验证手机号码是否是有效的
     * @param req
     * @return boolean
     * @author long
     * @date 2019/11/26 13:34
     */
    public   static  boolean    checkPhoneVerification(PhoneVerificationReq req){
        boolean  flag = true;
        if(StringUtils.isBlank(req.getPhone())&& PhoneUtil.isMobile(req.getPhone()) &&!PhoneUtil.isInvalidMobile(req.getPhone())){
            flag = false;
        }else if(StringUtils.isBlank(req.getPicTimeStamp())){
            flag = false;
        }else  if(StringUtils.isBlank(req.getPicVerification())){
            flag = false;
        }
        return   flag;
    }

    /**
     * @Description: 验证token 和 wx openId 是否能行
     * @param userCommonReq
     * @return boolean
     * @author long
     * @date 2019/11/26 13:50
     */
    public  static  boolean    checkTokenAndWxOpenid(UserCommonReq userCommonReq){
        boolean    flag = true ;
        if(StringUtils.isBlank(userCommonReq.getToken())){
            flag = false;
        }else if(StringUtils.isBlank(userCommonReq.getWxOpenid())){
            flag = false;
        }
        return flag;
    }

    /**
     * @Description: 所以任务执行相关的东西，都是由他来处理
     * @param taskReq
     * @return boolean
     * @author long
     * @date 2019/11/26 14:15
     */
    public  static  boolean   checkUserTaskIsEffective(TaskReq taskReq){
        boolean    flag = true ;
        if(StringUtils.isBlank(taskReq.getToken())){
            flag = false;
        }else if(StringUtils.isBlank(taskReq.getWxOpenId())){
            flag = false;
        }else if(taskReq.getTaskId()==0){
            flag = false;
        }
        return   flag ;
    }

    /**
     * @Description: 根据用户上级id 组装条件
     * @param superiorId
     * @return com.pf.play.rule.core.model.VcMember
     * @author long
     * @date 2019/11/27 10:35
     */
    public static   VcMember   changvcMemberTOsuperiorId(Integer superiorId){
        VcMember  vcMember = new  VcMember();
        vcMember.setSuperiorId(superiorId);
        return   vcMember;
    }




}
