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
         * 值是否等于3的判断条件
         */
        public static final int SIZE_VALUE_THREE = 3;

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


        /**
         * 运行计算状态：：0初始化，1锁定，2计算失败，3计算成功，4扣量
         * 这里3表示成功
         */
        public static final int RUN_STATUS_THREE = 3;

        /**
         * 跑数据被执行了5次为上限
         */
        public static final int RUN_NUM_FIVE = 5;


    }





    /**
     * 策略的枚举
     * 策略类型：1表示成交量虚假数据开关，2表示交易所时间控制，3买家付款超时时间，4卖家确认收款超时时间，5阿里支付默认订单金额，6手续费分出给用户的比例
     * 策略整形值:当策略类型等于1时（此字段值1表示虚假数据处于关闭，等于2表示开启虚假数据），等于2时（此字段值1表示双休日不交易，2表示交易）
     * 策略值：字段stg_type等于1，字段stg_num_value等于2时，则根据本字段的数据乘以倍数，等于2，表示交易时间的时间段
     */
    public enum StrategyEnum{
        STG_DATA_CLOSE(1, 1, ""),
        STG_DATA_OPEN(1, 2, ""),
        STG_TRADE_TIME_WEEKEND_CLOSE(2, 1, ""),
        STG_TRADE_TIME_WEEKEND_OPEN(2, 2, ""),
        STG_BUY_OVERTIME(3, 0, ""),
        STG_SELL_OVERTIME(4, 0, ""),
        STG_ALIPAY_MONEY(5, 0, ""),
        STG_SERVICE_CHARGE_RATIO(6, 0, ""),
        ;
        private int stgType;
        private int stgNumValue;
        private String stgValue;

        private StrategyEnum(int stgType, int stgNumValue, String stgValue) {
            this.stgType = stgType;
            this.stgNumValue = stgNumValue;
            this.stgValue = stgValue;
        }

        public int getStgType() {
            return stgType;
        }

        public void setStgType(int stgType) {
            this.stgType = stgType;
        }

        public int getStgNumValue() {
            return stgNumValue;
        }

        public void setStgNumValue(int stgNumValue) {
            this.stgNumValue = stgNumValue;
        }

        public String getStgValue() {
            return stgValue;
        }

        public void setStgValue(String stgValue) {
            this.stgValue = stgValue;
        }
    }

    /**
     * @Description: 订单交易流水的交易状态
     * <p>
     *     交易状态：1超时，2正常进行中，3问题申诉，4确认已付款（买家等待），5确认已收款（卖家确认）
     * </p>
     * @author yoko
     * @date 2019/12/3 16:16
    */
    public enum TradeStatusEnum{
        OVERTTIME(1, "超时"),
        ACTION(2, "正常进行中"),
        QUESTION(3, "问题申诉"),
        PAY(4, "确认已付款"),
        MAKE_COLLECTIONS(5, "确认已收款"),
        ;
        private int type;
        private String desc;

        private TradeStatusEnum(int type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    /**
     * @Description: 违约类型：1买家未付款（未在规定时间内），2卖家未确认收款（未在规定时间内），3被人投诉成功，4投诉失败
     * @author yoko
     * @date 2019/12/10 9:54
    */
    public enum ViolateTypeEnum{
        BUYER_UNPAID(1, "买家未付款（未在规定时间内）"),
        SELL_RECEIVABLE(2, "卖家未确认收款（未在规定时间内）"),
        SUCCESS_APPEAL(3, "被人投诉成功"),
        FAIL_APPEAL(4, "投诉失败"),
        ;
        private int type;
        private String desc;

        private ViolateTypeEnum(int type, String desc){
            this.type = type;
            this.desc = desc;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }


    /**
     * @Description: 服务的所有接口说明的枚举
     * @author yoko
     * @date 2019/12/10 9:54
     */
    public enum InterfaceEnum{
        APPEAL_GETACTIVEDATA(1, "AppealController.getActiveData", "获取我的申诉（主动发起的申诉）-列表"),
        APPEAL_GETPASSIVEDATA(2, "AppealController.getPassiveData", "获取被申诉（被他人申诉）-列表"),
        APPEAL_UPACTIVE(3, "AppealController.upActive", "更新我的申诉的数据"),
        APPEAL_UPPASSIVE(4, "AppealController.upPassive", "更新被申诉的数据"),
        APPEAL_ADDDATA(5, "AppealController.addData", "添加申诉的数据"),
        APPEAL_GETINFODATA(6, "AppealController.getInfoData", "获取我的申诉或者被申诉的数据-根据ID查询申诉的详情"),

        CODE_GETCD(7, "CodeController.getCd", "获取验证码-修改支付密码时"),

        CONSUMER_UPFIRSTPAYCODE(8, "ConsumerController.upFirstPayCode", "用户更新设置支付密码-第一次设置密码"),
        CONSUMER_UPPAYCODE(9, "ConsumerController.upPayCode", "用户更新设置支付密码"),
        CONSUMER_GETFIXED(10, "ConsumerController.getFixed", "查询用户固定账号/支付宝信息"),
        CONSUMER_ADDFIXED(11, "ConsumerController.addFixed", "用户添加固定账号/支付宝信息"),
        CONSUMER_UPFIXED(12, "ConsumerController.upFixed", "更新添加固定账号/支付宝信息"),
        CONSUMER_GETRATIO(13, "ConsumerController.getRatio", "获取用户手续费百分比的信息"),
        CONSUMER_GETBASIC(14, "ConsumerController.getBasic", "获取用户的基本信息"),

        ORDER_GETDATA(15, "OrderController.getData", "获取订单信息-列表"),
        ORDER_ADDDATA(16, "OrderController.addData", "发布订单号/我要买QHR"),
        ORDER_GETINFODATA(17, "OrderController.getInfoData", "获取订单详情"),
        ORDER_GETBUYDATA(18, "OrderController.getBuyData", "获取买入订单信息-列表"),
        ORDER_GETCANCELDATA(19, "OrderController.getCancelData", "获取已取消的订单信息-列表"),
        ORDER_UPCANCELDATA(20, "OrderController.upCancelData", "购买的订单：取消"),
        ORDER_GETUNPAIDINFODATA(21, "OrderController.getUnpaidInfoData", "获取用户待付款的订单信息-详情"),
        ORDER_GETUNPAIDDATA(22, "OrderController.getUnpaidData", "获取用户待付款的订单信息-列表"),
        ORDER_GETRECEIVABLEINFODATA(23, "OrderController.getReceivableInfoData", "获取用户待收款的订单信息-详情"),
        ORDER_GETRECEIVABLEDATA(24, "OrderController.getReceivableData", "获取用户待收款的订单信息-列表"),
        ORDER_GETFINISHINFODATA(25, "OrderController.getFinishInfoData", "获取用户已完成的订单信息-详情"),
        ORDER_GETFINISHDATA(26, "OrderController.getFinishData", "获取用户已完成的订单信息-列表"),
        ORDER_GETOVERTIMEINFODATA(27, "OrderController.getOverTimeInfoData", "获取用户已超时的订单信息-详情"),
        ORDER_GETOVERTIMEDATA(28, "OrderController.getOverTimeData", "获取用户已超时的订单信息-列表"),

        VIRTUAL_GETDATA(29, "VirtualCoinPriceController.getData", "获取虚拟币每天兑换的价格"),

        TRADE_GETTRADERULE(30, "TradeController.getTradeRule", "交易数据展现-规则"),
        TRADE_GETTRADETIME(31, "TradeController.getTradeTime", "获取交易所的开市时间"),
        TRADE_GETTRADEDATA(32, "TradeController.getTradeData", "获取交易所的当前买量、今日成交量"),
        TRADE_ADDDATA(33, "TradeController.addData", "卖家卖给买家/也就是添加订单交易流水"),
        TRADE_CONFIRMPAY(34, "TradeController.confirmPay", "买家确认支付"),
        TRADE_CONFIRMRPT(35, "TradeController.confirmRpt", "卖家确认已收款"),

        ALIPAY_SENDALI(36, "AlipayController.sendAli", "阿里支付：生成订单码")


        ;
        /**
         * 类型
         */
        private int type;
        /**
         * 接口地址
         */
        private String ads;
        /**
         * 接口描述
         */
        private String desc;

        private InterfaceEnum(int type,String ads, String desc){
            this.type = type;
            this.ads = ads;
            this.desc = desc;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getAds() {
            return ads;
        }

        public void setAds(String ads) {
            this.ads = ads;
        }
    }






}
