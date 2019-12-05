package com.pf.play.rule.core.controller;

import com.pf.play.common.utils.JsonResult;
import com.pf.play.model.protocol.request.CommonReq;
import com.pf.play.model.protocol.request.task.TaskReq;
import com.pf.play.model.protocol.request.uesr.LoginReq;
import com.pf.play.model.protocol.request.uesr.UserCommonReq;
import com.pf.play.model.protocol.response.uesr.LoginResp;
import com.pf.play.model.protocol.response.uesr.UserInfoResp;
import com.pf.play.rule.LoginMethod;
import com.pf.play.rule.MyMethod;
import com.pf.play.rule.TaskMethod;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.CacheKey;
import com.pf.play.rule.core.common.utils.constant.CachedKeyUtils;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.core.model.VcThirdParty;
import com.pf.play.rule.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public JsonResult<Object> getUserInfo(HttpServletRequest request, HttpServletResponse response, LoginReq loginReq){
        try{
            log.info("----------:进来啦!");

            UserInfoResp userInfoResp= ComponentUtil.loginService.login(loginReq);
            if(userInfoResp==null){
                return JsonResult.failedResult("wrong for data!",1+"");
            }

            LoginResp loginResp  = LoginMethod.changLoginResp(userInfoResp);
            return JsonResult.successResult(loginResp);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failedResult("wrong for data!",1+"");
        }
    }


    @PostMapping("/signOut")
    public JsonResult<Object> signOut(HttpServletRequest request, HttpServletResponse response, UserCommonReq updateUserReq){
        JsonResult<Object>     result  = null;
        try{
            log.info("----------:signOut!");

            Integer  memberId =0;
           boolean flag = TaskMethod.checkTokenAndWxOpenid(updateUserReq);
            if(flag){
                memberId = ComponentUtil.userMasonryService.queryTokenMemberId(updateUserReq.getToken(), updateUserReq.getWxOpenId());
            }else{
                return JsonResult.successResult(null);
            }
            String tokenstr = CachedKeyUtils.getCacheKey(CacheKey.TOKEN_INFO, updateUserReq.getToken());
            ComponentUtil.redisService.remove(tokenstr);
            VcThirdParty  vcThirdParty = LoginMethod.changLoginReqToVcThirdParty(updateUserReq);
            ComponentUtil.loginService.signOut(vcThirdParty);
            ComponentUtil.userInfoSevrice.userSynchronousQhr(memberId,"");
            return JsonResult.successResult(null);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failedResult("wrong for data!",1+"");
        }
    }
}
