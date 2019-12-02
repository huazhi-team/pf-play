package com.pf.play.rule.core.controller;

import com.pf.play.common.utils.JsonResult;
import com.pf.play.model.protocol.request.CommonReq;
import com.pf.play.model.protocol.request.task.TaskReq;
import com.pf.play.model.protocol.request.uesr.LoginReq;
import com.pf.play.model.protocol.request.uesr.UserCommonReq;
import com.pf.play.model.protocol.response.uesr.LoginResp;
import com.pf.play.model.protocol.response.uesr.UserInfoResp;
import com.pf.play.rule.LoginMethod;
import com.pf.play.rule.core.model.VcThirdParty;
import com.pf.play.rule.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/login")
public class LoginController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/userInfo")
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


    @GetMapping("/signOut")
    public JsonResult<Object> signOut(HttpServletRequest request, HttpServletResponse response, LoginReq loginReq){
        JsonResult<Object>     result  = null;
        try{
            log.info("----------:signOut!");
            boolean  flag   =   LoginMethod.checkRemoveSignOutToken(loginReq);

            if(flag){
                VcThirdParty vcThirdParty =LoginMethod.changLoginReqToVcThirdParty(loginReq);
                ComponentUtil.loginService.signOut(vcThirdParty);
            }

            return JsonResult.successResult(null);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failedResult("wrong for data!",1+"");
        }
    }
}
