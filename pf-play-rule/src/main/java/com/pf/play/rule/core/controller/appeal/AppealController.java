package com.pf.play.rule.core.controller.appeal;

import com.alibaba.fastjson.JSON;
import com.pf.play.common.utils.JsonResult;
import com.pf.play.common.utils.SignUtil;
import com.pf.play.common.utils.StringUtil;
import com.pf.play.model.protocol.request.RequestEncryptionJson;
import com.pf.play.model.protocol.request.appeal.RequestAppeal;
import com.pf.play.model.protocol.request.order.RequestOrder;
import com.pf.play.model.protocol.response.ResponseEncryptionJson;
import com.pf.play.rule.PublicMethod;
import com.pf.play.rule.core.common.exception.ExceptionMethod;
import com.pf.play.rule.core.common.utils.constant.ServerConstant;
import com.pf.play.rule.core.model.appeal.AppealModel;
import com.pf.play.rule.core.model.order.OrderModel;
import com.pf.play.rule.core.model.region.RegionModel;
import com.pf.play.rule.core.model.stream.StreamConsumerModel;
import com.pf.play.rule.core.model.trade.TradeModel;
import com.pf.play.rule.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description 申诉的Controller层
 * @Author yoko
 * @Date 2019/12/5 14:26
 * @Version 1.0
 */
