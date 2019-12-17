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
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.PfErrorCode;
import com.pf.play.rule.core.common.utils.constant.ServerConstant;
import com.pf.play.rule.core.model.consumer.ConsumerModel;
import com.pf.play.rule.core.model.order.OrderModel;
import com.pf.play.rule.core.model.price.VirtualCoinPriceDto;
import com.pf.play.rule.core.model.price.VirtualCoinPriceModel;
import com.pf.play.rule.core.model.strategy.StrategyModel;
import com.pf.play.rule.core.model.trade.TradeModel;
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
     * @Description: 交易数据展现-规则
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/td/getTradeRule
     * 请求的属性类:RequestTrade
     * 必填字段:{"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:isTrade+tradeTime+maxPrice+minPrice+stime+token+秘钥=sign
     */
    @RequestMapping(value = "/getTradeRule", method = {RequestMethod.POST})
    public JsonResult<Object> getTradeRule(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
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
            long memberId = PublicMethod.checkTradeRuleData(requestOrder);
            token = requestOrder.getToken();
            // 校验ctime
            // 校验sign

            // 开市时间
            StrategyModel strategyTimeQuery = PublicMethod.assembleStrategyQuery(ServerConstant.StrategyEnum.STG_TRADE_TIME_WEEKEND_OPEN.getStgType());
            StrategyModel strategyTimeModel = ComponentUtil.strategyService.getStrategyModel(strategyTimeQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            int isTrade = PublicMethod.checkTradeTime(strategyTimeModel);

            // 获取策略：策略类型：1表示成交量虚假数据开关
            StrategyModel strategySwitchQuery = PublicMethod.assembleStrategyQuery(ServerConstant.StrategyEnum.STG_DATA_OPEN.getStgType());
            StrategyModel strategySwitchModel = ComponentUtil.strategyService.getStrategyModel(strategySwitchQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            PublicMethod.checkStrategyShamData(strategySwitchModel);
            // 查询当前买量
            TradeModel buyTradeNumQuery = PublicMethod.assembleOrderTradeNum(ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            String buyTradeNum = ComponentUtil.tradeService.getOrderTradeNum(buyTradeNumQuery,ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            // 查询今日成交量
            TradeModel sucTradeNumQuery = PublicMethod.assembleOrderTradeNum(ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
            String sucTradeNum = ComponentUtil.tradeService.getOrderTradeNum(sucTradeNumQuery,ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            if (strategySwitchModel.getStgNumValue() == ServerConstant.StrategyEnum.STG_DATA_OPEN.getStgNumValue()){
                // 虚拟数据需要进行特殊处理
                buyTradeNum = StringUtil.getMultiply(buyTradeNum, strategySwitchModel.getStgValue());
                sucTradeNum = StringUtil.getMultiply(sucTradeNum, strategySwitchModel.getStgValue());
            }

            // 虚拟币每天兑换的价格
            VirtualCoinPriceModel virtualCoinPriceQuery = PublicMethod.assembleVirtualCoinPriceQuery();
            List<VirtualCoinPriceModel> virtualCoinPriceList =  ComponentUtil.virtualCoinPriceService.getVirtualCoinPriceList(virtualCoinPriceQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            if (virtualCoinPriceList.size() <= ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
                throw new ServiceException(PfErrorCode.ENUM_ERROR.P00003.geteCode(), PfErrorCode.ENUM_ERROR.P00003.geteDesc());
            }
            VirtualCoinPriceDto virtualCoinPriceDto = PublicMethod.assembleVirtualCoinPriceDto(virtualCoinPriceList);

            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(isTrade, strategyTimeModel.getStgValue(), virtualCoinPriceDto.getMaxPrice(),
                    virtualCoinPriceDto.getMinPrice(), stime, token, secretKeySign); // isTrade+tradeTime+maxPrice+minPrice+stime+token+秘钥=sign
            String strData = PublicMethod.assembleTradeRuleResult(stime, token, sign, isTrade, strategyTimeModel.getStgValue(), buyTradeNum, sucTradeNum, virtualCoinPriceDto);
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
     * @Description: 获取交易所的开市时间
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/td/getTradeTime
     * 请求的属性类:RequestTrade
     * 必填字段:{"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
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

            // 开市时间
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



    /**
     * @Description: 获取交易所的当前买量、今日成交量
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/td/getTradeData
     * 请求的属性类:RequestTrade
     * 必填字段:{"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 返回字段:{"buyTradeNum":"263055","sign":"5a9defde446bb278b0b10f14d48de4b3","stime":1575286872113,"sucTradeNum":"34845","token":"111111"}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:buyTradeNum+sucTradeNum+stime+token+秘钥=sign
     */
    @RequestMapping(value = "/getTradeData", method = {RequestMethod.POST})
    public JsonResult<Object> getTradeData(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
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

            // 获取策略：策略类型：1表示成交量虚假数据开关
            StrategyModel strategyQuery = PublicMethod.assembleStrategyQuery(ServerConstant.StrategyEnum.STG_DATA_OPEN.getStgType());
            StrategyModel strategyModel = ComponentUtil.strategyService.getStrategyModel(strategyQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            PublicMethod.checkStrategyShamData(strategyModel);
            // 查询当前买量
            TradeModel buyTradeNumQuery = PublicMethod.assembleOrderTradeNum(ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            String buyTradeNum = ComponentUtil.tradeService.getOrderTradeNum(buyTradeNumQuery,ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            // 查询今日成交量
            TradeModel sucTradeNumQuery = PublicMethod.assembleOrderTradeNum(ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
            String sucTradeNum = ComponentUtil.tradeService.getOrderTradeNum(sucTradeNumQuery,ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            if (strategyModel.getStgNumValue() == ServerConstant.StrategyEnum.STG_DATA_OPEN.getStgNumValue()){
                // 虚拟数据需要进行特殊处理
                buyTradeNum = StringUtil.getMultiply(buyTradeNum, strategyModel.getStgValue());
                sucTradeNum = StringUtil.getMultiply(sucTradeNum, strategyModel.getStgValue());
            }
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(buyTradeNum, sucTradeNum, stime, token, secretKeySign); // buyTradeNum+sucTradeNum+stime+token+秘钥=sign
            String strData = PublicMethod.assembleTradeDataResult(stime, token, sign, buyTradeNum, sucTradeNum);
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
     * @Description: 卖家卖给买家/也就是添加订单交易流水
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/td/addData
     * 请求的属性类:RequestTrade
     * 必填字段:{"orderNo":"order_no_test_1","payPw":"3","agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:orderNo+payPw+ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     * UPDATE tb_pf_order SET order_trade_status = 0 WHERE order_no = '';
     * UPDATE tb_pf_order_trade SET yn = 1 WHERE order_id =;
     * result=={
     *     "errcode": 0,
     *     "message": "success",
     *     "content": {
     *         "jsonData": "eyJzaWduIjoiYjFiZDVmNGJkMmYyYzU4YWU2NTBiMjk5NWRmODk0MzMiLCJzdGltZSI6MTU3NjU3NTk2NjYzMSwidG9rZW4iOiIxMTExMTEifQ=="
     *     },
     *     "sgid": "201912171745590000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/addData", method = {RequestMethod.POST})
    public JsonResult<Object> addData(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        try{
            String tempToken = "111111";
            ComponentUtil.redisService.set(tempToken, "3");
            log.info("jsonData:" + requestData.jsonData);
            // 解密
            String data = StringUtil.decoderBase64(requestData.jsonData);
            RequestTrade requestTrade  = JSON.parseObject(data, RequestTrade.class);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkTradeAddData(requestTrade);
            token = requestTrade.getToken();
            // 校验ctime
            // 校验sign

            // 查询卖家的基本信息
            ConsumerModel sellConsumerQuery = PublicMethod.assembleConsumerQuery(memberId);
            ConsumerModel sellConsumer = ComponentUtil.consumerFixedService.getConsumer(sellConsumerQuery);
            // check校验卖家信息
            PublicMethod.checkSellConsumer(sellConsumer, requestTrade.getPayPw());


            // 首先根据订单号查询是否有此订单信息
            OrderModel orderQuery = PublicMethod.assembleOrderQueryByTrade(requestTrade, memberId, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
            OrderModel orderModel = (OrderModel) ComponentUtil.orderService.findByObject(orderQuery);
            // check订单是否可以正常交易
            PublicMethod.checkOrderBySell(orderModel);

            // 查询买家的基本信息
            ConsumerModel buyConsumerQuery = PublicMethod.assembleConsumerQuery(orderModel.getMemberId());
            ConsumerModel buyConsumer = ComponentUtil.consumerFixedService.getConsumer(buyConsumerQuery);
            // check校验买家信息
            PublicMethod.checkBuyConsumer(buyConsumer);

            // 组装生成订单交易流水的数据
            TradeModel tradeModel = PublicMethod.assembleTradeData(orderModel, sellConsumer, buyConsumer);
            // 组装数据：扣除、冻结的钻石=买家的钻石个数+手续费
            ConsumerModel sellConsumerModel = PublicMethod.assembleConsumerAddReduceData(memberId, tradeModel.getTradeNum(), tradeModel.getServiceCharge());
            // 校验卖家是否有足够的钻石进行支付
            PublicMethod.checkEnoughResources(sellConsumer.getDayMasonry(), sellConsumerModel.getAddReduceNum());
            // 组装修改订单号状态的数据
            OrderModel orderUpStatus = PublicMethod.assembleUpdataOrderStatusData(orderModel.getOrderNo(),ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE, null);
            // 正式生成订单
            ComponentUtil.tradeService.createOrderTrade(tradeModel, sellConsumerModel, orderUpStatus);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
            String strData = PublicMethod.assembleTradeResult(stime, token, sign);
            // #插入流水
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 用户注册完毕则直接让用户处于登录状态
            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
            // #提醒买家 - 卖家已卖出钻石，请买家进行支付
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e);
            // 添加错误异常数据
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }




    /**
     * @Description: 买家确认支付
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/td/confirmPay
     * 请求的属性类:RequestTrade
     * 必填字段:{"orderNo":"order_no_1","pictureAds":"https://pics7.baidu.com/feed/21a4462309f790528810a506738b00cf7bcbd57d.jpeg?token=13e1f88d6796436f9bee0f740d8cc7b3&s=0E21D2055E721094748468B70300A002","agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:orderNo+pictureAds+ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     * result=={
     *     "errcode": "0",
     *     "message": "success",
     *     "content": {
     *         "jsonData": "eyJzaWduIjoiNzBkNDZlYzNmZGI3MWI4MjQ5YjkxYjY4ZmY3NGNiOWIiLCJzdGltZSI6MTU3NTQyNjgyNDE4MywidG9rZW4iOiIxMTExMTEifQ=="
     *     },
     *     "sgid": "201912041033300000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/confirmPay", method = {RequestMethod.POST})
    public JsonResult<Object> confirmPay(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        try{
            String tempToken = "111111";
            ComponentUtil.redisService.set(tempToken, "4");
            log.info("jsonData:" + requestData.jsonData);
            // 解密
            String data = StringUtil.decoderBase64(requestData.jsonData);
            RequestTrade requestTrade  = JSON.parseObject(data, RequestTrade.class);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkTradeConfirmPayData(requestTrade);
            token = requestTrade.getToken();
            // 校验ctime
            // 校验sign

            // 首先根据订单号查询是否有此订单信息
            OrderModel orderQuery = PublicMethod.assembleOrderQueryByConfirmPay(requestTrade, memberId, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
            OrderModel orderModel = (OrderModel) ComponentUtil.orderService.findByObject(orderQuery);
            // check订单是否存在
            PublicMethod.checkOrderByConfirmPay(orderModel);
            // 更新订单流水、订单交易流水的订单交易状态：更新成确认已付款
            TradeModel upTradeModel = PublicMethod.assembleUpTradeStatusByConfirmPay(orderModel.getId(), requestTrade.pictureAds, ServerConstant.TradeStatusEnum.PAY.getType(), ServerConstant.TradeStatusEnum.ACTION.getType());
            OrderModel upOrderModel = PublicMethod.assembleUpOrderStatusByConfirmPay(orderModel.getOrderNo(), ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
            ComponentUtil.tradeService.updateOrderAndOrderTradeStatus(upTradeModel, upOrderModel);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
            String strData = PublicMethod.assembleTradeResult(stime, token, sign);
            // #插入流水
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 用户注册完毕则直接让用户处于登录状态
            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
            // #提醒卖家 - 买家已付款，卖家需要核实是否收到款
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e);
            // 添加错误异常数据
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }



    /**
     * @Description: 卖家确认已收款
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/td/confirmRpt
     * 请求的属性类:RequestTrade
     * 必填字段:{"orderNo":"order_no_test_1","agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:orderNo+ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     * result=={
     *     "errcode": "0",
     *     "message": "success",
     *     "content": {
     *         "jsonData": "eyJzaWduIjoiYWQyYzFlZjQxNjMwM2I5OTcyNTJiM2QxZTg0ZDMwMzEiLCJzdGltZSI6MTU3NTQyNzcyMzY4NCwidG9rZW4iOiIxMTExMTEifQ=="
     *     },
     *     "sgid": "201912041048340000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/confirmRpt", method = {RequestMethod.POST})
    public JsonResult<Object> confirmReceipt(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        try{
            String tempToken = "111111";
            ComponentUtil.redisService.set(tempToken, "3");
            log.info("jsonData:" + requestData.jsonData);
            // 解密
            String data = StringUtil.decoderBase64(requestData.jsonData);
            RequestTrade requestTrade  = JSON.parseObject(data, RequestTrade.class);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkTradeConfirmReceiptData(requestTrade);
            token = requestTrade.getToken();
            // 校验ctime
            // 校验sign

            // 首先根据订单号查询是否有此订单信息
            OrderModel orderQuery = PublicMethod.assembleOrderQueryByConfirmRpt(requestTrade, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO);
            OrderModel orderModel = (OrderModel) ComponentUtil.orderService.findByObject(orderQuery);
            // check订单是否存在
            PublicMethod.checkOrderByConfirmReceipt(orderModel);
            TradeModel tradeQuery = PublicMethod.assembleTradeQueryByConfirmReceipt(orderModel.getId(), memberId, ServerConstant.TradeStatusEnum.PAY.getType());
            TradeModel tradeModel = (TradeModel) ComponentUtil.tradeService.findByObject(tradeQuery);
            // 组装卖家要扣除在冻结的钻石个数
            ConsumerModel sellConsumerModel = PublicMethod.assembleSellConsumerSubtractMasonry(tradeModel);
            // 组装买家要加的钻石个数
            ConsumerModel buyConsumerModel = PublicMethod.assembleBuyConsumerAddMasonry(tradeModel);
            // 更新订单流水：A.订单交易状态更新成完成. B.订单状态更新成交易完成
            // 更新订单交易流水：A.交易状态更新成确认已收款（卖家确认）
            // 卖家冻结钻石扣除、买家钻石加上
            TradeModel upTradeModel = PublicMethod.assembleUpTradeStatusByConfirmReceipt(orderModel.getId(), memberId, ServerConstant.TradeStatusEnum.MAKE_COLLECTIONS.getType(), ServerConstant.TradeStatusEnum.PAY.getType());
            OrderModel upOrderModel = PublicMethod.assembleOrderUpByConfirmReceipt(requestTrade, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_THREE, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_THREE);
            ComponentUtil.tradeService.tradeFinish(upTradeModel, upOrderModel, sellConsumerModel, buyConsumerModel);

            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
            String strData = PublicMethod.assembleTradeResult(stime, token, sign);
            // #插入流水
            // 数据加密
            String encryptionData = StringUtil.mergeCodeBase64(strData);
            ResponseEncryptionJson resultDataModel = new ResponseEncryptionJson();
            resultDataModel.jsonData = encryptionData;
            // 用户注册完毕则直接让用户处于登录状态
            ComponentUtil.redisService.set(token, String.valueOf(memberId), FIFTEEN_MIN, TimeUnit.SECONDS);
            // #提醒买家 - 卖家已确认收款，买家需要核实是否收到钻石
            // 返回数据给客户端
            return JsonResult.successResult(resultDataModel, cgid, sgid);
        }catch (Exception e){
            Map<String,String> map = ExceptionMethod.getException(e);
            // 添加错误异常数据
            return JsonResult.failedResult(map.get("message"), map.get("code"), cgid, sgid);
        }
    }












}
