package com.pf.play.rule.core.controller;

import com.pf.play.common.utils.JsonResult;
import com.pf.play.model.protocol.request.uesr.QhrReq;
import com.pf.play.model.protocol.request.uesr.UserCommonReq;
import com.pf.play.model.protocol.response.synchr.MemberResp;
import com.pf.play.rule.SynchroMethod;
import com.pf.play.rule.TaskMethod;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.core.model.VcMember;
import com.pf.play.rule.core.model.VcMemberResource;
import com.pf.play.rule.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/12/9 16:59
 * @Version 1.0
 */
@RestController
@RequestMapping("/play/synchr")
public class SynchronousController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/clickFabulous")
    public JsonResult<Object> getUserInfo(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq){
        try{
            log.info("----------:clickFabulous!");
            boolean   cheakFlag  = TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if (!cheakFlag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }
            Integer   memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenId());
            if(memberId!=0){
                ComponentUtil.synchroService.addGive(memberId);
            }
            return JsonResult.successResult(null);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failedResult("wrong for data!",1+"");
        }
    }

    @PostMapping("/goods")
    public JsonResult<Object> goods(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq){
        try{
            log.info("----------:goods!");
            boolean   cheakFlag  = TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if (!cheakFlag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }
            Integer   memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenId());
            if(memberId!=0){
                ComponentUtil.synchroService.addGoods(memberId);
            }
            return JsonResult.successResult(null);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failedResult("wrong for data!",1+"");
        }
    }

    @PostMapping("/getMemberInfo")
    public JsonResult<Object> goods(HttpServletRequest request, HttpServletResponse response, QhrReq qhrReq){
        try{
            log.info("----------:getMemberInfo!");
            boolean   cheakFlag  = SynchroMethod.cheakIsMemberId(qhrReq.getMemberId());
            if (!cheakFlag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }
            
            VcMemberResource vcMemberResource  =TaskMethod.changvcMemberResource(Integer.parseInt(qhrReq.getMemberId()));
            VcMemberResource  vr=ComponentUtil.synchroService.queryVcMemberResource(vcMemberResource);
            MemberResp memberResp=SynchroMethod.ChangToMemberResp(vr);
            return JsonResult.successResult(memberResp);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failedResult("wrong for data!",1+"");
        }
    }

}