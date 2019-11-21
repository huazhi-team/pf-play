package com.pf.play.rule.core.controller;

import com.pf.play.common.utils.JsonResult;
import com.pf.play.model.protocol.request.CommonReq;
import com.pf.play.model.protocol.request.uesr.LoginReq;
import com.pf.play.model.protocol.response.uesr.UserInfoResp;
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
    public JsonResult<Object> getUserInfo(HttpServletRequest request, HttpServletResponse response, CommonReq  commonReq){
        JsonResult<Object>     result  = null;
        try{
            log.info("----------:进来啦!");
            JsonResult.successResult(null);
            LoginReq loginReq1 = new LoginReq ();
            loginReq1.setLoginType(2);
            loginReq1.setWxOpenId("slllsdjdjsa");
            loginReq1.setPhone("asdakj");

            UserInfoResp userInfoResp= ComponentUtil.loginService.login(loginReq1);
            if(userInfoResp==null){
                return JsonResult.failedResult("wrong for data!",1+"");
            }
            return JsonResult.successResult(userInfoResp);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failedResult("wrong for data!",1+"");
        }
    }
}
