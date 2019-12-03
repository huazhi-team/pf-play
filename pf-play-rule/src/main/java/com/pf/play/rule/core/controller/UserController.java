package com.pf.play.rule.core.controller;

import com.pf.play.common.utils.JsonResult;
import com.pf.play.model.protocol.request.CommonReq;
import com.pf.play.model.protocol.request.uesr.LoginReq;
import com.pf.play.model.protocol.request.uesr.UpdateUserReq;
import com.pf.play.model.protocol.request.uesr.UserCommonReq;
import com.pf.play.model.protocol.response.my.Empirical;
import com.pf.play.model.protocol.response.my.Vitality;
import com.pf.play.model.protocol.response.uesr.MyEmpiricalResp;
import com.pf.play.model.protocol.response.uesr.MyFriendsResp;
import com.pf.play.model.protocol.response.uesr.MyMasonryResp;
import com.pf.play.model.protocol.response.uesr.MyVitalityResp;
import com.pf.play.rule.MyMethod;
import com.pf.play.rule.TaskMethod;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.core.model.UMasonryListLog;
import com.pf.play.rule.core.model.VcMember;
import com.pf.play.rule.core.model.VcMemberResource;
import com.pf.play.rule.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 用户信息
 * @Author long
 * @Date 2019/11/14 10:10
 * @Version 1.0
 */

@RestController
@RequestMapping("/user")
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
    @GetMapping("/myMasonry")
    public JsonResult<Object> myMasonry(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq){
        try{
            log.info("----------:masonryInfo!");
            LoginReq loginReq1 = new LoginReq();
            loginReq1.setWxOpenId(userCommonReq.getWxOpenid());
            loginReq1.setToken(userCommonReq.getToken());
            List<UMasonryListLog> list =ComponentUtil.userMasonryService.toKenQueryMasonryInfo(loginReq1);
            if(null==list||list.size()==0){
                list =new  ArrayList();
            }
            List<MyMasonryResp>  myMasonryResp = MyMethod.toMyMasonryResp(list);
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
    @GetMapping("/myFriends")
    public JsonResult<Object> myFriends(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq){
        JsonResult<Object>     result  = null;
        try{
            log.info("----------:myTeam!");
            List<MyFriendsResp>   list  = null;
            Integer   memberId =0;
            boolean  flag = TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenid());
            }

            VcMember     vcMember    = ComponentUtil.userInfoSevrice.getMySuperiorInfo(memberId);


            VcMemberResource vcMemberResource = ComponentUtil.userInfoSevrice.getMyTeamResourceInfo(memberId);
            if(vcMember==null){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }
            if(vcMemberResource==null){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }

            List<VcMember>      vcMemberList  = ComponentUtil.userInfoSevrice.getMyUpInfo(memberId);

            MyFriendsResp myFriendsResp   =  MyMethod.toMyFriendsResp(vcMember,vcMemberResource,vcMemberList);
            return JsonResult.successResult(myFriendsResp);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failedResult("wrong for data!",1+"");
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
    @GetMapping("/myEmpiricValue")
    public JsonResult<Object> myEmpiricValue(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq){


        JsonResult<Object>     result  = null;
        try{
            log.info("----------:myEmpiricValue!");
            List<MyFriendsResp>   list  = null;
            Integer   memberId =0;
            boolean  flag = TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(!flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenid());
            }
            VcMemberResource  vcMemberResource =null;
            if(memberId!=0){
                vcMemberResource =ComponentUtil.userInfoSevrice.getMyTeamResourceInfo(memberId);
            }

            List<Empirical>  empiricalList  =  ComponentUtil.userInfoSevrice.getMyEmpirical(memberId);
            MyEmpiricalResp  myEmpiricalResp   =null;
            if(vcMemberResource==null){
                  myEmpiricalResp = MyMethod.toMyEmpiricalResp(0F,empiricalList);
            }else{
                  myEmpiricalResp = MyMethod.toMyEmpiricalResp(vcMemberResource.getEmpiricalValue(),empiricalList);
            }
            return JsonResult.successResult(myEmpiricalResp);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failedResult("wrong for data!",1+"");
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
    @GetMapping("/myVitalityValue")
    public JsonResult<Object> myVitalityValue(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq){
        JsonResult<Object>     result  = null;
        try{
            log.info("----------:myVitalityValue!");
            List<MyFriendsResp>   list  = null;
            Integer   memberId =0;
            boolean  flag = TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(!flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenid());
            }
            VcMemberResource  vcMemberResource =null;
            if(memberId!=0){
                vcMemberResource =ComponentUtil.userInfoSevrice.getMyTeamResourceInfo(memberId);
            }

            List<Vitality>  vitalityList  =  ComponentUtil.userInfoSevrice.getMyDisVitalityValue(memberId);
            MyVitalityResp myVitalityResp   =null;
            if(vcMemberResource==null){
                myVitalityResp = MyMethod.toMyVitalityListResp(0F,vitalityList);
            }else{
                myVitalityResp = MyMethod.toMyVitalityListResp(vcMemberResource.getEmpiricalValue(),vitalityList);
            }
            return JsonResult.successResult(myVitalityResp);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failedResult("wrong for data!",1+"");
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
    @GetMapping("/queryUserInfo")
    public JsonResult<Object> queryUserInfo(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq){
        JsonResult<Object>     result  = null;
        try{
            log.info("----------:queryUserInfo!");
            List<MyFriendsResp>   list  = null;
            Integer   memberId =0;

            boolean flag = TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(!flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenid());
            }else{
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }

            VcMember   vcMember=ComponentUtil.userInfoSevrice.getMemeber(memberId);
            if(vcMember==null){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }
            return JsonResult.successResult(MyMethod.toMyUserInfoResp(vcMember));
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failedResult("wrong for data!",1+"");
        }
    }


    /**
     * @Description: TODO
     * @param request
    * @param response
    * @param updateUserReq
     * @return com.pf.play.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2019/11/28 16:41
     */
    @GetMapping("/editUserInfo")
    public JsonResult<Object> editUserInfo(HttpServletRequest request, HttpServletResponse response, UpdateUserReq updateUserReq){
        JsonResult<Object>     result  = null;
        try{
            log.info("----------:editUserInfo!");
            List<MyFriendsResp>   list  = null;
            Integer   memberId =0;

            boolean  flag =  MyMethod.checkUqdateUser(updateUserReq);
            if(flag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }

            UserCommonReq userCommonReq =MyMethod.uqdateUserToUserCommon(updateUserReq);
            flag = TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if(!flag){
                memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenid());
            }else{
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }

            VcMember vcMember = MyMethod.toUqdateVcMember(memberId,updateUserReq);

            flag  = ComponentUtil.userInfoSevrice.updateMemeber(vcMember);

            return JsonResult.successResult(flag);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failedResult("wrong for data!",1+"");
        }
    }

    @GetMapping("/userReceiveTaskReward")
    public JsonResult<Object> userReceiveTaskReward(HttpServletRequest request, HttpServletResponse response, CommonReq commonReq){
        JsonResult<Object>     result  = null;
        try{
            LoginReq loginReq1 = new LoginReq();
            loginReq1.setWxOpenId("slllsdjdjsa");
            loginReq1.setToken("0423837aee5d4d96a2cf868d5fc2d47d");
            List<UMasonryListLog> list =ComponentUtil.userMasonryService.toKenQueryMasonryInfo(loginReq1);
            if(null==list||list.size()==0){
                list =new  ArrayList();
            }
            return JsonResult.successResult(list);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failedResult("wrong for data!",1+"");
        }
    }



}
