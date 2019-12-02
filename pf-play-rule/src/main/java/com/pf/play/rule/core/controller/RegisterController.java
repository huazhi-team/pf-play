package com.pf.play.rule.core.controller;

import com.pf.play.common.utils.JsonResult;
import com.pf.play.model.protocol.request.uesr.PhoneVerificationReq;
import com.pf.play.model.protocol.request.uesr.RegisterReq;
import com.pf.play.model.protocol.response.uesr.LoginResp;
import com.pf.play.model.protocol.response.uesr.RegisterResp;
import com.pf.play.model.protocol.response.uesr.UserInfoResp;
import com.pf.play.rule.LoginMethod;
import com.pf.play.rule.TaskMethod;
import com.pf.play.rule.core.common.exception.ExceptionMethod;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.Constant;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Description 注册相关的
 * @Author long
 * @Date 2019/11/8 11:24
 * @Version 1.0
 */
@RestController
@RequestMapping("/register")
public class RegisterController {

    private static Logger log = LoggerFactory.getLogger(RegisterController.class);

    @GetMapping("/userInfo")
    public JsonResult<Object> register(HttpServletRequest request, HttpServletResponse response, RegisterReq registerReq ){
        try{
            log.info("----------:进来啦!");
            JsonResult.successResult(null);

            RegisterReq registerReq1  = new RegisterReq();
            boolean  flag  = TaskMethod.checkRegisterParameter(registerReq);
            if(!flag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }
//            registerReq1.setDeviceId("kjasdqlkwqejjwqlJ2");
//            registerReq1.setVersion("1.1.2");
//            registerReq1.setSmsVerification("775326");
//            registerReq1.setPhone("13606706366");
//            registerReq1.setWxName("小飞龙");
//            registerReq1.setMemberAdd("http://192.168.1.1/asdasd");
//            registerReq1.setOwner(0);
//            registerReq1.setMemberType(1);
//            registerReq1.setRegisterType(2);
//            registerReq1.setWxRefresh("Hsakshdeq1");
//            registerReq1.setWxUnionid("Kkshajdhhsa3");
//            registerReq1.setWxOpenid("slllsdjdjsa2");
//            registerReq1.setInviteCode("Sjadgsade");
//            registerReq1.setTimeStamp("1574651926");
            UserInfoResp userInfoResp = ComponentUtil.registerService.savaRegisterInfo(registerReq1);
            LoginResp loginResp  = LoginMethod.changLoginResp(userInfoResp);
            return JsonResult.successResult(loginResp);


        }catch (Exception e){
            Map<String,String> map=ExceptionMethod.getException(e);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }




    @GetMapping("/getPhoneVerification")
    public JsonResult<Object> getPhoneVerification(HttpServletRequest request, HttpServletResponse response, PhoneVerificationReq req){
        JsonResult<Object>     result  = null;
        try{
            log.info("----------:进来啦!");
            boolean  flag  = TaskMethod.checkPhoneVerification(req);
            if(!flag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }
            RegisterResp registerResp = ComponentUtil.registerService.getSmsVerification(req.getPhone());
            return JsonResult.successResult(registerResp);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map=ExceptionMethod.getException(e);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }

    @GetMapping("/test")
    public JsonResult<Object> test(HttpServletRequest request, HttpServletResponse response, RegisterReq registerReq){
        JsonResult<Object>     result  = null;
        try{
            log.info("----------:进来啦!");
            ComponentUtil.generateService.getNonexistentInformation(Constant.TOKEN);
            return JsonResult.successResult(null);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failedResult("wrong for data!",1+"");
        }
    }






}
