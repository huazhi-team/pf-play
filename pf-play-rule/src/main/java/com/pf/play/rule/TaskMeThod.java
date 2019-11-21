package com.pf.play.rule;

import com.pf.play.common.utils.BeanUtils;
import com.pf.play.common.utils.DateUtil;
import com.pf.play.rule.core.model.*;
import com.pf.play.rule.core.singleton.TaskSingleton;
import com.pf.play.rule.util.ComponentUtil;

import java.util.Date;
import java.util.List;

/**
 * @Description 装换处理表
 * @Author long
 * @Date 2019/11/20 20:41
 * @Version 1.0
 */
public class TaskMeThod {
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
        DateModel  dateModel  = TaskMeThod.getDate();
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
        DateModel dateModel=TaskMeThod.getDate();
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
        DateModel dateModel=TaskMeThod.getDate();
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



}
