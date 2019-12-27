package com.pf.play.rule.core.controller;

import com.alibaba.fastjson.JSON;
import com.pf.play.common.utils.JsonResult;
import com.pf.play.common.utils.StringUtil;
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
import com.pf.play.rule.PublicMethod;
import com.pf.play.rule.TaskMethod;
import com.pf.play.rule.core.common.exception.ExceptionMethod;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.CacheKey;
import com.pf.play.rule.core.common.utils.constant.Constant;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.core.common.utils.constant.ServerConstant;
import com.pf.play.rule.core.model.*;
import com.pf.play.rule.core.model.region.RegionModel;
import com.pf.play.rule.core.model.stream.StreamConsumerModel;
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
    public JsonResult<Object> myMasonry(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq)throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);

        try{
            log.info("----------:masonryInfo!");


            memberId = 0 ;
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
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.MY_MASONRY.getType(),
                    ServerConstant.InterfaceEnum.MY_MASONRY.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(myMasonryResp), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(myMasonryResp);
        }catch (Exception e){
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.MY_MASONRY.getType(),
                    ServerConstant.InterfaceEnum.MY_MASONRY.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(null), map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
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
    public JsonResult<Object> myFriends(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq)throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        try{
            log.info("----------:myTeam!");
            List<MyFriendsResp>   list  = null;
            memberId =0;
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
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.MY_FRIENDS.getType(),
                    ServerConstant.InterfaceEnum.MY_FRIENDS.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(myFriendsResp), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(myFriendsResp);
        }catch (Exception e){
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.MY_FRIENDS.getType(),
                    ServerConstant.InterfaceEnum.MY_FRIENDS.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(null), map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
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
    public JsonResult<Object> myEmpiricValue(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq)throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        try{
            log.info("----------:myEmpiricValue!");
            List<MyFriendsResp>   list  = null;
            memberId =0;
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
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.MY_EMPIRICVALUE.getType(),
                    ServerConstant.InterfaceEnum.MY_EMPIRICVALUE.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(myEmpiricalResp), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(myEmpiricalResp);
        }catch (Exception e){
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.MY_EMPIRICVALUE.getType(),
                    ServerConstant.InterfaceEnum.MY_EMPIRICVALUE.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(null), map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
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
    public JsonResult<Object> myVitalityValue(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq)throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        try{
            log.info("----------:myVitalityValue!");
            List<MyFriendsResp>   list  = null;
            memberId =0;
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
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.MY_VITALITYVALUE.getType(),
                    ServerConstant.InterfaceEnum.MY_VITALITYVALUE.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(myVitalityResp), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(myVitalityResp);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.MY_VITALITYVALUE.getType(),
                    ServerConstant.InterfaceEnum.MY_VITALITYVALUE.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(null), map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
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
    public JsonResult<Object> queryUserInfo(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq)throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        try{
            log.info("----------:queryUserInfo!");
            List<MyFriendsResp>   list  = null;
            memberId =0;

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
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.TASK_EXEGIVE.getType(),
                    ServerConstant.InterfaceEnum.TASK_EXEGIVE.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(vcMemberResource), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(MyMethod.toMyUserInfoResp(vcMember,vcMemberResource));
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.MY_USERINFO.getType(),
                    ServerConstant.InterfaceEnum.MY_USERINFO.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(null), map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
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
    public JsonResult<Object> editUserInfo(HttpServletRequest request, HttpServletResponse response, UpdateUserReq updateUserReq)throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        try{
            log.info("----------:editUserInfo!");
            List<MyFriendsResp>   list  = null;
            memberId =0;

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
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, updateUserReq, ServerConstant.InterfaceEnum.MY_EDITUSERINFO.getType(),
                    ServerConstant.InterfaceEnum.MY_EDITUSERINFO.getDesc(), null, JSON.toJSONString(updateUserReq), JSON.toJSONString(giveTaskResultResp), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(giveTaskResultResp);
        }catch (Exception e){
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, updateUserReq, ServerConstant.InterfaceEnum.MY_EDITUSERINFO.getType(),
                    ServerConstant.InterfaceEnum.MY_EDITUSERINFO.getDesc(), null, JSON.toJSONString(updateUserReq), JSON.toJSONString(null), map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }
    /**
     * @Description: 领取任务奖励
     * @param request
    * @param response
    * @param commonReq
     * @return com.pf.play.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2019/12/24 14:39
     */
    @PostMapping("/userReceiveTaskReward")
    public JsonResult<Object> userReceiveTaskReward(HttpServletRequest request, HttpServletResponse response, CommonReq commonReq)throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);

        try{
            LoginReq loginReq1 = new LoginReq();
            loginReq1.setWxOpenId("slllsdjdjsa");
            loginReq1.setToken("0423837aee5d4d96a2cf868d5fc2d47d");
//            List<UMasonryListLog> list =ComponentUtil.userMasonryService.toKenQueryMasonryInfo(loginReq1);
//            if(null==list||list.size()==0){
//                list =new  ArrayList();
//            }
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, commonReq, ServerConstant.InterfaceEnum.TASK_EXEGIVE.getType(),
                    ServerConstant.InterfaceEnum.TASK_EXEGIVE.getDesc(), null, JSON.toJSONString(commonReq), JSON.toJSONString(loginReq1), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(null);
        }catch (Exception e){
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, commonReq, ServerConstant.InterfaceEnum.MY_RECEIVETASKREWARD.getType(),
                    ServerConstant.InterfaceEnum.MY_RECEIVETASKREWARD.getDesc(), null, JSON.toJSONString(commonReq), JSON.toJSONString(null), map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
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
    public JsonResult<Object> myTodayTask(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq)throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        try{
            log.info("----------:myTodayTask!");
            TodayTaskResp   todayTaskResp  = new TodayTaskResp();
            memberId = 0;
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
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.MY_TODAYTASK.getType(),
                    ServerConstant.InterfaceEnum.MY_TODAYTASK.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(todayTaskResp), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(todayTaskResp);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.MY_TODAYTASK.getType(),
                    ServerConstant.InterfaceEnum.MY_TODAYTASK.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(null), map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
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
    public JsonResult<Object> exeTodayTask(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq)throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);

        try{
            log.info("----------:exeTodayTask!");
            TodayTaskResp   todayTaskResp  = new TodayTaskResp();
            memberId = 0;
            boolean  flag = TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenId());
            }
            if(memberId==0){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }

            //该用户今天是否领取过奖励

            flag = ComponentUtil.taskService.isReceiveAwards(memberId);
            if(!flag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR13.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR13.geteDesc());
            }
            //差一个验证是否领取狗  也差一个砖石更新，更新时记得加锁
            VcMemberResource  vcMemberResource  = ComponentUtil.userInfoSevrice.getMyResourceInfo(memberId);
            List<UTaskHave>  list  = ComponentUtil.taskService.getMyTask(memberId);

            if (list.size()==0){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR14.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR14.geteDesc());
            }

            Double  masonry=ComponentUtil.taskService.grantReward(vcMemberResource,list);
            if(masonry==0){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.TASK_ERRPR11.geteCode(),ErrorCode.ENUM_ERROR.TASK_ERRPR11.geteDesc());
            }
            ExeTodayTaskResp  exeTodayTaskResp =new ExeTodayTaskResp();
            exeTodayTaskResp.setMasonry(masonry);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.MY_EXETODAYTASK.getType(),
                    ServerConstant.InterfaceEnum.MY_EXETODAYTASK.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(exeTodayTaskResp), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(exeTodayTaskResp);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.MY_EXETODAYTASK.getType(),
                    ServerConstant.InterfaceEnum.MY_EXETODAYTASK.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(null), map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }


    @PostMapping("/test")
    public JsonResult<Object> test(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq)throws Exception{
        JsonResult<Object>     result  = null;
        try{
            ComponentUtil.redisService.hmSet(CacheKey.LEVEL0,"1","1");
            System.out.println(ComponentUtil.redisService.hmGet(CacheKey.LEVEL0,"1"));

//            ComponentUtil.redisService.deHmSet(CacheKey.LEVEL0,"1");
//            System.out.println(ComponentUtil.redisService.hmGet(CacheKey.LEVEL0,"1"));
//            ComponentUtil.userInfoSevrice.toRealName(2);

            return JsonResult.successResult(null);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }


}
