package com.pf.play.rule.core.common.utils.constant;

/**
 * @author df
 * @Description:服务端常量
 * @create 2018-07-24 16:05
 **/
public class ServerConstant {

    /**
     * 公共，公用的常量
     */
    public final class PUBLIC_CONSTANT{
        /**
         *值是否等于0的判断条件
         */
        public static final int SIZE_VALUE_ZERO = 0;

        /**
         * 值是否等于1的判断条件
         */
        public static final int SIZE_VALUE_ONE = 1;

        /**
         * 值是否等于2的判断条件
         */
        public static final int SIZE_VALUE_TWO = 2;

        /**
         * 字符串值等于1
         */
        public static final String STR_VALUE_ONE = "1";

        /**
         * token计算标识
         */
        public static final String TAG_HZ = "HZ";

        /**
         * 是否需要邮件提醒：1不需要邮件提醒，2需要邮件提醒
         * 这里2表示需要邮件提醒
         */
        public static final int MAIL_REMIND_YES = 2;

        /**
         * 钱包地址：前缀标签
         */
        public static final String CURRENCY_TAG = "gd";

        /**
         * 锁redis的key的前缀标签
         */
        public static final String REDIS_LOCK_TAG = "lock";


    }





    /**
     * 所有API源的标识类型
     */
    public enum ApiSourceTypeEnum{
        SRE_ZY("SRE_ZY","源:自有的广告API"),
        SRE_TS("SRE_TS","源:探索的广告API"),
        SRE_BD("SRE_BD","源:百度的广告API"),
        SRE_YM("SRE_YM","源:友盟的广告API"),
        ;
        private String sourceType;
        private String sourceTypeMsg;

        private ApiSourceTypeEnum(String sourceType, String sourceTypeMsg) {
            this.sourceType = sourceType;
            this.sourceTypeMsg = sourceTypeMsg;
        }

        public String getSourceType() {
            return sourceType;
        }

        public void setSourceType(String sourceType) {
            this.sourceType = sourceType;
        }

        public String getSourceTypeMsg() {
            return sourceTypeMsg;
        }

        public void setSourceTypeMsg(String sourceTypeMsg) {
            this.sourceTypeMsg = sourceTypeMsg;
        }
    }







}
