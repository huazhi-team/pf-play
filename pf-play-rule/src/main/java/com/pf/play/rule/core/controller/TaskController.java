package com.pf.play.rule.core.controller;

import com.pf.play.common.utils.JsonResult;
import com.pf.play.model.protocol.request.CommonReq;
import com.pf.play.model.protocol.request.task.TaskReq;
import com.pf.play.model.protocol.request.uesr.UserCommonReq;
import com.pf.play.model.protocol.response.task.*;
import com.pf.play.rule.TaskMethod;
import com.pf.play.rule.core.common.exception.ExceptionMethod;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.core.model.DisTaskType;
import com.pf.play.rule.core.model.DisWisemanInfo;
import com.pf.play.rule.core.model.UTaskHave;
import com.pf.play.rule.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
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

    private static Logger log = LoggerFactory.getLogger(SpCodeController.class);



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
    public JsonResult<Object> queryReceiveTaskList(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq){
        try{
            log.info("----------:receiveTask!");

            boolean  flag = TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            Integer     memberId =  0 ;
            if(!flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenId());
            }
            List<DisTaskType> list = ComponentUtil.taskService.queryReceiveTask(memberId);
            List<ReceiveTaskResp>  receiveTaskRespList = TaskMethod.changReceiveTaskResp(list);
            return JsonResult.successResult(receiveTaskRespList);
        }catch (Exception e){
            return JsonResult.failedResult("wrong for data!",1+"");
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
    public JsonResult<Object> myTask(HttpServletRequest request, HttpServletResponse response,UserCommonReq userCommonReq){
        try{
            log.info("----------:myTask!");
            Integer     memberId =  0 ;
            boolean  flag =TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenId());
            }else {
                return JsonResult.successResult(null);
            }

            List<UTaskHave> list = ComponentUtil.taskService.getMyTask(memberId);

            List<UserHavaTaskResp>  rslist=TaskMethod.changUserHavaTaskResp(list);

//            List<ReceiveTaskResp>  receiveTaskRespList = TaskMethod.changReceiveTaskResp(list);
            return JsonResult.successResult(rslist);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failedResult("wrong for data!",1+"");
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
    public JsonResult<Object> userTaskHistory(HttpServletRequest request, HttpServletResponse response,UserCommonReq  userCommonReq){
        try{
            log.info("----------:userTaskHistory!");
            Integer     memberId =  0 ;
            boolean  flag =TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(!flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenId());
            }else{
                return JsonResult.successResult(null);
            }

            if(memberId==0){
                return JsonResult.successResult(null);
            }
            List<UTaskHave> list = ComponentUtil.taskService.queryInvalidHaveTask(memberId);
//            List<DisTaskType> list = ComponentUtil.taskService.queryInvalidHaveTask(memberId);
            List<UserHistoryTaskResp>    userHistoryTaskList =TaskMethod.changUserHistoryTask(list);
            return JsonResult.successResult(userHistoryTaskList);
        }catch (Exception e){
            return JsonResult.failedResult("wrong for data!",1+"");
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
    public JsonResult<Object> exeReceiveTask(HttpServletRequest request, HttpServletResponse response,TaskReq  taskReq){
        try{
            log.info("----------:exeReceiveTask!");
            String    token  ="d9680065409547b69746912de48ee8d4";
            String    wxOpenId  ="slllsdjdjsa";
            Integer   taskId    = 2;
            boolean   cheakFlag  = TaskMethod.checkUserTaskIsEffective(taskReq);
            if (!cheakFlag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }
            Integer   memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(taskReq.getToken(), taskReq.getWxOpenId());
//            List<DisTaskType> list = ComponentUtil.taskService.queryInvalidHaveTask(memberId);
            boolean  checkFlag  =   ComponentUtil.taskService.checkUserCondition(memberId,taskReq.getTaskId());
            if(!checkFlag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR1.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR1.geteDesc());
            }

            boolean  insertFlag  = ComponentUtil.taskService.addUserTask(memberId,taskReq.getTaskId());
            if(!insertFlag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR1.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR1.geteDesc());
            }
            return JsonResult.successResult(insertFlag);
        }catch (Exception e){
            Map<String,String> map= ExceptionMethod.getException(e);
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
    public JsonResult<Object> queryGiveTaskList(HttpServletRequest request, HttpServletResponse response,UserCommonReq  userCommonReq){
        try{
            List<DisWisemanInfo>  list  = new ArrayList<>();
            log.info("----------:giveTaskList!");
            Integer     memberId =  0 ;
            boolean  flag =TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(!flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenId());
            }

            if(memberId==0){
                  list = ComponentUtil.taskService.getNoLoginDisWisemanInfo();
            }else{
                  list = ComponentUtil.taskService.queryUserDisWisemanInfo(memberId);
            }

            List<GiveTaskResp>  giveTaskRespsList=TaskMethod.changGiveTask(list);
            return JsonResult.successResult(giveTaskRespsList);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failedResult("wrong for data!",1+"");
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
    public JsonResult<Object> exeGiveTask(HttpServletRequest request, HttpServletResponse response,TaskReq  taskReq){
        try{
            List<DisWisemanInfo>  list  = new ArrayList<>();
            log.info("----------:exeGiveTask!");
            boolean   cheakFlag  = TaskMethod.checkUserTaskIsEffective(taskReq);
            if (!cheakFlag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }
            Integer   memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(taskReq.getToken(), taskReq.getWxOpenId());
            boolean       flag   =   ComponentUtil.taskService.checkExeTaskIdReward(memberId, taskReq.getTaskId());
            if(!flag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR8.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR8.geteDesc());
            }
            flag  = ComponentUtil.taskService.addRewardTaskLog(memberId,taskReq.getTaskId());
            if(!flag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR9.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR9.geteDesc());
            }

            GiveTaskResultResp giveTaskResultResp = new GiveTaskResultResp();
            giveTaskResultResp.setResult(flag);
            return JsonResult.successResult(giveTaskResultResp);
        }catch (Exception e){
            Map<String,String> map= ExceptionMethod.getException(e);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }

}
