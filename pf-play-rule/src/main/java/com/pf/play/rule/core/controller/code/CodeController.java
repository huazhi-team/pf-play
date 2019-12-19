package com.pf.play.rule.core.controller.code;

import com.alibaba.fastjson.JSON;
import com.pf.play.common.utils.JsonResult;
import com.pf.play.common.utils.SignUtil;
import com.pf.play.common.utils.StringUtil;
import com.pf.play.model.protocol.request.RequestEncryptionJson;
import com.pf.play.model.protocol.request.consumer.RequestConsumer;
import com.pf.play.model.protocol.response.ResponseEncryptionJson;
import com.pf.play.rule.PublicMethod;
import com.pf.play.rule.core.common.exception.ExceptionMethod;
import com.pf.play.rule.core.common.utils.SendSmsUtils;
import com.pf.play.rule.core.common.utils.constant.CachedKeyUtils;
import com.pf.play.rule.core.common.utils.constant.PfCacheKey;
import com.pf.play.rule.core.common.utils.constant.ServerConstant;
import com.pf.play.rule.core.model.consumer.ConsumerModel;
import com.pf.play.rule.core.model.region.RegionModel;
import com.pf.play.rule.core.model.stream.StreamConsumerModel;
import com.pf.play.rule.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description 验证码获取
 * @Author yoko
 * @Date 2019/12/10 17:16
 * @Version 1.0
 */
@RestController
@RequestMapping("/play/cd")
public class CodeController {
    private static Logger log = LoggerFactory.getLogger(CodeController.class);

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
     * @Description: 获取验证码-修改支付密码时
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/7 16:58
     * local:http://localhost:8082/play/cd/getCd
     * 请求的属性类:RequestConsumer
     * 必填字段:{"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     */
    @RequestMapping(value = "/getCd", method = {RequestMethod.POST})
//    public JsonResult<Object> getCd(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
    public JsonResult<Object> getCd(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsonData) throws Exception{
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
//            log.info("jsonData:" + requestData.jsonData);
            //解密
            data = StringUtil.decoderBase64(jsonData);
            requestConsumer  = JSON.parseObject(data, RequestConsumer.class);
            // check校验数据、校验用户是否登录、获得用户ID
            memberId = PublicMethod.checkGetCdData(requestConsumer);
            token = requestConsumer.getToken();

            // 判断是否频繁请求发送验证码
            String strKeyCache = CachedKeyUtils.getPfCacheKey(PfCacheKey.MEMBERID_UP_PAY_CODE, memberId);
            PublicMethod.checkOftenSendCode(strKeyCache);
            // 校验ctime
            // 校验sign

            // 组装查询用户信息的查询条件
            ConsumerModel consumerQuery = PublicMethod.assembleConsumerModel(memberId);
            ConsumerModel consumerModel = ComponentUtil.consumerFixedService.getConsumer(consumerQuery);
            PublicMethod.checkConsumer(consumerModel);
            // 发送验证码
            String strCode = SendSmsUtils.sendSmsCode(strKeyCache, consumerModel.getPhoneNum());
            PublicMethod.checkSendCode(strCode);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); // 服务器时间+token+秘钥=sign
            String strData = PublicMethod.assembleResult(stime, token, sign);
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 让用户处于登录状态
            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
            // 添加流水
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestConsumer, ServerConstant.InterfaceEnum.CODE_GETCD.getType(),
                    ServerConstant.InterfaceEnum.CODE_GETCD.getDesc(), null, data, strData, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e);
            // 添加异常
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestConsumer, ServerConstant.InterfaceEnum.CODE_GETCD.getType(),
                    ServerConstant.InterfaceEnum.CODE_GETCD.getDesc(), null, data, null, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            log.error(String.format("this CodeController.getCd() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }

}
