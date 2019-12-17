package com.pf.play.rule.core.controller;

import com.pf.play.common.utils.JsonResult;
import com.pf.play.model.protocol.request.CommonReq;
import com.pf.play.model.protocol.request.uesr.LoginReq;
import com.pf.play.model.protocol.request.uesr.UpdateUserReq;
import com.pf.play.model.protocol.request.uesr.UserCommonReq;
import com.pf.play.model.protocol.response.my.Empirical;
import com.pf.play.model.protocol.response.my.Vitality;
import com.pf.play.model.protocol.response.task.ExeTodayTaskResp;
import com.pf.play.model.protocol.response.task.GiveTaskResultResp;
import com.pf.play.model.protocol.response.task.TodayTaskResp;
import com.pf.play.model.protocol.response.uesr.MyEmpiricalResp;
import com.pf.play.model.protocol.response.uesr.MyFriendsResp;
import com.pf.play.model.protocol.response.uesr.MyMasonryResp;
import com.pf.play.model.protocol.response.uesr.MyVitalityResp;
import com.pf.play.rule.MyMethod;
import com.pf.play.rule.TaskMethod;
import com.pf.play.rule.core.common.exception.ExceptionMethod;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.core.model.*;
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
 * @Description 用户信息
 * @Author long
 * @Date 2019/11/14 10:10
 * @Version 1.0
 */

@RestController
@RequestMapping("/play/user")
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    /**
     * @Description: 我的资产信息
     * @param request
    * @param response
    * @param commonReq
     * @return com.pf.play.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2019/11/26 17:37
     *  必填字段:{"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","wxOpenId":"1"}
     *  客户端加密字段:ctime+cctime+token+秘钥=sign
     *  服务端加密字段:stime+token+秘钥=sign
     */
    @PostMapping("/myMasonry")
    public JsonResult<Object> myMasonry(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq){
        try{
            log.info("----------:masonryInfo!");


            Integer  memberId = 0 ;
            boolean  flag = TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenId());
            }

            if(memberId==0){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.IS_USER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.IS_USER_ERROR.geteDesc());
            }

            VcMemberResource   vcMemberResource1 = ComponentUtil.userInfoSevrice.getMyResourceInfo(memberId);

            UMasonrySummary uMasonrySummary =   ComponentUtil.userMasonryService.queryUMasonrySummary(memberId);

            List<UMasonryListLog> list =ComponentUtil.userMasonryService.toKenQueryMasonryInfo(memberId);
            if(null==list||list.size()==0){
                list =new  ArrayList();
            }
            MyMasonryResp  myMasonryResp = MyMethod.toMyMasonryResp(list,vcMemberResource1,uMasonrySummary);
            return JsonResult.successResult(myMasonryResp);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failedResult("wrong for data!",1+"");
        }
    }

    /**
     * @Description: 我的好友
     * @param request
    * @param response
    * @param userCommonReq
     * @return com.pf.play.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2019/11/27 19:07
     * 必填字段:{"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","wxOpenId":"1"}
     */
    @PostMapping("/myFriends")
    public JsonResult<Object> myFriends(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq){
        JsonResult<Object>     result  = null;
        try{
            log.info("----------:myTeam!");
            List<MyFriendsResp>   list  = null;
            Integer   memberId =0;
            Integer   superiorId =0;
            boolean  flag = TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(flag){
                UserInfoModel userInfoModel   = ComponentUtil.userMasonryService.queryTokenSuperiorId(userCommonReq.getToken(),
                            userCommonReq.getWxOpenId());
                if(userInfoModel==null){
                    throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
                }
                superiorId = userInfoModel.getSuperiorId();
                memberId = userInfoModel.getMemberId();

            }
            MyFriendsResp myFriendsResp   =  ComponentUtil.userInfoSevrice.toMyFriensResp(memberId,superiorId);
            return JsonResult.successResult(myFriendsResp);
        }catch (Exception e){
            Map<String,String> map= ExceptionMethod.getException(e);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }

    /**
     * @Description: 我的经验值
     * @param request
    * @param response
    * @param userCommonReq
     * @return com.pf.play.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2019/11/27 22:44
     * 必填字段:{"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","wxOpenId":"1"}
     */
    @PostMapping("/myEmpiricValue")
    public JsonResult<Object> myEmpiricValue(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq){
        JsonResult<Object>     result  = null;
        try{
            log.info("----------:myEmpiricValue!");
            List<MyFriendsResp>   list  = null;
            Integer   memberId =0;
            boolean  flag = TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenId());
            }
            VcMemberResource  vcMemberResource =null;
            if(memberId!=0){
                vcMemberResource =ComponentUtil.userInfoSevrice.getMyTeamResourceInfo(memberId);
            }

            List<Empirical>  empiricalList  =  ComponentUtil.userInfoSevrice.getMyEmpirical(memberId);
            VcMemberResource   vcMemberResource1 = ComponentUtil.userInfoSevrice.getMyResourceInfo(memberId);
            MyEmpiricalResp  myEmpiricalResp   =null;
            if(vcMemberResource==null){
                  myEmpiricalResp = MyMethod.toMyEmpiricalResp(0D,empiricalList,null);
            }else{
                  myEmpiricalResp = MyMethod.toMyEmpiricalResp(vcMemberResource.getEmpiricalValue(),empiricalList,vcMemberResource1);
            }
            return JsonResult.successResult(myEmpiricalResp);
        }catch (Exception e){
            Map<String,String> map=ExceptionMethod.getException(e);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }

    /**
     * @Description: 我的活跃值
     * @param request
    * @param response
    * @param userCommonReq
     * @return com.pf.play.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2019/11/27 22:45
     * 必填字段:{"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","wxOpenId":"1"}
     */
