package com.pf.play.rule.core.controller.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pf.play.common.utils.JsonResult;
import com.pf.play.common.utils.SignUtil;
import com.pf.play.common.utils.StringUtil;
import com.pf.play.model.protocol.request.RequestEncryptionJson;
import com.pf.play.model.protocol.request.consumer.RequestConsumer;
import com.pf.play.model.protocol.response.ResponseEncryptionJson;
import com.pf.play.rule.PublicMethod;
import com.pf.play.rule.core.common.exception.ExceptionMethod;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.*;
import com.pf.play.rule.core.model.UserInfoModel;
import com.pf.play.rule.core.model.consumer.ConsumerFixedModel;
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
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description 用户固定账号、支付密码的Controller层
 * @Author yoko
 * @Date 2019/11/21 10:00
 * @Version 1.0
 */
@RestController
@RequestMapping("/play/csm")
public class ConsumerController {
    private static Logger log = LoggerFactory.getLogger(ConsumerController.class);

    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

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
     * 客户端加密字段:payPw+upPayCode+ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     */
    @RequestMapping(value = "/upPayCode", method = {RequestMethod.POST})
    public JsonResult<Object> upPayCode(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        try{
            String tempToken = "111111";
            ComponentUtil.redisService.set(tempToken, "3");
            log.info("jsonData:" + requestData.jsonData);
            //解密
            String data = StringUtil.decoderBase64(requestData.jsonData);
            RequestConsumer requestConsumer  = JSON.parseObject(data, RequestConsumer.class);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkUpPayCodeData(requestConsumer);
            token = requestConsumer.getToken();

            // 校验短信验证码是否正确
            String strKeyCache = CachedKeyUtils.getPfCacheKey(PfCacheKey.MEMBERID_UP_PAY_CODE, memberId);
            PublicMethod.checkVerifCode(strKeyCache, requestConsumer.getUpPayCode());
            // 校验ctime
            // 校验sign

            // 修改支付密码
            UserInfoModel userInfoModel = PublicMethod.assembleUserInfo(memberId, requestConsumer.getPayPw());
            ComponentUtil.userInfoSevrice.updatePayPassword(userInfoModel);


            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
//            String tokon = SignUtil.getSgin(memberId, stime, secretKeySign); // 用户id+服务器时间+秘钥=token
            String sign = SignUtil.getSgin(stime, token, secretKeySign); //服务器时间+token+秘钥=sign
            String strData = PublicMethod.assembleResult(stime, token, sign);
            // #插入流水
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 让用户处于登录状态
            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e);
            // 添加错误异常数据
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }




    /**
     * @Description: 查询用户固定账号/支付宝信息
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/7 16:58
     * local:http://localhost:8082/play/csm/getData
     * 请求的属性类:RequestConsumer
     * 必填字段:{"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段（用户固定账号不为空）:fixedType+fixedNum+stime+token+秘钥=sign
     * 服务端加密字段（用户固定账号为空）:stime+token+秘钥=sign
     */
    @RequestMapping(value = "/getData", method = {RequestMethod.POST})
    public JsonResult<Object> getFixed(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        try{
            String tempToken = "111111";
            ComponentUtil.redisService.set(tempToken, "3");
            log.info("jsonData:" + requestData.jsonData);
            // 解密
            String data = StringUtil.decoderBase64(requestData.jsonData);
            RequestConsumer requestConsumer  = JSON.parseObject(data, RequestConsumer.class);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkGetFixedData(requestConsumer);
            token = requestConsumer.getToken();
            // 校验ctime
            // 校验sign

            // 查询用户固定数据
            ConsumerFixedModel consumerFixedQuery = PublicMethod.assembleConsumerFixedQuery(memberId);
            ConsumerFixedModel consumerFixedModel = ComponentUtil.consumerFixedService.getConsumerFixed(consumerFixedQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);

            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
//            String tokon = SignUtil.getSgin(memberId, stime, secretKeySign); // 用户did+用户账号+秘钥=token
            String sign;
            if (consumerFixedModel == null){
                sign = SignUtil.getSgin(stime, token, secretKeySign); //服务器时间+token+秘钥=sign
            }else {
                sign = SignUtil.getSgin(consumerFixedModel.getFixedType(), consumerFixedModel.getFixedNum(), stime, token, secretKeySign); //fixedType+fixedNum+服务器时间+token+秘钥=sign
            }
            String strData = PublicMethod.assembleGetFixedResult(stime, token, sign, consumerFixedModel);
            // #插入流水
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 用户注册完毕则直接让用户处于登录状态
            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e);
            // 添加错误异常数据
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }




