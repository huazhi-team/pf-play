package com.pf.play.rule.core.controller.order;

import com.alibaba.fastjson.JSON;
import com.pf.play.common.utils.JsonResult;
import com.pf.play.common.utils.SignUtil;
import com.pf.play.common.utils.StringUtil;
import com.pf.play.model.protocol.request.RequestEncryptionJson;
import com.pf.play.model.protocol.request.consumer.RequestConsumer;
import com.pf.play.model.protocol.request.order.RequestOrder;
import com.pf.play.model.protocol.request.trade.RequestTrade;
import com.pf.play.model.protocol.response.ResponseEncryptionJson;
import com.pf.play.rule.PublicMethod;
import com.pf.play.rule.core.common.exception.ExceptionMethod;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.core.common.utils.constant.PfErrorCode;
import com.pf.play.rule.core.common.utils.constant.ServerConstant;
import com.pf.play.rule.core.controller.price.VirtualCoinPriceController;
import com.pf.play.rule.core.model.order.OrderModel;
import com.pf.play.rule.core.model.price.VirtualCoinPriceDto;
import com.pf.play.rule.core.model.price.VirtualCoinPriceModel;
import com.pf.play.rule.core.model.strategy.StrategyModel;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description 订单的Controller层
 * @Author yoko
 * @Date 2019/11/25 11:06
 * @Version 1.0
 */
