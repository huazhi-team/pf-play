package com.pf.play.rule.core.controller.price;

import com.alibaba.fastjson.JSON;
import com.pf.play.common.utils.JsonResult;
import com.pf.play.common.utils.SignUtil;
import com.pf.play.common.utils.StringUtil;
import com.pf.play.model.protocol.request.RequestEncryptionJson;
import com.pf.play.model.protocol.request.consumer.RequestConsumer;
import com.pf.play.model.protocol.response.ResponseEncryptionJson;
import com.pf.play.rule.PublicMethod;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.core.common.utils.constant.PfErrorCode;
import com.pf.play.rule.core.common.utils.constant.ServerConstant;
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
 * @Description 虚拟币每天兑换的价格的Controller层
 * @Author yoko
 * @Date 2019/11/21 22:20
 * @Version 1.0
 */
@RestController
@RequestMapping("/play/pe")
public class VirtualCoinPriceController {
    private static Logger log = LoggerFactory.getLogger(VirtualCoinPriceController.class);

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
     * @Description: 获取虚拟币每天兑换的价格
     * @param request
     * @param response
     * @return com.gd.chain.common.utils.JsonResult<java.lang.Object>
     * @author yoko
     * @date 2019/11/21 22:58
     * local:http://localhost:8082/play/pe/getData
     * 请求的属性类:RequestConsumer
     * 必填字段:{"ctime":201911071802959,"cctime":201911071802959,"sign":"abcdefg","token":"111111"}
     * 客户端加密字段:ctime+cctime+token+秘钥=sign
     * 服务端加密字段:maxPrice+minPrice+stime+token+秘钥=sign
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
            RequestConsumer requestConsumer  = JSON.parseObject(data, RequestConsumer.class);
            // check校验数据、校验用户是否登录、获得用户ID
            long memberId = PublicMethod.checkGetVirtualCoinPriceData(requestConsumer);
            token = requestConsumer.getToken();
            // 校验ctime
            // 校验sign

            // 虚拟币每天兑换的价格
            VirtualCoinPriceModel virtualCoinPriceQuery = PublicMethod.assembleVirtualCoinPriceQuery();
            List<VirtualCoinPriceModel> virtualCoinPriceList =  ComponentUtil.virtualCoinPriceService.getVirtualCoinPriceList(virtualCoinPriceQuery, ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
            if (virtualCoinPriceList.size() <= ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
                throw new ServiceException(PfErrorCode.ENUM_ERROR.P00003.geteCode(), PfErrorCode.ENUM_ERROR.P00003.geteDesc());
            }
            VirtualCoinPriceDto virtualCoinPriceDto = PublicMethod.assembleVirtualCoinPriceDto(virtualCoinPriceList);
            // 组装返回客户端的数据
            long stime = System.currentTimeMillis();
            String sign = SignUtil.getSgin(virtualCoinPriceDto.getMaxPrice(), virtualCoinPriceDto.getMinPrice(), stime, token, secretKeySign); //maxPrice+minPrice+stime+token+秘钥=sign
            String strData = PublicMethod.assembleDayPriceResult(stime, token, sign, virtualCoinPriceDto);
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
            log.error(String.format("this VirtualCoinPriceController.getData() is error , the errorCode=%s and cgid=%s and sgid=%s and all data=%s!", code, "null", "null", request.getQueryString()));
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