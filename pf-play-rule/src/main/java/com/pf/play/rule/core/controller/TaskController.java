package com.pf.play.rule.core.controller;

import com.alibaba.fastjson.JSON;
import com.pf.play.common.utils.JsonResult;
import com.pf.play.common.utils.StringUtil;
import com.pf.play.model.protocol.request.task.TaskReq;
import com.pf.play.model.protocol.request.uesr.UserCommonReq;
import com.pf.play.model.protocol.response.task.*;
import com.pf.play.rule.PublicMethod;
import com.pf.play.rule.TaskMethod;
import com.pf.play.rule.core.common.exception.ExceptionMethod;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.Constant;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.core.common.utils.constant.ServerConstant;
import com.pf.play.rule.core.model.DisTaskType;
import com.pf.play.rule.core.model.DisWisemanInfo;
import com.pf.play.rule.core.model.UTaskHave;
import com.pf.play.rule.core.model.region.RegionModel;
import com.pf.play.rule.core.model.stream.StreamConsumerModel;
import com.pf.play.rule.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/7 13:31
 * @Version 1.0
 */
@RestController
@RequestMapping("/play/task")
public class TaskController {

    private static Logger log = LoggerFactory.getLogger(TaskController.class);



    /**
     * @Description: 领取任务列表详情
     * @param request
    * @param response
    * @param commonReq
     * @return com.pf.play.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2019/11/23 11:40
     */
    @PostMapping("/queryMemberReceiveTaskList")
    public JsonResult<Object> queryReceiveTaskList(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq)throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);

        try{
            log.info("----------:receiveTask!");
            boolean  flag = TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenId());
            }

            if (memberId==0){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.IS_USER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.IS_USER_ERROR.geteDesc());
            }
            List<DisTaskType> list = ComponentUtil.taskService.queryReceiveTask(memberId);
            List<ReceiveTaskResp>  receiveTaskRespList = TaskMethod.changReceiveTaskResp(list);

            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.TASK_RECEIVE_LIST.getType(),
                    ServerConstant.InterfaceEnum.TASK_RECEIVE_LIST.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(receiveTaskRespList), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(receiveTaskRespList);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.TASK_RECEIVE_LIST.getType(),
                    ServerConstant.InterfaceEnum.TASK_RECEIVE_LIST.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(null), map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }

    /**
     * @Description: 当前拥有的任务
     * @param request
    * @param response
    * @param commonReq
     * @return com.pf.play.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2019/11/23 11:35
     */
    @PostMapping("/userHavaTask")
    public JsonResult<Object> myTask(HttpServletRequest request, HttpServletResponse response,UserCommonReq userCommonReq)throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        try{
            log.info("----------:myTask!");
            boolean  flag =TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenId());
            }

            if (memberId==0){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.IS_USER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.IS_USER_ERROR.geteDesc());
            }

            List<UTaskHave> list = ComponentUtil.taskService.getMyTask(memberId);

            List<UserHavaTaskResp>  rslist=TaskMethod.changUserHavaTaskResp(list);

            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.TASK_HAVATASK.getType(),
                    ServerConstant.InterfaceEnum.TASK_HAVATASK.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(rslist), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(rslist);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.TASK_HAVATASK.getType(),
                    ServerConstant.InterfaceEnum.TASK_HAVATASK.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(null), map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }

    /**
     * @Description: 我的历史任务
     * @param request
    * @param response
    * @param commonReq
     * @return com.pf.play.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2019/11/23 11:34
     */
    @PostMapping("/userTaskHistory")
    public JsonResult<Object> userTaskHistory(HttpServletRequest request, HttpServletResponse response,UserCommonReq  userCommonReq)throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);

        try{
            log.info("----------:userTaskHistory!");
            memberId =  0 ;
            boolean  flag =TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenId());
            }

//            if (memberId==0){
//                throw  new ServiceException(ErrorCode.ENUM_ERROR.IS_USER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.IS_USER_ERROR.geteDesc());
//            }
            List<UTaskHave> list = ComponentUtil.taskService.queryInvalidHaveTask(memberId);
//            List<DisTaskType> list = ComponentUtil.taskService.queryInvalidHaveTask(memberId);
            List<UserHistoryTaskResp>    userHistoryTaskList =TaskMethod.changUserHistoryTask(list);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.TASK_TASKHISTORY.getType(),
                    ServerConstant.InterfaceEnum.TASK_TASKHISTORY.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(userHistoryTaskList), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(userHistoryTaskList);
        }catch (Exception e){
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.TASK_TASKHISTORY.getType(),
                    ServerConstant.InterfaceEnum.TASK_TASKHISTORY.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(null), map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }

    /**
     * @Description: 任务管理 领取任务功能
     * @param request
    * @param response
    * @param commonReq
     * @return com.pf.play.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2019/11/23 11:33
     */
    @PostMapping("/exeReceiveTask")
    public JsonResult<Object> exeReceiveTask(HttpServletRequest request, HttpServletResponse response,TaskReq  taskReq)throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        try{
            log.info("----------:exeReceiveTask!");
            //验证参数是否通过
            boolean   cheakFlag  = TaskMethod.checkUserTaskIsEffective(taskReq);
            if (!cheakFlag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }

            //获取到那个用户id
            memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(taskReq.getToken(), taskReq.getWxOpenId());
            if (memberId==0){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.IS_USER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.IS_USER_ERROR.geteDesc());
            }

            //查看魅力值是否ok
            cheakFlag  = ComponentUtil.userInfoSevrice.isCharmValueOk(memberId,taskReq.getTaskId());
            if(!cheakFlag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR12.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR12.geteDesc());
            }
