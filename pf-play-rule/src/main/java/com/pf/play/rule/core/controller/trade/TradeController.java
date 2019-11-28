package com.pf.play.rule.core.controller.trade;

import com.alibaba.fastjson.JSON;
import com.pf.play.common.utils.JsonResult;
import com.pf.play.common.utils.SignUtil;
import com.pf.play.common.utils.StringUtil;
import com.pf.play.model.protocol.request.RequestEncryptionJson;
import com.pf.play.model.protocol.request.order.RequestOrder;
import com.pf.play.model.protocol.request.trade.RequestTrade;
import com.pf.play.model.protocol.response.ResponseEncryptionJson;
import com.pf.play.rule.PublicMethod;
import com.pf.play.rule.core.common.exception.ExceptionMethod;
import com.pf.play.rule.core.common.utils.constant.ServerConstant;
import com.pf.play.rule.core.model.order.OrderModel;
import com.pf.play.rule.core.model.strategy.StrategyModel;
import com.pf.play.rule.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description 交易的Controller层
 * @Author yoko
 * @Date 2019/11/27 13:58
 * @Version 1.0
 */
@RestController
@RequestMapping("/play/td")
public class TradeController {
    private static Logger log = LoggerFactory.getLogger(TradeController.class);

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
     * @Description: 获取交易所的开市时间
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/td/getTradeTime
     * 请求的属性类:RequestTrade
     * 必填字段:{"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:isTrade+tradeTime+stime+token+秘钥=sign
     */
    @RequestMapping(value = "/getTradeTime", method = {RequestMethod.POST})
    public JsonResult<Object> getTradeTime(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        try{
            String tempToken = "111111";
            ComponentUtil.redisService.set(tempToken, "3");
            log.info("jsonData:" + requestData.jsonData);
            // 解密
            String data = StringUtil.decoderBase64(requestData.jsonData);
            RequestTrade requestOrder  = JSON.parseObject(data, RequestTrade.class);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkTradeTimeData(requestOrder);
            token = requestOrder.getToken();
            // 校验ctime
            // 校验sign

            // 订单列表
            StrategyModel strategyQuery = PublicMethod.assembleStrategyQuery(ServerConstant.StrategyEnum.STG_TRADE_TIME_WEEKEND_OPEN.getStgType());
            StrategyModel strategyModel = ComponentUtil.strategyService.getStrategyModel(strategyQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            int isTrade = PublicMethod.checkTradeTime(strategyModel);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(isTrade, strategyModel.getStgValue(), stime, token, secretKeySign); // isTrade+tradeTime+stime+token+秘钥=sign
            String strData = PublicMethod.assembleTradeTimeResult(stime, token, sign, isTrade, strategyModel.getStgValue());
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










}
