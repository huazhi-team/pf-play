package com.pf.play.rule.core.controller;

import com.pf.play.common.utils.JsonResult;
import com.pf.play.model.protocol.request.CommonReq;
import com.pf.play.model.protocol.request.task.TaskReq;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.core.model.DisTaskType;
import com.pf.play.rule.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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



    @GetMapping("/queryReceiveTaskList")
    public JsonResult<Object> queryReceiveTaskList(HttpServletRequest request, HttpServletResponse response,CommonReq  commonReq){
        try{
            log.info("----------:receiveTask!");
            Integer     memberId =  8 ;
            List<DisTaskType> list = ComponentUtil.taskService.queryReceiveTask(memberId);
            return JsonResult.successResult(list);
        }catch (Exception e){
            return JsonResult.failedResult("wrong for data!",1+"");
        }
    }


    @GetMapping("/userHavaTask")
    public JsonResult<Object> myTask(HttpServletRequest request, HttpServletResponse response,CommonReq  commonReq){
        try{
            log.info("----------:myTask!");
            String    token  ="d9680065409547b69746912de48ee8d4";
            String    wxOpenId  ="slllsdjdjsa";
            Integer   memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(token, wxOpenId);

            List<DisTaskType> list = ComponentUtil.taskService.getMyTask(memberId);
            return JsonResult.successResult(list);
        }catch (Exception e){
            return JsonResult.failedResult("wrong for data!",1+"");
        }
    }

    @GetMapping("/userTaskHistory")
    public JsonResult<Object> userTaskHistory(HttpServletRequest request, HttpServletResponse response,CommonReq  commonReq){
        try{
            log.info("----------:userTaskHistory!");
            String    token  ="d9680065409547b69746912de48ee8d4";
            String    wxOpenId  ="slllsdjdjsa";
            Integer   memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(token, wxOpenId);
            List<DisTaskType> list = ComponentUtil.taskService.queryInvalidHaveTask(memberId);
            return JsonResult.successResult(list);
        }catch (Exception e){
            return JsonResult.failedResult("wrong for data!",1+"");
        }
    }

    @GetMapping("/exeReceiveTask")
    public JsonResult<Object> exeReceiveTask(HttpServletRequest request, HttpServletResponse response,CommonReq  commonReq){
        try{
            log.info("----------:exeReceiveTask!");
            String    token  ="d9680065409547b69746912de48ee8d4";
            String    wxOpenId  ="slllsdjdjsa";
            Integer   taskId    = 2;
            Integer   memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(token, wxOpenId);
//            List<DisTaskType> list = ComponentUtil.taskService.queryInvalidHaveTask(memberId);
            boolean  checkFlag  =   ComponentUtil.taskService.checkUserCondition(memberId,taskId);
            if(!checkFlag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR1.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR1.geteDesc());
            }

            boolean  insertFlag  = ComponentUtil.taskService.addUserTask(memberId,taskId);
            if(!insertFlag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR1.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR1.geteDesc());
            }
            return JsonResult.successResult(null);
        }catch (Exception e){
            return JsonResult.failedResult("wrong for data!",1+"");
        }
    }


}