//            List<DisTaskType> list = ComponentUtil.taskService.queryInvalidHaveTask(memberId);
            boolean  checkFlag  =   ComponentUtil.taskService.checkUserCondition(memberId,taskReq.getTaskId());
            if(!checkFlag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR1.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR1.geteDesc());
            }

            boolean  insertFlag  = ComponentUtil.taskService.addUserTask(memberId,taskReq.getTaskId());
            if(!insertFlag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR1.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR1.geteDesc());
            }

            ExeReceiveTaskResp  receiveTaskResp = TaskMethod.toExeReceiveTaskResp(insertFlag);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, taskReq, ServerConstant.InterfaceEnum.TASK_EXERECEIVE.getType(),
                    ServerConstant.InterfaceEnum.TASK_EXERECEIVE.getDesc(), null, JSON.toJSONString(taskReq), JSON.toJSONString(receiveTaskResp), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);

            return JsonResult.successResult(receiveTaskResp);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e,1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, taskReq, ServerConstant.InterfaceEnum.TASK_EXERECEIVE.getType(),
                    ServerConstant.InterfaceEnum.TASK_EXERECEIVE.getDesc(), null, JSON.toJSONString(taskReq), JSON.toJSONString(null), map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);

            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }


    /**
     * @Description: 获取奖励任务大派送列表
     * @param request
    * @param response
    * @param commonReq
     * @return com.pf.play.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2019/11/23 11:27
     */
    @PostMapping("/giveTaskList")
    public JsonResult<Object> queryGiveTaskList(HttpServletRequest request, HttpServletResponse response,UserCommonReq  userCommonReq)throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        try{
            List<DisWisemanInfo>  list  = new ArrayList<>();
            log.info("----------:giveTaskList!");
            memberId =  0 ;
            boolean  flag =TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenId());
            }

            if(memberId==0){
                  list = ComponentUtil.taskService.getNoLoginDisWisemanInfo();
            }else{
                  list = ComponentUtil.taskService.queryUserDisWisemanInfo(memberId);
            }

            List<GiveTaskResp>  giveTaskRespsList=TaskMethod.changGiveTask(list);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.TASK_GIVELIST.getType(),
                    ServerConstant.InterfaceEnum.TASK_GIVELIST.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(giveTaskRespsList), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(giveTaskRespsList);
        }catch (Exception e){
            Map<String,String> map= ExceptionMethod.getException(e,1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.TASK_GIVELIST.getType(),
                    ServerConstant.InterfaceEnum.TASK_GIVELIST.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(null), map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);

            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }

    /**
     * @Description: 任务大派送 任务领取
     * @param request
    * @param response
    * @param commonReq
     * @return com.pf.play.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2019/11/23 11:18
     */
    @PostMapping("/exeGiveTask")
    public JsonResult<Object> exeGiveTask(HttpServletRequest request, HttpServletResponse response,TaskReq  taskReq)throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);

        try{
            List<DisWisemanInfo>  list  = new ArrayList<>();
            log.info("----------:exeGiveTask!");
            boolean   cheakFlag  = TaskMethod.checkUserTaskIsEffective(taskReq);
            if (!cheakFlag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }

            memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(taskReq.getToken(), taskReq.getWxOpenId());
            boolean       flag   =   ComponentUtil.taskService.checkExeTaskIdReward(memberId, taskReq.getTaskId());
            if(!flag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR13.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR13.geteDesc());
            }
            flag  = ComponentUtil.taskService.addRewardTaskLog(memberId,taskReq.getTaskId());
            if(!flag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR9.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR9.geteDesc());
            }

            GiveTaskResultResp giveTaskResultResp = new GiveTaskResultResp();
            giveTaskResultResp.setResult(flag);

            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, taskReq, ServerConstant.InterfaceEnum.TASK_EXEGIVE.getType(),
                    ServerConstant.InterfaceEnum.TASK_EXEGIVE.getDesc(), null, JSON.toJSONString(taskReq), JSON.toJSONString(giveTaskResultResp), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(giveTaskResultResp);
        }catch (Exception e){
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, taskReq, ServerConstant.InterfaceEnum.TASK_EXEGIVE.getType(),
                    ServerConstant.InterfaceEnum.TASK_EXEGIVE.getDesc(), null, JSON.toJSONString(taskReq), JSON.toJSONString(null), map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }


    /**
     * @Description:
     * @param request
    * @param response
    * @param userCommonReq
     * @return com.pf.play.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2019/12/24 13:55
     */
    @PostMapping("/todayTaskInfo")
    public JsonResult<Object> todayTaskInfo(HttpServletRequest request, HttpServletResponse response,UserCommonReq  userCommonReq){
        try{
            List<DisWisemanInfo>  list  = new ArrayList<>();
            log.info("----------:todayTaskInfo!");

            return JsonResult.successResult(null);
        }catch (Exception e){
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }

}
