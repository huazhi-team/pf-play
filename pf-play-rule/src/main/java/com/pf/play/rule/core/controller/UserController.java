package com.pf.play.rule.core.controller;

import com.pf.play.common.utils.JsonResult;
import com.pf.play.model.protocol.request.CommonReq;
import com.pf.play.model.protocol.request.uesr.LoginReq;
import com.pf.play.rule.core.model.UMasonryListLog;
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

    @GetMapping("/masonryInfo")
    public JsonResult<Object> getPhoneVerification(HttpServletRequest request, HttpServletResponse response, CommonReq  commonReq){
        JsonResult<Object>     result  = null;
        try{
            log.info("----------:masonryInfo!");
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
