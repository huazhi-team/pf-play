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

    /**
     * 订单：订单交易、取消需要对其进行锁定
     * 所以在变量名称前加了lock
     */
    String LOCK_ORDER = "-6";

    /**
     * 用户在操作他的钻石，需要对其进行锁定
     * 所以在变量名称前加了lock
     */
    String LOCK_CONSUMER = "-7";

    /**
     * 当前买量、今日成交量
     */
    String ORDER_TRADE_NUM = "-8";

    /**
     * task操作订单交易流水；
     * 如果多台服务器运行，会出现问题，所以在变量名称前加了lock
     */
    String LOCK_TRADE = "-9";

}