//    @PostMapping("/myVitalityValue")
    @PostMapping("/myVitalityValue")
    public JsonResult<Object> myVitalityValue(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq){
        JsonResult<Object>     result  = null;
        try{
            log.info("----------:myVitalityValue!");
            List<MyFriendsResp>   list  = null;
            Integer   memberId =0;
            boolean  flag = TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenId());
            }

            VcMemberResource  vcMemberResource =null;
            if(memberId!=0){
                vcMemberResource = ComponentUtil.userInfoSevrice.getMyResourceInfo(memberId);
            }else{
                throw  new ServiceException(ErrorCode.ENUM_ERROR.IS_USER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.IS_USER_ERROR.geteDesc());
            }

            VcMember   vcMember1=  TaskMethod.changvcMemberTOsuperiorId(memberId);

            VcMember   vcMember2 =ComponentUtil.userInfoSevrice.getSuperiorIdToPushPeople(vcMember1);

            List<Vitality>  vitalityList  =  ComponentUtil.userInfoSevrice.getMyDisVitalityValue(memberId);

            MyVitalityResp myVitalityResp   =null;

            myVitalityResp = MyMethod.toMyVitalityResp(vcMember2.getPushPeople(),vcMemberResource,  vitalityList );

            return JsonResult.successResult(myVitalityResp);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map=ExceptionMethod.getException(e);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }

    /**
     * @Description: 查询用户基本信息
     * @param request
    * @param response
    * @param userCommonReq
     * @return com.pf.play.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2019/11/28 16:39
     * 必填字段:{"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","wxOpenId":"1"}
     */
    @PostMapping("/queryUserInfo")
    public JsonResult<Object> queryUserInfo(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq){
        JsonResult<Object>     result  = null;
        try{
            log.info("----------:queryUserInfo!");
            List<MyFriendsResp>   list  = null;
            Integer   memberId =0;

            boolean flag = TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenId());
            }else{
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }

            if(memberId==0){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.IS_USER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.IS_USER_ERROR.geteDesc());
            }

            VcMember   vcMember=ComponentUtil.userInfoSevrice.getMemeber(memberId);

            VcMemberResource  vcMemberResource  = ComponentUtil.userInfoSevrice.getMyResourceInfo(memberId);
            if(vcMember==null){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }
            return JsonResult.successResult(MyMethod.toMyUserInfoResp(vcMember,vcMemberResource));
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map=ExceptionMethod.getException(e);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }


    /**
     * @Description: 用户信息编辑
     * @param request
    * @param response
    * @param updateUserReq
     * @return com.pf.play.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2019/11/28 16:41
     */
    @PostMapping("/editUserInfo")
    public JsonResult<Object> editUserInfo(HttpServletRequest request, HttpServletResponse response, UpdateUserReq updateUserReq){
        JsonResult<Object>     result  = null;
        try{
            log.info("----------:editUserInfo!");
            List<MyFriendsResp>   list  = null;
            Integer   memberId =0;

//            boolean  flag =  MyMethod.checkUqdateUser(updateUserReq);
//            if(!flag){
//                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
//            }

            UserCommonReq userCommonReq =MyMethod.uqdateUserToUserCommon(updateUserReq);
            boolean  flag = TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenId());
            }else{
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }


            if(memberId==0){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.IS_USER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.IS_USER_ERROR.geteDesc());
            }

            VcMember vcMember = MyMethod.toUqdateVcMember(memberId,updateUserReq);

            flag  = ComponentUtil.userInfoSevrice.updateMemeber(vcMember);

            GiveTaskResultResp  giveTaskResultResp = new GiveTaskResultResp();
            giveTaskResultResp.setResult(flag);

            //同步给动态信息
            ComponentUtil.userInfoSevrice.userSynchronousQhr(memberId,updateUserReq.getToken());
            return JsonResult.successResult(giveTaskResultResp);
        }catch (Exception e){
            Map<String,String> map=ExceptionMethod.getException(e);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }

    @PostMapping("/userReceiveTaskReward")
    public JsonResult<Object> userReceiveTaskReward(HttpServletRequest request, HttpServletResponse response, CommonReq commonReq){
        JsonResult<Object>     result  = null;
        try{
            LoginReq loginReq1 = new LoginReq();
            loginReq1.setWxOpenId("slllsdjdjsa");
            loginReq1.setToken("0423837aee5d4d96a2cf868d5fc2d47d");
//            List<UMasonryListLog> list =ComponentUtil.userMasonryService.toKenQueryMasonryInfo(loginReq1);
//            if(null==list||list.size()==0){
//                list =new  ArrayList();
//            }
            return JsonResult.successResult(null);
        }catch (Exception e){
            Map<String,String> map=ExceptionMethod.getException(e);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }


    /**
     * @Description: 今日任务状态
     * @param request
    * @param response
    * @param userCommonReq
     * @return com.pf.play.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2019/12/13 18:56
     */
    @PostMapping("/myTodayTask")
    public JsonResult<Object> myTodayTask(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq){


        JsonResult<Object>     result  = null;
        try{
            log.info("----------:myTodayTask!");
            TodayTaskResp   todayTaskResp  = new TodayTaskResp();
            Integer   memberId = 0;
            boolean  flag = TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenId());
            }

            if(memberId==0){
                return JsonResult.successResult(todayTaskResp);
            }

            //最大的信息
            UTaskHave             uTaskHaveMax  = ComponentUtil.taskService.getMaxUTaskHave(memberId);

            UTaskHave             uTaskHave  = ComponentUtil.taskService.getUTaskHave(memberId);
            //用户的点赞详情
            UMasonryListLog  uMasonryListLog = ComponentUtil.taskService.getUMasonryListLog(memberId);
            //用户剩余信息
//            UTaskHave   uTaskHave  = ComponentUtil.taskService.getMemberUDailyTaskStat(memberId);

            UdailyTaskStat udailyTaskStat =  ComponentUtil.taskService.getMemberUDailyTaskStat(memberId);

            todayTaskResp = TaskMethod.changTodayTaskResp(uTaskHaveMax,uMasonryListLog,udailyTaskStat,uTaskHave);

            return JsonResult.successResult(todayTaskResp);
        }catch (Exception e){
            Map<String,String> map=ExceptionMethod.getException(e);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }



    /**
     * @Description: 今日任务领取
     * @param request
    * @param response
    * @param userCommonReq
     * @return com.pf.play.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2019/12/13 18:56
     */
    @PostMapping("/exeTodayTask")
    public JsonResult<Object> exeTodayTask(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq){
        JsonResult<Object>     result  = null;
        try{
            log.info("----------:exeTodayTask!");
            TodayTaskResp   todayTaskResp  = new TodayTaskResp();
            Integer   memberId = 0;
            boolean  flag = TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenId());
            }
            if(memberId==0){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }


            //差一个验证是否领取狗  也差一个砖石更新，更新时记得加锁
            VcMemberResource  vcMemberResource  = ComponentUtil.userInfoSevrice.getMyResourceInfo(memberId);
            List<UTaskHave>  list  = ComponentUtil.taskService.getMyTask(memberId);

            Double  masonry=ComponentUtil.taskService.grantReward(vcMemberResource,list);
            if(masonry==0){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR11.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR11.geteDesc());
            }
            ExeTodayTaskResp  exeTodayTaskResp =new ExeTodayTaskResp();
            exeTodayTaskResp.setMasonry(masonry);
            return JsonResult.successResult(exeTodayTaskResp);
        }catch (Exception e){
            Map<String,String> map=ExceptionMethod.getException(e);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }


    @PostMapping("/test")
    public JsonResult<Object> test(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq){
        JsonResult<Object>     result  = null;
        try{

            ComponentUtil.userInfoSevrice.toRealName(2);

            return JsonResult.successResult(null);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map=ExceptionMethod.getException(e);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }


}
