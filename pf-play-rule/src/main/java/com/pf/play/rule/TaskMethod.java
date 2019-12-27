package com.pf.play.rule;

import com.pf.play.common.utils.BeanUtils;
import com.pf.play.common.utils.DateUtil;
import com.pf.play.common.utils.PhoneUtil;
import com.pf.play.model.protocol.request.task.TaskReq;
import com.pf.play.model.protocol.request.uesr.PhoneVerificationReq;
import com.pf.play.model.protocol.request.uesr.RegisterReq;
import com.pf.play.model.protocol.request.uesr.UserCommonReq;
import com.pf.play.model.protocol.response.task.*;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.CacheKey;
import com.pf.play.rule.core.common.utils.constant.Constant;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.core.model.*;
import com.pf.play.rule.core.singleton.EmpiricalVitalitySingleton;
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
                                                          Integer symbo, Double activeValue){
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
    public static UTaskHave   changeAddUTaskHave(Integer memberId,Integer taskId,Integer validityDay,
                                                    Double totalNum,Integer taskLevel,Integer taskCirleDay,Double eventDay){
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
        uTaskHave.setSurplusCount(taskCirleDay);
        uTaskHave.setEveryNum(eventDay);
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
    public   static  UMasonryListLog   changeUMasonryListLog(Integer memberId,Integer taskId,Integer type,Integer SymbolType,Double needResource){
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
    public   static VcMemberResource  changeUpdateResource(Integer memberId,Double masonry,Double charmValue){
        VcMemberResource vcMemberResource = new VcMemberResource();
        vcMemberResource.setMemberId(memberId);
        vcMemberResource.setUpdateTime(new Date());
        vcMemberResource.setDayMasonry(masonry);
        vcMemberResource.setCharmValue(charmValue);
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
    public static   VcMemberResource   changUpdateResourceTOActiveValue(Integer memberId,Double activeValue ){
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
        for(int  i=0;i<6;i++){
            if(i<=vcMemberResource.getDarenLevel()){
                if(i==0){
                    if(rs.getIsLevel0()==1){
                        map.put("size"+i,2);
                    }else{
                        map.put("size"+i,1);
                    }
                }else if(i==1){
                    if(vcMember.getIsCertification()==1){
                        map.put("size"+i,3);
                    }else if(rs.getIsLevel1()==1){
                        map.put("size"+i,2);
                    } else{
                        map.put("size"+i,1);
                    }
                }else if(i==2){
                    if(vcMember.getIsCertification()==1){
                        map.put("size"+i,3);
                    }else if(rs.getIsLevel2()==1){
                        map.put("size"+i,2);
                    } else{
                        map.put("size"+i,1);
                    }
                }else if(i==3){
                    if(vcMember.getIsCertification()==1){
                        map.put("size"+i,3);
                    }else if(rs.getIsLevel3()==1){
                        map.put("size"+i,2);
                    } else{
                        map.put("size"+i,1);
                    }
                }else if(i==4){
                    if(vcMember.getIsCertification()==1){
                        map.put("size"+i,3);
                    }else if(rs.getIsLevel4()==1){
                        map.put("size"+i,2);
                    } else{
                        map.put("size"+i,1);
                    }
                }else if(i==5){
                    if(vcMember.getIsCertification()==1){
                        map.put("size"+i,3);
                    }else if(rs.getIsLevel5()==1){
                        map.put("size"+i,2);
                    } else{
                        map.put("size"+i,1);
                    }
                }
            }else{
                map.put("size"+i,3);
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
            if(taskId==1){
                if(rewardReceive.getIsLevel0()==2){
                    return false;
                }
            }else if(taskId==2){
                if(rewardReceive.getIsLevel1()==2){
                    return false;
                }
            }else if(taskId==3){
                if(rewardReceive.getIsLevel2()==2){
                    return false;
                }
            }else if(taskId==4){
                if(rewardReceive.getIsLevel3()==2){
                    return false;
                }
            }else if(taskId==5){
                if(rewardReceive.getIsLevel4()==2){
                    return false;
                }
            }else if(taskId==6){
                if(rewardReceive.getIsLevel5()==2){
                    return false;
                }
            }
        }else{
            return false;
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
        if(level==2){
            vcRewardReceive.setIsLevel1(2);
        }else if(level==3){
            vcRewardReceive.setIsLevel2(2);
        }else if(level==4){
            vcRewardReceive.setIsLevel3(2);
        }else if(level==5){
            vcRewardReceive.setIsLevel4(2);
        }else if(level==6){
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
    public  static  UMasonrySummary  updateUMasonrySummary(Integer memberId,Integer type,Double masonry ){
        UMasonrySummary  uMasonrySummary = new UMasonrySummary();
        uMasonrySummary.setMemberId(memberId);
        if(type==Constant.TASK_SYMBOL_TYPE1){
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
        }
        else  if(StringUtils.isBlank(registerReq.getMemberAdd())){
            flag = false;
        }
//        else if(StringUtils.isBlank(registerReq.getWxRefresh())){
//            flag = false;
//        }
//        else  if(StringUtils.isBlank(registerReq.getInviteCode())){
//            flag = false;
//        }
        else  if(StringUtils.isBlank(registerReq.getTimeStamp())){
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
        }else if(StringUtils.isBlank(userCommonReq.getWxOpenId())){
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



    /**
     * @Description: 修改用户
     * @param list
     * @return com.pf.play.rule.core.model.UvitalityValueList
     * @author long
     * @date 2019/11/30 13:32
     */
    public static  UvitalityValueList   changUvitalityValueList( List<UvitalityValueList>  list){
        List<Long>  listId = new ArrayList<>();
        UvitalityValueList uvitalityValueList =new UvitalityValueList();
        for(UvitalityValueList valueList:list){
            listId.add(valueList.getId());
        }
        uvitalityValueList.setIdList(listId);
        uvitalityValueList.setIsCount(2);
        return   uvitalityValueList;
    }

    /**
     * @Description: 转换成领取任务出去字段
     * @param list
     * @return java.util.List<com.pf.play.model.protocol.response.task.ReceiveTaskResp>
     * @author long
     * @date 2019/12/1 17:11
     */
    public static  List<ReceiveTaskResp>   changReceiveTaskResp( List<DisTaskType>  list){
        List<ReceiveTaskResp>  list1 = new ArrayList<>();
        for(DisTaskType disTaskType:list){
            ReceiveTaskResp  receiveTaskResp = new ReceiveTaskResp();
            BeanUtils.copy(disTaskType,receiveTaskResp);
            if(disTaskType.getTaskId()!=1){
                list1.add(receiveTaskResp);
            }

        }
        return   list1;
    }


    /**
     * @Description: 当前拥有的转换成  UserHavaTaskResp 出去
     * @param list
     * @return com.pf.play.model.protocol.response.task.UserHavaTaskResp
     * @author long
     * @date 2019/12/1 20:46
     */
    public  static List<UserHavaTaskResp> changUserHavaTaskResp(List<UTaskHave> list){
        List<UserHavaTaskResp>  userHavaTaskRespList = new ArrayList<>();
        List<DisTaskType> disTaskTypeList = TaskSingleton.getInstance().getDisTaskTypeList();

        List<DisTaskAttribute> attributeList =TaskSingleton.getInstance().getAttributeTypeList1();
        List<DisTaskAttribute> rewardAttributeList =TaskSingleton.getInstance().getAttributeTypeList2();
        for(UTaskHave uTaskHave :list){
            UserHavaTaskResp  userHavaTaskResp  = new  UserHavaTaskResp();
            userHavaTaskResp.setTaskId(uTaskHave.getTaskId());
            userHavaTaskResp.setTaskLevel(uTaskHave.getTaskLevel());
            userHavaTaskResp.setEndTimeStr(uTaskHave.getEndTimeStr());
            userHavaTaskResp.setAlreadyNum(uTaskHave.getAlreadyNum());
            for(DisTaskType disTaskType:disTaskTypeList){
                if(uTaskHave.getTaskId()==disTaskType.getTaskId()){
                    userHavaTaskResp.setTotalNum(disTaskType.getTotalNum());
                    userHavaTaskResp.setEveryNum(disTaskType.getEveryNum());
                    userHavaTaskResp.setTaskName(disTaskType.getTaskName());
                    if(uTaskHave.getAlreadyNum()==0){
                        userHavaTaskResp.setSurplusNum(30D);
                    }else{
                        userHavaTaskResp.setSurplusNum(disTaskType.getTaskCircleDay()-(uTaskHave.getAlreadyNum()/disTaskType.getEveryNum()));
                    }
                    break;
                }
            }

            for(DisTaskAttribute disTaskAttribute:attributeList){
                if(disTaskAttribute.getTaskId()==uTaskHave.getTaskId()){
                    userHavaTaskResp.setWhere1(disTaskAttribute.getKey1());
                    userHavaTaskResp.setWhere2(disTaskAttribute.getKey2());
                    break;
                }
            }

            for(DisTaskAttribute disTaskAttribute:rewardAttributeList){
                if(disTaskAttribute.getTaskId()==uTaskHave.getTaskId()){
                    userHavaTaskResp.setActiveValue(Float.parseFloat(disTaskAttribute.getKey1()));
                    break;
                }
            }

            userHavaTaskRespList.add(userHavaTaskResp);
        }

        return  userHavaTaskRespList;
    }




    /**
     * @Description: 当前拥有的转换成  UserHistoryTaskResp 出去
     * @param list
     * @return com.pf.play.model.protocol.response.task.UserHavaTaskResp
     * @author long
     * @date 2019/12/1 20:46
     */
    public  static List<UserHistoryTaskResp> changUserHistoryTask(List<UTaskHave> list){
        List<UserHistoryTaskResp>  userHavaTaskRespList = new ArrayList<>();
        List<DisTaskType> disTaskTypeList = TaskSingleton.getInstance().getDisTaskTypeList();

        List<DisTaskAttribute> attributeList =TaskSingleton.getInstance().getAttributeTypeList1();
        List<DisTaskAttribute> rewardAttributeList =TaskSingleton.getInstance().getAttributeTypeList2();
        for(UTaskHave uTaskHave :list){
            UserHistoryTaskResp  userHistoryTaskResp  = new  UserHistoryTaskResp();
            userHistoryTaskResp.setTaskId(uTaskHave.getTaskId());
            userHistoryTaskResp.setCurrentState(uTaskHave.getCurrentState());
            userHistoryTaskResp.setTaskLevel(uTaskHave.getTaskLevel());
            userHistoryTaskResp.setEndTimeStr(uTaskHave.getEndTimeStr());
            userHistoryTaskResp.setAlreadyNum(uTaskHave.getAlreadyNum());
            for(DisTaskType disTaskType:disTaskTypeList){
                if(uTaskHave.getTaskId()==disTaskType.getTaskId()){
                    userHistoryTaskResp.setTotalNum(disTaskType.getTotalNum());
                    userHistoryTaskResp.setTaskName(disTaskType.getTaskName());
                    if(uTaskHave.getAlreadyNum()==0){
                        userHistoryTaskResp.setSurplusNum(30D);
                    }else{
                        userHistoryTaskResp.setSurplusNum(disTaskType.getTaskCircleDay()-(uTaskHave.getAlreadyNum()/disTaskType.getEveryNum()));
                    }
                    break;
                }
            }

            for(DisTaskAttribute disTaskAttribute:attributeList){
                if(disTaskAttribute.getTaskId()==uTaskHave.getTaskId()){
                    userHistoryTaskResp.setWhere1(disTaskAttribute.getKey1());
                    userHistoryTaskResp.setWhere2(disTaskAttribute.getKey2());
                    break;
                }
            }
            for(DisTaskAttribute disTaskAttribute:rewardAttributeList){
                if(disTaskAttribute.getTaskId()==uTaskHave.getTaskId()){
                    userHistoryTaskResp.setActiveValue(Double.valueOf(disTaskAttribute.getKey1()));
                    break;
                }
            }
            userHavaTaskRespList.add(userHistoryTaskResp);
        }
        return  userHavaTaskRespList;
    }

    /**
     * @Description: 任务大派送转换成  GiveTaskResp 出去
     * @param list
     * @return java.util.List<com.pf.play.model.protocol.response.task.GiveTaskResp>
     * @author long
     * @date 2019/12/2 19:00
     */
    public  static List<GiveTaskResp> changGiveTask(List<DisWisemanInfo> list){
        List<GiveTaskResp>   list1 = new ArrayList<>();
        for (DisWisemanInfo disWisemanInfo:list){
            GiveTaskResp  giveTaskResp  = new GiveTaskResp();
            giveTaskResp.setIsDisplay(disWisemanInfo.getIsDisplay());
            giveTaskResp.setTaskId(disWisemanInfo.getWisemanId());
            giveTaskResp.setTaskLevel(disWisemanInfo.getRelativeLevel());
            giveTaskResp.setTaskName(disWisemanInfo.getWisemanName());
            giveTaskResp.setRemarks(disWisemanInfo.getRemarks());
            giveTaskResp.setIsReceive(disWisemanInfo.getIsReceive());
            list1.add(giveTaskResp);
        }
        return  list1;
    }





    /**
     * @Description: 修改用户
     * @param memberId
     * @return com.pf.play.rule.core.model.UvitalityValueList
     * @author long
     * @date 2019/11/30 13:32
     */
    public static  VcMemberResource   testChangUvitalityValueList(Integer  memberId){
        VcMemberResource  vcMemberResource  =new VcMemberResource();
        vcMemberResource.setMemberId(memberId);
        vcMemberResource.setFrozenMasonry(11.2D);
        return   vcMemberResource;
    }



    /**
     * @Description: 用户表转换成修改上级id
     * @param vcMember
     * @return java.util.List<java.lang.Integer>
     * @author long
     * @date 2019/12/4 19:23
     */
    public  static   List<Integer>   getSuperiorIdList(VcMember vcMember){
        List<Integer>  list  = new ArrayList<>();
        List<Integer>  uplist  = new ArrayList<>();
        try{
            if(vcMember != null){
                if(!StringUtils.isBlank(vcMember.getExtensionMemberId())){
                    String []  superiorId  = vcMember.getExtensionMemberId().split(",");
                    for(int  i= 0 ;i<superiorId.length-1; i++){
                        list.add(Integer.parseInt(superiorId[i]));
                    }
                }
            }

            for(int  i =1 ;i<=list.size();i++){
                uplist.add(list.get(list.size()-i));
            }







        }catch (Exception e){
            e.printStackTrace();
        }
        return  uplist ;
    }

//    /**
//     * @Description: 准备自己经验值更新
//     * @param uVitalityValueList
//     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Float>>
//     * @author long
//     * @date 2019/12/4 20:12
//     */
//    public static  VcMemberResource   getMyActiveValue(UvitalityValueList uVitalityValueList){
//        VcMemberResource  vcMemberResource  =  new VcMemberResource();
//        vcMemberResource.setMemberId(uVitalityValueList.getMemberId());
//        vcMemberResource.setActiveValue(uVitalityValueList.getActiveValue());
//        return vcMemberResource;
//    }


    public static  VcMemberResource   getVcMemberResource(List<Integer> idList){
        VcMemberResource  vcMemberResource = new VcMemberResource();
        vcMemberResource.setIdList(idList);
        return vcMemberResource;
    }

    /**
     * @Description: 组合成修改的团队活力值或者自己的活力值方法
     * @param memberId
    * @param activeValue
     * @return com.pf.play.rule.core.model.VcMemberResource
     * @author long
     * @date 2019/12/5 22:27
     */
    public static  VcMemberResource   getUqdateMyActiveValue(Integer memberId,Double activeValue,Integer  type ){
        VcMemberResource  vcMemberResource= new VcMemberResource();
        vcMemberResource.setMemberId(memberId);
        if(type== Constant.TASK_SYMBOL_TYPE1){
            vcMemberResource.setActiveValueAdd(activeValue);
        }else if(type== Constant.TASK_SYMBOL_TYPE2){
            vcMemberResource.setActiveValueCut(activeValue);
        }
        return   vcMemberResource ;
    }

    /**
     * @Description: 团队需要修改的值
     * @param memberId
    * @param activeValue
     * @return com.pf.play.rule.core.model.VcMemberResource
     * @author long
     * @date 2019/12/6 9:49
     */
    public static  VcMemberResource   getUqdateTeamActive(Integer memberId,Double activeValue){
        VcMemberResource  vcMemberResource  = new  VcMemberResource();
        vcMemberResource.setMemberId(memberId);
        vcMemberResource.setTeamActive(activeValue);
        return   vcMemberResource;
    }


    /**
     * @Description: 根据用户信息查看该用户的属于的等级情况
     * @param queryVcMember
     * @return java.lang.Integer
     * @author long
     * @date 2019/12/11 17:18
     */
    public static   Integer   getLevel(VcMemberResource  queryVcMember){
        Integer   level  =  0  ;
        List<DisVitalityValue>   list  =  EmpiricalVitalitySingleton.getInstance().getDisVitalityValue();
        for(DisVitalityValue  disVitalityValue:list){

            if(queryVcMember.getDarenLevel()==0){
                continue;
            }

            if(disVitalityValue.getPushNumber()>queryVcMember.getPushPeople()||
                    disVitalityValue.getTeamVitalitNum()>queryVcMember.getTeamActive()||
                        disVitalityValue.getAllianceVitalitNum()>queryVcMember.getAllianceActive()){
                break;
            }
            level++;
        }
        return level;
    }

    /**
     * @Description: 组装成VcMember Level2 的查询信息
     * @param memeberId
     * @return com.pf.play.rule.core.model.VcMember
     * @author long
     * @date 2019/12/11 11:57
     */
    public  static   VcMember    getVcMemberLevel2(Integer  memeberId){
        VcMember  vcMember =  new VcMember();
        vcMember.setSuperiorId(memeberId);
        vcMember.setDarenLevel(1);
        return   vcMember;
    }


    /**
     * @Description: 组装成VcMember Level2 的查询信息
     * @param list
     * @return com.pf.play.rule.core.model.VcMember
     * @author long
     * @date 2019/12/11 11:57
     */
    public  static   VcMember    getVcMemberLevel2(List<Integer> list,Integer darenLevel){
        VcMember  vcMember =  new VcMember();
//        vcMember.setSuperiorId(memeberId);
        vcMember.setIdList(list);
        vcMember.setDarenLevel(darenLevel);
        return   vcMember;
    }



    /**
     * @Description: 修改资源达人等级信息
     * @param memeberId
    * @param darenLevel
     * @return com.pf.play.rule.core.model.VcMemberResource
     * @author long
     * @date 2019/12/11 17:40
     */
    public  static   VcMemberResource    updateResourceLevel(Integer memeberId,Integer darenLevel){
        VcMemberResource  vcMemberResource =  new VcMemberResource();
        vcMemberResource.setMemberId(memeberId);
        vcMemberResource.setDarenLevel(darenLevel);
        return   vcMemberResource;
    }



    /**
     * @Description: 等级是否有效
     * @param vcRewardReceive
    * @param darenLevel
     * @return boolean
     * @author long
     * @date 2019/12/11 17:59
     */
    public static    boolean     checkLevelIsReceive(VcRewardReceive vcRewardReceive,Integer  darenLevel){
        boolean flag  =  false  ;
        if(darenLevel==1){
            if( vcRewardReceive.getIsLevel1().equals("1")){
                return true;
            }
        }else if(darenLevel==2){
            if( vcRewardReceive.getIsLevel2().equals("1")){
                return true;
            }
        }else if(darenLevel==3){
            if( vcRewardReceive.getIsLevel3().equals("1")){
                return true;
            }
        }else if(darenLevel==4){
            if( vcRewardReceive.getIsLevel4().equals("1")){
                return true;
            }
        }else if(darenLevel==5){
            if( vcRewardReceive.getIsLevel5().equals("1")){
                return true;
            }
        }
        return  flag;
    }


    /**
     * @Description: 修改处理活跃值状态
     * @param uvitalityValueList
     * @return com.pf.play.rule.core.model.UvitalityValueList
     * @author long
     * @date 2019/12/11 20:37
     */
    public static  UvitalityValueList   updateUvitalityValueList(UvitalityValueList  uvitalityValueList,Integer type){
        UvitalityValueList  uvitalityValueList1 =  new UvitalityValueList();
        uvitalityValueList1.setId(uvitalityValueList.getId());
        uvitalityValueList1.setIsCount(type);
        return  uvitalityValueList1;
    }

    /**
     * @Description: 修改团队活跃值的
     * @param memberId
    * @param list
     * @return com.pf.play.rule.core.model.VcMemberResource
     * @author long
     * @date 2019/12/12 14:23
     */
    public static VcMemberResource   updateHeroActive(Integer memberId,List<VcMemberResource> list){
        VcMemberResource  vcMemberResource =  new VcMemberResource();
        double    teamActive   =  0D;
        for(VcMemberResource  vcMemberResource1 : list){
            teamActive = teamActive + vcMemberResource1.getActiveValue();
        }
        vcMemberResource.setMemberId(memberId);
        vcMemberResource.setTeamActive(teamActive);
        return  vcMemberResource;
    }

    /**
     * @Description: 用户每天任务
     * @param memberId
    * @param list
     * @return com.pf.play.rule.core.model.VcMemberResource
     * @author long
     * @date 2019/12/12 14:23
     */
    public static UdailyTaskStat   changUdailyTaskStat(Integer memberId){
        UdailyTaskStat  vcMemberResource =  new UdailyTaskStat();
        vcMemberResource.setMemberId(memberId);
        vcMemberResource.setCurday(DateUtil.getDayNumber(new Date()));
        return  vcMemberResource;
    }



    /**
     * @Description: 查询拥有的task有效信息
     * @param memberId
    * @param day
     * @return com.pf.play.rule.core.model.UTaskHave
     * @author long
     * @date 2019/12/12 17:34
     */
    public static UTaskHave   changUTaskHave(Integer memberId){
        UTaskHave  uTaskHave =  new UTaskHave();
        uTaskHave.setMemberId(memberId);
        uTaskHave.setCurday(DateUtil.getDayNumber(new Date()));
        uTaskHave.setCurrentState(1);
        return  uTaskHave;
    }




    /**
     * @Description: 初始化信息
     * @param memberId
    * @param list
     * @return com.pf.play.rule.core.model.VcMemberResource
     * @author long
     * @date 2019/12/12 14:23
     */
    public static UdailyTaskStat   initUdailyTaskStat(Integer memberId){
        UdailyTaskStat  udailyTaskStat =  new UdailyTaskStat();
        udailyTaskStat.setMemberId(memberId);
        udailyTaskStat.setAcceptNumber(0);
        udailyTaskStat.setLookCommodityNum(0);
        return  udailyTaskStat;
    }



    /**
     * @Description: 筛选出来最大的要求
     * @param list
    * @param day
     * @return com.pf.play.rule.core.model.UTaskHave
     * @author long
     * @date 2019/12/12 17:34
     */
    public static UTaskHave   changUTaskHaveMax(List<UTaskHave>  list){
        UTaskHave  uTaskHave =  new UTaskHave();
        for(UTaskHave uTaskHave1 :list){
            uTaskHave=uTaskHave1;
            break;
        }
        return  uTaskHave;
    }


    /**
     * @Description: TODO
     * @param uTaskHave  用户最大值信息
    * @param uMasonryListLog  用户访问次数信息
    * @param udailyTaskStat   用户剩余信息
     * @return com.pf.play.model.protocol.response.task.TodayTaskResp
     * @author long
     * @date 2019/12/13 17:18
     */
    public  static TodayTaskResp changTodayTaskResp(UTaskHave  uTaskHave,UMasonryListLog uMasonryListLog,
                                                    UdailyTaskStat udailyTaskStat,UTaskHave  uTaskHave1){


        TodayTaskResp  todayTaskResp  = new TodayTaskResp();

        todayTaskResp.setAcceptNumber(udailyTaskStat.getAcceptNumber());
        todayTaskResp.setLookCommodityNum(udailyTaskStat.getLookCommodityNum());
        todayTaskResp.setIsComplete(1);
        todayTaskResp.setLookCommodityNumCount(0);
        todayTaskResp.setAcceptNumberCount(0);
        List<DisTaskAttribute>  list = TaskSingleton.getInstance().getAttributeTypeList1();
        for(DisTaskAttribute  disTaskAttribute  : list){
            if(uTaskHave.getTaskId()==disTaskAttribute.getTaskId()){
                todayTaskResp.setLookCommodityNumCount(Integer.parseInt(disTaskAttribute.getKey1()));
                todayTaskResp.setAcceptNumberCount(Integer.parseInt(disTaskAttribute.getKey2()));
                if(udailyTaskStat.getAcceptNumber()>=Integer.parseInt(disTaskAttribute.getKey1())&&udailyTaskStat.getLookCommodityNum()>=Integer.parseInt(disTaskAttribute.getKey2())){
                    todayTaskResp.setIsComplete(2);
                }
//                break;
            }
        }

        //是否已领取
        boolean flag = ComponentUtil.taskService.isReceiveAwards(uTaskHave.getMemberId());
        if(!flag){
            todayTaskResp.setIsComplete(3);
        }

        todayTaskResp.setAlreadyNum(uTaskHave1.getAlreadyNumCount());
        todayTaskResp.setSurplusNum(uTaskHave1.getSurplusNumCount());
        if(uMasonryListLog==null){
            todayTaskResp.setUpReward(0D);
        }else{
            todayTaskResp.setUpReward(uMasonryListLog.getMasonryNum());
        }
        return   todayTaskResp;
    }


    /**
     * @Description: 组合查询信息
     * @param memberId
     * @return com.pf.play.rule.core.model.UMasonryListLog
     * @author long
     * @date 2019/12/13 16:24
     */
    public   static  UTaskHave  toUTaskHave(Integer memberId){
        UTaskHave   uTaskHave  =  new UTaskHave();
        uTaskHave.setMemberId(memberId);
        return uTaskHave;
    }

    /**
     * @Description: 组合查询信息
     * @param memberId
     * @return com.pf.play.rule.core.model.UMasonryListLog
     * @author long
     * @date 2019/12/13 16:24
     */
    public   static  UMasonryListLog  toUMasonryListLog(Integer memberId){
        UMasonryListLog   uMasonryListLog  =  new UMasonryListLog();
        uMasonryListLog.setMemberId(memberId);
        return uMasonryListLog;
    }


    /**
     * @Description: 用户每天任务
     * @param memberId
    * @param list
     * @return com.pf.play.rule.core.model.VcMemberResource
     * @author long
     * @date 2019/12/12 14:23
     */
    public static UdailyTaskStat   changUdailyTaskStatDay(Integer memberId){
        UdailyTaskStat  udailyTaskStat =  new UdailyTaskStat();
        udailyTaskStat.setMemberId(memberId);
        udailyTaskStat.setCurday(DateUtil.getDayNumber(new Date()));
        return  udailyTaskStat;
    }

    /**
     * @Description: 计算有效的任务信息
     * @param list
     * @return java.lang.Double
     * @author long
     * @date 2019/12/13 20:00
     */
    public static Double statTaskMasonry(List<UTaskHave>  list ){
        Double  masonry =0D;
        List<DisTaskType> taskList = TaskSingleton.getInstance().getDisTaskTypeList();
        for(UTaskHave  uTaskHave: list){
            for(DisTaskType disTaskType:taskList){
                if(disTaskType.getTaskId()==uTaskHave.getTaskId()){
                    masonry=masonry+disTaskType.getEveryNum();
                }
            }
        }
        return  masonry;
    }

    /**
     * @Description: TODO
     * @param memberId   奖励人
    * @param subMemberId  直推人id
    * @param type   类型 1 、实名制   2 、直推任务
    * @param activeValue  活跃值
    * @param totalNum    总次数
    * @param endTime    有效时间
     * @return com.pf.play.rule.core.model.USubReward
     * @author long
     * @date 2019/12/16 20:20
     */
    public static USubReward   changUSubReward(Integer memberId,Integer subMemberId,Integer type,Double activeValue,Integer totalNum){
        DateModel   dateModel = TaskMethod.getDate();
        USubReward  uSubReward =  new USubReward();
        BeanUtils.copy(dateModel,uSubReward);
        uSubReward.setMemberId(memberId);
        uSubReward.setSubId(subMemberId);
        uSubReward.setTotalNum(totalNum);
        uSubReward.setRewardType(type);
        Date endTime = DateUtil.getDateBetween(dateModel.getCreateTime(),totalNum);
        uSubReward.setEndTime(endTime);
        uSubReward.setActiveValue(activeValue);
        return  uSubReward;
    }

    /**
     * @Description: 实名制修改VcMember字段
     * @param memberId
     * @return com.pf.play.rule.core.model.VcMember
     * @author long
     * @date 2019/12/16 21:42
     */
    public static VcMember   changRealnameMember(Integer memberId){
        VcMember   vcMember =  new VcMember();
        vcMember.setMemberId(memberId);
        vcMember.setIsCertification(2);
        return  vcMember;
    }

    /**
     * @Description: 实名制修改VcMemberResource字段
     * @param memberId
     * @return com.pf.play.rule.core.model.VcMemberResource
     * @author long
     * @date 2019/12/16 21:44
     */
    public static VcMemberResource   changRealnameResource(Integer memberId){
        VcMemberResource   vcMemberResource =  new VcMemberResource();
        vcMemberResource.setMemberId(memberId);
        vcMemberResource.setPushPeople(1);
        vcMemberResource.setUpdateTime(new Date());
        return  vcMemberResource;
    }

    /**
     * @Description: 当前用户魅力值是否满足要求
     * @param vcMemberResource
    * @param taskId
     * @return boolean
     * @author long
     * @date 2019/12/17 11:33
     */
    public static boolean  isCharmValue(VcMemberResource vcMemberResource,Integer  taskId){
        double   charmValue =   0D ;
        List<DisTaskType>   list  =   TaskSingleton.getInstance().getDisTaskTypeList();
        for(DisTaskType disTaskType :list ){
            if(disTaskType.getTaskId()==taskId){
                charmValue=disTaskType.getCharmValue();
                break;
            }
        }
        if(vcMemberResource.getCharmValue()>=charmValue){
            return  true;
        }

        return   false ;
    }


    /**
     * @Description: 需要消耗砖石数
     * @param taskId
     * @return java.lang.Double
     * @author long
     * @date 2019/12/21 20:36
     */
    public static  Double   getNeedCharmValue(Integer  taskId){
        Double     charmValue  = 0D;
        List<DisTaskType>   list  =   TaskSingleton.getInstance().getDisTaskTypeList();
        for(DisTaskType  disTaskType:list){
            if(disTaskType.getTaskId()==taskId){
                charmValue = disTaskType.getCharmValue();
                break;
            }
        }
        return   charmValue;
    }



    /**
     * @Description: TODO
     * @param list
     * @return java.lang.Double
     * @author long
     * @date 2019/12/17 13:59
     */
    public static Double  countUSubReward(List<USubReward> list){
        Double  activeValue  =0D;
        for(USubReward  uSubReward:list){
            activeValue = activeValue + uSubReward.getActiveValue();
        }
        return   activeValue ;
    }

    /**
     * @Description: TODO
     * @param memberId
     * @return com.pf.play.rule.core.model.USubReward
     * @author long
     * @date 2019/12/17 14:28
     */
    public static USubReward    toUsubReward(Integer memberId){
        USubReward  uSubReward  = new USubReward();
        uSubReward.setMemberId(memberId);
        uSubReward.setEndTime(new Date());
        return uSubReward;
    }

    /**
     * @Description: 去除联盟值的
     * @param listAll
    * @param listHero
     * @return java.util.List<java.lang.Integer>
     * @author long
     * @date 2019/12/18 18:49
     */
    public static List<Integer>    removeHeroes(List<VcMember> listAll,List<VcMember>    listHero){
        List<Integer>  list  = new ArrayList<>();
        for(VcMember  all:listAll){
            boolean  flag = true;
            for(VcMember hero :listHero){
                if (all.getMemberId()==hero.getMemberId()){
                    flag = false  ;
                    break;
                }
            }
            if(flag){
                list.add(all.getMemberId());
            }
        }
        return list;
    }

    /**
     * @Description: 删除用户redis 信息
     * @param memberId
    * @param level
     * @return void
     * @author long
     * @date 2019/12/18 20:09
     */
    public static  void   deleteRedisLevel(Integer memberId,Integer level){
        if(level==0){
            ComponentUtil.redisService.deHmSet(CacheKey.LEVEL0,memberId+"");
        }else if(level==1){
            ComponentUtil.redisService.deHmSet(CacheKey.LEVEL1,memberId+"");
        }else if(level==2){
            ComponentUtil.redisService.deHmSet(CacheKey.LEVEL2,memberId+"");
        }else if(level==3){
            ComponentUtil.redisService.deHmSet(CacheKey.LEVEL3,memberId+"");
        }else if(level==4){
            ComponentUtil.redisService.deHmSet(CacheKey.LEVEL4,memberId+"");
        }else if(level==5){
            ComponentUtil.redisService.deHmSet(CacheKey.LEVEL5,memberId+"");
        }
    }



    public static VcMember toIdList(List<Integer>   idList){
        VcMember  vcMember = new VcMember();
        vcMember.setIdList(idList);
        return  vcMember;
    }

    /**
     * @Description: 查询当天 用户是否有领取记录的信息
     * @param memberId
     * @return com.pf.play.rule.core.model.UMasonryListLog
     * @author long
     * @date 2019/12/20 21:02
     */
    public static UMasonryListLog  queryUMasonryListLog(Integer  memberId){
        UMasonryListLog  uMasonryListLog = new UMasonryListLog();
        uMasonryListLog.setMemberId(memberId);
        uMasonryListLog.setCurday(DateUtil.getDayNumber(new Date()));
        uMasonryListLog.setType(1);
        return uMasonryListLog;
    }


    /**
     * @Description: 修改信息
     * @param list
     * @return com.pf.play.rule.core.model.UTaskHave
     * @author long
     * @date 2019/12/21 15:02
     */
    public static UTaskHave  updateUTaskHaveEventDay(List<UTaskHave> list){
        UTaskHave  uTaskHave = new UTaskHave();
        List<Long>  idList =  new ArrayList<>();
        for (UTaskHave  uTaskHave1:list){
            idList.add(uTaskHave1.getId());
        }
        uTaskHave.setIdList(idList);
        return uTaskHave;
    }


    /**
     * @Description: 修改状态信息
     * @param list
     * @return com.pf.play.rule.core.model.UTaskHave
     * @author long
     * @date 2019/12/21 15:02
     */
    public static List<UTaskHave>  updateUTaskHaveStat(List<UTaskHave> list){
        List<UTaskHave>  list1 = new ArrayList<>();
        for(UTaskHave uTaskHave:list){
            UTaskHave  uTaskHave1 = new UTaskHave();
            uTaskHave1.setId(uTaskHave.getId());
            if(uTaskHave.getSurplusCount()==1){
                uTaskHave1.setCurrentState(2);
                uTaskHave1.setUpdateTime(new Date());
                uTaskHave1.setIsValid(2);
                list1.add(uTaskHave1);
                continue;
            }else if(uTaskHave.getEndTime().compareTo(new Date())==-1){
                uTaskHave1.setCurrentState(3);
                uTaskHave1.setUpdateTime(new Date());
                uTaskHave1.setIsValid(2);
                list1.add(uTaskHave1);
                continue;
            }
        }
        return list1;
    }

    /**
     * @Description: 转换成查询list
     * @param list
     * @return java.util.List<java.lang.Integer>
     * @author long
     * @date 2019/12/21 21:53
     */
    public static List<Integer>  toQueryMemberList(List<VcMember> list){
        List<Integer>   queryList = new ArrayList<>();
        for(VcMember  vcMember:list){
            queryList.add(vcMember.getMemberId());
        }
        return  queryList;
    }




    /**
     * @Description: 根据等级memeberId 和 level  是否有信息
     * @param list
    * @param level
     * @return com.pf.play.rule.core.model.VcMemberResource
     * @author long
     * @date 2019/12/22 15:42
     */
    public static VcMemberResource  quertLevel(List<Integer> list,Integer  level){
        VcMemberResource   resourceArrayList = new VcMemberResource();
        resourceArrayList.setIdList(list);
        resourceArrayList.setDarenLevel(level);
        return  resourceArrayList;
    }

    /**
     * @Description: 组装查询的条件
     * @param memberId
    * @param masonry
     * @return com.pf.play.rule.core.model.VcMemberResource
     * @author long
     * @date 2019/12/22 17:04
     */
    public static VcMemberResource  updateTaskExpire(Integer memberId,Double  masonry){
        VcMemberResource   resource = new VcMemberResource();
        resource.setMemberId(memberId);
        resource.setUpdateTime(new Date());
        resource.setDayMasonry(masonry);
        return  resource;
    }

    /**
     * @Description: 根据达人等级，返回对应的任务类型值
     * @param darenLevel - 达人等级
     * @return int
     * @author yoko
     * @date 2019/12/23 19:12
    */
    public static int gettaskType(int darenLevel){
        int taskType = 0;
        if (darenLevel == 1){
            taskType = Constant.TASK_TYPE14;
        }else if (darenLevel == 2){
            taskType = Constant.TASK_TYPE15;
        }else if (darenLevel == 3){
            taskType = Constant.TASK_TYPE16;
        }else if (darenLevel == 4){
            taskType = Constant.TASK_TYPE17;
        }else if (darenLevel == 5){
            taskType = Constant.TASK_TYPE18;
        }
        return taskType;
    }


    /**
     * @Description: 添加修改经验值的信息
     * @param vcMemberResource1
    * @param empiricalValue
     * @return com.pf.play.rule.core.model.VcMemberResource
     * @author long
     * @date 2019/12/23 17:15
     */
    public static   VcMemberResource   changSuperiorId(VcMemberResource  vcMemberResource1,Double  empiricalValue){
        VcMemberResource  vcMemberResource = new  VcMemberResource();
        List<DisEmpiricalValueLevel>  list  = EmpiricalVitalitySingleton.getInstance().getDisEmpiricalValueLevel();
        Integer  count = 0;
        Integer  level = 0;
        for(DisEmpiricalValueLevel  disEmpiricalValueLevel : list){
            if(count==0){
                count++;
                continue;
            }else{
                if((vcMemberResource1.getEmpiricalValue()+empiricalValue)<disEmpiricalValueLevel.getUpgradeNum()){
                    break;
                }else{
                    level++;
                }
            }
        }

        vcMemberResource.setEmpiricalLevel(level);
        vcMemberResource.setMemberId(vcMemberResource1.getMemberId());
        vcMemberResource.setEmpiricalValue(empiricalValue);
        return   vcMemberResource;
    }

    /**
     * @Description: 结果转换出去
     * @param result
     * @return com.pf.play.model.protocol.response.task.ExeReceiveTaskResp
     * @author long
     * @date 2019/12/26 10:48
     */
    public static ExeReceiveTaskResp  toExeReceiveTaskResp(boolean  result){
        ExeReceiveTaskResp  receiveTaskResp =  new ExeReceiveTaskResp();
        receiveTaskResp.setResult(result);
        return  receiveTaskResp;
    }

    /**
     * @Description: 更新用户信息
     * @param
     * @return com.pf.play.rule.core.model.UTaskHave
     * @author long
     * @date 2019/12/27 9:38
     */
    public static UTaskHave  updateUTaskHave(){
        UTaskHave  uTaskHave =  new UTaskHave();
        uTaskHave.setEndTime(new Date());
        return  uTaskHave;
    }

    /**
     * @Description: TODO
     * @param list
    * @param currentState
     * @return com.pf.play.rule.core.model.UTaskHave
     * @author long
     * @date 2019/12/27 13:42
     */
    public static UTaskHave  updateUTaskHave(List<Long>  list,Integer currentState){
        UTaskHave  uTaskHave =  new UTaskHave();
        uTaskHave.setIdList(list);
        uTaskHave.setCurrentState(currentState);
        return  uTaskHave;
    }
}
