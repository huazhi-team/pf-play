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
     * @Description: 获取订单信息
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/od/getData
     * 请求的属性类:RequestOrder
     * 必填字段:{"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","sortType":"1", "pageNumber":1,"pageSize":3}
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
     * 必填字段:{"tradeNum":"10","tradePrice":"1.92","ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
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
     * @Description: 获取买入订单信息
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/od/getBuyData
     * 请求的属性类:RequestOrder
     * 必填字段:{"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","pageNumber":1,"pageSize":3}
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
     * @Description: 获取已取消的订单信息
     * <p>查询用户已经取消的订单信息</p>
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/od/getCancelData
     * 请求的属性类:RequestOrder
     * 必填字段:{"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","pageNumber":1,"pageSize":3}
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
            OrderModel orderQuery = PublicMethod.assembleCancelOrderQuery(memberId, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
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
     * 必填字段:{"orderNo":"1111","ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
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
     * @Description: 获取用户待付款的订单信息
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/od/getUnpaidData
     * 请求的属性类:RequestOrder
     * 必填字段:{"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","pageNumber":1,"pageSize":3}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:超时时间+stime+token+秘钥=sign
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
            StrategyModel strategyQuery = PublicMethod.assembleStrategyQuery(ServerConstant.StrategyEnum.STG_BUY_OVERTIME.getStgType());
            StrategyModel strategyModel = ComponentUtil.strategyService.getStrategyModel(strategyQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            // 获取待付款订单列表
            OrderModel orderQuery = PublicMethod.assembleUnpaidOrderQuery(requestOrder, memberId, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
            List<OrderModel> orderList = ComponentUtil.orderService.getUnpaidOrderList(orderQuery);
            orderQuery.getPage();
            log.info("data :" + orderList.size());
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(strategyModel.getStgValue(), stime, token, secretKeySign); // 超时时间+stime+token+秘钥=sign
            String strData = PublicMethod.assembleUnpaidOrderResult(stime, token, sign, orderList, Integer.parseInt(strategyModel.getStgValue()), orderQuery.getRowCount());
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
     * @Description: 获取用户待收款的订单信息
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/od/getReceivableData
     * 请求的属性类:RequestOrder
     * 必填字段:{"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","pageNumber":1,"pageSize":3}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:超时时间+stime+token+秘钥=sign
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

            // 策略:查询指定的卖家确认收款超时时间
            StrategyModel strategyQuery = PublicMethod.assembleStrategyQuery(ServerConstant.StrategyEnum.STG_SELL_OVERTIME.getStgType());
            StrategyModel strategyModel = ComponentUtil.strategyService.getStrategyModel(strategyQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            // 获取待付款订单列表
            OrderModel orderQuery = PublicMethod.assembleReceivableQuery(requestOrder, memberId, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
            List<OrderModel> orderList = ComponentUtil.orderService.getUnpaidOrderList(orderQuery);
            orderQuery.getPage();
            log.info("data :" + orderList.size());
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(strategyModel.getStgValue(), stime, token, secretKeySign); // 超时时间+stime+token+秘钥=sign
            String strData = PublicMethod.assembleUnpaidOrderResult(stime, token, sign, orderList, Integer.parseInt(strategyModel.getStgValue()), orderQuery.getRowCount());
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
     * @Description: 获取用户已完成的订单信息
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/od/getFinishData
     * 请求的属性类:RequestOrder
     * 必填字段:{"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","pageNumber":1,"pageSize":3}
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



}
