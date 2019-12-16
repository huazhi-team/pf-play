package com.pf.play.rule.core.common.exception;

import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.ErrorCode;
import com.pf.play.rule.core.common.utils.constant.PfErrorCode;
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
        String code = ""; // 返回给客户端的状态码
        String message = ""; // 返回给客户端的错误信息
        String dbCode = ""; // 插入数据库的状态码
        String dbMessage = ""; //插入数据库的错误信息
        if (e instanceof ServiceException){
            if (!StringUtils.isBlank(((ServiceException) e).getCode())){
                dbCode = ((ServiceException) e).getCode();
                code = ErrorCode.ERROR_CONSTANT.DEFAULT_SERVICE_EXCEPTION_ERROR_CODE;
                message = e.getMessage();
            }else {
                code = ErrorCode.ERROR_CONSTANT.DEFAULT_SERVICE_EXCEPTION_ERROR_CODE;
                message = ErrorCode.ERROR_CONSTANT.DEFAULT_SERVICE_EXCEPTION_ERROR_MESSAGE;
            }
        }else {
            code = ErrorCode.ERROR_CONSTANT.DEFAULT_EXCEPTION_ERROR_CODE;
            message = ErrorCode.ERROR_CONSTANT.DEFAULT_EXCEPTION_ERROR_MESSAGE;
        }
        // 获取录入数据库的错误信息
        if (!StringUtils.isBlank(dbCode)){
            dbMessage = getErrorDbDesc(dbCode);
        }
        map.put("code",code);
        map.put("message",message);
        map.put("dbCode", dbCode);
        map.put("dbMessage", dbMessage);
        return map;
    }

    /**
     * @Description: 通过枚举循环获取要插入数据库的错误信息
     * @param code - 错误码
     * @return String - 要录入数据库的错误信息
     * @author yoko
     * @date 2019/12/16 18:03
    */
    public static String getErrorDbDesc(String code){
        String dbDesc = "";
        // 错误信息-A
        for (ErrorCode.ENUM_ERROR enums : ErrorCode.ENUM_ERROR.values()){
            if (enums.geteCode().equals(code)){
                dbDesc = enums.getDbDesc();
                break;
            }
        }

        // 错误信息-B
        if (StringUtils.isBlank(dbDesc)){
            for (PfErrorCode.ENUM_ERROR enums : PfErrorCode.ENUM_ERROR.values()){
                if (enums.geteCode().equals(code)){
                    dbDesc = enums.getDbDesc();
                    break;
                }
            }
        }
        return dbDesc;
    }
}
