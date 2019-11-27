package com.pf.play.rule.core.controller;

import com.pf.play.common.utils.JsonResult;
import com.pf.play.model.protocol.request.CommonReq;
import com.pf.play.model.protocol.request.task.TaskReq;
import com.pf.play.model.protocol.request.uesr.UserCommonReq;
import com.pf.play.rule.TaskMethod;
import com.pf.play.rule.core.common.exception.ExceptionMethod;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.core.model.DisTaskType;
import com.pf.play.rule.core.model.DisWisemanInfo;
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
@RequestMapping("/task")
public class TaskController {

    private static Logger log = LoggerFactory.getLogger(SpCodeController.class);

    @PostMapping("/typeTaskInfo")
    public JsonResult<Object> actionPay(HttpServletRequest request, HttpServletResponse response, CommonReq commonReq){
        try{
            log.info("----------:type_taskInfo!");
            JsonResult.successResult(null);
        }catch (Exception e){
            return JsonResult.failedResult("wrong for data!",1+"");
        }
        return null;
    }


    /**
     * @Description: 任务管理列表详情
     * @param request
    * @param response
    * @param commonReq
     * @return com.pf.play.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2019/11/23 11:40
     */
    @GetMapping("/queryMemberReceiveTaskList")
    public JsonResult<Object> queryReceiveTaskList(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq){
        try{
            log.info("----------:receiveTask!");

            boolean  flag = TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            Integer     memberId =  0 ;
            if(!flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenid());
            }
            List<DisTaskType> list = ComponentUtil.taskService.queryReceiveTask(memberId);
            return JsonResult.successResult(list);
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
    @GetMapping("/userHavaTask")
    public JsonResult<Object> myTask(HttpServletRequest request, HttpServletResponse response,UserCommonReq userCommonReq){
        try{
            log.info("----------:myTask!");
            Integer     memberId =  0 ;
            boolean  flag =TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(!flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenid());
            }
            List<DisTaskType> list = ComponentUtil.taskService.getMyTask(memberId);
            return JsonResult.successResult(list);
        }catch (Exception e){
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
    @GetMapping("/userTaskHistory")
    public JsonResult<Object> userTaskHistory(HttpServletRequest request, HttpServletResponse response,UserCommonReq  userCommonReq){
        try{
            log.info("----------:userTaskHistory!");
            Integer     memberId =  0 ;
            boolean  flag =TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(!flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenid());
            }
            List<DisTaskType> list = ComponentUtil.taskService.queryInvalidHaveTask(memberId);
            return JsonResult.successResult(list);
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
    @GetMapping("/exeReceiveTask")
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
    @GetMapping("/giveTaskList")
    public JsonResult<Object> queryGiveTaskList(HttpServletRequest request, HttpServletResponse response,UserCommonReq  userCommonReq){
        try{
            List<DisWisemanInfo>  list  = new ArrayList<>();
            log.info("----------:giveTaskList!");
            Integer     memberId =  0 ;
            boolean  flag =TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(!flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenid());
            }

            if(memberId==0){
                  list = ComponentUtil.taskService.getNoLoginDisWisemanInfo();
            }else{
                  list = ComponentUtil.taskService.queryUserDisWisemanInfo(memberId);
            }
            return JsonResult.successResult(list);
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
    @GetMapping("/exeGiveTask")
    public JsonResult<Object> exeGiveTask(HttpServletRequest request, HttpServletResponse response,TaskReq  taskReq){
        try{
            List<DisWisemanInfo>  list  = new ArrayList<>();
            log.info("----------:exeGiveTask!");
            String    token  ="d9680065409547b69746912de48ee8d4";
            String    wxOpenId  = "slllsdjdjsa";
            Integer   taskId    = 1;

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
            return JsonResult.successResult(flag);
        }catch (Exception e){
            return JsonResult.failedResult("wrong for data!",1+"");
        }
    }

}
