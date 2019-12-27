package com.pf.play.rule.core.controller;

import com.alibaba.fastjson.JSON;
import com.pf.play.common.utils.JsonResult;
import com.pf.play.common.utils.StringUtil;
import com.pf.play.model.protocol.request.CommonReq;
import com.pf.play.model.protocol.request.task.TaskReq;
import com.pf.play.model.protocol.request.uesr.LoginReq;
import com.pf.play.model.protocol.request.uesr.UserCommonReq;
import com.pf.play.model.protocol.response.uesr.LoginResp;
import com.pf.play.model.protocol.response.uesr.UserInfoResp;
import com.pf.play.rule.LoginMethod;
import com.pf.play.rule.MyMethod;
import com.pf.play.rule.PublicMethod;
import com.pf.play.rule.TaskMethod;
import com.pf.play.rule.core.common.exception.ExceptionMethod;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.*;
import com.pf.play.rule.core.model.VcThirdParty;
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
import java.util.Map;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/13 22:11
 * @Version 1.0
 */
@RestController
@RequestMapping("/play/login")
public class LoginController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/userInfo")
    public JsonResult<Object> getUserInfo(HttpServletRequest request, HttpServletResponse response, LoginReq loginReq)throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        long memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        try{
            log.info("----------:进来啦!");
            regionModel = PublicMethod.assembleRegionModel(ip);
            UserInfoResp userInfoResp= ComponentUtil.loginService.login(loginReq);
//            if(userInfoResp==null){
//                return JsonResult.failedResult("wrong for data!",1+"");
//            }

            memberId=userInfoResp.getMemberId();
            LoginResp loginResp  = LoginMethod.changLoginResp(userInfoResp);

            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, loginReq, ServerConstant.InterfaceEnum.LOGIN_USERINFO.getType(),
                    ServerConstant.InterfaceEnum.LOGIN_USERINFO.getDesc(), null, JSON.toJSONString(loginReq), JSON.toJSONString(loginResp), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(loginResp);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, loginReq, ServerConstant.InterfaceEnum.LOGIN_USERINFO.getType(),
                    ServerConstant.InterfaceEnum.LOGIN_USERINFO.getDesc(), null, data, null, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }


    @PostMapping("/signOut")
    public JsonResult<Object> signOut(HttpServletRequest request, HttpServletResponse response, UserCommonReq updateUserReq)throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        try{
            log.info("----------:signOut!");

           boolean flag = TaskMethod.checkTokenAndWxOpenid(updateUserReq);
            if(flag){
                memberId = ComponentUtil.userMasonryService.queryTokenMemberId(updateUserReq.getToken(), updateUserReq.getWxOpenId());
            }else{
                return JsonResult.successResult(null);
            }
//            String tokenstr = CachedKeyUtils.getCacheKey(CacheKey.TOKEN_INFO, updateUserReq.getToken());
            ComponentUtil.redisService.remove(updateUserReq.getToken());
            VcThirdParty  vcThirdParty = LoginMethod.changLoginReqToVcThirdParty(updateUserReq);
            ComponentUtil.loginService.signOut(vcThirdParty);
            ComponentUtil.userInfoSevrice.userSynchronousQhr(memberId,"");

            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, updateUserReq, ServerConstant.InterfaceEnum.LOGIN_SIGNOUT.getType(),
                    ServerConstant.InterfaceEnum.LOGIN_SIGNOUT.getDesc(), null, JSON.toJSONString(updateUserReq), JSON.toJSONString(null), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(null);
        }catch (Exception e){
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE2);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, updateUserReq, ServerConstant.InterfaceEnum.LOGIN_SIGNOUT.getType(),
                    ServerConstant.InterfaceEnum.LOGIN_SIGNOUT.getDesc(), null, JSON.toJSONString(updateUserReq), JSON.toJSONString(null), null);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }
}
