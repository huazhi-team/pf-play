package com.pf.play.rule.core.controller;

import com.alibaba.fastjson.JSON;
import com.pf.play.common.utils.JsonResult;
import com.pf.play.common.utils.StringUtil;
import com.pf.play.model.protocol.request.give.SendGiftResp;
import com.pf.play.model.protocol.request.uesr.QhrReq;
import com.pf.play.model.protocol.request.uesr.UserCommonReq;
import com.pf.play.model.protocol.response.synchr.MemberResp;
import com.pf.play.model.protocol.response.task.ExeReceiveTaskResp;
import com.pf.play.rule.PublicMethod;
import com.pf.play.rule.SynchroMethod;
import com.pf.play.rule.TaskMethod;
import com.pf.play.rule.core.common.exception.ExceptionMethod;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.Constant;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.core.common.utils.constant.ServerConstant;
import com.pf.play.rule.core.model.VcMember;
import com.pf.play.rule.core.model.VcMemberResource;
import com.pf.play.rule.core.model.region.RegionModel;
import com.pf.play.rule.core.model.stream.StreamConsumerModel;
import com.pf.play.rule.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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

    /**
     * @Description: 用户用户点赞统计
     * @param request
    * @param response
    * @param userCommonReq
     * @return com.pf.play.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2019/12/26 10:03
     */
    @PostMapping("/clickFabulous")
    public JsonResult<Object> getUserInfo(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq)throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);

        try{
            log.info("----------:clickFabulous!");
            memberId   = userCommonReq.getMemberId();
            if(memberId!=0){
                ComponentUtil.synchroService.addGive(memberId);
            }
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.SYNCHR_FABULOUS.getType(),
                    ServerConstant.InterfaceEnum.SYNCHR_FABULOUS.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(null), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);

            return JsonResult.successResult(null);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.SYNCHR_FABULOUS.getType(),
                    ServerConstant.InterfaceEnum.SYNCHR_FABULOUS.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(null), map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }

    /**
     * @Description: 商品同步
     * @param request
    * @param response
    * @param userCommonReq
     * @return com.pf.play.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2019/12/17 9:57
     */
    @PostMapping("/goods")
    public JsonResult<Object> goods(HttpServletRequest request, HttpServletResponse response, UserCommonReq userCommonReq)throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);

        try{
            log.info("----------:goods!");
            boolean   cheakFlag  = TaskMethod.checkTokenAndWxOpenid(userCommonReq);
            if (!cheakFlag){
                return JsonResult.successResult(null);
            }
            memberId   = ComponentUtil.userMasonryService.queryTokenMemberId(userCommonReq.getToken(), userCommonReq.getWxOpenId());
            if(memberId==0){
                return JsonResult.successResult(null);
            }
            if(memberId!=0){
                ComponentUtil.synchroService.addGoods(memberId);
            }
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.SYNCHR_GOODS.getType(),
                    ServerConstant.InterfaceEnum.SYNCHR_GOODS.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(null), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);

            return JsonResult.successResult(null);
        }catch (Exception e){
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, userCommonReq, ServerConstant.InterfaceEnum.SYNCHR_GOODS.getType(),
                    ServerConstant.InterfaceEnum.SYNCHR_GOODS.getDesc(), null, JSON.toJSONString(userCommonReq), JSON.toJSONString(null), map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }

    /**
     * @Description: memberId 查看商品信息
     * @param request
    * @param response
    * @param qhrReq
     * @return com.pf.play.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2019/12/24 16:03
     */
    @PostMapping("/getMemberInfo")
    public JsonResult<Object> goods(HttpServletRequest request, HttpServletResponse response, QhrReq qhrReq)throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        try{
            log.info("----------:getMemberInfo!");
            boolean   cheakFlag  = SynchroMethod.cheakIsMemberId(qhrReq.getMemberId());
            if (!cheakFlag){
                throw  new ServiceException(ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteCode(),ErrorCode.ENUM_ERROR.PARAMETER_ERROR.geteDesc());
            }
            
            VcMemberResource vcMemberResource  =TaskMethod.changvcMemberResource(Integer.parseInt(qhrReq.getMemberId()));
            VcMemberResource  vr=ComponentUtil.synchroService.queryVcMemberResource(vcMemberResource);
            MemberResp memberResp=SynchroMethod.ChangToMemberResp(vr);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, qhrReq, ServerConstant.InterfaceEnum.SYNCHR_GOODS.getType(),
                    ServerConstant.InterfaceEnum.SYNCHR_GOODS.getDesc(), null, JSON.toJSONString(qhrReq), JSON.toJSONString(memberResp), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);

            return JsonResult.successResult(memberResp);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, qhrReq, ServerConstant.InterfaceEnum.SYNCHR_MEMBERINFO.getType(),
                    ServerConstant.InterfaceEnum.SYNCHR_MEMBERINFO.getDesc(), null, JSON.toJSONString(qhrReq), JSON.toJSONString(null), map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }

    /**
     * @Description: 用户打赏给其他用户
     * @param request
    * @param response
    * @param sendGiftResp
     * @return com.pf.play.common.utils.JsonResult<java.lang.Object>
     * @author long
     * @date 2019/12/24 16:04
     */
    @PostMapping("/sendGifts")
    public JsonResult<Object> sendGifts(HttpServletRequest request, HttpServletResponse response, SendGiftResp sendGiftResp)throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        Integer memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        try{
            log.info("----------:sendGifts!");
            int   flag1 =  ComponentUtil.synchroService.checkSendInfo(sendGiftResp.getSendMemberId(),
                                                    sendGiftResp.getReceiptMemberId(),sendGiftResp.getPayPw());
            if(flag1==-1){
                return JsonResult.failedResult(ErrorCode.ENUM_ERROR.SYNCHRONOUS1.geteCode(),ErrorCode.ENUM_ERROR.SYNCHRONOUS1.geteDesc());
            }

            if(flag1!=0){
                return JsonResult.failedResult(ErrorCode.ENUM_ERROR.SYNCHRONOUS2.geteCode(),ErrorCode.ENUM_ERROR.SYNCHRONOUS2.geteDesc());
            }

            Boolean flag = ComponentUtil.synchroService.chechMemberResource(sendGiftResp.getSendMemberId(),sendGiftResp.getMasonryCount());
            if(!flag){
                return JsonResult.failedResult(ErrorCode.ENUM_ERROR.SYNCHRONOUS3.geteCode(),ErrorCode.ENUM_ERROR.SYNCHRONOUS3.geteDesc());
            }

            int  count = ComponentUtil.synchroService.addMemberResource(sendGiftResp.getSendMemberId(),
                    sendGiftResp.getReceiptMemberId(),sendGiftResp.getMasonryCount());

            if(count==0){
                return JsonResult.failedResult(ErrorCode.ENUM_ERROR.SYNCHRONOUS4.geteCode(),ErrorCode.ENUM_ERROR.SYNCHRONOUS4.geteDesc());
            }

            ExeReceiveTaskResp receiveTaskResp = TaskMethod.toExeReceiveTaskResp(true);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, sendGiftResp, ServerConstant.InterfaceEnum.SYNCHR_GIFTS.getType(),
                    ServerConstant.InterfaceEnum.SYNCHR_GIFTS.getDesc(), null, JSON.toJSONString(sendGiftResp), JSON.toJSONString(true), null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            return JsonResult.successResult(receiveTaskResp);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> map= ExceptionMethod.getException(e, Constant.CODE_ERROR_TYPE1);
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, sendGiftResp, ServerConstant.InterfaceEnum.SYNCHR_GIFTS.getType(),
                    ServerConstant.InterfaceEnum.SYNCHR_GIFTS.getDesc(), null, JSON.toJSONString(sendGiftResp), JSON.toJSONString(null), map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            return JsonResult.failedResult(map.get("message"),map.get("code"));
        }
    }
}