@RestController
@RequestMapping("/play/al")
public class AppealController {
    private static Logger log = LoggerFactory.getLogger(AppealController.class);

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
     * @Description: 获取我的申诉（主动发起的申诉）-列表
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/al/getActiveData
     * 请求的属性类:RequestAppeal
     * 必填字段:{"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","pageNumber":1,"pageSize":3}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     * result=={
     *     "errcode": "0",
     *     "message": "success",
     *     "content": {
     *         "jsonData": "eyJhTGlzdCI6W3siYXBwZWFsRGVzY3JpYmUiOiLnlLPor4nljp/lm6BfMSIsImFwcGVhbFJlcGxlbmlzaCI6IueUs+ivieihpeWFhV8xIiwiYXBwZWFsUmVzdWx0IjowLCJidXlOaWNrbmFtZSI6IuWwj+mjnum+mTEiLCJjcmVhdGVUaW1lIjoiMjAxOS0xMi0wNSAxNToyNTo0NCIsImlkIjoxLCJpZGVudGl0eVR5cGUiOjEsIm9yZGVyTm8iOiJvcmRlcl9ub18xIiwib3JkZXJUcmFkZVRpbWUiOiIyMDE5LTEyLTA0IDEwOjI1OjM2IiwicmVmdXRlRGVzY3JpYmUiOiLlj43pqbPljp/lm6BfMSIsInJlZnV0ZVJlcGxlbmlzaCI6IuWPjemps+ihpeWFhV8xIiwic2VsbE5pY2tuYW1lIjoi5bCP6aOe6b6ZMyIsInNlcnZpY2VDaGFyZ2UiOiIzLjMiLCJ0b3RhbFByaWNlIjoiMTIuMSIsInRyYWRlTnVtIjoiMTEiLCJ0cmFkZVByaWNlIjoiMS4xIn0seyJhcHBlYWxEZXNjcmliZSI6IueUs+ivieWOn+WboF8yIiwiYXBwZWFsUmVwbGVuaXNoIjoi55Sz6K+J6KGl5YWFXzIiLCJhcHBlYWxSZXN1bHQiOjAsImJ1eU5pY2tuYW1lIjoi5Lmw5a62X+aYteensF8zIiwiaWQiOjIsImlkZW50aXR5VHlwZSI6Miwib3JkZXJObyI6Im9yZGVyX25vXzIiLCJvcmRlclRyYWRlVGltZSI6IjIwMTktMTEtMjcgMjE6NTM6MDIiLCJyZWZ1dGVEZXNjcmliZSI6IiIsInJlZnV0ZVJlcGxlbmlzaCI6IiIsInNlbGxOaWNrbmFtZSI6IuWNluWutl/mmLXnp7BfNCIsInNlcnZpY2VDaGFyZ2UiOiIxLjUiLCJ0b3RhbFByaWNlIjoiIiwidHJhZGVOdW0iOiIzIiwidHJhZGVQcmljZSI6IjMifSx7ImFwcGVhbERlc2NyaWJlIjoi55Sz6K+J5Y6f5ZugXzQiLCJhcHBlYWxSZXBsZW5pc2giOiLnlLPor4nooaXlhYVfNCIsImFwcGVhbFJlc3VsdCI6MCwiYnV5Tmlja25hbWUiOiLkubDlrrZf5pi156ewXzciLCJpZCI6NCwiaWRlbnRpdHlUeXBlIjoyLCJvcmRlck5vIjoib3JkZXJfbm9fNCIsIm9yZGVyVHJhZGVUaW1lIjoiMjAxOS0xMS0yNyAyMTo1MzowMiIsInJlZnV0ZURlc2NyaWJlIjoiIiwicmVmdXRlUmVwbGVuaXNoIjoiIiwic2VsbE5pY2tuYW1lIjoi5Y2W5a62X+aYteensF84Iiwic2VydmljZUNoYXJnZSI6IjIuNSIsInRvdGFsUHJpY2UiOiIiLCJ0cmFkZU51bSI6IjUiLCJ0cmFkZVByaWNlIjoiNSJ9XSwicm93Q291bnQiOjEwLCJzaWduIjoiZGZmZmZjNTI2NzI5NmIwZjVjYTE3ZWI0MmY3OTZiZjciLCJzdGltZSI6MTU3NTU1MTU3OTMyNywidG9rZW4iOiIxMTExMTEifQ=="
     *     },
     *     "sgid": "201912052111280000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/getActiveData", method = {RequestMethod.POST})
//    public JsonResult<Object> getActiveData(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
    public JsonResult<Object> getActiveData(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        long memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        RequestAppeal requestAppeal = new RequestAppeal();
        try{
//            String tempToken = "111111";
//            ComponentUtil.redisService.set(tempToken, "3");
            // 解密
            data = StringUtil.decoderBase64(jsonData);
            requestAppeal  = JSON.parseObject(data, RequestAppeal.class);
            // check校验数据、校验用户是否登录、获得用户ID
            memberId = PublicMethod.checkActiveData(requestAppeal);
            token = requestAppeal.getToken();
            // 校验ctime
            // 校验sign

            // 申诉数据
            AppealModel appealQuery = PublicMethod.assembleAppealQuery(requestAppeal, memberId, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            List<AppealModel> appealList = ComponentUtil.appealService.queryByList(appealQuery);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
            String strData = PublicMethod.assembleAppealResult(stime, token, sign, appealList, appealQuery.getRowCount());
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 用户注册完毕则直接让用户处于登录状态
            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
            // 添加流水
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestAppeal, ServerConstant.InterfaceEnum.APPEAL_GETACTIVEDATA.getType(),
                    ServerConstant.InterfaceEnum.APPEAL_GETACTIVEDATA.getDesc(), null, data, strData, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
            // 添加异常
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestAppeal, ServerConstant.InterfaceEnum.APPEAL_GETACTIVEDATA.getType(),
                    ServerConstant.InterfaceEnum.APPEAL_GETACTIVEDATA.getDesc(), null, data, null, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            log.error(String.format("this AppealController.getActiveData() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }



    /**
     * @Description: 获取被申诉（被他人申诉）-列表
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/al/getPassiveData
     * 请求的属性类:RequestAppeal
     * 必填字段:{"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","pageNumber":1,"pageSize":3}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     * result=={
     *     "errcode": "0",
     *     "message": "success",
     *     "content": {
     *         "jsonData": "eyJhTGlzdCI6W3siYXBwZWFsRGVzY3JpYmUiOiLnlLPor4nljp/lm6BfMSIsImFwcGVhbFJlcGxlbmlzaCI6IueUs+ivieihpeWFhV8xIiwiYXBwZWFsUmVzdWx0IjowLCJidXlOaWNrbmFtZSI6IuWwj+mjnum+mTEiLCJjcmVhdGVUaW1lIjoiMjAxOS0xMi0wNSAxNToyNTo0NCIsImlkIjoxLCJpZGVudGl0eVR5cGUiOjEsIm9yZGVyTm8iOiJvcmRlcl9ub18xIiwib3JkZXJUcmFkZVRpbWUiOiIyMDE5LTEyLTA0IDEwOjI1OjM2IiwicmVmdXRlRGVzY3JpYmUiOiLlj43pqbPljp/lm6BfMSIsInJlZnV0ZVJlcGxlbmlzaCI6IuWPjemps+ihpeWFhV8xIiwic2VsbE5pY2tuYW1lIjoi5bCP6aOe6b6ZMyIsInNlcnZpY2VDaGFyZ2UiOiIzLjMiLCJ0b3RhbFByaWNlIjoiMTIuMSIsInRyYWRlTnVtIjoiMTEiLCJ0cmFkZVByaWNlIjoiMS4xIn0seyJhcHBlYWxEZXNjcmliZSI6IueUs+ivieWOn+WboF8yIiwiYXBwZWFsUmVwbGVuaXNoIjoi55Sz6K+J6KGl5YWFXzIiLCJhcHBlYWxSZXN1bHQiOjAsImJ1eU5pY2tuYW1lIjoi5Lmw5a62X+aYteensF8zIiwiaWQiOjIsImlkZW50aXR5VHlwZSI6Miwib3JkZXJObyI6Im9yZGVyX25vXzIiLCJvcmRlclRyYWRlVGltZSI6IjIwMTktMTEtMjcgMjE6NTM6MDIiLCJyZWZ1dGVEZXNjcmliZSI6IiIsInJlZnV0ZVJlcGxlbmlzaCI6IiIsInNlbGxOaWNrbmFtZSI6IuWNluWutl/mmLXnp7BfNCIsInNlcnZpY2VDaGFyZ2UiOiIxLjUiLCJ0b3RhbFByaWNlIjoiIiwidHJhZGVOdW0iOiIzIiwidHJhZGVQcmljZSI6IjMifSx7ImFwcGVhbERlc2NyaWJlIjoi55Sz6K+J5Y6f5ZugXzQiLCJhcHBlYWxSZXBsZW5pc2giOiLnlLPor4nooaXlhYVfNCIsImFwcGVhbFJlc3VsdCI6MCwiYnV5Tmlja25hbWUiOiLkubDlrrZf5pi156ewXzciLCJpZCI6NCwiaWRlbnRpdHlUeXBlIjoyLCJvcmRlck5vIjoib3JkZXJfbm9fNCIsIm9yZGVyVHJhZGVUaW1lIjoiMjAxOS0xMS0yNyAyMTo1MzowMiIsInJlZnV0ZURlc2NyaWJlIjoiIiwicmVmdXRlUmVwbGVuaXNoIjoiIiwic2VsbE5pY2tuYW1lIjoi5Y2W5a62X+aYteensF84Iiwic2VydmljZUNoYXJnZSI6IjIuNSIsInRvdGFsUHJpY2UiOiIiLCJ0cmFkZU51bSI6IjUiLCJ0cmFkZVByaWNlIjoiNSJ9XSwicm93Q291bnQiOjEwLCJzaWduIjoiZGZmZmZjNTI2NzI5NmIwZjVjYTE3ZWI0MmY3OTZiZjciLCJzdGltZSI6MTU3NTU1MTU3OTMyNywidG9rZW4iOiIxMTExMTEifQ=="
     *     },
     *     "sgid": "201912052111280000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/getPassiveData", method = {RequestMethod.POST})
//    public JsonResult<Object> getPassiveData(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
    public JsonResult<Object> getPassiveData(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        long memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        RequestAppeal requestAppeal = new RequestAppeal();
        try{
//            String tempToken = "111111";
//            ComponentUtil.redisService.set(tempToken, "3");
            // 解密
            data = StringUtil.decoderBase64(jsonData);
            requestAppeal  = JSON.parseObject(data, RequestAppeal.class);
            // check校验数据、校验用户是否登录、获得用户ID
            memberId = PublicMethod.checkPassiveData(requestAppeal);
            token = requestAppeal.getToken();
            // 校验ctime
            // 校验sign

            // 被申诉数据
            AppealModel appealQuery = PublicMethod.assembleAppealQuery(requestAppeal, memberId, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
            List<AppealModel> appealList = ComponentUtil.appealService.queryByList(appealQuery);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
            String strData = PublicMethod.assembleAppealResult(stime, token, sign, appealList, appealQuery.getRowCount());
            // #插入流水
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 用户注册完毕则直接让用户处于登录状态
            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
            // 添加流水
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestAppeal, ServerConstant.InterfaceEnum.APPEAL_GETPASSIVEDATA.getType(),
                    ServerConstant.InterfaceEnum.APPEAL_GETPASSIVEDATA.getDesc(), null, data, strData, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
            // 添加异常
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestAppeal, ServerConstant.InterfaceEnum.APPEAL_GETPASSIVEDATA.getType(),
                    ServerConstant.InterfaceEnum.APPEAL_GETPASSIVEDATA.getDesc(), null, data, null, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            log.error(String.format("this AppealController.getPassiveData() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }



    /**
     * @Description: 更新我的申诉的数据
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/al/upActive
     * 请求的属性类:RequestAppeal
     * 必填字段:{"id":1,"appealDescribe":"更新_申诉原因_1","appealReplenish":"更新_申诉补充_1","pictureAds":"http://www.baidu.com","agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:id+appealDescribe+pictureAds+ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     * result=={
     *     "errcode": "0",
     *     "message": "success",
     *     "content": {
     *         "jsonData": "eyJzaWduIjoiMWZmN2VmNTQzOTBiZDI5M2U5MjI1NzYzYTlhNzUzNjMiLCJzdGltZSI6MTU3NTU1Mzc4MDY1MywidG9rZW4iOiIxMTExMTEifQ=="
     *     },
     *     "sgid": "201912052149100000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/upActive", method = {RequestMethod.POST})
//    public JsonResult<Object> upActive(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
    public JsonResult<Object> upActive(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        long memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        RequestAppeal requestAppeal = new RequestAppeal();
        try{
//            String tempToken = "111111";
//            ComponentUtil.redisService.set(tempToken, "3");
            // 解密
            data = StringUtil.decoderBase64(jsonData);
            requestAppeal  = JSON.parseObject(data, RequestAppeal.class);
            // check校验数据、校验用户是否登录、获得用户ID
            memberId = PublicMethod.checkUpActiveData(requestAppeal);
            token = requestAppeal.getToken();
            // 校验ctime
            // 校验sign

            // 更新申诉数据
            AppealModel appealModel = PublicMethod.assembleAppealUpdateActive(requestAppeal, memberId);
            ComponentUtil.appealService.updateActive(appealModel);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
            String strData = PublicMethod.assembleUpAppealResult(stime, token, sign);
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 用户注册完毕则直接让用户处于登录状态
            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
            // 添加流水
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestAppeal, ServerConstant.InterfaceEnum.APPEAL_UPACTIVE.getType(),
                    ServerConstant.InterfaceEnum.APPEAL_UPACTIVE.getDesc(), null, data, strData, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
            // 添加异常
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestAppeal, ServerConstant.InterfaceEnum.APPEAL_UPACTIVE.getType(),
                    ServerConstant.InterfaceEnum.APPEAL_UPACTIVE.getDesc(), null, data, null, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            log.error(String.format("this AppealController.upActive() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }


    /**
     * @Description: 更新被申诉的数据
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/al/upPassive
     * 请求的属性类:RequestAppeal
     * 必填字段:{"id":1,"refuteDescribe":"更新_反驳原因_1","refuteReplenish":"更新_反驳补充_1","refutePictureAds":"http://www.baidu.com","agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:id+refuteDescribe+refutePictureAds+ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     * result=={
     *     "errcode": "0",
     *     "message": "success",
     *     "content": {
     *         "jsonData": "eyJzaWduIjoiN2RkNWM1ODEyZTc3OTRmNWYxNDg5OTIwM2Q2YmIxZGEiLCJzdGltZSI6MTU3NTU1NTQzODI4NSwidG9rZW4iOiIxMTExMTEifQ=="
     *     },
     *     "sgid": "201912052216580000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/upPassive", method = {RequestMethod.POST})
//    public JsonResult<Object> upPassive(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
    public JsonResult<Object> upPassive(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        long memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        RequestAppeal requestAppeal = new RequestAppeal();
        try{
//            String tempToken = "111111";
//            ComponentUtil.redisService.set(tempToken, "4");
            // 解密
            data = StringUtil.decoderBase64(jsonData);
            requestAppeal  = JSON.parseObject(data, RequestAppeal.class);
            // check校验数据、校验用户是否登录、获得用户ID
            memberId = PublicMethod.checkUpPassiveData(requestAppeal);
            token = requestAppeal.getToken();
            // 校验ctime
            // 校验sign

            // 更新被申诉数据
            AppealModel appealModel = PublicMethod.assembleAppealUpdatePassive(requestAppeal, memberId);
            ComponentUtil.appealService.updatePassive(appealModel);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
            String strData = PublicMethod.assembleUpAppealResult(stime, token, sign);
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 用户注册完毕则直接让用户处于登录状态
            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
            // 添加流水
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestAppeal, ServerConstant.InterfaceEnum.APPEAL_UPPASSIVE.getType(),
                    ServerConstant.InterfaceEnum.APPEAL_UPPASSIVE.getDesc(), null, data, strData, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
            // 添加异常
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestAppeal, ServerConstant.InterfaceEnum.APPEAL_UPPASSIVE.getType(),
                    ServerConstant.InterfaceEnum.APPEAL_UPPASSIVE.getDesc(), null, data, null, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            log.error(String.format("this AppealController.upPassive() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }



    /**
     * @Description: 添加申诉的数据
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/al/addData
     * 请求的属性类:RequestAppeal
     * 必填字段:{"orderNo":"order_no_1","appealDescribe":"更新_申诉原因_1","appealReplenish":"更新_申诉补充_1","pictureAds":"http://www.baidu.com","agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:orderNo+appealDescribe+pictureAds+ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     * result=={
     *     "errcode": "0",
     *     "message": "success",
     *     "content": {
     *         "jsonData": "eyJzaWduIjoiMWZmN2VmNTQzOTBiZDI5M2U5MjI1NzYzYTlhNzUzNjMiLCJzdGltZSI6MTU3NTU1Mzc4MDY1MywidG9rZW4iOiIxMTExMTEifQ=="
     *     },
     *     "sgid": "201912052149100000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/addData", method = {RequestMethod.POST})
//    public JsonResult<Object> addData(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
    public JsonResult<Object> addData(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        long memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        RequestAppeal requestAppeal = new RequestAppeal();
        try{
//            String tempToken = "111111";
//            ComponentUtil.redisService.set(tempToken, "3");
            // 解密
            data = StringUtil.decoderBase64(jsonData);
            requestAppeal  = JSON.parseObject(data, RequestAppeal.class);
            // check校验数据、校验用户是否登录、获得用户ID
            memberId = PublicMethod.checkAddAppealData(requestAppeal);
            token = requestAppeal.getToken();
            // 校验ctime
            // 校验sign

            // 首先根据订单号查询是否有此订单信息
            OrderModel orderQuery = PublicMethod.assembleOrderQueryByAppeal(requestAppeal.getOrderNo());
            OrderModel orderModel = (OrderModel) ComponentUtil.orderService.findByObject(orderQuery);
            PublicMethod.checkOrderByAppeal(orderModel);

            // 判断订单号是否重复申诉
            AppealModel appealQuery = PublicMethod. assembleAppealQuery(orderModel.getId());
            AppealModel checkAppealModel = (AppealModel) ComponentUtil.appealService.findByObject(appealQuery);
            PublicMethod.checkAppealData(checkAppealModel);

            // 判断用户在此订单中是属于买家还是卖家：true=买家；false=卖家
            int identityType = PublicMethod.checkIdentityType(orderModel, memberId);// 身份类别：是买家还是卖家；1买家，2卖家

            // 查询订单交易流水：订单号主键ID + 用户身份（买家or卖家）
            TradeModel tradeQuery = PublicMethod.assembleTradeQueryByAppeal(orderModel.getId(), memberId, identityType);
            TradeModel tradeModel = (TradeModel) ComponentUtil.tradeService.findByObject(tradeQuery);
            PublicMethod.checkTradeData(tradeModel, memberId, identityType);


            // 组装要新增的申诉数据
            AppealModel appealModel = PublicMethod.assembleAppealAddData(requestAppeal, tradeModel, memberId, identityType);
            // 组装要更新的订单流水的申诉状态数据
            OrderModel upOrderModel = PublicMethod.assembleUpOrderAppealStatus(orderModel);
            // 组装要更新的订单交易流水的申诉状态数据
            TradeModel upTradeModel = PublicMethod.assembleUpTradeAppealStatus(tradeModel);
            ComponentUtil.appealService.addAppeal(appealModel, upOrderModel, upTradeModel);

            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
            String strData = PublicMethod.assembleUpAppealResult(stime, token, sign);
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 用户注册完毕则直接让用户处于登录状态
            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
            // 添加流水
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestAppeal, ServerConstant.InterfaceEnum.APPEAL_ADDDATA.getType(),
                    ServerConstant.InterfaceEnum.APPEAL_ADDDATA.getDesc(), null, data, strData, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
            // 添加异常
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestAppeal, ServerConstant.InterfaceEnum.APPEAL_ADDDATA.getType(),
                    ServerConstant.InterfaceEnum.APPEAL_ADDDATA.getDesc(), null, data, null, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            log.error(String.format("this AppealController.addData() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }



    /**
     * @Description: 获取我的申诉或者被申诉的数据-根据ID查询申诉的详情
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/al/getInfoData
     * 请求的属性类:RequestAppeal
     * 必填字段:{"id":1,"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     * result=={
     *     "errcode": 0,
     *     "message": "success",
     *     "content": {
     *         "jsonData": "eyJhcHBlYWwiOnsiYXBwZWFsRGVzY3JpYmUiOiLmm7TmlrBf55Sz6K+J5Y6f5ZugXzEiLCJhcHBlYWxSZXBsZW5pc2giOiLmm7TmlrBf55Sz6K+J6KGl5YWFXzEiLCJhcHBlYWxSZXN1bHQiOjAsImJ1eU5pY2tuYW1lIjoi5bCP6aOe6b6ZMSIsImNyZWF0ZVRpbWUiOiIyMDE5LTEyLTA1IDE1OjI1OjQ0IiwiaWQiOjEsImlkZW50aXR5VHlwZSI6MSwib3JkZXJObyI6Im9yZGVyX25vXzEiLCJvcmRlclRyYWRlVGltZSI6IjIwMTktMTItMDQgMTA6MjU6MzYiLCJwaWN0dXJlQWRzIjoiaHR0cDovL3d3dy5iYWlkdS5jb20iLCJyZWZ1dGVEZXNjcmliZSI6IuabtOaWsF/lj43pqbPljp/lm6BfMSIsInJlZnV0ZVBpY3R1cmVBZHMiOiJodHRwOi8vd3d3LmJhaWR1LmNvbSIsInJlZnV0ZVJlcGxlbmlzaCI6IuabtOaWsF/lj43pqbPooaXlhYVfMSIsInNlbGxOaWNrbmFtZSI6IuWwj+mjnum+mTMiLCJzZXJ2aWNlQ2hhcmdlIjoiMy4zIiwidG90YWxQcmljZSI6IjEyLjEiLCJ0cmFkZU51bSI6IjExIiwidHJhZGVQcmljZSI6IjEuMSJ9LCJzaWduIjoiODYxNTUzY2UyMDcyMjg1NTE4OGEzNWZhYTlkOTI4MDQiLCJzdGltZSI6MTU3NjI0MjY0OTM4NiwidG9rZW4iOiIxMTExMTEifQ=="
     *     },
     *     "sgid": "201912132110370000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/getInfoData", method = {RequestMethod.POST})
//    public JsonResult<Object> getInfoData(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
    public JsonResult<Object> getInfoData(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        String ip = StringUtil.getIpAddress(request);
        String data = "";
        long memberId = 0;
        RegionModel regionModel = PublicMethod.assembleRegionModel(ip);
        RequestAppeal requestAppeal = new RequestAppeal();
        try{
//            String tempToken = "111111";
//            ComponentUtil.redisService.set(tempToken, "3");
            // 解密
            data = StringUtil.decoderBase64(jsonData);
            requestAppeal  = JSON.parseObject(data, RequestAppeal.class);
            // check校验数据、校验用户是否登录、获得用户ID
            memberId = PublicMethod.checkInfoData(requestAppeal);
            token = requestAppeal.getToken();
            // 校验ctime
            // 校验sign

            // 申诉数据
//            AppealModel appealQuery = PublicMethod.assembleAppealQuery(requestAppeal, memberId, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            AppealModel appealModel = (AppealModel) ComponentUtil.appealService.findById(requestAppeal.getId());
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
            String strData = PublicMethod.assembleAppealByIdResult(stime, token, sign, appealModel);
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 用户注册完毕则直接让用户处于登录状态
            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
            // 添加流水
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestAppeal, ServerConstant.InterfaceEnum.APPEAL_GETINFODATA.getType(),
                    ServerConstant.InterfaceEnum.APPEAL_GETINFODATA.getDesc(), null, data, strData, null);
            ComponentUtil.streamConsumerService.addVisit(streamConsumerModel);
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
            // 添加异常
            StreamConsumerModel streamConsumerModel = PublicMethod.assembleStream(sgid, cgid, memberId, regionModel, requestAppeal, ServerConstant.InterfaceEnum.APPEAL_GETINFODATA.getType(),
                    ServerConstant.InterfaceEnum.APPEAL_GETINFODATA.getDesc(), null, data, null, map);
            ComponentUtil.streamConsumerService.addError(streamConsumerModel);
            log.error(String.format("this AppealController.getInfoData() is error , the cgid=%s and sgid=%s and all data=%s!", cgid, sgid, data));
            e.printStackTrace();
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }

}