    /**
     * @Description: 用户添加固定账号/支付宝信息
     * <p>这里间接理解用户实名认证，因为这个接口接收了用户的姓名，身份证，支付宝</p>
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/7 16:58
     * local:http://localhost:8082/play/csm/addData
     * 请求的属性类:RequestConsumer
     * 必填字段:{"fullName":"小五哥","idCard":"435202111111111111","fixedType":2,"fixedNum":13717505292,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:fullName+idCard+fixedType+fixedNum+ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     */
    @RequestMapping(value = "/addData", method = {RequestMethod.POST})
    public JsonResult<Object> addFixed(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        try{
            String tempToken = "111111";
            ComponentUtil.redisService.set(tempToken, "3");
            log.info("jsonData:" + requestData.jsonData);
            // 解密
            String data = StringUtil.decoderBase64(requestData.jsonData);
            RequestConsumer requestConsumer  = JSON.parseObject(data, RequestConsumer.class);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkAddFixedData(requestConsumer);
            token = requestConsumer.getToken();
            // 校验ctime
            // 校验sign
            // 查询用户固定数据
            ConsumerFixedModel consumerFixedQuery = PublicMethod.assembleConsumerFixedQuery(memberId);
            ConsumerFixedModel consumerFixedModel = ComponentUtil.consumerFixedService.getConsumerFixed(consumerFixedQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            if (consumerFixedModel != null){
                throw new ServiceException(PfErrorCode.ENUM_ERROR.C00012.geteCode(), PfErrorCode.ENUM_ERROR.C00012.geteDesc());
            }
            // 添加固定账号数据
            ConsumerFixedModel addDataModel = new ConsumerFixedModel();
            addDataModel.setMemberId(memberId);
            ComponentUtil.consumerFixedService.add(addDataModel);


            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
//            String tokon = SignUtil.getSgin(memberId, stime, secretKeySign); // 用户did+用户账号+秘钥=token
            String sign = SignUtil.getSgin(stime, token, secretKeySign); //服务器时间+token+秘钥=sign
            String strData = PublicMethod.assembleResult(stime, token, sign);
            // #插入流水
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 用户注册完毕则直接让用户处于登录状态
            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e);
            // 添加错误异常数据
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }




    /**
     * @Description: 更新添加固定账号/支付宝信息
     * <p>
     *     1.这里间接理解用户实名认证，因为这个接口接收了用户的姓名，身份证，支付宝
     *     2.这里已经经过实名认证，如果用户 要修改信息，则只可以修改支付宝账号
     *     3.如果除了支付宝账号，还有其它数据要进行修改，则需要重新进行实名认证
     * </p>
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/7 16:58
     * local:http://localhost:8082/play/csm/upData
     * 请求的属性类:RequestConsumer
     * 必填字段:{"fixedNum":13717505293,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:fixedNum+ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     */
    @RequestMapping(value = "/upData", method = {RequestMethod.POST})
    public JsonResult<Object> upFixed(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        try{
            String tempToken = "111111";
            ComponentUtil.redisService.set(tempToken, "3");
            log.info("jsonData:" + requestData.jsonData);
            // 解密
            String data = StringUtil.decoderBase64(requestData.jsonData);
            RequestConsumer requestConsumer  = JSON.parseObject(data, RequestConsumer.class);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkUpFixedData(requestConsumer);
            token = requestConsumer.getToken();
            // 校验ctime
            // 校验sign
            // 更新用户固定账号数据
            ConsumerFixedModel upDataModel = PublicMethod.assembleUpConsumerFixed(memberId, requestConsumer.getFixedNum());
            ComponentUtil.consumerFixedService.update(upDataModel);

            // 更新缓存里面的数据
            String strKeyCache = CachedKeyUtils.getPfCacheKey(PfCacheKey.CONSUMER_FIXED, memberId);
            String strCache = (String) ComponentUtil.redisService.get(strKeyCache);
            if (!StringUtils.isBlank(strCache)) {
                // 从缓存里面获取数据
                ConsumerFixedModel dataModel = JSON.parseObject(strCache, ConsumerFixedModel.class);
                dataModel.setFixedNum(requestConsumer.getFixedNum());
                ComponentUtil.redisService.set(strKeyCache, JSON.toJSONString(dataModel, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty), FIVE_MIN);
            }
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
//            String tokon = SignUtil.getSgin(memberId, stime, secretKeySign); // 用户did+用户账号+秘钥=token
            String sign = SignUtil.getSgin(stime, token, secretKeySign); //服务器时间+token+秘钥=sign
            String strData = PublicMethod.assembleResult(stime, token, sign);
            // #插入流水
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 用户注册完毕则直接让用户处于登录状态
            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
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
            log.error(String.format("this ConsumerController.upFixed() is error , the errorCode=%s and cgid=%s and sgid=%s and all data=%s!", code, "null", "null", request.getQueryString()));
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
