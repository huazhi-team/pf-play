package com.pf.play.rule.core.common.utils.constant;

/**
 * @Description 支付业务的缓存key
 * @Author yoko
 * @Date 2019/11/21 16:03
 * @Version 1.0
 */
public interface PfCacheKey {

    /**
     * 更新支付密码：验证码
     */
    String MEMBERID_UP_PAY_CODE = "-1";

    /**
     * 用户固定账号
     */
    String CONSUMER_FIXED = "-2";

    /**
     * 策略
     */
    String STRATEGY = "-3";

    /**
     * 虚拟币每天兑换的价格：今天、昨天
     */
    String VIRTUALCOIN_PRICE_LIST = "-4";

    /**
     * 虚拟币每天兑换的价格：今天
     */
    String VIRTUALCOIN_PRICE = "-5";

}
