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
import com.pf.play.rule.core.model.consumer.ConsumerModel;
import com.pf.play.rule.core.model.region.RegionModel;
import com.pf.play.rule.core.model.stream.StreamConsumerModel;
import com.pf.play.rule.util.ComponentUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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
     * @Description: 用户更新设置支付密码-第一次设置密码
     * <p>
     *     此接口第一次设置支付密码，无需手机验证码
     * </p>
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/7 16:58
     * local:http://localhost:8082/play/csm/upFirstPayCode
     * 请求的属性类:RequestConsumer
     * 必填字段:{"payPw":"15967171415","agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:payPw+ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     */
    @RequestMapping(value = "/upFirstPayCode", method = {RequestMethod.POST})
//    public JsonResult<Object> upFirstPayCode(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
    public JsonResult<Object> upFirstPayCode(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        long memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        RequestConsumer requestConsumer = new RequestConsumer();
        try{
//            String tempToken = "111111";
//            ComponentUtil.redisService.set(tempToken, "3");
            //解密
            data = StringUtil.decoderBase64(jsonData);
            requestConsumer  = JSON.parseObject(data, RequestConsumer.class);
            // check校验数据、校验用户是否登录、获得用户ID
            memberId = PublicMethod.checkFirstPayCodeData(requestConsumer);
            token = requestConsumer.getToken();

            // 校验ctime
            // 校验sign

            // 修改支付密码
            UserInfoModel userInfoModel = PublicMethod.assembleUserInfo(memberId, requestConsumer.getPayPw());
            ComponentUtil.userInfoSevrice.updatePayPassword(userInfoModel);


            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); //服务器时间+token+秘钥=sign
            String strData = PublicMethod.assembleResult(stime, token, sign);
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 让用户处于登录状态
//            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
            // 添加流水
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestConsumer, ServerConstant.InterfaceEnum.CONSUMER_UPFIRSTPAYCODE.getType(),
                    ServerConstant.InterfaceEnum.CONSUMER_UPFIRSTPAYCODE.getDesc(), null, data, strData, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
            // 添加异常
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestConsumer, ServerConstant.InterfaceEnum.CONSUMER_UPFIRSTPAYCODE.getType(),
                    ServerConstant.InterfaceEnum.CONSUMER_UPFIRSTPAYCODE.getDesc(), null, data, null, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            log.error(String.format("this ConsumerController.upFirstPayCode() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }


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
//    public JsonResult<Object> upPayCode(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
    public JsonResult<Object> upPayCode(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        long memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        RequestConsumer requestConsumer = new RequestConsumer();
        try{
//            String tempToken = "111111";
//            ComponentUtil.redisService.set(tempToken, "3");
            //解密
            data = StringUtil.decoderBase64(jsonData);
            requestConsumer  = JSON.parseObject(data, RequestConsumer.class);
            // check校验数据、校验用户是否登录、获得用户ID
            memberId = PublicMethod.checkUpPayCodeData(requestConsumer);
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
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 让用户处于登录状态
//            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
            // 添加流水
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestConsumer, ServerConstant.InterfaceEnum.CONSUMER_UPPAYCODE.getType(),
                    ServerConstant.InterfaceEnum.CONSUMER_UPPAYCODE.getDesc(), null, data, strData, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
            // 添加异常
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestConsumer, ServerConstant.InterfaceEnum.CONSUMER_UPPAYCODE.getType(),
                    ServerConstant.InterfaceEnum.CONSUMER_UPPAYCODE.getDesc(), null, data, null, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            log.error(String.format("this ConsumerController.upPayCode() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
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
     * local:http://localhost:8082/play/csm/getFixed
     * 请求的属性类:RequestConsumer
     * 必填字段:{"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段（用户固定账号不为空）:fixedType+fixedNum+stime+token+秘钥=sign
     * 服务端加密字段（用户固定账号为空）:stime+token+秘钥=sign
     */
    @RequestMapping(value = "/getFixed", method = {RequestMethod.POST})
//    public JsonResult<Object> getFixed(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
    public JsonResult<Object> getFixed(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        long memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        RequestConsumer requestConsumer = new RequestConsumer();
        try{
//            String tempToken = "111111";
//            ComponentUtil.redisService.set(tempToken, "3");
            // 解密
            data = StringUtil.decoderBase64(jsonData);
            requestConsumer  = JSON.parseObject(data, RequestConsumer.class);
            // check校验数据、校验用户是否登录、获得用户ID
            memberId = PublicMethod.checkGetFixedData(requestConsumer);
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
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 用户注册完毕则直接让用户处于登录状态
//            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
            // 添加流水
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestConsumer, ServerConstant.InterfaceEnum.CONSUMER_GETFIXED.getType(),
                    ServerConstant.InterfaceEnum.CONSUMER_GETFIXED.getDesc(), null, data, strData, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
            // 添加异常
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestConsumer, ServerConstant.InterfaceEnum.CONSUMER_GETFIXED.getType(),
                    ServerConstant.InterfaceEnum.CONSUMER_GETFIXED.getDesc(), null, data, null, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            log.error(String.format("this ConsumerController.getFixed() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
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
     * local:http://localhost:8082/play/csm/addFixed
     * 请求的属性类:RequestConsumer
     * 必填字段:{"fullName":"小五哥","idCard":"435202111111111111","fixedType":2,"fixedNum":13717505292,"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:fullName+idCard+fixedType+fixedNum+ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     */
    @RequestMapping(value = "/addFixed", method = {RequestMethod.POST})
//    public JsonResult<Object> addFixed(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
    public JsonResult<Object> addFixed(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        long memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        RequestConsumer requestConsumer = new RequestConsumer();
        try{
//            String tempToken = "111111";
//            ComponentUtil.redisService.set(tempToken, "3");
            // 解密
            data = StringUtil.decoderBase64(jsonData);
            requestConsumer  = JSON.parseObject(data, RequestConsumer.class);
            // check校验数据、校验用户是否登录、获得用户ID
            memberId = PublicMethod.checkAddFixedData(requestConsumer);
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
            ConsumerFixedModel addDataModel = PublicMethod.assembleConsumerFixedAdd(requestConsumer, memberId);
            ComponentUtil.consumerFixedService.add(addDataModel);

            ComponentUtil.userInfoSevrice.toRealName(Integer.parseInt(memberId + ""));
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
//            String tokon = SignUtil.getSgin(memberId, stime, secretKeySign); // 用户did+用户账号+秘钥=token
            String sign = SignUtil.getSgin(stime, token, secretKeySign); //服务器时间+token+秘钥=sign
            String strData = PublicMethod.assembleResult(stime, token, sign);
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 用户注册完毕则直接让用户处于登录状态
//            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
            // 添加流水
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestConsumer, ServerConstant.InterfaceEnum.CONSUMER_ADDFIXED.getType(),
                    ServerConstant.InterfaceEnum.CONSUMER_ADDFIXED.getDesc(), null, data, strData, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
            // 添加异常
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestConsumer, ServerConstant.InterfaceEnum.CONSUMER_ADDFIXED.getType(),
                    ServerConstant.InterfaceEnum.CONSUMER_ADDFIXED.getDesc(), null, data, null, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            log.error(String.format("this ConsumerController.addFixed() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
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
     * local:http://localhost:8082/play/csm/upFixed
     * 请求的属性类:RequestConsumer
     * 必填字段:{"fixedNum":13717505293,"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:fixedNum+ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     */
    @RequestMapping(value = "/upFixed", method = {RequestMethod.POST})
//    public JsonResult<Object> upFixed(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
    public JsonResult<Object> upFixed(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        long memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        RequestConsumer requestConsumer = new RequestConsumer();
        try{
//            String tempToken = "111111";
//            ComponentUtil.redisService.set(tempToken, "3");
            // 解密
            data = StringUtil.decoderBase64(jsonData);
            requestConsumer  = JSON.parseObject(data, RequestConsumer.class);
            // check校验数据、校验用户是否登录、获得用户ID
            memberId = PublicMethod.checkUpFixedData(requestConsumer);
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
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 用户注册完毕则直接让用户处于登录状态
//            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
            // 添加流水
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestConsumer, ServerConstant.InterfaceEnum.CONSUMER_UPFIXED.getType(),
                    ServerConstant.InterfaceEnum.CONSUMER_UPFIXED.getDesc(), null, data, strData, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
            // 添加异常
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestConsumer, ServerConstant.InterfaceEnum.CONSUMER_UPFIXED.getType(),
                    ServerConstant.InterfaceEnum.CONSUMER_UPFIXED.getDesc(), null, data, null, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            log.error(String.format("this ConsumerController.upFixed() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }


    /**
     * @Description: 获取用户手续费百分比的信息
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/21 22:58
     * local:http://localhost:8082/play/csm/getRatio
     * 请求的属性类:RequestConsumer
     * 必填字段:{"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:empiricalLevel+ratio+stime+token+秘钥=sign
     */
    @RequestMapping(value = "/getRatio", method = {RequestMethod.POST})
//    public JsonResult<Object> getRatio(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
    public JsonResult<Object> getRatio(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        long memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        RequestConsumer requestConsumer = new RequestConsumer();
        try{
//            String tempToken = "111111";
//            ComponentUtil.redisService.set(tempToken, "3");
            // 解密
            data = StringUtil.decoderBase64(jsonData);
            requestConsumer  = JSON.parseObject(data, RequestConsumer.class);
            // check校验数据、校验用户是否登录、获得用户ID
            memberId = PublicMethod.checkRatioData(requestConsumer);
            token = requestConsumer.getToken();
            // 校验ctime
            // 校验sign

            // 获取用户收取手续费的比例
            ConsumerModel consumerQuery = PublicMethod.assembleServiceChargeQuery(memberId);
            ConsumerModel consumerModel = ComponentUtil.consumerFixedService.getConsumerServiceCharge(consumerQuery);
            PublicMethod.checkConsumerServiceChargeData(consumerModel);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(consumerModel.getEmpiricalLevel(), consumerModel.getRatio(), stime, token, secretKeySign); //maxPrice+minPrice+stime+token+秘钥=sign
            String strData = PublicMethod.assembleRatioResult(stime, token, sign, consumerModel.getEmpiricalLevel(), consumerModel.getRatio());
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 用户注册完毕则直接让用户处于登录状态
//            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
            // 添加流水
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestConsumer, ServerConstant.InterfaceEnum.CONSUMER_GETRATIO.getType(),
                    ServerConstant.InterfaceEnum.CONSUMER_GETRATIO.getDesc(), null, data, strData, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
            // 添加异常
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestConsumer, ServerConstant.InterfaceEnum.CONSUMER_GETRATIO.getType(),
                    ServerConstant.InterfaceEnum.CONSUMER_GETRATIO.getDesc(), null, data, null, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            log.error(String.format("this ConsumerController.getRatio() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }



    /**
     * @Description: 获取用户的基本信息
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/21 22:58
     * local:http://localhost:8082/play/csm/getBasic
     * 请求的属性类:RequestConsumer
     * 必填字段:{"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:phoneNum+isActive+stime+token+秘钥=sign
     */
    @RequestMapping(value = "/getBasic", method = {RequestMethod.POST})
//    public JsonResult<Object> getBasic(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
    public JsonResult<Object> getBasic(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        long memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        RequestConsumer requestConsumer = new RequestConsumer();
        try{
//            String tempToken = "111111";
//            ComponentUtil.redisService.set(tempToken, "3");
            // 解密
            data = StringUtil.decoderBase64(jsonData);
            requestConsumer  = JSON.parseObject(data, RequestConsumer.class);
            // check校验数据、校验用户是否登录、获得用户ID
            memberId = PublicMethod.checkBasicData(requestConsumer);
            token = requestConsumer.getToken();
            // 校验ctime
            // 校验sign

            // 组装查询用户信息的查询条件
            ConsumerModel consumerQuery = PublicMethod.assembleConsumerModel(memberId);
            ConsumerModel consumerModel = ComponentUtil.consumerFixedService.getConsumer(consumerQuery);
            PublicMethod.checkConsumer(consumerModel);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(consumerModel.getPhoneNum(), consumerModel.getIsActive(), stime, token, secretKeySign); // phoneNum+isActive+stime+token+秘钥=sign
            String strData = PublicMethod.assembleBasicResult(stime, token, sign, consumerModel.getNickname(), consumerModel.getPhoneNum(), consumerModel.getIsCertification(), consumerModel.getIsActive());
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 用户注册完毕则直接让用户处于登录状态
//            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
            // 添加流水
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestConsumer, ServerConstant.InterfaceEnum.CONSUMER_GETBASIC.getType(),
                    ServerConstant.InterfaceEnum.CONSUMER_GETBASIC.getDesc(), null, data, strData, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
            // 添加异常
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestConsumer, ServerConstant.InterfaceEnum.CONSUMER_GETBASIC.getType(),
                    ServerConstant.InterfaceEnum.CONSUMER_GETBASIC.getDesc(), null, data, null, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            log.error(String.format("this ConsumerController.getBasic() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }




//    /**
//     * @Description: 旷视实名认证
//     * <p>
//     *     这里只是整个流程的一部分，整体流程说明如下：
//     *     1.客户端提交用户的真实姓名、身份证、支付宝账号
//     *     2.服务端把用户的信息存储到缓存中
//     *     3.服务端把用户真实姓名、身份证组装数据访问旷视提供的接口
//     *     4.把旷视返回的biz_token字段值返回给客户端
//     *     5.把biz_token值返回给客户端
//     *     6.提供实名认证回调结果的接口
//     * </p>
//     * @param request
//     * @param response
//     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
//     * @author yoko
//     * @date 2019/11/7 16:58
//     * local:http://localhost:8082/play/csm/certification
//     * 请求的属性类:RequestConsumer
//     * 必填字段:{"fullName":"小五哥","idCard":"435202111111111111","fixedType":2,"fixedNum":13717505292,"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
//     * 客户端加密字段:fullName+idCard+fixedType+fixedNum+ctime+cctime+token+秘钥=sign
//     * 服务端加密字段:stime+token+秘钥=sign
//     */
//    @RequestMapping(value = "/certification", method = {RequestMethod.POST})
////    public JsonResult<Object> certification(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
//    public JsonResult<Object> certification(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData) throws Exception{
//        String sgid = ComponentUtil.redisIdService.getSgid();
//        String cgid = "";
//        String token;
//        String ip = StringUtil.getIpAddress(request);
//        String data = "";
//        long memberId = 0;
//        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
//        RequestConsumer requestConsumer = new RequestConsumer();
//        try{
////            String tempToken = "111111";
////            ComponentUtil.redisService.set(tempToken, "3");
//            // 解密
//            data = StringUtil.decoderBase64(jsonData);
//            requestConsumer  = JSON.parseObject(data, RequestConsumer.class);
//            // check校验数据、校验用户是否登录、获得用户ID
//            memberId = PublicMethod.checkAddFixedData(requestConsumer);
//            token = requestConsumer.getToken();
//            // 校验ctime
//            // 校验sign
//            // 查询用户固定数据
//            ConsumerFixedModel consumerFixedQuery = PublicMethod.assembleConsumerFixedQuery(memberId);
//            ConsumerFixedModel consumerFixedModel = ComponentUtil.consumerFixedService.getConsumerFixed(consumerFixedQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
//            if (consumerFixedModel != null){
//                throw new ServiceException(PfErrorCode.ENUM_ERROR.C00012.geteCode(), PfErrorCode.ENUM_ERROR.C00012.geteDesc());
//            }
//            // 添加固定账号数据
//            ConsumerFixedModel addDataModel = PublicMethod.assembleConsumerFixedAdd(requestConsumer, memberId);
//            ComponentUtil.consumerFixedService.add(addDataModel);
//
//            ComponentUtil.userInfoSevrice.toRealName(Integer.parseInt(memberId + ""));
//            // 组装返回客户端的数据
//            long stime = System.currentTimeMillis();
////            String tokon = SignUtil.getSgin(memberId, stime, secretKeySign); // 用户did+用户账号+秘钥=token
//            String sign = SignUtil.getSgin(stime, token, secretKeySign); //服务器时间+token+秘钥=sign
//            String strData = PublicMethod.assembleResult(stime, token, sign);
//            // 数据加密
//            String encryptionData = StringUtil.mergeCodeBase64(strData);
//            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
//            resultDataModel.jsonData = encryptionData;
//            // 用户注册完毕则直接让用户处于登录状态
//            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
//            // 添加流水
//            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestConsumer, ServerConstant.InterfaceEnum.CONSUMER_ADDFIXED.getType(),
//                    ServerConstant.InterfaceEnum.CONSUMER_ADDFIXED.getDesc(), null, data, strData, null);
//            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
//            // 返回数据给客户端
//            return JsonResult.successResult(resultDataModel, cgid, sgid);
//        }catch (Exception e){
//            Map<String,String> map = ExceptionMethod.getException(e, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
//            // 添加异常
//            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestConsumer, ServerConstant.InterfaceEnum.CONSUMER_ADDFIXED.getType(),
//                    ServerConstant.InterfaceEnum.CONSUMER_ADDFIXED.getDesc(), null, data, null, map);
//            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
//            log.error(String.format("this ConsumerController.addFixed() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
//            e.printStackTrace();
//            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
//        }
//    }

}
