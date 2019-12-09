package com.pf.play.rule.core.common.utils;


import com.pf.play.common.utils.SendSms;
import com.pf.play.rule.util.ComponentUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Description 发送验证码包含验证码存储在缓存中
 * @Author yoko
 * @Date 2019/11/21 11:45
 * @Version 1.0
 */
public class SendSmsUtils {
    private static Logger log = LoggerFactory.getLogger(SendSmsUtils.class);

    /**
     * 三分钟
     */
    public static long THREE_MIN = 180;



    /**
     * @Description: 给手机号发送短信验证码
     * @param key - 要缓存的key
     * @param phoneNum - 要发送的手机号
     * @return String - 返回短信验证码；如果为空则说明发送验证码失败
     * @author yoko
     * @date 2019/12/9 21:07
    */
    public static String sendSmsCode(String key, String phoneNum){
        // 生成4位数随机数
        int num = (int)((Math.random()*9+1)*1000);
        String smsCode = String.valueOf(num);
        boolean flag = SendSms.aliSendSms(phoneNum, smsCode);
        if (flag){
            // redis存储验证码
            ComponentUtil.redisService.set(key, smsCode, THREE_MIN);
            return smsCode;
        }else {
            return null;
        }
    }

    /**
     * @Description: 校验验证码是否正确
     * @param key - 要查询的验证码存储的key
     * @param smsCode - 要校验的验证码值
     * @return int - 1表示成功，2表示验证码输入有误，3表示验证码超时
     * @author yoko
     * @date 2019/12/9 21:33
    */
    public static int checkSmsCode(String key, String smsCode){
        String strCache = (String) ComponentUtil.redisService.get(key);
        if (!StringUtils.isBlank(strCache)) {
            if (smsCode.equals(strCache)){
                return 1;
            }else {
                return 2;
            }
        }else {
            return 3;
        }
    }

    public static void main(String[] args) {
        int num = (int)((Math.random()*9+1)*1000);
        log.info("num:" + num);
    }

}
