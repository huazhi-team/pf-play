package com.pf.play.rule.core.common.exception;

import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/25 10:54
 * @Version 1.0
 */
public class ExceptionMethod {
    public  static Map<String,String> getException(Exception e){
        Map<String,String>  map   =  new HashMap<>();
        map.put("code","");
        map.put("message","");
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
        map.put("code",code);
        map.put("message",message);
        return map;
    }
}
