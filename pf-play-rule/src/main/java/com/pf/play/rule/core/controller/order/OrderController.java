package com.pf.play.rule.core.controller.order;

import com.alibaba.fastjson.JSON;
import com.pf.play.common.utils.JsonResult;
import com.pf.play.common.utils.SignUtil;
import com.pf.play.common.utils.StringUtil;
import com.pf.play.model.protocol.request.RequestEncryptionJson;
import com.pf.play.model.protocol.request.consumer.RequestConsumer;
import com.pf.play.model.protocol.request.order.RequestOrder;
import com.pf.play.model.protocol.response.ResponseEncryptionJson;
import com.pf.play.rule.PublicMethod;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.core.common.utils.constant.PfErrorCode;
import com.pf.play.rule.core.common.utils.constant.ServerConstant;
import com.pf.play.rule.core.controller.price.VirtualCoinPriceController;
import com.pf.play.rule.core.model.order.OrderModel;
import com.pf.play.rule.core.model.price.VirtualCoinPriceDto;
import com.pf.play.rule.core.model.price.VirtualCoinPriceModel;
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
     * 请求的属性类:RequestConsumer
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
            log.error(String.format("this OrderController.getData() is error , the errorCode=%s and cgid=%s and sgid=%s and all data=%s!", code, "null", "null", request.getQueryString()));
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



    /**
     * @Description: 发布订单号/我要买QHR
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/25 22:58
     * local:http://localhost:8082/play/od/getData
     * 请求的属性类:RequestConsumer
     * 必填字段:{"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111","sortType":"1", "pageNumber":1,"pageSize":3}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
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
            // #已经写到这里
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
            log.error(String.format("this OrderController.getData() is error , the errorCode=%s and cgid=%s and sgid=%s and all data=%s!", code, "null", "null", request.getQueryString()));
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
