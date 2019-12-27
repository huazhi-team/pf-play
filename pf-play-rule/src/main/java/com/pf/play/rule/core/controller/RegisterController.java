package com.pf.play.rule.core.controller;

import com.alibaba.fastjson.JSON;
import com.pf.play.common.utils.JsonResult;
import com.pf.play.common.utils.StringUtil;
import com.pf.play.model.protocol.request.register.PhoneRegister;
import com.pf.play.model.protocol.request.uesr.PhoneVerificationReq;
import com.pf.play.model.protocol.request.uesr.RegisterReq;
import com.pf.play.model.protocol.response.register.PhoneRegisterResp;
import com.pf.play.model.protocol.response.uesr.LoginResp;
import com.pf.play.model.protocol.response.uesr.RegisterResp;
import com.pf.play.model.protocol.response.uesr.UserInfoResp;
import com.pf.play.rule.LoginMethod;
import com.pf.play.rule.PublicMethod;
import com.pf.play.rule.RegisterMethod;
import com.pf.play.rule.TaskMethod;
import com.pf.play.rule.core.common.exception.ExceptionMethod;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.Constant;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.core.common.utils.constant.ServerConstant;
import com.pf.play.rule.core.model.VcMember;
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
 * @Description 注册相关的
 * @Author long
 * @Date 2019/11/8 11:24
 * @Version 1.0
 */
@RestController
@RequestMapping("/play/register")
public class RegisterController {

    private static Logger log = LoggerFactory.getLogger(RegisterController.class);

    @PostMapping("/userInfo")
    public JsonResult<Object> register(HttpServletRequest request, HttpServletResponse response, RegisterReq registerReq )throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);

        try{
            log.info("----------:进来啦!");
            boolean  flag  = TaskMethod.checkRegisterParameter(registerReq);
            if(!flag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }


            String  verKey =registerReq.getPhone()+registerReq.getTimeStamp();
            String  smsVerification = (String) ComponentUtil.redisService.get(verKey);
            if(smsVerification==null){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.R000007.geteCode(),ErrorCode.ENUM_ERROR.R000007.geteDesc());
            }
            if(!smsVerification.equals(registerReq.getSmsVerification())){ //验证码错误！
                throw  new ServiceException(ErrorCode.ENUM_ERROR.R000006.geteCode(),ErrorCode.ENUM_ERROR.R000006.geteDesc());
            }

            UserInfoResp userInfoResp = ComponentUtil.registerService.savaRegisterInfo(registerReq);
            LoginResp loginResp  = LoginMethod.changLoginResp(userInfoResp);
            ComponentUtil.redisService.set(userInfoResp.getToken(),userInfoResp.getMemberId()+"");

            ComponentUtil.userInfoSevrice.userSynchronousQhr(userInfoResp.getMemberId(),userInfoResp.getToken());


            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, registerReq, ServerConstant.InterfaceEnum.REGISTER_USERINFO.getType(),
                    ServerConstant.InterfaceEnum.REGISTER_USERINFO.getDesc(), null, JSON.toJSONString(registerReq), JSON.toJSONString(loginResp), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);

            return JsonResult.successResult(loginResp);


        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, registerReq, ServerConstant.InterfaceEnum.REGISTER_USERINFO.getType(),
                    ServerConstant.InterfaceEnum.REGISTER_USERINFO.getDesc(), null, JSON.toJSONString(registerReq), JSON.toJSONString(null), map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);

            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }



    @PostMapping("/phoneRegister")
    public JsonResult<Object> phoneRegister(HttpServletRequest request, HttpServletResponse response, PhoneRegister phoneRegister )throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        try{
            log.info("----------:phoneRegister");
            boolean  chechFlag = RegisterMethod.cheackPhoneDeployOk(phoneRegister);
            if(!chechFlag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }

            String  key =phoneRegister.getPhone()+phoneRegister.getTimeStamp();
            String  smsVerification = (String) ComponentUtil.redisService.get(key);

            if(smsVerification==null){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.R000007.geteCode(),ErrorCode.ENUM_ERROR.R000007.geteDesc());
            }

            if(!smsVerification.equals(phoneRegister.getSmsVerification())){ //验证码错误！
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }

            boolean  flag = ComponentUtil.registerService.isPhoneExist(phoneRegister.getPhone());
            if(!flag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.R000004.geteCode(),ErrorCode.ENUM_ERROR.R000004.geteDesc());
            }
            VcMember vcMember = ComponentUtil.registerService.checkInviteCode(phoneRegister.getInviteCode());
            if(vcMember==null){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.R000002.geteCode(),ErrorCode.ENUM_ERROR.R000002.geteDesc());
            }

            ComponentUtil.registerService.addRegisterPhoneDisplay(phoneRegister.getPhone(),phoneRegister.getInviteCode());
//            UserInfoResp userInfoResp = ComponentUtil.registerService.savaRegisterInfo(registerReq);
//            LoginResp loginResp  = LoginMethod.changLoginResp(userInfoResp);
            PhoneRegisterResp registerResp = new PhoneRegisterResp();
            registerResp.setState(1);

            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, phoneRegister, ServerConstant.InterfaceEnum.REGISTER_PHONE.getType(),
                    ServerConstant.InterfaceEnum.REGISTER_PHONE.getDesc(), null, JSON.toJSONString(phoneRegister), JSON.toJSONString(registerResp), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);

            return JsonResult.successResult(registerResp);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, phoneRegister, ServerConstant.InterfaceEnum.REGISTER_PHONE.getType(),
                    ServerConstant.InterfaceEnum.REGISTER_PHONE.getDesc(), null, JSON.toJSONString(phoneRegister), JSON.toJSONString(null), map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);

            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }



    @PostMapping("/getPhoneVerification")
    public JsonResult<Object> getPhoneVerification(HttpServletRequest request, HttpServletResponse response, PhoneVerificationReq req)throws Exception{
        JsonResult<Object>     result  = null;
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        try{
            log.info("----------:进来啦!");
            boolean  flag  = TaskMethod.checkPhoneVerification(req);
            if(!flag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }

            flag = ComponentUtil.registerService.isPhoneExist(req.getPhone());
            if(!flag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.R000005.geteCode(),ErrorCode.ENUM_ERROR.R000005.geteDesc());
            }
            RegisterResp registerResp = ComponentUtil.registerService.getSmsVerification(req.getPhone());

            if(registerResp==null){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.R000004.geteCode(),ErrorCode.ENUM_ERROR.R000004.geteDesc());
            }

            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, req, ServerConstant.InterfaceEnum.REGISTER_PHONE_VERIFICATION.getType(),
                    ServerConstant.InterfaceEnum.REGISTER_PHONE_VERIFICATION.getDesc(), null, JSON.toJSONString(req), JSON.toJSONString(registerResp), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(registerResp);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, req, ServerConstant.InterfaceEnum.REGISTER_PHONE_VERIFICATION.getType(),
                    ServerConstant.InterfaceEnum.REGISTER_PHONE_VERIFICATION.getDesc(), null, JSON.toJSONString(req), JSON.toJSONString(null), map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }

    @GetMapping("/test")
    public JsonResult<Object> test(HttpServletRequest request, HttpServletResponse response, PhoneRegister phoneRegister){
        JsonResult<Object>     result  = null;
        try{

            return JsonResult.successResult(null);
        }catch (Exception e){
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }






}
