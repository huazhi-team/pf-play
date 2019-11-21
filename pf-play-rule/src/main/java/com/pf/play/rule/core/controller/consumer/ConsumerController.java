package com.pf.play.rule.core.controller.consumer;

import com.alibaba.fastjson.JSON;
import com.pf.play.common.utils.JsonResult;
import com.pf.play.common.utils.SignUtil;
import com.pf.play.common.utils.StringUtil;
import com.pf.play.model.protocol.request.RequestEncryptionJson;
import com.pf.play.model.protocol.request.consumer.RequestConsumer;
import com.pf.play.model.protocol.response.ResponseEncryptionJson;
import com.pf.play.rule.PublicMethod;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.*;
import com.pf.play.rule.core.model.UserInfoModel;
import com.pf.play.rule.util.ComponentUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2019/11/21 10:00
 * @Version 1.0
 */
@RestController
@RequestMapping("/play/csm")
public class ConsumerController {
    private static Logger log = LoggerFactory.getLogger(ConsumerController.class);

    /**
     * 15分钟.
     */
    public long FIFTEEN_MIN = 900;

    /**
     * 30分钟.
     */
    public long THIRTY_MIN = 30;

    @Value("${secret.key.token}")
    private String secretKeyToken;

    @Value("${secret.key.sign}")
    private String secretKeySign;


    /**
     * @Description: 用户更新设置支付密码
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/7 16:58
     * local:http://localhost:8082/play/csm/upPayCode
     * 请求的属性类:RequestConsumer
     * 必填字段:{"payPw":"15967171415","upPayCode":123456,"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:payPw+upPayCode+ctime+cctime+秘钥=sign
     * 服务端加密字段:stime+memberId+token+秘钥=sign
     */
    @RequestMapping(value = "/upPayCode", method = {RequestMethod.POST})
    public JsonResult<Object> upPayCode(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        try{
            String tempToken = "111111";
            ComponentUtil.redisService.set(tempToken, "3");
            log.info("jsonData:" + requestData.jsonData);
            //解密
            String data = StringUtil.decoderBase64(requestData.jsonData);
            RequestConsumer requestConsumer  = JSON.parseObject(data, RequestConsumer.class);
            if (requestConsumer == null ){
                throw new ServiceException(PfErrorCode.ENUM_ERROR.C00001.geteCode(), PfErrorCode.ENUM_ERROR.C00001.geteDesc());
            }
            if (StringUtils.isBlank(requestConsumer.getToken())){
                throw new ServiceException(PfErrorCode.ENUM_ERROR.C00002.geteCode(), PfErrorCode.ENUM_ERROR.C00002.geteDesc());
            }
            if (StringUtils.isBlank(requestConsumer.getPayPw())){
                throw new ServiceException(PfErrorCode.ENUM_ERROR.C00003.geteCode(), PfErrorCode.ENUM_ERROR.C00003.geteDesc());
            }
            if (requestConsumer.getUpPayCode() == null || requestConsumer.getUpPayCode() == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
                throw new ServiceException(PfErrorCode.ENUM_ERROR.C00004.geteCode(), PfErrorCode.ENUM_ERROR.C00004.geteDesc());
            }

            // 校验是否登录
            String loginToken = requestConsumer.getToken();
            String strCache = (String)ComponentUtil.redisService.get(loginToken);
            long memberId;
            if (!StringUtils.isBlank(strCache)) {
                // 登录存储在缓存中的did
                memberId = Long.parseLong(strCache);
            }else {
                throw new ServiceException(PfErrorCode.ENUM_ERROR.C00000.geteCode(), PfErrorCode.ENUM_ERROR.C00000.geteDesc());
            }
            // 校验短信验证码是否正确
            String strKeyCache_memberId = CachedKeyUtils.getPfCacheKey(PfCacheKey.MEMBERID_UP_PAY_CODE, memberId);
            String strCache_memberId = (String) ComponentUtil.redisService.get(strKeyCache_memberId);
            strCache_memberId = "123456";
            if (!StringUtils.isBlank(strCache_memberId)){
                int upPayCode = Integer.parseInt(strCache_memberId);
                if (upPayCode != requestConsumer.getUpPayCode()){
                    throw new ServiceException(PfErrorCode.ENUM_ERROR.C00005.geteCode(), PfErrorCode.ENUM_ERROR.C00005.geteDesc());
                }
            }else {
                throw new ServiceException(PfErrorCode.ENUM_ERROR.C00006.geteCode(), PfErrorCode.ENUM_ERROR.C00006.geteDesc());
            }
            // 校验ctime
            // 校验sign

            // 修改支付密码
            UserInfoModel userInfoModel = PublicMethod.assembleUserInfo(memberId, requestConsumer.getPayPw());
            ComponentUtil.userInfoSevrice.updatePayPassword(userInfoModel);


            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
//            String tokon = SignUtil.getSgin(memberId, stime, secretKeySign); // 用户did+用户账号+秘钥=token
            String tokon = requestConsumer.getToken(); // 用户token
            String sign = SignUtil.getSgin(stime, tokon, secretKeySign); //服务器时间+token+秘钥=sign
            String strData = PublicMethod.assembleUpPayCodeResult(stime, tokon, sign);
            // #插入流水
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 用户注册完毕则直接让用户处于登录状态
            ComponentUtil.redisService.set(tokon, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
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
            log.error(String.format("this InitController.getData() is error , the errorCode=%s and cgid=%s and sgid=%s and all data=%s!", code, "null", "null", request.getQueryString()));
            // 记录错误日志
            int mailRemind = ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO;
            if (code.equals(ErrorCode.ERROR_CONSTANT.DEFAULT_EXCEPTION_ERROR_CODE)){
                //是属于系统错误，需要发送邮件提醒
                mailRemind = ServerConstant.PUBLIC_CONSTANT.MAIL_REMIND_YES;
            }else{
                code = ErrorCode.ERROR_CONSTANT.DEFAULT_SERVICE_EXCEPTION_ERROR_CODE;
            }
            e.printStackTrace();
            // 添加错误异常数据
            return JsonResult.failedResult(message, code, cgid, sgid);
        }
    }

}
