package com.pf.play.rule.core.controller;

import com.pf.play.common.utils.JsonResult;
import com.pf.play.model.protocol.request.CommonReq;
import com.pf.play.model.protocol.request.uesr.RegisterReq;
import com.pf.play.model.protocol.response.uesr.RegisterResp;
import com.pf.play.model.protocol.response.uesr.UserInfoResp;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.Constant;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.util.ComponentUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public JsonResult<Object> register(HttpServletRequest request, HttpServletResponse response, CommonReq commonReq){
        try{
            log.info("----------:进来啦!");
            JsonResult.successResult(null);

            RegisterReq registerReq1  = new RegisterReq();
            registerReq1.setDeviceId("kjasdqlkwqejjwqlJ");
            registerReq1.setVersion("1.1.2");
            registerReq1.setSmsVerification("813063");
            registerReq1.setPhone("13606706341");
            registerReq1.setWxName("小飞龙");
            registerReq1.setMemberAdd("http://192.168.1.1/asdasd");
            registerReq1.setOwner(0);
            registerReq1.setMemberType(1);
            registerReq1.setRegisterType(2);
            registerReq1.setWxRefresh("Hsakshdeq");
            registerReq1.setWxUnionid("Kkshajdhhsa");
            registerReq1.setWxOpenid("slllsdjdjsa");
            registerReq1.setInviteCode("Sjadgsade");
            registerReq1.setTimeStamp("1574162081");
            UserInfoResp userInfoResp = ComponentUtil.registerService.savaRegisterInfo(registerReq1);
            return JsonResult.successResult(userInfoResp);


        }catch (Exception e){
            e.printStackTrace();
            String code = "";
            String message = "";
            if (e instanceof ServiceException){
                if (!StringUtils.isBlank(((ServiceException) e).getCode())){
                    code = ((ServiceException) e).getCode();
                    message = e.getMessage();
                }else {
                    code = ErrorCode.ERROR_CONSTANT.DEFAULT_SERVICE_EXCEPTION_ERROR_CODE;
                }
            }else {
                code = ErrorCode.ERROR_CONSTANT.DEFAULT_EXCEPTION_ERROR_CODE;
                message = ErrorCode.ERROR_CONSTANT.DEFAULT_EXCEPTION_ERROR_MESSAGE;
            }
            return JsonResult.failedResult(message,code);
        }
    }




    @GetMapping("/getPhoneVerification")
    public JsonResult<Object> getPhoneVerification(HttpServletRequest request, HttpServletResponse response, CommonReq  commonReq){
        JsonResult<Object>     result  = null;
        try{
            log.info("----------:进来啦!");
            JsonResult.successResult(null);

            RegisterResp registerResp = ComponentUtil.registerService.getSmsVerification("13606706341");
            return JsonResult.successResult(registerResp);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failedResult("wrong for data!",1+"");
        }
    }

    @GetMapping("/test")
    public JsonResult<Object> test(HttpServletRequest request, HttpServletResponse response, RegisterReq registerReq){
        JsonResult<Object>     result  = null;
        try{
            log.info("----------:进来啦!");
            JsonResult.successResult(null);


            ComponentUtil.generateService.getNonexistentInformation(Constant.TOKEN);
            return JsonResult.successResult(null);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failedResult("wrong for data!",1+"");
        }
    }






}