@RestController
@RequestMapping("/play/od")
public class OrderController {
    private static Logger log = LoggerFactory.getLogger(OrderController.class);

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
     * @Description: 获取订单信息-列表
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/od/getData
     * 请求的属性类:RequestOrder
     * 必填字段:{"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","sortType":"1", "pageNumber":1,"pageSize":3}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     */
    @RequestMapping(value = "/getData", method = {RequestMethod.POST})
    public JsonResult<Object> getData(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        try{
            String tempToken = "111111";
            ComponentUtil.redisService.set(tempToken, "3");
            log.info("jsonData:" + requestData.jsonData);
            // 解密
            String data = StringUtil.decoderBase64(requestData.jsonData);
            RequestOrder requestOrder  = JSON.parseObject(data, RequestOrder.class);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkOrderData(requestOrder);
            token = requestOrder.getToken();
            // 校验ctime
            // 校验sign

            // 订单列表
            OrderModel orderQuery = PublicMethod.assembleOrderQuery(requestOrder, memberId, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
            List <OrderModel> orderList = ComponentUtil.orderService.queryByList(orderQuery);
            orderQuery.getPage();
            log.info("data :" + orderList.size());
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
            String strData = PublicMethod.assembleOrderResult(stime, token, sign, orderList);
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
     * @Description: 发布订单号/我要买QHR
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/od/addData
     * 请求的属性类:RequestOrder
     * 必填字段:{"tradeNum":"10","tradePrice":"1.92","agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:tradeNum+tradePrice+ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
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
            RequestOrder requestOrder  = JSON.parseObject(data, RequestOrder.class);
            // 获取虚拟币今日价格信息
            VirtualCoinPriceModel VirtualCoinPriceQuery = PublicMethod.assembleVirtualCoinPriceQueryToday();
            VirtualCoinPriceModel virtualCoinPriceModel = ComponentUtil.virtualCoinPriceService.getVirtualCoinPrice(VirtualCoinPriceQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkAddOrderData(requestOrder, virtualCoinPriceModel);
            token = requestOrder.getToken();
            // 校验ctime
            // 校验sign

            // 获取订单号
            String orderNo = ComponentUtil.redisIdService.getOrderNo();
            // 新增订单号
            OrderModel orderAdd = PublicMethod.assembleAddOrderData(requestOrder, memberId, orderNo);
            ComponentUtil.orderService.add(orderAdd);

            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
            String strData = PublicMethod.assembleOrderResult(stime, token, sign);
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
     * @Description: 获取订单详情
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/od/getInfoData
     * 请求的属性类:RequestOrder
     * 必填字段:{"orderNo":"order_no_dzf_1","agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:orderNo+ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     */
    @RequestMapping(value = "/getInfoData", method = {RequestMethod.POST})
    public JsonResult<Object> getInfoData(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        try{
            String tempToken = "111111";
            ComponentUtil.redisService.set(tempToken, "3");
            log.info("jsonData:" + requestData.jsonData);
            // 解密
            String data = StringUtil.decoderBase64(requestData.jsonData);
            RequestOrder requestOrder  = JSON.parseObject(data, RequestOrder.class);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkOrderInfoData(requestOrder);
            token = requestOrder.getToken();
            // 校验ctime
            // 校验sign

            // 订单详情
            OrderModel orderQuery = PublicMethod.assembleOrderInfoQuery(requestOrder);
            OrderModel orderModel = (OrderModel) ComponentUtil.orderService.findByObject(orderQuery);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
            String strData = PublicMethod.assembleOrderInfoResult(stime, token, sign, orderModel);
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
     * @Description: 获取买入订单信息-列表
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/od/getBuyData
     * 请求的属性类:RequestOrder
     * 必填字段:{"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","pageNumber":1,"pageSize":3}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     */
    @RequestMapping(value = "/getBuyData", method = {RequestMethod.POST})
    public JsonResult<Object> getBuyData(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        try{
            String tempToken = "111111";
            ComponentUtil.redisService.set(tempToken, "3");
            log.info("jsonData:" + requestData.jsonData);
            // 解密
            String data = StringUtil.decoderBase64(requestData.jsonData);
            RequestOrder requestOrder  = JSON.parseObject(data, RequestOrder.class);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkOrderData(requestOrder);
            token = requestOrder.getToken();
            // 校验ctime
            // 校验sign

            // 订单列表
            OrderModel orderQuery = PublicMethod.assembleBuySellOrderQuery(requestOrder, memberId, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
            List <OrderModel> orderList = ComponentUtil.orderService.queryByList(orderQuery);
            orderQuery.getPage();
            log.info("data :" + orderList.size());
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
            String strData = PublicMethod.assembleBuyOrderResult(stime, token, sign, orderList);
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
     * @Description: 获取已取消的订单信息-列表
     * <p>查询用户已经取消的订单信息</p>
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/od/getCancelData
     * 请求的属性类:RequestOrder
     * 必填字段:{"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","pageNumber":1,"pageSize":3}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     */
    @RequestMapping(value = "/getCancelData", method = {RequestMethod.POST})
    public JsonResult<Object> getCancelData(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        try{
            String tempToken = "111111";
            ComponentUtil.redisService.set(tempToken, "3");
            log.info("jsonData:" + requestData.jsonData);
            // 解密
            String data = StringUtil.decoderBase64(requestData.jsonData);
            RequestOrder requestOrder  = JSON.parseObject(data, RequestOrder.class);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkOrderData(requestOrder);
            token = requestOrder.getToken();
            // 校验ctime
            // 校验sign

            // 订单列表-已取消的订单
            OrderModel orderQuery = PublicMethod.assembleCancelOrderQuery(requestOrder, memberId, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
            List <OrderModel> orderList = ComponentUtil.orderService.queryByList(orderQuery);
            orderQuery.getPage();
            log.info("data :" + orderList.size());
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
            String strData = PublicMethod.assembleBuyOrderResult(stime, token, sign, orderList);
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
     * @Description: 购买的订单：取消
     * <p>
     *     买家取消自己的求购订单；
     *     这里要注意的是：
     *      1.要进行取消的订单是否没有被redis进行锁住（刚好那时候有用户要对此订单进行交易）
     *      2.查看订单是否属于锁定状态
     * </p>
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/od/upCancelData
     * 请求的属性类:RequestOrder
     * 必填字段:{"orderNo":"order_no_dzf_1","agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:orderNo+ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     */
    @RequestMapping(value = "/upCancelData", method = {RequestMethod.POST})
    public JsonResult<Object> upCancelData(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        try{
            String tempToken = "111111";
            ComponentUtil.redisService.set(tempToken, "3");
            log.info("jsonData:" + requestData.jsonData);
            // 解密
            String data = StringUtil.decoderBase64(requestData.jsonData);
            RequestOrder requestOrder  = JSON.parseObject(data, RequestOrder.class);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkCancelOrderData(requestOrder);
            token = requestOrder.getToken();
            // 校验ctime
            // 校验sign

            // 获取订单数据
            OrderModel orderQuery = PublicMethod.assembleCancelOrderQuery(requestOrder, memberId);
            OrderModel orderModel = (OrderModel) ComponentUtil.orderService.findByObject(orderQuery);
            // 校验订单数据
            PublicMethod.checkCancelOrder(orderModel);
            // 正式取消订单
            orderQuery.setOrderStatus(ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO); // 更细成取消的状态
            ComponentUtil.orderService.cancelOrder(orderQuery);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
            String strData = PublicMethod.assembleOrderResult(stime, token, sign);
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
     * @Description: 获取用户待付款的订单信息-详情
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/od/getUnpaidInfoData
     * 请求的属性类:RequestOrder
     * 必填字段:{"orderNo":"order_no_3","agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:orderNo+ctime+cctime+token+秘钥=sign
     * 服务端加密字段:buyOvertime+sellOverTime+stime+token+秘钥=sign
     * result=={
     *     "errcode": 0,
     *     "message": "success",
     *     "content": {
     *         "jsonData": "eyJidXlPdmVydGltZSI6MzAsImNvT3JkZXIiOnsiYXBwZWFsU3RhdHVzIjoyLCJidXlOaWNrbmFtZSI6IuS5sOWutl/mmLXnp7BfMSIsImNyZWF0ZVRpbWUiOiIyMDE5LTExLTAzIDE0OjQ4OjIyIiwib3JkZXJObyI6Im9yZGVyX25vXzMiLCJvcmRlclN0YXR1cyI6MSwib3JkZXJUcmFkZVN0YXR1cyI6MSwicGF5VGltZSI6IjIwMTktMTItMDMgMjA6NDY6NTYiLCJwaWN0dXJlQWRzIjoiaHR0cHM6Ly9waWNzNy5iYWlkdS5jb20vZmVlZC8yMWE0NDYyMzA5Zjc5MDUyODgxMGE1MDY3MzhiMDBjZjdiY2JkNTdkLmpwZWc/dG9rZW49MTNlMWY4OGQ2Nzk2NDM2ZjliZWUwZjc0MGQ4Y2M3YjMmcz0wRTIxRDIwNTVFNzIxMDk0NzQ4NDY4QjcwMzAwQTAwMiIsInJlY2VpdmVUaW1lIjoiMjAxOS0xMi0wNCAxNzo0MDoyOCIsInNlbGxOaWNrbmFtZSI6IuWNluWutl/mmLXnp7BfMiIsInNlcnZpY2VDaGFyZ2UiOiIwLjUiLCJ0b3RhbFByaWNlIjoiMTA4LjkiLCJ0cmFkZUNyZWF0ZVRpbWUiOiIyMDE5LTExLTI3IDIxOjUzOjAyIiwidHJhZGVOdW0iOiIzMyIsInRyYWRlUHJpY2UiOiIzLjMiLCJ1cGRhdGVUaW1lIjoiMjAxOS0xMi0wNiAxOToyOToyMSJ9LCJzZWxsT3ZlclRpbWUiOjYwLCJzaWduIjoiNjkzMDY1MzRkZjI4ZWZmMDkwMzdkYTBhMDUwMGIyZmYiLCJzdGltZSI6MTU3NjU1NTI0MDk5MSwidG9rZW4iOiIxMTExMTEifQ=="
     *     },
     *     "sgid": "201912171200060000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/getUnpaidInfoData", method = {RequestMethod.POST})
    public JsonResult<Object> getUnpaidInfoData(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        try{
            String tempToken = "111111";
            ComponentUtil.redisService.set(tempToken, "3");
            log.info("jsonData:" + requestData.jsonData);
            // 解密
            String data = StringUtil.decoderBase64(requestData.jsonData);
            RequestOrder requestOrder  = JSON.parseObject(data, RequestOrder.class);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkUnpaidInfoData(requestOrder);
            token = requestOrder.getToken();
            // 校验ctime
            // 校验sign

            // 策略:查询指定的买家支付超时时间
            StrategyModel buyStrategyQuery = PublicMethod.assembleStrategyQuery(ServerConstant.StrategyEnum.STG_BUY_OVERTIME.getStgType());
            StrategyModel buyStrategyModel = ComponentUtil.strategyService.getStrategyModel(buyStrategyQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            // 策略:查询指定的卖家确认收款超时时间
            StrategyModel sellStrategyQuery = PublicMethod.assembleStrategyQuery(ServerConstant.StrategyEnum.STG_SELL_OVERTIME.getStgType());
            StrategyModel sellStrategyModel = ComponentUtil.strategyService.getStrategyModel(sellStrategyQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            // 获取待付款订单列表
            OrderModel orderQuery = PublicMethod.assembleUnpaidOrderByOrderOnQuery(requestOrder, memberId);
            OrderModel orderModel = ComponentUtil.orderService.getUnpaidOrder(orderQuery);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(buyStrategyModel.getStgValue(), sellStrategyModel.getStgValue(), stime, token, secretKeySign); // buyOvertime+sellOverTime+stime+token+秘钥=sign
            String strData = PublicMethod.assembleUnpaidOrderByOrderNoResult(stime, token, sign, orderModel, Integer.parseInt(buyStrategyModel.getStgValue()), Integer.parseInt(sellStrategyModel.getStgValue()));
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
     * @Description: 获取用户待付款的订单信息-列表
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/od/getUnpaidData
     * 请求的属性类:RequestOrder
     * 必填字段:{"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","pageNumber":1,"pageSize":3}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:buyOvertime+sellOverTime+stime+token+秘钥=sign
     * result=={
     *     "errcode": "0",
     *     "message": "success",
     *     "content": {
     *         "jsonData": "eyJjb0xpc3QiOlt7ImFwcGVhbFN0YXR1cyI6MSwiYnV5Tmlja25hbWUiOiLkubDlrrZfc2JfMSIsImNyZWF0ZVRpbWUiOiIyMDE5LTExLTI2IDEzOjUxOjQ2Iiwib3JkZXJObyI6Im9yZGVyX25vX2R6Zl8xIiwib3JkZXJTdGF0dXMiOjEsIm9yZGVyVHJhZGVTdGF0dXMiOjEsInBheVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4IiwicmVjZWl2ZVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4Iiwic2VsbE5pY2tuYW1lIjoi5Y2W5a62X3NiXzEiLCJzZXJ2aWNlQ2hhcmdlIjoiMC4zIiwidG90YWxQcmljZSI6IjEiLCJ0cmFkZUNyZWF0ZVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4IiwidHJhZGVOdW0iOiIxIiwidHJhZGVQcmljZSI6IjEifSx7ImFwcGVhbFN0YXR1cyI6MSwiYnV5Tmlja25hbWUiOiLkubDlrrZfc2JfNSIsImNyZWF0ZVRpbWUiOiIyMDE5LTExLTI2IDEzOjUxOjQ2Iiwib3JkZXJObyI6Im9yZGVyX25vX2R6Zl80Iiwib3JkZXJTdGF0dXMiOjEsIm9yZGVyVHJhZGVTdGF0dXMiOjIsInBheVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4IiwicmVjZWl2ZVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4Iiwic2VsbE5pY2tuYW1lIjoi5Y2W5a62X3NiXzUiLCJzZXJ2aWNlQ2hhcmdlIjoiMC42IiwidG90YWxQcmljZSI6IjgiLCJ0cmFkZUNyZWF0ZVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4IiwidHJhZGVOdW0iOiIyIiwidHJhZGVQcmljZSI6IjQifSx7ImFwcGVhbFN0YXR1cyI6MSwiYnV5Tmlja25hbWUiOiLkubDlrrZfc2JfMiIsImNyZWF0ZVRpbWUiOiIyMDE5LTExLTI2IDEzOjUxOjQ2Iiwib3JkZXJObyI6Im9yZGVyX25vX2R6Zl8yIiwib3JkZXJTdGF0dXMiOjEsIm9yZGVyVHJhZGVTdGF0dXMiOjIsInBheVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4IiwicmVjZWl2ZVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4Iiwic2VsbE5pY2tuYW1lIjoi5Y2W5a62X3NiXzIiLCJzZXJ2aWNlQ2hhcmdlIjoiMC42IiwidG90YWxQcmljZSI6IjQiLCJ0cmFkZUNyZWF0ZVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4IiwidHJhZGVOdW0iOiIyIiwidHJhZGVQcmljZSI6IjIifV0sIm92ZXJ0aW1lIjo2MCwicm93Q291bnQiOjUsInNpZ24iOiJhMTY0NThiZmNmOWI0NjVhNGY0ZDcyMWRmMjhhNDQ2MyIsInN0aW1lIjoxNTc1NDUyNTY3MDI2LCJ0b2tlbiI6IjExMTExMSJ9"
     *     },
     *     "sgid": "201912041742460000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/getUnpaidData", method = {RequestMethod.POST})
    public JsonResult<Object> getUnpaidData(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        try{
            String tempToken = "111111";
            ComponentUtil.redisService.set(tempToken, "3");
            log.info("jsonData:" + requestData.jsonData);
            // 解密
            String data = StringUtil.decoderBase64(requestData.jsonData);
            RequestOrder requestOrder  = JSON.parseObject(data, RequestOrder.class);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkUnpaidData(requestOrder);
            token = requestOrder.getToken();
            // 校验ctime
            // 校验sign

            // 策略:查询指定的买家支付超时时间
            StrategyModel buyStrategyQuery = PublicMethod.assembleStrategyQuery(ServerConstant.StrategyEnum.STG_BUY_OVERTIME.getStgType());
            StrategyModel buyStrategyModel = ComponentUtil.strategyService.getStrategyModel(buyStrategyQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            // 策略:查询指定的卖家确认收款超时时间
            StrategyModel sellStrategyQuery = PublicMethod.assembleStrategyQuery(ServerConstant.StrategyEnum.STG_SELL_OVERTIME.getStgType());
            StrategyModel sellStrategyModel = ComponentUtil.strategyService.getStrategyModel(sellStrategyQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            // 获取待付款订单列表
            OrderModel orderQuery = PublicMethod.assembleUnpaidOrderQuery(requestOrder, memberId, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
            List<OrderModel> orderList = ComponentUtil.orderService.getUnpaidOrderList(orderQuery);
            orderQuery.getPage();
            log.info("data :" + orderList.size());
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(buyStrategyModel.getStgValue(), sellStrategyModel.getStgValue(), stime, token, secretKeySign); // buyOvertime+sellOverTime+stime+token+秘钥=sign
            String strData = PublicMethod.assembleUnpaidOrderResult(stime, token, sign, orderList, Integer.parseInt(buyStrategyModel.getStgValue()), Integer.parseInt(sellStrategyModel.getStgValue()), orderQuery.getRowCount());
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
     * @Description: 获取用户待收款的订单信息-详情
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/od/getReceivableInfoData
     * 请求的属性类:RequestOrder
     * 必填字段:{"orderNo":"order_no_dzf_2","agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:orderNo+ctime+cctime+token+秘钥=sign
     * 服务端加密字段:buyOvertime+sellOverTime+stime+token+秘钥=sign
     * result=={
     *     "errcode": 0,
     *     "message": "success",
     *     "content": {
     *         "jsonData": "eyJidXlPdmVydGltZSI6MzAsImNvT3JkZXIiOnsiYXBwZWFsU3RhdHVzIjoxLCJidXlOaWNrbmFtZSI6IuS5sOWutl9zYl8yIiwiY3JlYXRlVGltZSI6IjIwMTktMTEtMjYgMTM6NTE6NDYiLCJvcmRlck5vIjoib3JkZXJfbm9fZHpmXzIiLCJvcmRlclN0YXR1cyI6MSwib3JkZXJUcmFkZVN0YXR1cyI6MiwicGF5VGltZSI6IjIwMTktMTItMDQgMTc6NDA6MjgiLCJwaWN0dXJlQWRzIjoiaHR0cDovL3d3dy5iYWlkdS5jb20iLCJyZWNlaXZlVGltZSI6IjIwMTktMTItMDQgMTc6NDA6MjgiLCJzZWxsRml4ZWROdW0iOiIxMzcxNzUwNTIwMyIsInNlbGxOaWNrbmFtZSI6IuWNluWutl9zYl8yIiwic2VydmljZUNoYXJnZSI6IjAuNiIsInRvdGFsUHJpY2UiOiI0IiwidHJhZGVDcmVhdGVUaW1lIjoiMjAxOS0xMi0wNCAxNzo0MDoyOCIsInRyYWRlTnVtIjoiMiIsInRyYWRlUHJpY2UiOiIyIiwidXBkYXRlVGltZSI6IjIwMTktMTItMTcgMTQ6NTk6NTQifSwic2VsbE92ZXJUaW1lIjo2MCwic2lnbiI6IjNhMTRkZWVhMzRiMjI0ZWEwMmRkMDUxNTY1ZTM4ZGZiIiwic3RpbWUiOjE1NzY1NjY5NTI4NzMsInRva2VuIjoiMTExMTExIn0="
     *     },
     *     "sgid": "201912171515480000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/getReceivableInfoData", method = {RequestMethod.POST})
    public JsonResult<Object> getReceivableInfoData(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        try{
            String tempToken = "111111";
            ComponentUtil.redisService.set(tempToken, "4");
            log.info("jsonData:" + requestData.jsonData);
            // 解密
            String data = StringUtil.decoderBase64(requestData.jsonData);
            RequestOrder requestOrder  = JSON.parseObject(data, RequestOrder.class);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkReceivableInfoData(requestOrder);
            token = requestOrder.getToken();
            // 校验ctime
            // 校验sign

            // 策略:查询指定的买家支付超时时间
            StrategyModel buyStrategyQuery = PublicMethod.assembleStrategyQuery(ServerConstant.StrategyEnum.STG_BUY_OVERTIME.getStgType());
            StrategyModel buyStrategyModel = ComponentUtil.strategyService.getStrategyModel(buyStrategyQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            // 策略:查询指定的卖家确认收款超时时间
            StrategyModel sellStrategyQuery = PublicMethod.assembleStrategyQuery(ServerConstant.StrategyEnum.STG_SELL_OVERTIME.getStgType());
            StrategyModel sellStrategyModel = ComponentUtil.strategyService.getStrategyModel(sellStrategyQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            // 获取待付款订单-详情
            OrderModel orderQuery = PublicMethod.assembleReceivableInfoQuery(requestOrder, memberId);
            OrderModel orderModel = ComponentUtil.orderService.getUnpaidOrder(orderQuery);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(buyStrategyModel.getStgValue(), sellStrategyModel.getStgValue(), stime, token, secretKeySign); // buyOvertime+sellOverTime+stime+token+秘钥=sign
            String strData = PublicMethod.assembleUnpaidOrderInfoResult(stime, token, sign, orderModel, Integer.parseInt(buyStrategyModel.getStgValue()), Integer.parseInt(sellStrategyModel.getStgValue()));
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
     * @Description: 获取用户待收款的订单信息-列表
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/od/getReceivableData
     * 请求的属性类:RequestOrder
     * 必填字段:{"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","pageNumber":1,"pageSize":3}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:buyOvertime+sellOverTime+stime+token+秘钥=sign
     * result=={
     *     "errcode": "0",
     *     "message": "success",
     *     "content": {
     *         "jsonData": "eyJjb0xpc3QiOlt7ImFwcGVhbFN0YXR1cyI6MSwiYnV5Tmlja25hbWUiOiLkubDlrrZfc2JfMSIsImNyZWF0ZVRpbWUiOiIyMDE5LTExLTI2IDEzOjUxOjQ2Iiwib3JkZXJObyI6Im9yZGVyX25vX2R6Zl8xIiwib3JkZXJTdGF0dXMiOjEsIm9yZGVyVHJhZGVTdGF0dXMiOjEsInBheVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4IiwicmVjZWl2ZVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4Iiwic2VsbE5pY2tuYW1lIjoi5Y2W5a62X3NiXzEiLCJzZXJ2aWNlQ2hhcmdlIjoiMC4zIiwidG90YWxQcmljZSI6IjEiLCJ0cmFkZUNyZWF0ZVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4IiwidHJhZGVOdW0iOiIxIiwidHJhZGVQcmljZSI6IjEifSx7ImFwcGVhbFN0YXR1cyI6MSwiYnV5Tmlja25hbWUiOiLkubDlrrZfc2JfNSIsImNyZWF0ZVRpbWUiOiIyMDE5LTExLTI2IDEzOjUxOjQ2Iiwib3JkZXJObyI6Im9yZGVyX25vX2R6Zl80Iiwib3JkZXJTdGF0dXMiOjEsIm9yZGVyVHJhZGVTdGF0dXMiOjIsInBheVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4IiwicmVjZWl2ZVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4Iiwic2VsbE5pY2tuYW1lIjoi5Y2W5a62X3NiXzUiLCJzZXJ2aWNlQ2hhcmdlIjoiMC42IiwidG90YWxQcmljZSI6IjgiLCJ0cmFkZUNyZWF0ZVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4IiwidHJhZGVOdW0iOiIyIiwidHJhZGVQcmljZSI6IjQifSx7ImFwcGVhbFN0YXR1cyI6MSwiYnV5Tmlja25hbWUiOiLkubDlrrZfc2JfMiIsImNyZWF0ZVRpbWUiOiIyMDE5LTExLTI2IDEzOjUxOjQ2Iiwib3JkZXJObyI6Im9yZGVyX25vX2R6Zl8yIiwib3JkZXJTdGF0dXMiOjEsIm9yZGVyVHJhZGVTdGF0dXMiOjIsInBheVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4IiwicmVjZWl2ZVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4Iiwic2VsbE5pY2tuYW1lIjoi5Y2W5a62X3NiXzIiLCJzZXJ2aWNlQ2hhcmdlIjoiMC42IiwidG90YWxQcmljZSI6IjQiLCJ0cmFkZUNyZWF0ZVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4IiwidHJhZGVOdW0iOiIyIiwidHJhZGVQcmljZSI6IjIifV0sIm92ZXJ0aW1lIjo2MCwicm93Q291bnQiOjUsInNpZ24iOiJhMTY0NThiZmNmOWI0NjVhNGY0ZDcyMWRmMjhhNDQ2MyIsInN0aW1lIjoxNTc1NDUyNTY3MDI2LCJ0b2tlbiI6IjExMTExMSJ9"
     *     },
     *     "sgid": "201912041742460000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/getReceivableData", method = {RequestMethod.POST})
    public JsonResult<Object> getReceivableData(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        try{
            String tempToken = "111111";
            ComponentUtil.redisService.set(tempToken, "4");
            log.info("jsonData:" + requestData.jsonData);
            // 解密
            String data = StringUtil.decoderBase64(requestData.jsonData);
            RequestOrder requestOrder  = JSON.parseObject(data, RequestOrder.class);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkReceivableData(requestOrder);
            token = requestOrder.getToken();
            // 校验ctime
            // 校验sign

            // 策略:查询指定的买家支付超时时间
            StrategyModel buyStrategyQuery = PublicMethod.assembleStrategyQuery(ServerConstant.StrategyEnum.STG_BUY_OVERTIME.getStgType());
            StrategyModel buyStrategyModel = ComponentUtil.strategyService.getStrategyModel(buyStrategyQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            // 策略:查询指定的卖家确认收款超时时间
            StrategyModel sellStrategyQuery = PublicMethod.assembleStrategyQuery(ServerConstant.StrategyEnum.STG_SELL_OVERTIME.getStgType());
            StrategyModel sellStrategyModel = ComponentUtil.strategyService.getStrategyModel(sellStrategyQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            // 获取待付款订单列表
            OrderModel orderQuery = PublicMethod.assembleReceivableQuery(requestOrder, memberId, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
            List<OrderModel> orderList = ComponentUtil.orderService.getUnpaidOrderList(orderQuery);
            orderQuery.getPage();
            log.info("data :" + orderList.size());
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(buyStrategyModel.getStgValue(), sellStrategyModel.getStgValue(), stime, token, secretKeySign); // buyOvertime+sellOverTime+stime+token+秘钥=sign
            String strData = PublicMethod.assembleUnpaidOrderResult(stime, token, sign, orderList, Integer.parseInt(buyStrategyModel.getStgValue()), Integer.parseInt(sellStrategyModel.getStgValue()), orderQuery.getRowCount());
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
     * @Description: 获取用户已完成的订单信息-详情
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/od/getFinishInfoData
     * 请求的属性类:RequestOrder
     * 必填字段:{"orderNo":"order_no_dzf_2","orderType":1,"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:orderNo+orderType+ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     * result=={
     *     "errcode": 0,
     *     "message": "success",
     *     "content": {
     *         "jsonData": "eyJjb09yZGVyIjp7ImFwcGVhbFN0YXR1cyI6MSwiYnV5Tmlja25hbWUiOiLkubDlrrZfc2JfOCIsImNyZWF0ZVRpbWUiOiIyMDE5LTExLTI2IDEzOjUxOjQ2Iiwib3JkZXJObyI6Im9yZGVyX25vX3l3Y18xIiwib3JkZXJTdGF0dXMiOjMsIm9yZGVyVHJhZGVTdGF0dXMiOjMsIm9yZGVyVHlwZSI6MSwicGF5VGltZSI6IjIwMTktMTItMDQgMTc6NDA6MjgiLCJwaWN0dXJlQWRzIjoiaHR0cDovL3d3dy5iYWlkdS5jb20iLCJyZWNlaXZlVGltZSI6IjIwMTktMTItMDQgMTc6NDA6MjgiLCJzZWxsRml4ZWROdW0iOiIxMzcxNzUwNTIwMyIsInNlbGxOaWNrbmFtZSI6IuWNluWutl9zYl84Iiwic2VydmljZUNoYXJnZSI6IjAuMyIsInRvdGFsUHJpY2UiOiIxIiwidHJhZGVDcmVhdGVUaW1lIjoiMjAxOS0xMi0wNCAxNzo0MDoyOCIsInRyYWRlTnVtIjoiMSIsInRyYWRlUHJpY2UiOiIxIiwidXBkYXRlVGltZSI6IjIwMTktMTItMTcgMTY6NDk6NDYifSwic2lnbiI6ImUwOTA1ZmRkN2E4M2IyMTBiZDMxNjhiZTU5M2Y1MzVhIiwic3RpbWUiOjE1NzY1NzI4MTk0NTQsInRva2VuIjoiMTExMTExIn0="
     *     },
     *     "sgid": "201912171653340000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/getFinishInfoData", method = {RequestMethod.POST})
    public JsonResult<Object> getFinishInfoData(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        try{
            String tempToken = "111111";
            ComponentUtil.redisService.set(tempToken, "3");
            log.info("jsonData:" + requestData.jsonData);
            // 解密
            String data = StringUtil.decoderBase64(requestData.jsonData);
            RequestOrder requestOrder  = JSON.parseObject(data, RequestOrder.class);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkFinishInfoData(requestOrder);
            token = requestOrder.getToken();
            // 校验ctime
            // 校验sign

            // 获取已完成订单详情
            OrderModel orderQuery = PublicMethod.assembleFinishInfoQuery(requestOrder, memberId);
            OrderModel orderModel = ComponentUtil.orderService.getFinishOrder(orderQuery);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
            String strData = PublicMethod.assembleFinishOrderInfoResult(stime, token, sign, orderModel);
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
     * @Description: 获取用户已完成的订单信息-列表
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/od/getFinishData
     * 请求的属性类:RequestOrder
     * 必填字段:{"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","pageNumber":1,"pageSize":3}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     * result=={
     *     "errcode": "0",
     *     "message": "success",
     *     "content": {
     *         "jsonData": "eyJjb0xpc3QiOlt7ImFwcGVhbFN0YXR1cyI6MSwiYnV5Tmlja25hbWUiOiLkubDlrrZfc2JfMTAiLCJjcmVhdGVUaW1lIjoiMjAxOS0xMS0yNiAxMzo1MTo0NiIsIm9yZGVyTm8iOiJvcmRlcl9ub195d2NfMyIsIm9yZGVyU3RhdHVzIjozLCJvcmRlclRyYWRlU3RhdHVzIjozLCJvcmRlclR5cGUiOjIsInBheVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4IiwicmVjZWl2ZVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4Iiwic2VsbE5pY2tuYW1lIjoi5Y2W5a62X3NiXzEwIiwic2VydmljZUNoYXJnZSI6IjAuMyIsInRvdGFsUHJpY2UiOiIxIiwidHJhZGVDcmVhdGVUaW1lIjoiMjAxOS0xMS0yNiAxMzo1MTo0NiIsInRyYWRlTnVtIjoiMSIsInRyYWRlUHJpY2UiOiIxIn0seyJhcHBlYWxTdGF0dXMiOjEsImJ1eU5pY2tuYW1lIjoi5Lmw5a62X3NiXzgiLCJjcmVhdGVUaW1lIjoiMjAxOS0xMS0yNiAxMzo1MTo0NiIsIm9yZGVyTm8iOiJvcmRlcl9ub195d2NfMSIsIm9yZGVyU3RhdHVzIjozLCJvcmRlclRyYWRlU3RhdHVzIjozLCJvcmRlclR5cGUiOjIsInBheVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4IiwicmVjZWl2ZVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4Iiwic2VsbE5pY2tuYW1lIjoi5Y2W5a62X3NiXzgiLCJzZXJ2aWNlQ2hhcmdlIjoiMC4zIiwidG90YWxQcmljZSI6IjEiLCJ0cmFkZUNyZWF0ZVRpbWUiOiIyMDE5LTExLTI2IDEzOjUxOjQ2IiwidHJhZGVOdW0iOiIxIiwidHJhZGVQcmljZSI6IjEifSx7ImFwcGVhbFN0YXR1cyI6MSwiYnV5Tmlja25hbWUiOiLkubDlrrZfc2JfMTQiLCJjcmVhdGVUaW1lIjoiMjAxOS0xMS0yNiAxMzo1MTo0NiIsIm9yZGVyTm8iOiJvcmRlcl9ub195d2NfNyIsIm9yZGVyU3RhdHVzIjozLCJvcmRlclRyYWRlU3RhdHVzIjozLCJvcmRlclR5cGUiOjEsInBheVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4IiwicmVjZWl2ZVRpbWUiOiIyMDE5LTEyLTA0IDE3OjQwOjI4Iiwic2VsbE5pY2tuYW1lIjoi5Y2W5a62X3NiXzE0Iiwic2VydmljZUNoYXJnZSI6IjAuMyIsInRvdGFsUHJpY2UiOiIxIiwidHJhZGVDcmVhdGVUaW1lIjoiMjAxOS0xMS0yNiAxMzo1MTo0NiIsInRyYWRlTnVtIjoiMSIsInRyYWRlUHJpY2UiOiIxIn1dLCJyb3dDb3VudCI6OCwic2lnbiI6IjRmYmY3NTI1ODlkMzkwNGRiOWQ0YmU3MjkzZDE0OTY5Iiwic3RpbWUiOjE1NzU0NjQ1Njk2MDcsInRva2VuIjoiMTExMTExIn0="
     *     },
     *     "sgid": "201912042102450000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/getFinishData", method = {RequestMethod.POST})
    public JsonResult<Object> getFinishData(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        try{
            String tempToken = "111111";
            ComponentUtil.redisService.set(tempToken, "3");
            log.info("jsonData:" + requestData.jsonData);
            // 解密
            String data = StringUtil.decoderBase64(requestData.jsonData);
            RequestOrder requestOrder  = JSON.parseObject(data, RequestOrder.class);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkFinishData(requestOrder);
            token = requestOrder.getToken();
            // 校验ctime
            // 校验sign

            // 获取已完成订单列表
            OrderModel orderQuery = PublicMethod.assembleFinishQuery(requestOrder, memberId);
            List<OrderModel> orderList = ComponentUtil.orderService.getFinishOrderList(orderQuery);
            orderQuery.getPage();
            log.info("data :" + orderList.size());
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
            String strData = PublicMethod.assembleFinishOrderResult(stime, token, sign, orderList, orderQuery.getRowCount());
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
     * @Description: 获取用户已超时的订单信息-详情
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/od/getOverTimeInfoData
     * 请求的属性类:RequestOrder
     * 必填字段:{"orderNo":"order_no_dzf_2","orderType":1,"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:orderNo+orderType+ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     * result=={
     *     "errcode": 0,
     *     "message": "success",
     *     "content": {
     *         "jsonData": "eyJjb09yZGVyIjp7ImFwcGVhbFN0YXR1cyI6MSwiYnV5Tmlja25hbWUiOiLkubDlrrZfc2JfOCIsImNyZWF0ZVRpbWUiOiIyMDE5LTExLTI2IDEzOjUxOjQ2Iiwib3JkZXJObyI6Im9yZGVyX25vX3l3Y18xIiwib3JkZXJTdGF0dXMiOjMsIm9yZGVyVHJhZGVTdGF0dXMiOjMsIm9yZGVyVHlwZSI6MSwicGF5VGltZSI6IjIwMTktMTItMDQgMTc6NDA6MjgiLCJwaWN0dXJlQWRzIjoiaHR0cDovL3d3dy5iYWlkdS5jb20iLCJyZWNlaXZlVGltZSI6IjIwMTktMTItMDQgMTc6NDA6MjgiLCJzZWxsRml4ZWROdW0iOiIxMzcxNzUwNTIwMyIsInNlbGxOaWNrbmFtZSI6IuWNluWutl9zYl84Iiwic2VydmljZUNoYXJnZSI6IjAuMyIsInRvdGFsUHJpY2UiOiIxIiwidHJhZGVDcmVhdGVUaW1lIjoiMjAxOS0xMi0wNCAxNzo0MDoyOCIsInRyYWRlTnVtIjoiMSIsInRyYWRlUHJpY2UiOiIxIiwidXBkYXRlVGltZSI6IjIwMTktMTItMTcgMTY6NDk6NDYifSwic2lnbiI6ImUwOTA1ZmRkN2E4M2IyMTBiZDMxNjhiZTU5M2Y1MzVhIiwic3RpbWUiOjE1NzY1NzI4MTk0NTQsInRva2VuIjoiMTExMTExIn0="
     *     },
     *     "sgid": "201912171653340000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/getOverTimeInfoData", method = {RequestMethod.POST})
    public JsonResult<Object> getOverTimeInfoData(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        try{
            String tempToken = "111111";
            ComponentUtil.redisService.set(tempToken, "3");
            log.info("jsonData:" + requestData.jsonData);
            // 解密
            String data = StringUtil.decoderBase64(requestData.jsonData);
            RequestOrder requestOrder  = JSON.parseObject(data, RequestOrder.class);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkOverTimeInfoData(requestOrder);
            token = requestOrder.getToken();
            // 校验ctime
            // 校验sign

            // 获取已超时订单详情
            OrderModel orderQuery = PublicMethod.assembleOverTimeInfoQuery(requestOrder, memberId);
            OrderModel orderModel = ComponentUtil.orderService.getOverTimeOrder(orderQuery);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
            String strData = PublicMethod.assembleOverTimeInfoResult(stime, token, sign, orderModel);
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
     * @Description: 获取用户已超时的订单信息-列表
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/od/getOverTimeData
     * 请求的属性类:RequestOrder
     * 必填字段:{"agtVer":1,"clientVer":1,"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","pageNumber":1,"pageSize":3}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:stime+token+秘钥=sign
     * result=={
     *     "errcode": 0,
     *     "message": "success",
     *     "content": {
     *         "jsonData": "eyJjb0xpc3QiOlt7ImFwcGVhbFN0YXR1cyI6MSwiYnV5Tmlja25hbWUiOiLkubDlrrZf5pi156ewX3ljcyIsImNyZWF0ZVRpbWUiOiIyMDE5LTExLTA5IDE0OjQ4OjIyIiwib3JkZXJObyI6Im9yZGVyX25vX3ljc18xIiwib3JkZXJTdGF0dXMiOjEsIm9yZGVyVHJhZGVTdGF0dXMiOjEsIm9yZGVyVHlwZSI6MSwicGF5VGltZSI6IjIwMTktMTItMTcgMTg6MDg6NTMiLCJwaWN0dXJlQWRzIjoiaHR0cHM6Ly9waWNzNy5iYWlkdS5jb20vZmVlZC8yMWE0NDYyMzA5Zjc5MDUyODgxMGE1MDY3MzhiMDBjZjdiY2JkNTdkLmpwZWc/dG9rZW49MTNlMWY4OGQ2Nzk2NDM2ZjliZWUwZjc0MGQ4Y2M3YjMmcz0wRTIxRDIwNTVFNzIxMDk0NzQ4NDY4QjcwMzAwQTAwMiIsInJlY2VpdmVUaW1lIjoiMjAxOS0xMi0xNyAxODo1NToyMyIsInNlbGxGaXhlZE51bSI6IjEzNzE3NTA1MjkyIiwic2VsbE5pY2tuYW1lIjoi5Y2W5a62X+aYteensF96YyIsInNlcnZpY2VDaGFyZ2UiOiIwLjM1IiwidG90YWxQcmljZSI6Ijk5IiwidHJhZGVDcmVhdGVUaW1lIjoiMjAxOS0xMS0wOSAxNDo0ODoyMiIsInRyYWRlTnVtIjoiMzMiLCJ0cmFkZVByaWNlIjoiMyIsInVwZGF0ZVRpbWUiOiIyMDE5LTEyLTE3IDIxOjE4OjAxIn0seyJhcHBlYWxTdGF0dXMiOjEsImJ1eU5pY2tuYW1lIjoi5Lmw5a62X+aYteensF96YyIsImNyZWF0ZVRpbWUiOiIyMDE5LTExLTA5IDE0OjQ4OjIyIiwib3JkZXJObyI6Im9yZGVyX25vX3ljc19sc18xIiwib3JkZXJTdGF0dXMiOjEsIm9yZGVyVHJhZGVTdGF0dXMiOjEsIm9yZGVyVHlwZSI6MiwicGF5VGltZSI6IjIwMTktMTItMTcgMTg6MDg6NTMiLCJwaWN0dXJlQWRzIjoiaHR0cHM6Ly9waWNzNy5iYWlkdS5jb20vZmVlZC8yMWE0NDYyMzA5Zjc5MDUyODgxMGE1MDY3MzhiMDBjZjdiY2JkNTdkLmpwZWc/dG9rZW49MTNlMWY4OGQ2Nzk2NDM2ZjliZWUwZjc0MGQ4Y2M3YjMmcz0wRTIxRDIwNTVFNzIxMDk0NzQ4NDY4QjcwMzAwQTAwMiIsInJlY2VpdmVUaW1lIjoiMjAxOS0xMi0xNyAxODo1NToyMyIsInNlbGxGaXhlZE51bSI6IjEzNzE3NTA1MjkyIiwic2VsbE5pY2tuYW1lIjoi5Y2W5a62X+aYteensF95Y3MiLCJzZXJ2aWNlQ2hhcmdlIjoiMC4zNSIsInRvdGFsUHJpY2UiOiI5OSIsInRyYWRlQ3JlYXRlVGltZSI6IjIwMTktMTEtMDkgMTQ6NDg6MjIiLCJ0cmFkZU51bSI6IjMzIiwidHJhZGVQcmljZSI6IjMiLCJ1cGRhdGVUaW1lIjoiMjAxOS0xMi0xMyAxOTo0NjowMSJ9LHsiYXBwZWFsU3RhdHVzIjoxLCJidXlOaWNrbmFtZSI6IuS5sOWutl/mmLXnp7BfMyIsImNyZWF0ZVRpbWUiOiIyMDE5LTExLTAyIDE0OjQ4OjIyIiwib3JkZXJObyI6Im9yZGVyX25vXzIiLCJvcmRlclN0YXR1cyI6MSwib3JkZXJUcmFkZVN0YXR1cyI6MSwib3JkZXJUeXBlIjoxLCJwYXlUaW1lIjoiMjAxOS0xMi0wNCAxNzo0MDoyOCIsInBpY3R1cmVBZHMiOiIiLCJyZWNlaXZlVGltZSI6IjIwMTktMTItMDQgMTc6NDA6MjgiLCJzZWxsRml4ZWROdW0iOiJ6ZmIxMzcxNzUwNTIwNCIsInNlbGxOaWNrbmFtZSI6IuWNluWutl/mmLXnp7BfNCIsInNlcnZpY2VDaGFyZ2UiOiIxLjUiLCJ0b3RhbFByaWNlIjoiNDguNCIsInRyYWRlQ3JlYXRlVGltZSI6IjIwMTktMTEtMDIgMTQ6NDg6MjIiLCJ0cmFkZU51bSI6IjIyIiwidHJhZGVQcmljZSI6IjIuMiIsInVwZGF0ZVRpbWUiOiIyMDE5LTEyLTEwIDExOjMwOjExIn1dLCJyb3dDb3VudCI6Mywic2lnbiI6IjI0NGZlNmQ4ODY0YTRiOGY5NzNjOWU1YWQ4ZjE5NzI3Iiwic3RpbWUiOjE1NzY1ODkxODQzODgsInRva2VuIjoiMTExMTExIn0="
     *     },
     *     "sgid": "201912172126240000001",
     *     "cgid": ""
     * }
     */
    @RequestMapping(value = "/getOverTimeData", method = {RequestMethod.POST})
    public JsonResult<Object> getOverTimeData(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestEncryptionJson requestData) throws Exception{
        String sgid = ComponentUtil.redisIdService.getSgid();
        String cgid = "";
        String token;
        try{
            String tempToken = "111111";
            ComponentUtil.redisService.set(tempToken, "3");
            log.info("jsonData:" + requestData.jsonData);
            // 解密
            String data = StringUtil.decoderBase64(requestData.jsonData);
            RequestOrder requestOrder  = JSON.parseObject(data, RequestOrder.class);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkOverTimeData(requestOrder);
            token = requestOrder.getToken();
            // 校验ctime
            // 校验sign

            // 获取已超时订单列表
            OrderModel orderQuery = PublicMethod.assembleOverTimeQuery(requestOrder, memberId);
            List<OrderModel> orderList = ComponentUtil.orderService.getOverTimeOrderList(orderQuery);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(stime, token, secretKeySign); // stime+token+秘钥=sign
            String strData = PublicMethod.assembleOverTimeOrderResult(stime, token, sign, orderList, orderQuery.getRowCount());
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
