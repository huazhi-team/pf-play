package com.pf.play.rule.core.controller;

import com.pf.play.common.utils.JsonResult;
import com.pf.play.model.protocol.request.CommonReq;
import com.pf.play.rule.core.model.CodeInfoModel;
import com.pf.play.rule.util.ComponentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

import static java.lang.System.out;

/**
 * @author df
 * @Description:接收sp增值请求的Controller层
 * @create 2019-05-22 16:26
 * http://localhost:8082/sp/pay_way
 **/
@RestController
@RequestMapping("/sp")
public class SpCodeController {
    private static Logger log = LoggerFactory.getLogger(SpCodeController.class);

    /**
     * @Description: TODO(接收增值的请求的接口)
     * @author df
     * @param model
     * @create 16:36 2019/5/22
     * testUrl:
     **/
    @GetMapping("/pay_way")
    public JsonResult<Object> actionPay(HttpServletRequest request, HttpServletResponse response, CommonReq commonReq){
        try{
            log.info("----------:进来啦!");
            JsonResult.successResult(null);
        }catch (Exception e){
            return JsonResult.failedResult("wrong for data!",1+"");
        }
        return null;
    }


    /**
     * @Description: TODO(接收增值的请求的接口)
     * @author df
     * @param model
     * @create 16:36 2019/5/22
     * testUrl:
     **/
    @RequestMapping(value = "/GetPostRequest", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult<Object> actionDataPay(HttpServletRequest request, HttpServletResponse response,@RequestBody CodeInfoModel model){
//        try{
//            log.info("----------:进来啦!");
//            List<AppModel> dataList = ComponentUtil.appService.getListApp(new AppModel());
//
//            JsonResult.successResult(null);
//        }catch (Exception e){
//            return JsonResult.failedResult("wrong for data!",1+"");
//        }
        return null;
    }

    public static void main(String[] args) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        out.println(uuid);
    }
}
